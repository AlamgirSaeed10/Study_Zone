package com.software.acuity.splick.models;

import java.io.Serializable;

public class PortfolioModel implements Serializable {

    String id = "";
    String user_id = "";
    String title = "";
    String description = "";
    String add_time = "";
    String file_url = "";
    String fullName = "";

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public PortfolioModel() {
    }

    public PortfolioModel(String id, String user_id, String title, String description,
                          String add_time, String file_url) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.add_time = add_time;
        this.file_url = file_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
