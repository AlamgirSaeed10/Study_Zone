package com.software.acuity.splick.models.partnerships;

import java.io.Serializable;

public class AffiliateDeals implements Serializable {

    String id = "";
    String deal_title = "";
    String business_id = "";
    String bus_name = "";
    String comm_amount = "";
    String comm_type = "";
    String deal_url = "";
    String aff_time = "";
    String status = "";
    String deal_id = "";
    String deal_name = "";
    int views = 0;
    String orders = "";
    String revenue = "";
    String commission = "";
    String information = "";
    String affiliate_id= "";
    String aff_name = "";

    public String getAffiliate_id() {
        return affiliate_id;
    }

    public void setAffiliate_id(String affiliate_id) {
        this.affiliate_id = affiliate_id;
    }

    public String getAff_name() {
        return aff_name;
    }

    public void setAff_name(String aff_name) {
        this.aff_name = aff_name;
    }

    public AffiliateDeals() {
    }

    public AffiliateDeals(String id, String deal_title, String business_id, String bus_name, String comm_amount, String comm_type, String deal_url, String aff_time,
                          String status) {
        this.id = id;
        this.deal_title = deal_title;
        this.business_id = business_id;
        this.bus_name = bus_name;
        this.comm_amount = comm_amount;
        this.comm_type = comm_type;
        this.deal_url = deal_url;
        this.aff_time = aff_time;
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeal_title() {
        return deal_title;
    }

    public void setDeal_title(String deal_title) {
        this.deal_title = deal_title;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
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

    public String getDeal_url() {
        return deal_url;
    }

    public void setDeal_url(String deal_url) {
        this.deal_url = deal_url;
    }

    public String getAff_time() {
        return aff_time;
    }

    public void setAff_time(String aff_time) {
        this.aff_time = aff_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
