package com.platform.cfs.controller;

import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.constant.Const;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.service.ILoginService;
import com.platform.cfs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LoginController
 * @Description 用于登录功能使用的controller
 * @author 刘非
 * @date 2019-01-29 14:13
 */

@Controller
public class LoginController extends BaseController{

    @Autowired
    private ILoginService loginService;


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
     */
    @RequestMapping("/login")
    @ResponseBody
    public Response signin(HttpServletRequest request, String username, String password){
        isNull(username,"用户名不能为空");
        isNull(password,"密码不能为空");

        // 调用业务层验证类
        Response response = loginService.loginValid(username, password);
        if(response.isSuccess()){
            // 将用户放入session中
            request.getSession().setAttribute(Const.SYSTEM_SESSION,response.getData());
        }
        return response;
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request){
        SystemUser user = (SystemUser)request.getSession().getAttribute(Const.SYSTEM_SESSION);
        if(Utils.isNull(user)){
            return "redirect:/";
        }
        return "home";
    }


    @RequestMapping("/getloginname")
    @ResponseBody
    public Response getLoginName(HttpServletRequest request){
        SystemUser user = (SystemUser)request.getSession().getAttribute(Const.SYSTEM_SESSION);
        if(Utils.isNotNull(user)){
            String name = user.getName();
            if(Utils.isEmpty(name)){
                name = user.getUsername();
            }
            if(Utils.isEmpty(name)){
                name = user.getMobile();
            }

            return ResponseUtil.buildResponse(name);
        }
        return ResponseUtil.buildErrorResponse();
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute(Const.SYSTEM_SESSION);
        return "redirect:/";
    }






}
