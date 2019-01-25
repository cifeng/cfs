package com.platform.cfs.service;

import java.util.List;
import java.util.Map;

import com.platform.cfs.config.database.ReadOnlyConnection;
import com.platform.cfs.entity.MstDict;
import com.platform.cfs.mapper.MstDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author baihezhuo
 * @since 2017年5月7日 下午11:02:17
 */
@Service
public class MstDictService {
	
    @Autowired
    private MstDictMapper mstDictMapper;

    @ReadOnlyConnection
    public List<MstDict> findAll(Integer page, Integer rows){
        if (page != null && page != null) {
            PageHelper.startPage(page, rows);
        }
        return mstDictMapper.selectAll();
    }
    
    public List<MstDict> findByStatus(Map<String, Object> params){
    	return mstDictMapper.findByStatus(params);
    }

}
