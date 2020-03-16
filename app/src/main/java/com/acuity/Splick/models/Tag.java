package com.acuity.Splick.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Tag {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

  public   class Data{
        ArrayList<String> strings;

      public ArrayList<String> getStrings() {
          return strings;
      }

      public void setStrings(ArrayList<String> strings) {
          this.strings = strings;
      }

      public Data() {

      }
  }
}
