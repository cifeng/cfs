package com.platform.cfs.common.exception;

import com.platform.cfs.common.enums.ResponseCode;
import com.platform.cfs.utils.Jackson2Helper;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler({CommonException.class})
    @ResponseBody
    public String requestNotReadable(CommonException ex){
        ex.printStackTrace();
        //json 数据读取失败
        Map<String,Object> result = new HashMap<>(2);
        result.put("code", ex.getCode());
        result.put("msg", ex.getErrorMessage());
        return Jackson2Helper.toJsonString(result);
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    public String requestNotReadable2(BindException ex){
        ex.printStackTrace();
        //json 数据读取失败
        Map<String,Object> result = new HashMap<>(2);
        result.put("code", ResponseCode.PARAM_ERROR.getCode());
        result.put("msg", ex.getFieldError().getDefaultMessage());
        return Jackson2Helper.toJsonString(result);
    }

}
