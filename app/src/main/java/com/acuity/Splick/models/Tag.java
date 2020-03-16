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
      private static final String TAG = "Data";
        ArrayList<String> tags;

        public Data() {
        }

        public ArrayList<String> getTags() {
            Log.d(TAG, "getTags: "+tags.get(0));
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }
    }
}
