package com.acuity.Splick.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("data")
    private T data;
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;

    public ApiResponse(T data, String message, String status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    ApiResponse() {
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
