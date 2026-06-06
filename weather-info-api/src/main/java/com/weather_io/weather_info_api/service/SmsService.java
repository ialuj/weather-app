package com.weather_io.weather_info_api.service;

import com.weather_io.weather_info_api.dto.SmsResponse;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SmsService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public SmsResponse sendSMS(String to, String message, String type, String pilotTag) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/sms/send")
                    .queryParam("to", to)
                    .queryParam("message", message)
                    .queryParam("type", type == null || StringUtils.isBlank(type) ? "general" : type)
                    .queryParam("pilotTag", pilotTag == null || StringUtils.isBlank(pilotTag) ? "" : pilotTag)
                    .build()
                    .toUri();

            log.debug("Calling Weather-AI API: {}", uri);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map<String, Object> apiResponse = responseEntity.getBody();

            if (apiResponse == null) {
                throw new RuntimeException("No data received from Weather-AI API");
            }

            return new SmsResponse(to, message, "SENT");

        } catch (RestClientException e) {
            log.error("Error sending SMS to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }
    }

    public void sendAlertSMS(String to, String alertType, Object data) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/sms/alert")
                    .queryParam("to", to)
                    .queryParam("alertType", alertType == null || StringUtils.isBlank(alertType) ? "rain" : alertType)
                    .queryParam("data", data)
                    .build()
                    .toUri();

            log.debug("Calling Weather-AI API: {}", uri);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            Map<String, Object> apiResponse = responseEntity.getBody();

            if (apiResponse == null) {
                throw new RuntimeException("No data received from Weather-AI API");
            }

        } catch (RestClientException e) {
            log.error("Error sending SMS to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send SMS: " + e.getMessage());
        }
    }
}
