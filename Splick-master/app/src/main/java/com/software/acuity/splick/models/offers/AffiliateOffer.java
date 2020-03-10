package com.software.acuity.splick.models.offers;

import java.io.Serializable;

public class AffiliateOffer implements Serializable {
     String id = "";
     String business_id = "";
     String affiliate_id = "";
     String status = "";
     String info = "";
     String deal_id = "";
     String add_time = "";
     String business_name = "";
     String deal_title = "";

     public AffiliateOffer(){}

    public AffiliateOffer(String id, String business_id, String affiliate_id, String status, String info, String deal_id, String add_time, String business_name, String deal_title) {
        this.id = id;
        this.business_id = business_id;
        this.affiliate_id = affiliate_id;
        this.status = status;
        this.info = info;
        this.deal_id = deal_id;
        this.add_time = add_time;
        this.business_name = business_name;
        this.deal_title = deal_title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getAffiliate_id() {
        return affiliate_id;
    }

    public void setAffiliate_id(String affiliate_id) {
        this.affiliate_id = affiliate_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getDeal_title() {
        return deal_title;
    }

    public void setDeal_title(String deal_title) {
        this.deal_title = deal_title;
    }
}
