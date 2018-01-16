package com.mj.entity;

public class SocketMessage {
    public String message;
    public String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}