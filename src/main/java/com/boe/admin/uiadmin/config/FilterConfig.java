package com.boe.admin.uiadmin.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 跨域过滤器最先执行
 * 这里定义的过滤器第二执行
 * security过滤器最后执行
 *
 */

@Configuration
public class FilterConfig {




    @Bean
    public FilterRegistrationBean<LogFilter> FirstFilter() {
        FilterRegistrationBean<LogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("FirstFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
    


}