package com.platform.cfs.common.exception;


//import javax.ejb.ApplicationException;

/**
 * Base Exception class that can be thrown by the business layer.
 * 
 * 业务异常基类。不需要要捕获。
 * 
 * 
 */
@SuppressWarnings("serial")
//@ApplicationException(rollback=true)
public class GenericBusinessException extends RuntimeException   {
	
	//错误Key，统一在资源文件 ErrorMessage.java 里编排
	private int errorCode = 0;                                  
	//错误基本描述，面向操作人员可理解，必须为中文 , 例子：箱号10018已锁定，无法开户
	private String errorDescription = ""; 
	//错误原因和详细信息，一般从具体的异常类中取出，如 jdbc execption , hibernate , spring 等框架的异常信息。可能为英文。
	//例子：drawer id=9 not found 
	private String errorDetail = "";     
	
	public GenericBusinessException(){		
	}
	
	/**
	 * 不要轻易使用
	 * @param cause
	 */
	public GenericBusinessException(Throwable cause){	
		super(cause);
	}
    public GenericBusinessException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }
	
    public GenericBusinessException(String errorDescription) {
        super(errorDescription);
        this.errorDescription = errorDescription;
    }
    public GenericBusinessException(String errorDescription, Throwable cause) {
        super(cause);
        this.errorDescription = errorDescription;
    }
    
    public GenericBusinessException(int errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
    public GenericBusinessException(int errorCode, String errorDescription, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public GenericBusinessException(String errorDescription , String errorDetail) {
        super(errorDescription);
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }
    public GenericBusinessException(int errorCode, String errorDescription , String errorDetail) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }
    
    public GenericBusinessException(String errorDescription , String errorDetail, Throwable cause) {
        super(cause);
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }
   
    public GenericBusinessException(int errorCode, String errorDescription , String errorDetail, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetail = errorDetail;
    }
    
    
    
    
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}
    
}
