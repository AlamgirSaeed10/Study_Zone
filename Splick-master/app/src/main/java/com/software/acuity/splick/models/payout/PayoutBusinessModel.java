package com.software.acuity.splick.models.payout;

public class PayoutBusinessModel {

    String business_id = "";
    String bus_name = "";
    String views = "0";
    String orders = "0";
    String revenue = "0";
    String commission = "0";
    String commissionForWithdraw = "0";
    boolean selected = false;

    public String getCommissionForWithdraw() {
        return commissionForWithdraw;
    }

    public void setCommissionForWithdraw(String commissionForWithdraw) {
        this.commissionForWithdraw = commissionForWithdraw;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public PayoutBusinessModel() {
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

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }
}
