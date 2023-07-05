package org.hejia.jrb.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        log.debug("CORS限制打开");
        CorsConfiguration config = new CorsConfiguration();
        // 是否允许携带cookie
        config.setAllowCredentials(true);
        // 是否允许携带cookie
//        config.addAllowedOrigin("*");
        // 允许携带的头
        config.addAllowedHeader("*");
        // 允许访问的方式
        config.addAllowedMethod("*");

        // 是否允许携带cookie
        config.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);

    }

}
