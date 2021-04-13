package com.jsh.filter.example.config;

import com.jsh.filter.example.filter.FirstSampleFilter;
import com.jsh.filter.example.filter.SecondSampleFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> firstSampleFilter(){
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>(new FirstSampleFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/sample/filter/first"));

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> secondSampleFilter(){
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>(new SecondSampleFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/sample/filter/second"));

        return registrationBean;
    }
}
