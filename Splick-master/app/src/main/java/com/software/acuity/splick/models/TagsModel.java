package com.software.acuity.splick.models;

import java.io.Serializable;

public class TagsModel implements Serializable {

    String tagName;
    boolean tagStatus;

    public TagsModel(String tagName, boolean tagStatus) {
        this.tagName = tagName;
        this.tagStatus = tagStatus;
    }

    public TagsModel() {

    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public boolean getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(boolean tagStatus) {
        this.tagStatus = tagStatus;
    }
}
