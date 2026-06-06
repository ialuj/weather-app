package com.weather_io.weather_info_api.service;

import com.weather_io.weather_info_api.dto.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, Integer> cityHits = new ConcurrentHashMap<>();

    private final CityService cityService;

    private Map<String, double[]> cityCoordinates;

    private Set<String> cityNames;

    public WeatherService(CityService cityService) {
        this.cityService = cityService;
        cityCoordinates = this.cityService.getCityHits();
        cityNames = this.cityService.getCityNames();
    }

    public WeatherResponse getCompleteWeather(String cityName, int days, Boolean ai, String units, String lang) {
        log.info("Fetching weather data for city: {}, days: {}, ai: {}, units: {}, lang: {}", cityName, days, ai, units, lang);

        if (!cityCoordinates.containsKey(cityName)) {
            throw new RuntimeException("City not supported: " + cityName +
                    ". Supported cities: " + String.join(", ", cityNames));
        }

        cityHits.merge(cityName.toLowerCase(), 1, Integer::sum);

        double[] coords = cityCoordinates.get(cityName);
        double lat = coords[0];
        double lon = coords[1];

        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("ai", ai)
                    .queryParam("days", days)
                    .queryParam("units", units)
                    .queryParam("lang", lang)
                    .build()
                    .toUri();

            log.debug("Calling Weather-AI API: {}", uri);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> apiResponse = responseEntity.getBody();

            if (apiResponse == null) {
                throw new RuntimeException("No data received from Weather-AI API");
            }

            return mapToWeatherResponse(cityName, apiResponse);

        } catch (RestClientException e) {
            log.error("Error fetching weather for {}: {}", cityName, e.getMessage());
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    public WeatherResponse getWeatherByCity(String city, int days, Boolean ai, String units, String lang, String weatherType) {
        log.info("Fetching weather data for city: {}, days: {}, ai: {}, units: {}, lang: {}", city, days, ai, units, lang);

        if (!cityCoordinates.containsKey(city)) {
            throw new RuntimeException("City not supported: " + city +
                    ". Supported cities: " + String.join(", ", cityCoordinates.keySet()));
        }

        cityHits.merge(city.toLowerCase(), 1, Integer::sum);

        double[] coords = cityCoordinates.get(city);
        double lat = coords[0];
        double lon = coords[1];

        try {

            URI uri;
            switch (weatherType) {
                case "weather":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                case "forecast":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .build()
                            .toUri();
                case "current":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/current")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                case "daily":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/daily")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                case "hourly":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/hourly")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                default:
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
            }

            log.debug("Calling Weather-AI API: {}", uri);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> apiResponse = responseEntity.getBody();

            if (apiResponse == null) {
                throw new RuntimeException("No data received from Weather-AI API");
            }

            return mapToWeatherResponse(city, apiResponse);

        } catch (RestClientException e) {
            log.error("Error fetching weather for {}: {}", city, e.getMessage());
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }

    }

    public WeatherResponse getWeatherByLocation(String latitude, String longitude, int days, Boolean ai, String units, String lang, String weatherType) {
        log.info("Fetching weather data: lat: {}, lon: {}, days: {}, ai: {}, units: {}, lang: {}, weatherType: {}", latitude, longitude, days, ai, units, lang, weatherType);

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        try {

            URI uri;
            switch (weatherType) {
                case "weather":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                case "forecast":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .build()
                            .toUri();
                case "current":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/current")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                case "daily":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/daily")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                case "hourly":
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/hourly")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
                default:
                    uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("ai", ai)
                            .queryParam("days", days)
                            .queryParam("units", units)
                            .queryParam("lang", lang)
                            .build()
                            .toUri();
            }

            log.debug("Calling Weather-AI API: {}", uri);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> apiResponse = responseEntity.getBody();

            if (apiResponse == null) {
                throw new RuntimeException("No data received from Weather-AI API");
            }

            return mapToWeatherResponse("", apiResponse);

        } catch (RestClientException e) {
            log.error("Error fetching weather for lat {} and lon: {}: {}, {}", lat, lon, e.getMessage());
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage());
        }
    }

    public Map<String, Integer> getCityAnalytics() {
        return new ConcurrentHashMap<>(cityHits);
    }

    public void resetAnalytics() {
        cityHits.clear();
        log.info("Analytics data has been reset");
    }

    public Map<String, double[]> getAvailableCities() {
        return Map.copyOf(cityCoordinates);
    }

    public int getTotalCitiesSupported() {
        return cityCoordinates.size();
    }

    @SuppressWarnings("unchecked")
    private WeatherResponse mapToWeatherResponse(String cityName, Map<String, Object> apiResponse) {
        WeatherResponse response = new WeatherResponse();
        response.setCity(cityName);
        response.setTimestamp(System.currentTimeMillis());

        // ========== CURRENT WEATHER ==========
        Map<String, Object> currentData = null;

        if (apiResponse.containsKey("current")) {
            currentData = (Map<String, Object>) apiResponse.get("current");
        } else if (apiResponse.containsKey("data")) {
            Object data = apiResponse.get("data");
            if (data instanceof Map) currentData = (Map<String, Object>) data;
        } else if (apiResponse.containsKey("weather")) {
            currentData = (Map<String, Object>) apiResponse.get("weather");
        } else {
            currentData = apiResponse;
        }

        WeatherResponse.CurrentWeather currentWeather = new WeatherResponse.CurrentWeather();

        Double temp = extractDouble(currentData, "temp_c", "temperature", "temp", "current_temp", "tempC");
        currentWeather.setTemperature(temp != null ? temp : 0.0);
        currentWeather.setCondition(extractCondition(currentData));

        Integer humidity = extractInt(currentData, "humidity", "hum");
        currentWeather.setHumidity(humidity != null ? humidity : 0);

        Double wind = extractDouble(currentData, "wind_kph", "windSpeed", "wind", "windKph", "wind_speed");
        currentWeather.setWindKph(wind != null ? wind : 0.0);

        Double feelsLike = extractDouble(currentData, "feelslike_c", "feels_like", "feelslike", "feelsLike");
        currentWeather.setFeelsLike(feelsLike != null ? feelsLike : 0.0);

        response.setCurrent(currentWeather);

        // ========== AI SUMMARY ==========
        String aiSummaryText = extractAiSummary(apiResponse);
        if (aiSummaryText == null || aiSummaryText.isEmpty()) {
            aiSummaryText = getFallbackAiSummary(cityName, currentWeather.getTemperature());
        }

        WeatherResponse.AiSummary aiSummary = new WeatherResponse.AiSummary();
        aiSummary.setText(aiSummaryText);
        aiSummary.setRecommendation(getFallbackRecommendation(currentWeather.getTemperature()));
        response.setAiSummary(aiSummary);

        // ========== FORECAST ==========
        List<WeatherResponse.ForecastDay> forecastList = new ArrayList<>();

        Object forecastObj = apiResponse.get("forecast");

        if (forecastObj instanceof List) {
            List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) forecastObj;
            forecastList = parseForecastDays(forecastDays);

        } else if (forecastObj instanceof Map) {
            Map<String, Object> forecastMap = (Map<String, Object>) forecastObj;

            List<Map<String, Object>> forecastDays = null;

            if (forecastMap.containsKey("forecastday")) {
                forecastDays = (List<Map<String, Object>>) forecastMap.get("forecastday");
            } else if (forecastMap.containsKey("data")) {
                forecastDays = (List<Map<String, Object>>) forecastMap.get("data");
            }

            if (forecastDays != null) {
                forecastList = parseForecastDays(forecastDays);
            }

        } else if (apiResponse.containsKey("daily")) {
            Object dailyObj = apiResponse.get("daily");
            if (dailyObj instanceof List) {
                List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) dailyObj;
                forecastList = parseForecastDays(forecastDays);
            } else if (dailyObj instanceof Map) {
                Map<String, Object> dailyMap = (Map<String, Object>) dailyObj;
                if (dailyMap.containsKey("data")) {
                    List<Map<String, Object>> forecastDays = (List<Map<String, Object>>) dailyMap.get("data");
                    forecastList = parseForecastDays(forecastDays);
                }
            }
        }

        response.setForecast(forecastList);

        return response;
    }

    /**
     * Converte uma lista de dias do forecast em objetos ForecastDay
     */
    private List<WeatherResponse.ForecastDay> parseForecastDays(List<Map<String, Object>> forecastDays) {
        List<WeatherResponse.ForecastDay> result = new ArrayList<>();

        for (Map<String, Object> day : forecastDays) {
            WeatherResponse.ForecastDay forecastDay = new WeatherResponse.ForecastDay();

            // Extract date
            String date = extractString(day, "date", "datetime");
            forecastDay.setDate(date != null ? date : "");

            // Extract temperatures - podem estar no próprio dia ou num sub-objeto "day"
            Map<String, Object> dayData = day;
            if (day.containsKey("day")) {
                dayData = (Map<String, Object>) day.get("day");
            }

            Double maxTemp = extractDouble(dayData, "maxtemp_c", "maxTemp", "temp_max", "max", "maxTemp");
            Double minTemp = extractDouble(dayData, "mintemp_c", "minTemp", "temp_min", "min", "minTemp");

            forecastDay.setMaxTemp(maxTemp != null ? maxTemp : 0.0);
            forecastDay.setMinTemp(minTemp != null ? minTemp : 0.0);

            // Extract condition
            String condition = extractCondition(dayData);
            forecastDay.setCondition(condition != null ? condition : "Not available");

            result.add(forecastDay);
        }

        return result;
    }

    private String extractString(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object value = map.get(key);

            if (value instanceof String) {
                String str = (String) value;
                if (!str.isEmpty()) {
                    return str;
                }
            }

            if (value instanceof Map) {
                Map<?, ?> nestedMap = (Map<?, ?>) value;

                Object textValue = nestedMap.get("text");
                if (textValue instanceof String && !((String) textValue).isEmpty()) {
                    return (String) textValue;
                }

                Object valueValue = nestedMap.get("value");
                if (valueValue instanceof String && !((String) valueValue).isEmpty()) {
                    return (String) valueValue;
                }
            }
        }
        return null;
    }

    private String extractCondition(Map<String, Object> data) {
        Object cond = data.get("condition");
        if (cond instanceof Map) {
            Object text = ((Map<?, ?>) cond).get("text");
            if (text instanceof String) return (String) text;
        }
        if (cond instanceof String) return (String) cond;

        String[] keys = {"weather", "condition_text", "description", "sky"};
        for (String key : keys) {
            Object value = data.get(key);
            if (value instanceof String) return (String) value;
            if (value instanceof Map) {
                Object text = ((Map<?, ?>) value).get("text");
                if (text instanceof String) return (String) text;
            }
        }
        return "Not available";
    }

    private String extractAiSummary(Map<String, Object> response) {
        String[] keys = {"ai_summary", "summary", "ai_description", "ai", "description"};

        for (String key : keys) {
            Object value = response.get(key);
            if (value instanceof String) {
                String str = (String) value;
                if (!str.isEmpty()) return str;
            }
            if (value instanceof Map) {
                Object text = ((Map<?, ?>) value).get("text");
                if (text instanceof String && !((String) text).isEmpty()) {
                    return (String) text;
                }
                Object summary = ((Map<?, ?>) value).get("summary");
                if (summary instanceof String && !((String) summary).isEmpty()) {
                    return (String) summary;
                }
            }
        }
        return null;
    }

    private Double extractDouble(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object value = map.get(key);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            if (value instanceof String) {
                try {
                    return Double.parseDouble((String) value);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return null;
    }

    private Integer extractInt(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            Object value = map.get(key);
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            if (value instanceof String) {
                try {
                    return Integer.parseInt((String) value);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return null;
    }

    private String getFallbackAiSummary(String city, Double temp) {
        if (temp > 30) {
            return String.format("%s is quite warm today at %.0f°C. Stay hydrated and avoid direct sun exposure.", city, temp);
        } else if (temp > 20) {
            return String.format("%s has pleasant weather at %.0f°C. Perfect for outdoor activities.", city, temp);
        } else if (temp > 10) {
            return String.format("%s is cool at %.0f°C. A light jacket would be comfortable.", city, temp);
        } else {
            return String.format("%s is cold at %.0f°C. Bundle up if you're heading out.", city, temp);
        }
    }

    private String getFallbackRecommendation(Double temp) {
        if (temp > 30) return "🧊 Wear light clothes and drink water";
        if (temp > 20) return "☀️ Enjoy the nice weather outdoors";
        if (temp > 10) return "🧥 Take a jacket, it's fresh outside";
        return "❄️ Wear warm layers";
    }


}