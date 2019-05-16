package com.platform.cfs.mapper;

import com.platform.cfs.config.database.BaseMapper;
import com.platform.cfs.entity.SystemUser;

import java.util.List;

public interface SystemUserMapper extends BaseMapper<SystemUser>{

    Integer deleteByBatch(List<String> list);
}