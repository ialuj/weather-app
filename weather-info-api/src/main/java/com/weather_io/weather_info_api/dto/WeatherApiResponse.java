package com.weather_io.weather_info_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class WeatherApiResponse implements Serializable {
    private Location location;
    private Current current;
    private Ai ai;
    private Forecast forecast;

    // Getters e Setters
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Current getCurrent() { return current; }
    public void setCurrent(Current current) { this.current = current; }

    public Ai getAi() { return ai; }
    public void setAi(Ai ai) { this.ai = ai; }

    public Forecast getForecast() { return forecast; }
    public void setForecast(Forecast forecast) { this.forecast = forecast; }

    // Classes internas
    public static class Location {
        private String name;
        private String country;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }

    public static class Current {
        @JsonProperty("temp_c")
        private Double tempC;
        private Condition condition;
        private Integer humidity;
        @JsonProperty("wind_kph")
        private Double windKph;
        @JsonProperty("feelslike_c")
        private Double feelsLikeC;

        public Double getTempC() { return tempC; }
        public void setTempC(Double tempC) { this.tempC = tempC; }

        public Condition getCondition() { return condition; }
        public void setCondition(Condition condition) { this.condition = condition; }

        public Integer getHumidity() { return humidity; }
        public void setHumidity(Integer humidity) { this.humidity = humidity; }

        public Double getWindKph() { return windKph; }
        public void setWindKph(Double windKph) { this.windKph = windKph; }

        public Double getFeelsLikeC() { return feelsLikeC; }
        public void setFeelsLikeC(Double feelsLikeC) { this.feelsLikeC = feelsLikeC; }
    }

    public static class Condition {
        private String text;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    public static class Ai {
        private String summary;
        private String recommendation;

        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }

        public String getRecommendation() { return recommendation; }
        public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
    }

    public static class Forecast {
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() { return forecastday; }
        public void setForecastday(List<ForecastDay> forecastday) { this.forecastday = forecastday; }
    }

    public static class ForecastDay {
        private String date;
        private Day day;

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }

        public Day getDay() { return day; }
        public void setDay(Day day) { this.day = day; }
    }

    public static class Day {
        @JsonProperty("maxtemp_c")
        private Double maxTempC;
        @JsonProperty("mintemp_c")
        private Double minTempC;
        private Condition condition;

        public Double getMaxTempC() { return maxTempC; }
        public void setMaxTempC(Double maxTempC) { this.maxTempC = maxTempC; }

        public Double getMinTempC() { return minTempC; }
        public void setMinTempC(Double minTempC) { this.minTempC = minTempC; }

        public Condition getCondition() { return condition; }
        public void setCondition(Condition condition) { this.condition = condition; }
    }
}