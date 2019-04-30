package com.platform.cfs.controller;

import com.platform.cfs.common.enums.ResponseCode;
import com.platform.cfs.common.exception.CommonException;
import com.platform.cfs.utils.Utils;
import org.apache.commons.lang3.StringUtils;

public class BaseController{

    protected void  isNull(Object object,String msg){
        if(object instanceof String){
            if(StringUtils.isBlank(Utils.nulltostr(object))){
                throw  new CommonException(ResponseCode.PARAM_ERROR.getCode(),msg);
            }
        }else{
            if(Utils.isNull(object)){
                throw  new CommonException(ResponseCode.PARAM_ERROR.getCode(),msg);
            }
        }
    }







}
