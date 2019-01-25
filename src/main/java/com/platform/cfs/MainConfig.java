/**
 * Copyright 2017 JINZAY All Rights Reserved.
 */
package com.platform.cfs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * <B>系统名称：</B>EES<BR>
 * <B>模块名称：</B>EES-SYS<BR>
 * <B>中文类名：</B>MainConfig<BR>
 * <B>概要说明：</B>MainConfig<BR>
 * @author bhz
 * @since 2017年2月8日 下午1:28:48
 */
@EnableWebMvc
@Configuration
@ComponentScan({"com.platform.cfs.mapper.*"})
@MapperScan(basePackages = "com.platform.cfs.mapper")
public class MainConfig {

}
