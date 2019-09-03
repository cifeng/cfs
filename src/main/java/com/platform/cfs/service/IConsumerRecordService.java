package com.platform.cfs.service;


import com.platform.cfs.config.database.PageVO;
import com.platform.cfs.entity.ConsumeRecord;

public interface IConsumerRecordService {


    int save(ConsumeRecord consumeRecord);

    PageVO queryByList(ConsumeRecord consumeRecord);

    ConsumeRecord queryById(String id);

    int edit(ConsumeRecord consumeRecord);

    int delete(String id);

    PageVO queryUserAndList(ConsumeRecord consumeRecord);
}
