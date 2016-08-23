package com.dragovorn.dragonbot.api.twitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * *************************************************************************
 * (c) Dragovorn 2016. This file was created by Andrew at 1:12 AM.
 * as of 8/23/16 the project dragonbot is Copyrighted.
 * *************************************************************************
 */
public class Error {

    @JsonProperty("error")
    private String statusText;
    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int code;

    @Override
    public String toString() {
        return "Error{statusText=\'" + statusText + "\', statusCode=" + code + ", message\'" + message + "\'}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Error that = (Error) object;

        if (code != that.code) {
            return false;
        }

        return (statusText != null ? !statusText.equals(that.statusText) : that.statusText != null) && (!(message != null ? !message.equals(that.message) : that.message != null));
    }

    @Override
    public int hashCode() {
        int result = statusText != null ? statusText.hashCode() : 0;

        result = 31 * result + code;
        result = 31 * result + (message != null ? message.hashCode() : 0);

        return result;
    }

    public String getStatusText() {
        return this.statusText;
    }

    public int getStatusCode() {
        return this.code;
    }

    public void setStatusCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}