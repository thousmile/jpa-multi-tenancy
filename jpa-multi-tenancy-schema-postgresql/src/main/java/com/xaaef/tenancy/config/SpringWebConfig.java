package com.xaaef.tenancy.config;

import com.xaaef.tenancy.interceptor.TenantIdInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@AllArgsConstructor
public class SpringWebConfig implements WebMvcConfigurer {

    private final TenantIdInterceptor tenantIdInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantIdInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
