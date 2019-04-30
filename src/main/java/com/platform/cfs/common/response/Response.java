package com.platform.cfs.common.response;

import com.platform.cfs.common.enums.ResponseCode;

public class Response<T> {
    private Integer code;
    private T data;
    private String msg;
    private boolean visible = true;

    public boolean isVisible()
    {
        return this.visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public Response() {
    }

    public Response(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Response(Integer code, T data, String msg, boolean visible) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.visible = visible;
    }

    public Response(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return this.code.intValue() == ResponseCode.SUCCESS.getCode();
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("Response{");
        sb.append("code=").append(this.code);
        sb.append(", data=").append(this.data);
        sb.append(", msg='").append(this.msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
