package com.software.acuity.splick.models.payout;

public class Payout {

    String id = "";
    String affiliate_id = "";
    String business_id = "";
    String request_amount = "";
    String status = "";
    String add_time = "";
    String proceed_by = "";
    String psp_reference = "";
    String proceed_time = "";
    String affiliate_name = "";
    String business_name = "";


    public Payout(){}

    public Payout(String id, String affiliate_id, String business_id, String request_amount, String status, String add_time, String proceed_by, String psp_reference, String proceed_time, String affiliate_name, String business_name) {
        this.id = id;
        this.affiliate_id = affiliate_id;
        this.business_id = business_id;
        this.request_amount = request_amount;
        this.status = status;
        this.add_time = add_time;
        this.proceed_by = proceed_by;
        this.psp_reference = psp_reference;
        this.proceed_time = proceed_time;
        this.affiliate_name = affiliate_name;
        this.business_name = business_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAffiliate_id() {
        return affiliate_id;
    }

    public void setAffiliate_id(String affiliate_id) {
        this.affiliate_id = affiliate_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getRequest_amount() {
        return request_amount;
    }

    public void setRequest_amount(String request_amount) {
        this.request_amount = request_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getProceed_by() {
        return proceed_by;
    }

    public void setProceed_by(String proceed_by) {
        this.proceed_by = proceed_by;
    }

    public String getPsp_reference() {
        return psp_reference;
    }

    public void setPsp_reference(String psp_reference) {
        this.psp_reference = psp_reference;
    }

    public String getProceed_time() {
        return proceed_time;
    }

    public void setProceed_time(String proceed_time) {
        this.proceed_time = proceed_time;
    }

    public String getAffiliate_name() {
        return affiliate_name;
    }

    public void setAffiliate_name(String affiliate_name) {
        this.affiliate_name = affiliate_name;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }
}
