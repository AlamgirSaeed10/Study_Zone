package com.software.acuity.splick.models.about;

public class SocialDeal {

    String id = "";
    String user_id = "";
    String account_type = "";
    String account_id = "";
    String account_link = "";
    String total_followers = "";

    public SocialDeal() {
    }

    public SocialDeal(String id, String user_id, String account_type, String account_id, String account_link, String total_followers) {
        this.id = id;
        this.user_id = user_id;
        this.account_type = account_type;
        this.account_id = account_id;
        this.account_link = account_link;
        this.total_followers = total_followers;
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

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_link() {
        return account_link;
    }

    public void setAccount_link(String account_link) {
        this.account_link = account_link;
    }

    public String getTotal_followers() {
        return total_followers;
    }

    public void setTotal_followers(String total_followers) {
        this.total_followers = total_followers;
    }
}