package com.software.acuity.splick.models.business_deals;

public class BusinessDeal {
    String id = "";
    String user_id = "";
    String deal_title = "";
    String deal_url = "";
    String deal_details = "";
    String comm_amount = "";
    String comm_type = "";
    String deal_status = "";
    String add_time = "";

    public BusinessDeal() {
    }

    public BusinessDeal(String id, String user_id, String deal_title, String deal_url, String deal_details, String comm_amount, String comm_type, String deal_status, String add_time) {
        this.id = id;
        this.user_id = user_id;
        this.deal_title = deal_title;
        this.deal_url = deal_url;
        this.deal_details = deal_details;
        this.comm_amount = comm_amount;
        this.comm_type = comm_type;
        this.deal_status = deal_status;
        this.add_time = add_time;
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

    public String getDeal_title() {
        return deal_title;
    }

    public void setDeal_title(String deal_title) {
        this.deal_title = deal_title;
    }

    public String getDeal_url() {
        return deal_url;
    }

    public void setDeal_url(String deal_url) {
        this.deal_url = deal_url;
    }

    public String getDeal_details() {
        return deal_details;
    }

    public void setDeal_details(String deal_details) {
        this.deal_details = deal_details;
    }

    public String getComm_amount() {
        return comm_amount;
    }

    public void setComm_amount(String comm_amount) {
        this.comm_amount = comm_amount;
    }

    public String getComm_type() {
        return comm_type;
    }

    public void setComm_type(String comm_type) {
        this.comm_type = comm_type;
    }

    public String getDeal_status() {
        return deal_status;
    }

    public void setDeal_status(String deal_status) {
        this.deal_status = deal_status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
