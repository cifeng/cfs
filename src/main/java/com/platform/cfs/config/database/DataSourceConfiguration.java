package com.platform.cfs.config.database;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

	@Value("${druid.type}")
	private Class<? extends DataSource> dataSourceType;
	
 
	@Bean(name = "masterDataSource")
	@Primary
	@ConfigurationProperties(prefix = "druid.master") 
	public DataSource masterDataSource() throws SQLException{
		DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		LOGGER.info("========MASTER: {}=========", masterDataSource);
		return masterDataSource;
	}
 
	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "druid.slave")
	public DataSource slaveDataSource(){
		DataSource slaveDataSource = DataSourceBuilder.create().type(dataSourceType).build();
		LOGGER.info("========SLAVE: {}=========", slaveDataSource);
		return slaveDataSource;
	}
  



  
}
