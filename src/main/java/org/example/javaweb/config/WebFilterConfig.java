package org.example.javaweb.config;

import org.example.javaweb.web.filter.RequestTimingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<RequestTimingFilter> requestTimingFilter() {
        FilterRegistrationBean<RequestTimingFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new RequestTimingFilter());
        reg.setOrder(1);
        reg.addUrlPatterns("/*");
        return reg;
    }
}
