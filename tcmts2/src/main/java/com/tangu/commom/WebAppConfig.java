package com.tangu.commom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author fenglei on 10/25/17.
 * 为了加入IntervalLogInterceptor，监控每个controll方法的执行时间
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${tangu.enableIntervalLogInterceptor}")
    private Boolean enableIntervalLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        if(enableIntervalLogInterceptor){
            registry.addInterceptor(new IntervalLogInterceptor()).addPathPatterns("/**");
        }

        registry.addInterceptor(new MobileControlInterceptor()).addPathPatterns("/MobileHandle");

        super.addInterceptors(registry);
    }
}
