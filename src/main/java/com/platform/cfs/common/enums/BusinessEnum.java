package com.platform.cfs.common.enums;

public enum BusinessEnum
{

    LOGIN_ERROR(10002, "用户名或密码不正确!"),
    PK_ISNUL(10101, "主键不能为空");

    private int code;
    private String msg;

    private BusinessEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }



}