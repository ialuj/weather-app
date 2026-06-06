package com.weather_io.weather_info_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:4200",
                        "http://localhost:4201",
                        "http://127.0.0.1:4200",
                        "https://weather-app-eta-two-9gv21m3g2m.vercel.app",
                        "weather-app-eta-two-9gv21m3g2m.vercel.app",
                        "www.weather-app-eta-two-9gv21m3g2m.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
