package com.jiangnan.dal.dataobject;

/**
 * Created by gaoshuai.gs on 2015/4/2.
 */
public class AiResultDO<T> {

    private String aiNums;
    private T data;

    public String getAiNums() {
        return aiNums;
    }

    public void setAiNums(String aiNums) {
        this.aiNums = aiNums;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
