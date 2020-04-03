package com.blesk.accountservice.DTO;

public class ErrorMessage {

    private String timestamp;

    private String message;

    private boolean error;

    public ErrorMessage() {
    }

    public ErrorMessage(String timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
        this.error = true;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}