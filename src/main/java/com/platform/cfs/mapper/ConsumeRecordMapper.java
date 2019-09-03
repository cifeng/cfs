package com.platform.cfs.mapper;

import com.platform.cfs.config.database.BaseMapper;
import com.platform.cfs.entity.ConsumeRecord;

import java.util.List;

public interface ConsumeRecordMapper extends BaseMapper<ConsumeRecord> {
    /**
     *  修改全部修改  不改最后记录时间
     * @param consumeRecord
     * @return
     */
    int edit(ConsumeRecord consumeRecord);

    /**
     * 联表查询 带出用户姓名和手机号
     */
    List<ConsumeRecord> queryUserAndList(ConsumeRecord consumeRecord);
}