package com.software.acuity.splick.models.partnerships;

public class AffiliateDealStat {
    String business_id = "";
    String bus_name = "";
    String views = "";
    String orders = "";
    String revenue = "";

    public AffiliateDealStat(){

    }

    public AffiliateDealStat(String business_id, String bus_name, String views, String orders, String revenue) {
        this.business_id = business_id;
        this.bus_name = bus_name;
        this.views = views;
        this.orders = orders;
        this.revenue = revenue;
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

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
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
}
