/**
 * Copyright 2017 JINZAY All Rights Reserved.
 */
package com.platform.cfs;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
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
@Slf4j
public class MainConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico, /druid/*,*.html,/page/*");
        log.info(" druid filter register : {} ", filterRegistrationBean);
        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {

        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
//      reg.setAsyncSupported(true);
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", "localhost");
        reg.addInitParameter("deny","/deny");
//      reg.addInitParameter("loginUsername", "bhz");
//      reg.addInitParameter("loginPassword", "bhz");
        log.info(" druid console manager init : {} ", reg);
        return reg;
    }

}
