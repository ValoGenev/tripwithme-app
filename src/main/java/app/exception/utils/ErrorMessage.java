package app.exception.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class ErrorMessage {
    private String message;
    private Integer statusCode;

    public ErrorMessage(String message, Integer httpStatus) {
        this.message = message;
        this.statusCode = httpStatus;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("statusCode")
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getStatusCode(), that.getStatusCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getStatusCode());
    }
}