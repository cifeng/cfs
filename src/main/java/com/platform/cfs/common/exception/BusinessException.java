package com.platform.cfs.common.exception;

public class BusinessException extends CommonException {

    public BusinessException(int code, String msg) {
        super(code,msg);
    }
}
