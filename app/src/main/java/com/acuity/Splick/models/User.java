package com.acuity.Splick.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("user_role")
    @Expose
    private String userRole;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_bio")
    @Expose
    private String userBio;
    @SerializedName("user_location")
    @Expose
    private String userLocation;
    @SerializedName("location_lat")
    @Expose
    private String locationLat;
    @SerializedName("location_lng")
    @Expose
    private String locationLng;
    @SerializedName("insta_userid")
    @Expose
    private String instaUserid;
    @SerializedName("insta_username")
    @Expose
    private String instaUsername;
    @SerializedName("youtube_username")
    @Expose
    private String youtubeUsername;
    @SerializedName("snapchat_username")
    @Expose
    private String snapchatUsername;
    @SerializedName("website_url")
    @Expose
    private String websiteUrl;
    @SerializedName("industry_tags")
    @Expose
    private String industryTags;
    @SerializedName("user_profile")
    @Expose
    private String userProfile;
    @SerializedName("user_banner")
    @Expose
    private String userBanner;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("add_time")
    @Expose
    private String addTime;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("followers")
    @Expose
    private List<String> followers = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public String getInstaUserid() {
        return instaUserid;
    }

    public void setInstaUserid(String instaUserid) {
        this.instaUserid = instaUserid;
    }

    public String getInstaUsername() {
        return instaUsername;
    }

    public void setInstaUsername(String instaUsername) {
        this.instaUsername = instaUsername;
    }

    public String getYoutubeUsername() {
        return youtubeUsername;
    }

    public void setYoutubeUsername(String youtubeUsername) {
        this.youtubeUsername = youtubeUsername;
    }

    public String getSnapchatUsername() {
        return snapchatUsername;
    }

    public void setSnapchatUsername(String snapchatUsername) {
        this.snapchatUsername = snapchatUsername;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getIndustryTags() {
        return industryTags;
    }

    public void setIndustryTags(String industryTags) {
        this.industryTags = industryTags;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserBanner() {
        return userBanner;
    }

    public void setUserBanner(String userBanner) {
        this.userBanner = userBanner;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

}