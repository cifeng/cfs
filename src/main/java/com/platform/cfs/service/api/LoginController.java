package com.platform.cfs.service.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName LoginController
 * @Description 用于登录功能使用的controller
 * @author 刘非
 * @date 2019-01-29 14:13
 */

@Controller
public class LoginController {

    /**
     * 直接敲域名跳转到指定的登录页面
     * @return
     */
    @RequestMapping("/")
    public String login(){
        return "login";
    }


    /**
     * 登录接口
     * @return
     */
    @RequestMapping("/login")
    public String signin(String username,String password){


        return "login";
    }


}
