package com.weather_io.weather_info_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherInfoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherInfoApiApplication.class, args);
        System.out.println("🚀 Weather-AI Integration Service is running!");
        System.out.println("📡 API available at: https://weather-backend.onrender.com/api");
	}

}
