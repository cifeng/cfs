package com.platform.cfs.config.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadWriteSplitRoutingDataSource extends AbstractRoutingDataSource {
	 
	  @Override
	  protected Object determineCurrentLookupKey() {
		  return DataBaseContextHolder.getDataBaseType();
	  }
}
