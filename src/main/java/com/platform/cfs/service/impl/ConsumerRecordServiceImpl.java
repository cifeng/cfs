package com.platform.cfs.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.cfs.cloopen.Sms;
import com.platform.cfs.cloopen.utils.SmsParam;
import com.platform.cfs.common.exception.BusinessException;
import com.platform.cfs.config.database.PageVO;
import com.platform.cfs.entity.ConsumeRecord;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.mapper.ConsumeRecordMapper;
import com.platform.cfs.mapper.SystemUserMapper;
import com.platform.cfs.service.IConsumerRecordService;
import com.platform.cfs.utils.BigDecimalUtils;
import com.platform.cfs.utils.Jackson2Helper;
import com.platform.cfs.utils.KeyUtil;
import com.platform.cfs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ConsumerRecordServiceImpl implements IConsumerRecordService {

    @Autowired
    private ConsumeRecordMapper consumeRecordMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private Sms sms;

    @Override
    public int save(ConsumeRecord consumeRecord) {
        consumeRecord.setId(KeyUtil.generatorUUID());
        consumeRecord.setLastTime(new Date());
        SystemUser user = systemUserMapper.queryById(consumeRecord.getUserId());
        user.setLastTime(consumeRecord.getLastTime());
        updateUser(consumeRecord, user);
        SmsParam param  = new SmsParam(user.getName(),"消费",consumeRecord.getBalance(),user.getBalance(),user.getMobile());
        sms.send(param);
        return consumeRecordMapper.save(consumeRecord);
    }

    private void updateUser(ConsumeRecord consumeRecord, SystemUser user) {
        // 如果金额不为空 则按金额进行减
        if(Utils.isNotEmpty(user.getBalance())&&Utils.isNotEmpty(consumeRecord.getBalance())){

            if(Utils.nulltoZero(consumeRecord.getBalance())>0){
                String subtract = BigDecimalUtils.subtract(user.getBalance(), consumeRecord.getBalance());
                if(new BigDecimal(subtract).doubleValue()<=0){
                    throw new BusinessException(2001,"添加失败,用户账户余额不足");
                }
                user.setBalance(subtract);
            }

        }
        // 如果次数不为空  则按次数减
        if(Utils.isNotNull(user.getFrequency())
                &&user.getFrequency()>0
                &&Utils.isNotNull(consumeRecord.getFrequency())
                &&consumeRecord.getFrequency()>0){
            if(consumeRecord.getFrequency()>user.getFrequency()){
                throw new BusinessException(2002,"添加失败,用户剩余次数不足");
            }
            user.setFrequency(user.getFrequency()-consumeRecord.getFrequency());
        }
        systemUserMapper.update(user);
    }


    @Override
    public PageVO queryByList(ConsumeRecord consumeRecord) {
        Page<PageInfo> pageInfo = PageHelper.startPage(consumeRecord.getPageIndex(), consumeRecord.getPageSize());
        List<ConsumeRecord> list = consumeRecordMapper.queryByList(consumeRecord);
        return new PageVO(pageInfo.getTotal(),list);
    }

    @Override
    public ConsumeRecord queryById(String id) {
        return consumeRecordMapper.queryById(id);
    }

    @Override
    public int edit(ConsumeRecord consumeRecord) {
        SystemUser user = systemUserMapper.queryById(consumeRecord.getUserId());
        ConsumeRecord record = queryById(consumeRecord.getId());
        // 如果之前消费过金额  则将金额再加回去
        if(Utils.isNotEmpty(record.getBalance())){
            String add = BigDecimalUtils.add(user.getBalance(), record.getBalance());
            user.setBalance(add);
        }

        // 如果之前使用过次数 则将次数再加回去
        if(Utils.isNotNull(record.getFrequency())){
            user.setFrequency(user.getFrequency()+record.getFrequency());
        }
        updateUser(consumeRecord, user);
        return consumeRecordMapper.edit(consumeRecord);
    }

    @Override
    public int delete(String id) {
        int num =0;

        ConsumeRecord record = queryById(id);
        SystemUser user = systemUserMapper.queryById(record.getUserId());
        if(Utils.isNotNull(user)){
            // 如果之前消费过金额  则将金额再加回去
            if(Utils.isNotEmpty(record.getBalance())){
                String add = BigDecimalUtils.add(user.getBalance(), record.getBalance());
                user.setBalance(add);
            }

            // 如果之前使用过次数 则将次数再加回去
            if(Utils.isNotNull(record.getFrequency())){
                user.setFrequency(user.getFrequency()+record.getFrequency());
            }
            systemUserMapper.update(user);
            num = consumeRecordMapper.delete(record);
        }
        return num;
    }
}
