package com.software.acuity.splick.models.business_deals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Business implements Serializable {
    String id = "";
    String full_name = "";
    String user_role = "";
    String user_name = "";
    String user_email = "";
    String user_pass = "";
    String user_bio = "";
    String user_location = "";
    String location_lat = "";
    String location_lng = "";
    String insta_userid = "";
    String insta_username = "";
    String youtube_username = "";
    String snapchat_username = "";
    String website_url = "";
    String industry_tags = "";
    String user_profile = "";
    String user_banner = "";
    String user_status = "";
    String add_time = "";
    String thumbnail_url = "";
    String profile_path = "";
    List<BusinessDeal> businessDeals = new ArrayList<>();

    public Business(){}

    public Business(String id,
                    String full_name,
                    String user_role,
                    String user_name,
                    String user_email,
                    String user_pass,
                    String user_bio,
                    String user_location,
                    String location_lat,
                    String location_lng,
                    String insta_userid,
                    String insta_username,
                    String youtube_username,
                    String snapchat_username,
                    String website_url,
                    String industry_tags,
                    String user_profile,
                    String user_banner,
                    String user_status,
                    String add_time,
                    String thumbnail_url,
                    String profile_path,
                    List<BusinessDeal> businessDeals) {
        this.id = id;
        this.full_name = full_name;
        this.user_role = user_role;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_pass = user_pass;
        this.user_bio = user_bio;
        this.user_location = user_location;
        this.location_lat = location_lat;
        this.location_lng = location_lng;
        this.insta_userid = insta_userid;
        this.insta_username = insta_username;
        this.youtube_username = youtube_username;
        this.snapchat_username = snapchat_username;
        this.website_url = website_url;
        this.industry_tags = industry_tags;
        this.user_profile = user_profile;
        this.user_banner = user_banner;
        this.user_status = user_status;
        this.add_time = add_time;
        this.thumbnail_url = thumbnail_url;
        this.businessDeals = businessDeals;
        this.profile_path = profile_path;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_bio() {
        return user_bio;
    }

    public void setUser_bio(String user_bio) {
        this.user_bio = user_bio;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }

    public String getInsta_userid() {
        return insta_userid;
    }

    public void setInsta_userid(String insta_userid) {
        this.insta_userid = insta_userid;
    }

    public String getInsta_username() {
        return insta_username;
    }

    public void setInsta_username(String insta_username) {
        this.insta_username = insta_username;
    }

    public String getYoutube_username() {
        return youtube_username;
    }

    public void setYoutube_username(String youtube_username) {
        this.youtube_username = youtube_username;
    }

    public String getSnapchat_username() {
        return snapchat_username;
    }

    public void setSnapchat_username(String snapchat_username) {
        this.snapchat_username = snapchat_username;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public String getIndustry_tags() {
        return industry_tags;
    }

    public void setIndustry_tags(String industry_tags) {
        this.industry_tags = industry_tags;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getUser_banner() {
        return user_banner;
    }

    public void setUser_banner(String user_banner) {
        this.user_banner = user_banner;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public List<BusinessDeal> getBusinessDeals() {
        return businessDeals;
    }

    public void setBusinessDeals(List<BusinessDeal> businessDeals) {
        this.businessDeals = businessDeals;
    }
}
