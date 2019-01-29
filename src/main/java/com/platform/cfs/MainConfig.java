/**
 * Copyright 2017 JINZAY All Rights Reserved.
 */
package com.platform.cfs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @ClassName MainConfig
 * @Description 配置文件入口
 * @author 刘非
 * @date 2019-01-29 14:12
 */
@EnableAutoConfiguration // 使用这个注解会从properties和yml中读取ViewResolve的配置 使用 @EnableWebMvc 注解需要显示用@bean编码方式配置ViewResolve
@Configuration
@ComponentScan({"com.platform.cfs.mapper.*"})
@MapperScan(basePackages = "com.platform.cfs.mapper")
public class MainConfig {

}
