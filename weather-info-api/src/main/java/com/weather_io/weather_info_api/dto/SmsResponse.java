package com.weather_io.weather_info_api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SmsResponse implements Serializable {
    private String to;
    private String message;
    private String status;
    private LocalDateTime timestamp;

    public SmsResponse(String to, String message, String sent) {
        this.setTo(to);
        this.setMessage(message);
        this.setStatus(sent);
        this.setTimestamp(LocalDateTime.now());
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
