package com.maojie.config;

import com.maojie.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: Web configuration class for setting up interceptors in the application. Configures the LoginInterceptor to handle authentication on specific routes.
 * @Param: None
 * @return: None
 * @Author: Maojie Xu
 * @Date: 10/25/2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * @Description: Injects the LoginInterceptor to be used in the interceptor configuration
     * @Param: None
     * @return: Instance of LoginInterceptor
     */
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * @Description: Adds the LoginInterceptor to the interceptor registry and excludes certain paths from interception
     * @Param: registry - InterceptorRegistry instance to manage interceptors
     * @return: None
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/login", "/user/register", "/doc.html", "/webjars/**", "/swagger-resources/**", "/v3/api-docs/**"
                );
    }
}
