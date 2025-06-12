package org.lw.vms.config;

/**
 * @version 1.0
 * @auther Yongqi Wang
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 允许所有路径
                        .allowedOrigins("http://localhost:5173") // 允许来自这个源的请求
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                        .allowedHeaders("*") // 允许的请求头
                        .allowCredentials(true); // 是否允许发送 cookie（如果需要的话）
            }
        };
    }
}
