package com.platform.cfs.service;

import com.platform.cfs.common.response.Response;

public interface ILoginService {

    /**
     * 登录验证接口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    Response loginValid(String username,String password);

}
