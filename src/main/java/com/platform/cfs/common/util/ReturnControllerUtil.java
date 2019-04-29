package com.platform.cfs.common.util;

import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;

/**
 * @ClassName ReturnControllerUtil
 * @Description Controller中对返回数据进行封装的方法
 * @author 刘非
 * @date 2017-09-25 9:30
 */
public class ReturnControllerUtil {
    public static final  String CREATE="CREATE";
    public static final  String UPDATE="UPDATE";
    public static final  String DELETE="DELETE";
    public static final  String SUBMIT="SUBMIT";

    private static final Integer CODE_SUCCESS=0;
    private static final Integer CODE_ERROR=1;

    /**
     * @Title getCud
     * @Description 对增删改方法提取公共返回代码
     * @author 刘非
     * @param num 受影响行数
     * @param cud CREATE,UPDATE,DELETE
     * @return Response 返回指定响应对象
     */
    public static Response getCud(int num, String cud){
        int code=CODE_ERROR;
        StringBuffer sbf=new StringBuffer();
        if(CREATE.equals(cud)){
            sbf.append("添加");
        }else if(UPDATE.equals(cud)){
            sbf.append("修改");
        }else if(DELETE.equals(cud)){
            sbf.append("删除");
        }else if(SUBMIT.equals(cud)){
            sbf.append("提交");
        }
        if(num>0){
            code=CODE_SUCCESS;
            sbf.append("成功");
        }else{
            sbf.append("失败");
        }
        return ResponseUtil.buildErrorResponse(code,sbf.toString());
    };

}
