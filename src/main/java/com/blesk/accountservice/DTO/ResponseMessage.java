package com.blesk.accountservice.DTO;

public class ResponseMessage {

    private String timestamp;

    private String message;

    private boolean error;

    public ResponseMessage() {
    }

    public ResponseMessage(String timestamp, String message, boolean error) {
        this.timestamp = timestamp;
        this.message = message;
        this.error = error;
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
        return "ResponseMessage{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}