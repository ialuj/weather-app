package com.weather_io.weather_info_api.controller;

import com.weather_io.weather_info_api.dto.SmsResponse;
import com.weather_io.weather_info_api.dto.WeatherResponse;
import com.weather_io.weather_info_api.service.SmsService;
import com.weather_io.weather_info_api.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/v1/sms")
//@CrossOrigin(origins = "*")
public class SmsController {

    private final SmsService smsService;

    private final RestTemplate restTemplate = new RestTemplate();

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<SmsResponse> sendSMS(@RequestParam(name = "to") String to, @RequestParam(name = "message") String message, @RequestParam(name = "type", required = false) String type, @RequestParam(name = "pilotTag", required = false) String pilotTag) {
        SmsResponse response = smsService.sendSMS(to, message, type, pilotTag);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/alert")
    public ResponseEntity<?> sendSMS(@RequestParam(name = "to") String to, @RequestParam(name = "alertType") String alertType, @RequestParam(name = "data", required = false) Object data) {
        smsService.sendAlertSMS(to, alertType, data);
        return ResponseEntity.ok().body("Alert Sent!");
    }
}
