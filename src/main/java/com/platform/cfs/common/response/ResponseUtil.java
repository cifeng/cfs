package com.platform.cfs.common.response;

import com.platform.cfs.common.enums.BusinessEnum;
import com.platform.cfs.common.enums.ResponseCode;
import com.platform.cfs.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;

public class ResponseUtil {
    public static <T> Response<T> buildResponse()
    {
        return new Response(Integer.valueOf(ResponseCode.SUCCESS.getCode()), null);
    }
    public static <T> Response<T> buildResponse(T data)
    {
        return new Response(Integer.valueOf(ResponseCode.SUCCESS.getCode()), data);
    }

    public static <T> Response<T> buildErrorResponse(int code, String msg)
    {
        return new Response(Integer.valueOf(code), msg);
    }

    public static <T> Response<T> buildErrorResponse(ResponseCode responseCode)
    {
        return new Response(Integer.valueOf(responseCode.getCode()), responseCode.getDescription());
    }
    public static <T> Response<T> buildErrorResponse(BusinessEnum responseCode)
    {
        return new Response(Integer.valueOf(responseCode.getCode()), responseCode.getMsg());
    }

    public static <T> Response<T> buildErrorResponse()
    {
        return new Response(Integer.valueOf(ResponseCode.SYSTEM_ERROR.getCode()), ResponseCode.SYSTEM_ERROR.getDescription());
    }

}
