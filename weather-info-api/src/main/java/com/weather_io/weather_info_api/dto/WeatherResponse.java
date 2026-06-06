package com.weather_io.weather_info_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class WeatherResponse implements Serializable {
    private String city;
    private CurrentWeather current;
    private AiSummary aiSummary;
    private List<ForecastDay> forecast;
    private long timestamp;

    // Getters e Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public CurrentWeather getCurrent() { return current; }
    public void setCurrent(CurrentWeather current) { this.current = current; }

    public AiSummary getAiSummary() { return aiSummary; }
    public void setAiSummary(AiSummary aiSummary) { this.aiSummary = aiSummary; }

    public List<ForecastDay> getForecast() { return forecast; }
    public void setForecast(List<ForecastDay> forecast) { this.forecast = forecast; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    // Classes internas
    public static class CurrentWeather {
        private Double temperature;
        private String condition;
        private Integer humidity;
        private Double windKph;
        private Double feelsLike;

        public Double getTemperature() { return temperature; }
        public void setTemperature(Double temperature) { this.temperature = temperature; }

        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }

        public Integer getHumidity() { return humidity; }
        public void setHumidity(Integer humidity) { this.humidity = humidity; }

        public Double getWindKph() { return windKph; }
        public void setWindKph(Double windKph) { this.windKph = windKph; }

        public Double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(Double feelsLike) { this.feelsLike = feelsLike; }
    }

    public static class AiSummary {
        private String text;
        private String recommendation;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }

        public String getRecommendation() { return recommendation; }
        public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    }

    public static class ForecastDay {
        private String date;
        private Double maxTemp;
        private Double minTemp;
        private String condition;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public Double getMaxTemp() { return maxTemp; }
        public void setMaxTemp(Double maxTemp) { this.maxTemp = maxTemp; }

        public Double getMinTemp() { return minTemp; }
        public void setMinTemp(Double minTemp) { this.minTemp = minTemp; }

        public String getCondition() { return condition; }
        public void setCondition(String condition) { this.condition = condition; }
    }
}