package com.jiangnan.web.common.auth;

import com.alibaba.common.collection.ArrayHashMap;
import com.alibaba.common.collection.ArrayHashSet;
import com.alibaba.common.lang.ArrayUtil;
import com.alibaba.common.lang.MessageUtil;
import com.alibaba.common.lang.StringUtil;
import com.alibaba.common.regexp.PathNameCompiler;
import com.alibaba.common.regexp.Perl5CompilerWrapper;
import com.alibaba.service.GenericService;
import com.alibaba.service.ServiceInitializationException;
import com.alibaba.service.resource.ResourceLoaderService;
import com.alibaba.service.resource.ResourceNotFoundException;
import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.oro.text.regex.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 为页面授权的service。
 *
 * @author Michael Zhou
 */
public class DefaultPageAuthorizationService extends GenericService implements PageAuthorizationService {
    private Map realms;

    /**
     * 初始化service。
     *
     * @throws ServiceInitializationException 如果初始化失败
     */
    @Override
    public void init() throws ServiceInitializationException {
        ResourceLoaderService resourceLoaderService = (ResourceLoaderService) getService(ResourceLoaderService.SERVICE_NAME);
        String[] descriptors = getConfiguration().getStringArray(ACCESS_DESCRIPTORS);

        if (ArrayUtil.isEmpty(descriptors)) {
            descriptors = ArrayUtil.EMPTY_STRING_ARRAY;
        }

        realms = new HashMap();

        for (String descriptor : descriptors) {
            URL descriptorURL;

            try {
                descriptorURL = resourceLoaderService.getResourceAsURL(descriptor);
            } catch (ResourceNotFoundException e) {
                throw new ServiceInitializationException("Access descriptor not found: " + descriptor, e);
            }

            loadDescriptor(descriptorURL);
        }

        super.init();
    }

    /**
     * 装载access描述文件。
     *
     * @param descriptorURL access描述文件的URL
     * @throws ServiceInitializationException 如果初始化失败
     */
    private void loadDescriptor(URL descriptorURL) throws ServiceInitializationException {
        Digester digester = new Digester();
        String descriptor = descriptorURL.toExternalForm();

        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Loading access descriptor: " + descriptor);
        }

        // 创建realm，如果realm已经存在，则直接使用之。
        digester.addFactoryCreate("access", new AbstractObjectCreationFactory() {
            @Override
            public Object createObject(Attributes attributes) throws Exception {
                String realmName = StringUtil.trimToNull(attributes.getValue("realm"));
                AuthRealm realm = (AuthRealm) realms.get(realmName);

                if (realm == null) {
                    realm = new AuthRealm(realmName);
                    realms.put(realmName, realm);
                }

                return realm;
            }
        });

        // 创建grants
        digester.addObjectCreate("access/match", AuthGrants.class);
        digester.addSetProperties("access/match", "target", "target");

        // 创建grant
        digester.addObjectCreate("access/match/grant", AuthGrant.class);
        digester.addSetProperties("access/match/grant", new String[] { "user", "role", "allow", "deny" }, new String[] {
                "user", "role", "allow", "deny" });

        // 调用 grants.addGrant()
        digester.addSetNext("access/match/grant", "addGrant");

        // 调用 realm.addGrants()
        digester.addSetNext("access/match", "addGrants");

        try {
            digester.parse(descriptor);
        } catch (IOException e) {
            throw new ServiceInitializationException("Failed to parse " + descriptor, e);
        } catch (SAXException e) {
            throw new ServiceInitializationException("Failed to parse " + descriptor, e);
        }
    }

    /**
     * 根据规则判断指定的角色是否能访问指定的target。
     *
     * @param target 匹配的目标
     * @param realmName realm的名称
     * @param userId 用户名
     * @param roleNames 角色列表
     * @param action 行为
     * @return true 表明能够访问，false表明不能访问
     */
    public boolean isAllow(String target, String realmName, String userId, String[] roleNames, String action) {
        realmName = StringUtil.trimToNull(realmName);
        userId = StringUtil.defaultIfBlank(userId, ANONYMOUS_USER);

        String roleNameStr = null;

        if (getLogger().isDebugEnabled()) {
            roleNameStr = ArrayUtil.toString(roleNames);
        }

        AuthRealm realm = (AuthRealm) realms.get(realmName);

        // 判断realm。
        if (realm == null) {
            if (getLogger().isDebugEnabled()) {
                logDebug("Access denied: realm not found", target, realmName, userId, roleNameStr, action);
            }

            return false;
        }

        // 找出所有匹配的pattern，按匹配长度倒排序。
        Object[][] grants = realm.getMatchedGrants(target);

        if (grants.length == 0) {
            if (getLogger().isDebugEnabled()) {
                logDebug("Access denied: no patterns matched", target, realmName, userId, roleNameStr, action);
            }

            return false;
        }

        // 按顺序检查授权，直到role或user被allow或deny
        for (Object[] pair : grants) {
            List grantList = ((AuthGrants) pair[0]).getGrants();
            String matchedTarget = (String) pair[2];

            for (Iterator j = grantList.iterator(); j.hasNext();) {
                AuthGrant grant = (AuthGrant) j.next();
                String grantUser = grant.getUser();
                String grantRole = grant.getRole();

                // 判断user或role是否匹配
                boolean userMatch = false;
                boolean roleMatch = false;

                if (grantUser != null) {
                    // 排除匿名用户
                    userMatch = grantUser.equals(MATCH_EVERYTHING) && !ANONYMOUS_USER.equals(userId)
                            || grantUser.equals(userId);
                }

                if (grantRole != null) {
                    // 排除匿名用户
                    roleMatch = grantRole.equals(MATCH_EVERYTHING) && !ANONYMOUS_USER.equals(userId)
                            || ArrayUtil.contains(roleNames, grantRole);
                }

                if (!userMatch && !roleMatch) {
                    continue;
                }

                // 判断action是否匹配
                boolean actionAllowed = false;
                boolean actionDenied = false;

                if (!grant.getAllowedActionSet().isEmpty()) {
                    actionAllowed = grant.getAllowedActionSet().contains(MATCH_EVERYTHING)
                            || grant.getAllowedActionSet().contains(action);
                }

                if (!grant.getDeniedActionSet().isEmpty()) {
                    actionDenied = grant.getDeniedActionSet().contains(MATCH_EVERYTHING)
                            || grant.getDeniedActionSet().contains(action);
                }

                if (!actionAllowed && !actionDenied) {
                    continue;
                }

                boolean allowed = !actionDenied;

                if (getLogger().isDebugEnabled()) {
                    if (allowed) {
                        logDebug("Access permitted: " + grant.toString(matchedTarget), target, realmName, userId,
                                roleNameStr, action);
                    } else {
                        logDebug("Access denied: " + grant.toString(matchedTarget), target, realmName, userId,
                                roleNameStr, action);
                    }
                }

                return allowed;
            }
        }

        // 默认为拒绝
        if (getLogger().isDebugEnabled()) {
            logDebug("Access denied: user or role has not be authorized", target, realmName, userId, roleNameStr,
                    action);

            getLogger()
                    .debug(
                            MessageUtil
                                    .formatMessage(
                                            "Access denied: user or role has not be authorized for target \"{0}\", realm=\"{1}\", user=\"{2}\", roles=\"{3}\"",
                                            target, realmName, userId, ArrayUtil.toString(roleNames)));
        }

        return false;
    }

    /**
     * 记录debug日志。
     */
    private void logDebug(String message, String target, String realmName, String userId, String roleNameStr,
                          String action) {
        getLogger().debug(
                MessageUtil.formatMessage(
                        "{0}: target \"{1}\", realm=\"{2}\", user=\"{3}\", roles=\"{4}\", action=\"{5}\"",
                        new Object[] { message, target, realmName, userId, roleNameStr, action }));
    }

    /**
     * 代表一个auth realm，每个realm包含一套独立的权限设置。
     */
    public static class AuthRealm {
        private String name;
        private List patterns = new ArrayList();

        public AuthRealm(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addGrants(AuthGrants grants) {
            AuthMatchPattern[] pats = grants.getMatchPatterns();

            for (AuthMatchPattern pat : pats) {
                patterns.add(pat);
            }
        }

        /**
         * 匹配所有的pattern，按匹配的长度倒排序。
         *
         * @param target 要匹配的target
         * @return 数组[0=被匹配的grants，1=匹配长度，2=匹配的target]
         */
        public Object[][] getMatchedGrants(String target) {
            List patternList = new ArrayList(patterns.size());
            PatternMatcher matcher = new Perl5Matcher();

            // 匹配所有，注意，这里按倒序匹配，这样长度相同的匹配，以后面的为准。
            for (ListIterator i = patterns.listIterator(patterns.size()); i.hasPrevious();) {
                AuthMatchPattern pattern = (AuthMatchPattern) i.previous();

                if (matcher.matchesPrefix(target, pattern.getTargetPattern())) {
                    MatchResult result = matcher.getMatch();

                    patternList.add(new Object[] { pattern, new Integer(result.length()), pattern.getTarget() });
                }
            }

            if (patternList.isEmpty()) {
                return new Object[0][];
            }

            // 按匹配长度倒排序，注意，这是稳定排序
            Collections.sort(patternList, new Comparator() {
                public int compare(Object o1, Object o2) {
                    Object[] pair1 = (Object[]) o1;
                    Object[] pair2 = (Object[]) o2;

                    return ((Integer) pair2[1]).compareTo((Integer) pair1[1]);
                }
            });

            // 除去重复的匹配
            Map grantsSet = new ArrayHashMap(patternList.size());

            for (Iterator i = patternList.iterator(); i.hasNext();) {
                Object[] pair = (Object[]) i.next();
                AuthMatchPattern pattern = (AuthMatchPattern) pair[0];
                AuthGrants grants = pattern.getGrants();

                pair[0] = grants;

                if (!grantsSet.containsKey(grants)) {
                    grantsSet.put(grants, pair);
                }
            }

            return (Object[][]) grantsSet.values().toArray(new Object[grantsSet.size()][]);
        }
    }

    /**
     * 代表一个用来匹配的pattern。
     */
    public static class AuthMatchPattern {
        private String target;
        private Pattern targetPattern;
        private AuthGrants grants;

        public AuthMatchPattern(String target, AuthGrants grants) {
            this.grants = grants;
            this.target = StringUtil.trimToEmpty(target);

            // 对于相对路径，自动在前面加上/**，表示从头部开始匹配。
            if (!this.target.startsWith("/")) {
                this.target = "/**/" + this.target;
            }

            try {
                this.targetPattern = new PathNameCompiler().compile(this.target, Perl5CompilerWrapper.READ_ONLY_MASK);
            } catch (MalformedPatternException e) {
                IllegalArgumentException iae = new IllegalArgumentException("Invalid target pattern: "
                        + this.targetPattern);

                iae.initCause(e);
                throw iae;
            }
        }

        public String getTarget() {
            return target;
        }

        public Pattern getTargetPattern() {
            return targetPattern;
        }

        public AuthGrants getGrants() {
            return grants;
        }

        @Override
        public String toString() {
            return "Pattern{target=" + target + "}";
        }
    }

    /**
     * 代表一组授权的集合。
     */
    public static class AuthGrants {
        private AuthMatchPattern[] patterns;
        private List grants = new ArrayList();

        public void addGrant(AuthGrant grant) {
            if (grant.getRole() != null || grant.getUser() != null) {
                grants.add(grant);
            }
        }

        public List getGrants() {
            return grants;
        }

        public AuthMatchPattern[] getMatchPatterns() {
            return patterns;
        }

        public void setTarget(String target) {
            String[] targets = StringUtil.split(target, ", ");

            if (ArrayUtil.isNotEmpty(targets)) {
                patterns = new AuthMatchPattern[targets.length];

                for (int i = 0; i < targets.length; i++) {
                    patterns[i] = new AuthMatchPattern(targets[i], this);
                }
            }
        }
    }

    /**
     * 代表一个授权，可以对role和user进行授权。
     */
    public static class AuthGrant {
        private Set allowedActionSet = new ArrayHashSet(4);
        private Set deniedActionSet = new ArrayHashSet(4);
        private String role;
        private String user;

        public Set getAllowedActionSet() {
            return allowedActionSet;
        }

        public Set getDeniedActionSet() {
            return deniedActionSet;
        }

        public String getRole() {
            return role;
        }

        public String getUser() {
            return user;
        }

        public void setAllow(String allow) {
            String[] allows = StringUtil.split(allow, ", ");

            for (String allow2 : allows) {
                allowedActionSet.add(allow2);
            }

            if (allowedActionSet.size() > 1 && allowedActionSet.contains(MATCH_EVERYTHING)) {
                allowedActionSet.clear();
                allowedActionSet.add(MATCH_EVERYTHING);
            }
        }

        public void setDeny(String deny) {
            String[] denies = StringUtil.split(deny, ", ");

            for (String denie : denies) {
                deniedActionSet.add(denie);
            }

            if (deniedActionSet.size() > 1 && deniedActionSet.contains(MATCH_EVERYTHING)) {
                deniedActionSet.clear();
                deniedActionSet.add(MATCH_EVERYTHING);
            }
        }

        public void setRole(String role) {
            this.role = StringUtil.trimToNull(role);
        }

        public void setUser(String user) {
            this.user = StringUtil.trimToNull(user);
        }

        @Override
        public String toString() {
            return toString(null);
        }

        public String toString(String matchedTarget) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("Grant{");

            if (matchedTarget != null) {
                buffer.append("target[").append(matchedTarget).append("], ");
            }

            if (user != null && role == null) {
                buffer.append("user[").append(user).append("]");
            } else if (user == null && role != null) {
                buffer.append("role[").append(role).append("]");
            } else if (user != null && role != null) {
                buffer.append("user[").append(user).append("], role[").append(role).append("]");
            }

            buffer.append(", allow").append(allowedActionSet);
            buffer.append(", deny").append(deniedActionSet);

            buffer.append("}");

            return buffer.toString();
        }
    }
}
