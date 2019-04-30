package com.platform.cfs.common.enums;

public enum ResponseCode
{
    SUCCESS(200, "success", "成功"),
    SYSTEM_ERROR(1002, "system_error", "系统异常"),
    PARAM_ERROR(1003, "param_error", "参数异常"),
    SQL_ERROR(1004, "sql_error", "数据库异常"),
    NEED_LOGIN(1005, "need_login", "请先登录!");


    private int code;
    private String msg;
    private String description;

    private ResponseCode(int code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("ResponseCode{");
        sb.append("code=").append(this.code);
        sb.append(", msg='").append(this.msg).append('\'');
        sb.append(", description='").append(this.description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}