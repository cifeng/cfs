package com.platform.cfs.common.exception;

public class CommonException extends RuntimeException
        implements ILoggerRecord
{
    private static final long serialVersionUID = 2956675295337329394L;
    private String appName;
    private Integer code;
    private String errorMessage;
    private Boolean isAlarm;
    private Integer errorLevel;

    public CommonException()
    {
    }

    public CommonException(String message)
    {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public Integer getCode()
    {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Boolean getIsAlarm() {
        return this.isAlarm;
    }

    public void setIsAlarm(Boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

    public Integer getErrorLevel() {
        return this.errorLevel;
    }

    public void setErrorLevel(Integer errorLevel) {
        this.errorLevel = errorLevel;
    }

    public boolean inLog()
    {
        return true;
    }
}
