package br.com.grso.domain.exception;

import java.time.LocalDateTime;

public class ExceptionDetails {
    private final LocalDateTime timestamp;
    private final Integer status;
    private final String message;
    private final String path;

    public ExceptionDetails(LocalDateTime timestamp, Integer status, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
