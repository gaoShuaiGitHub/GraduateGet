package com.jiangnan.dal.dataobject;

/**
 * Created by gaoshuai.gs on 2015/4/2.
 */
public class AiResult<T> {

    private Integer aiNums;
    private T data;

    public Integer getAiNums() {
        return aiNums;
    }

    public void setAiNums(Integer aiNums) {
        this.aiNums = aiNums;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
