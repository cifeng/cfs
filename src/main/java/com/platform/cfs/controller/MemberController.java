package com.platform.cfs.controller;

import com.platform.cfs.common.enums.BusinessEnum;
import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.config.database.PageVO;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.service.IMemberService;
import com.platform.cfs.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@Controller
public class MemberController extends BaseController {

    @Autowired
    private IMemberService memberService;



    @RequestMapping("/input")
    public String input(){
        return "/member/member";
    }

    @PostMapping("/save")
    @ResponseBody
    public Response save(SystemUser user){
        return memberService.save(user);
    }



    @PostMapping("/edit")
    @ResponseBody
    public Response edit(SystemUser user){
        return memberService.edit(user);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Response delete(String ids){
        if(StringUtils.isBlank(ids)||StringUtils.equals(",",ids)){
            return ResponseUtil.buildErrorResponse(BusinessEnum.PK_ISNUL);
        }
        return memberService.delete(ids);
    }


    @PostMapping("/query")
    @ResponseBody
    public Response queryById(String id){
        if(StringUtils.isBlank(id)){
            return ResponseUtil.buildErrorResponse(BusinessEnum.PK_ISNUL);
        }
        SystemUser systemUser = memberService.queryById(id);
        if(Utils.isNotNull(systemUser)){
            return ResponseUtil.buildResponse(systemUser);
        }
        return ResponseUtil.buildErrorResponse();
    }

    @RequestMapping("/list")
    @ResponseBody
    public Response queryByList(SystemUser user){
        PageVO pageInfo = memberService.queryByList(user);
        return ResponseUtil.buildResponse(pageInfo);
    }


}
