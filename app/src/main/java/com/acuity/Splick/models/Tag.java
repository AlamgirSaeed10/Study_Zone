package com.acuity.Splick.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tag {
        @SerializedName("success")
        @Expose
        private boolean success;
        @SerializedName("data")
        @Expose
        private List<Data> data = new ArrayList<Data>();

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
    public class Data{
        @SerializedName("tag_id")
        @Expose
        private long tagId;
        @SerializedName("tag_string")
        @Expose
        private String tagString;

        public long getTagId() {
            return tagId;
        }

        public void setTagId(long tagId) {
            this.tagId = tagId;
        }

        public String getTagString() {
            return tagString;
        }

        public void setTagString(String tagString) {
            this.tagString = tagString;
        }
    }


}


