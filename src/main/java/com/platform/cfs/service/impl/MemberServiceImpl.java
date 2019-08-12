package com.platform.cfs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.cfs.cloopen.Sms;
import com.platform.cfs.cloopen.utils.SmsParam;
import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.config.database.PageVO;
import com.platform.cfs.constant.Const;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.mapper.SystemUserMapper;
import com.platform.cfs.service.IMemberService;
import com.platform.cfs.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberServiceImpl implements IMemberService {


    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private Sms sms;

    @Override
    public Response save(SystemUser user) {
        user.setId(KeyUtil.generatorUUID());
        // 设置初始密码
        final String initPwd = "123456";
        user.setPassword(MD5Util.MD5(initPwd));
        user.setState(Const.DATA_EFFECTIVE);
        user.setCreateTime(new Date());
        if(Utils.isNotEmpty(user.getBalance())&&Utils.nulltoZero(user.getBalance())>0){
            user.setLastTime(new Date());
        }
        int num = systemUserMapper.save(user);
        if(num>0){
            SmsParam param  = new SmsParam(user.getName(),"充值",user.getBalance(),user.getBalance(),user.getMobile());
            sms.send(param);
            return ResponseUtil.buildResponse();
        }
        return ResponseUtil.buildErrorResponse();
    }


    @Override
    public Response edit(SystemUser user) {
        SystemUser systemUser = queryById(user.getId());
        user.setUpdateTime(new Date());
        if(Utils.isNotEmpty(user.getBalance())&&Utils.nulltoZero(user.getBalance())>0){
            user.setLastTime(new Date());
        }
        int num = systemUserMapper.update(user);
        if(num>0){
            String subtract = BigDecimalUtils.subtract(user.getBalance(), systemUser.getBalance());
            SmsParam param  = new SmsParam(user.getName(),"充值",subtract,user.getBalance(),user.getMobile());
            sms.send(param);
            return ResponseUtil.buildResponse();
        }
        return ResponseUtil.buildErrorResponse();
    }

    @Override
    public Response delete(String ids) {
        String[] array = ids.split(",");

        Integer num = systemUserMapper.deleteByBatch( Arrays.stream(array).collect(Collectors.toList()));
        if(num>0){
            return ResponseUtil.buildResponse();
        }
        return ResponseUtil.buildErrorResponse();
    }

    @Override
    public SystemUser queryById(String id) {
        return  systemUserMapper.queryById(id);
    }

    @Override
    public PageVO queryByList(SystemUser user) {
        Page<PageInfo> pageInfo = PageHelper.startPage(user.getPageIndex(), user.getPageSize());
        List<SystemUser> list = systemUserMapper.queryByList(user);
        return new PageVO(pageInfo.getTotal(),list);
    }
}
