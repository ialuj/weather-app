package com.weather_io.weather_info_api.controller;

import com.weather_io.weather_info_api.dto.WeatherResponse;
import com.weather_io.weather_info_api.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/weather")
//@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/by-city")
    public ResponseEntity<WeatherResponse> getWeatherByCity(@RequestParam(name = "city") String city, @RequestParam(name = "days", required = false) int days, @RequestParam(name = "ai", required = false) Boolean ai, @RequestParam(name = "units", required = false) String units, @RequestParam(name = "lang", required = false) String lang, @RequestParam(name = "weatherType") String weatherType) {
        WeatherResponse response = weatherService.getWeatherByCity(city, days, ai, units, lang, weatherType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-location")
    public ResponseEntity<WeatherResponse> getWeatherByLocation(@RequestParam(name = "lat") String lat, @RequestParam(name = "lon") String lon, @RequestParam(name = "days") int days, @RequestParam(name = "ai", required = false) Boolean ai, @RequestParam(name = "units", required = false) String units, @RequestParam(name = "lang", required = false) String lang, @RequestParam(name = "weatherType") String weatherType) {
        WeatherResponse response = weatherService.getWeatherByLocation(lat, lon, days, ai, units, lang, weatherType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Integer>> getAnalytics() {
        return ResponseEntity.ok(weatherService.getCityAnalytics());
    }

    @DeleteMapping("/analytics")
    public ResponseEntity<String> resetAnalytics() {
        weatherService.resetAnalytics();
        return ResponseEntity.ok("Analytics reset successfully");
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\": \"OK\", \"service\": \"Weather-AI Integration\"}");
    }
}
