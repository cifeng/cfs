package com.platform.cfs.common.exception;

import com.platform.cfs.utils.Jackson2Helper;
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
        Map<String,Object> result = new HashMap<>(5);
        result.put("code", ex.getCode());
        result.put("msg", ex.getErrorMessage());
        return Jackson2Helper.toJsonString(result);
    }

}
