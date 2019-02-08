package com.pilot.self_bbs_spa_boot.config;


import com.pilot.self_bbs_spa_boot.controller.interceptor.CustomInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private CustomInterceptor customInterceptor;

    public WebConfig( CustomInterceptor customInterceptor) {
        this.customInterceptor = customInterceptor;
    }
    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(customInterceptor);
    }


}
