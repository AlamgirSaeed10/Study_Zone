package com.acuity.Splick.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    Integer data;

    public Register() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
