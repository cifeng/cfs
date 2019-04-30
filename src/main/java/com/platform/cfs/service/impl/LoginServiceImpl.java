package com.platform.cfs.service.impl;

import com.platform.cfs.common.enums.BusinessEnum;
import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.constant.Const;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.mapper.SystemUserMapper;
import com.platform.cfs.service.ILoginService;
import com.platform.cfs.utils.Jackson2Helper;
import com.platform.cfs.utils.MD5Util;
import com.platform.cfs.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private SystemUserMapper systemUserMapper;


    @Override
    public Response loginValid(String username, String password) {
        // 有效性验证，验证用户是否存在
        // 1 封装参数
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(MD5Util.MD5(password));
        systemUser.setState(Const.DATA_EFFECTIVE);
        // 2 调用mapper验证
        log.info("登录校验====》有效性验证开始====》参数:{}",Jackson2Helper.toJsonString(systemUser));
        List<SystemUser> list = systemUserMapper.queryByList(systemUser);
        log.info("登录校验《====有效性验证结束《====结果:{}",Jackson2Helper.toJsonString(list));
        if(Utils.isNotEmpty(list)){
            return ResponseUtil.buildResponse(list.get(0));
        }
        return ResponseUtil.buildErrorResponse(BusinessEnum.LOGIN_ERROR);
    }
}
