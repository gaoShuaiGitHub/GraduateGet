package com.jiangnan.dal.dataobject;

/**
 * Created by gaoshuai.gs on 2015/5/13.
 *权重列表对象
 */
public class ProbabilityListDO {

    private Integer id;
    private String type;
    private Double university;
    private Integer address;
    private Integer Money;
    private Integer eduBackground;
    private Integer specialty;
    private Integer companySize;
    private Integer postTime;
    private Integer deliveryThree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getUniversity() {
        return university;
    }

    public void setUniversity(Double university) {
        this.university = university;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public Integer getMoney() {
        return Money;
    }

    public void setMoney(Integer money) {
        Money = money;
    }

    public Integer getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(Integer eduBackground) {
        this.eduBackground = eduBackground;
    }

    public Integer getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Integer specialty) {
        this.specialty = specialty;
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public Integer getPostTime() {
        return postTime;
    }

    public void setPostTime(Integer postTime) {
        this.postTime = postTime;
    }

    public Integer getDeliveryThree() {
        return deliveryThree;
    }

    public void setDeliveryThree(Integer deliveryThree) {
        this.deliveryThree = deliveryThree;
    }

    @Override
    public String toString() {
        return "ProbabilityListDO{" +
                "type='" + type + '\'' +
                ", university=" + university +
                ", address=" + address +
                ", Money=" + Money +
                ", eduBackground=" + eduBackground +
                ", specialty=" + specialty +
                ", companySize=" + companySize +
                ", postTime=" + postTime +
                ", deliveryThree=" + deliveryThree +
                '}';
    }
}
