package com.platform.cfs.mapper;

import java.util.List;
import java.util.Map;

import com.platform.cfs.config.database.BaseMapper;
import com.platform.cfs.entity.MstDict;

public interface MstDictMapper extends BaseMapper<MstDict> {
	
	List<MstDict> findByStatus(Map<String, Object> params);
	
}