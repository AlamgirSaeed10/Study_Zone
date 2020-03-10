package com.software.acuity.splick.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final String PREFERENCE_NAME = "splick";
    public static final String APP_INIT_KEY = "app_init";
    public static final String FB_PROFILE_KEY = "fb_profile";
    public static final String APP_TURN = "app_turn";
    public static final String AFFILIATE_ID = "aff_id";

    //REQUEST CODES
    public static final int GALLERY_INTENT_REQUEST_CODE = 1000;
    public static final int CAMERA_INTENT_REQUEST_CODE = 1001;

    //Business Type (affiliate, business)
    public static final String USER_TYPE = "user_type";
    public static final String USER_ID = "user_id";
    public static final String LOGIN_STATUS = "login_status";
    public static final String USER_JSON_OBJECT = "user_json_object";

    //Api Endpoints
    public static final String BASE_URL = "https://splick.aqtdemos.com/api/";

    //Common Endpoints
    public static final String API_VIEW_PORTFOLIO = "v1_portfolio/view";
    public static final String API_ADD_PORTFOLIO = "v1_portfolio/add";
    public static final String API_USER_AUTHENTICATE = "v1_user/authenticate";
    public static final String API_USER_REGISTER = "v1_user/register";
    public static final String API_PROFILE_UPDATE = "v1_user/update";
    public static final String API_FORGET_PASSWORD = "v1_user/password";

    //Call it for affiliate
    public static final String API_GET_BUSINESS_TAGS = "business/tags";
    //CAll it for business
    public static final String API_GET_AFFILIATE_TAGS = "affiliate/tags";

    //Business Endpoints
    public static final String API_BUSINESS_EDIT_DEAL = "v1_deal/edit";
    public static final String API_BUSINESS_SIGN_UP_DEALS = "v1_deal/signups/{user_id}";
    public static final String API_BUSINESS_ADD_CLICK = "v1_click/add";
    public static final String API_BUSINESS_VIEW_SINGLE_POST = "v1_post/view-single";
    public static final String API_BUSINESS_VIEW_AFFILIATE = "view/affiliate";
    public static final String API_BUSINESS_STAT_BUSINESS = "v1_stat/business";
    public static final String API_BUSINESS_AFFILIATE_BUSINESS = "v1_stat/affiliations/business";

    public static final String API_BUSINESS_ADD_DEAL = "v1_deal/add";
    public static final String API_BUSINESS_CLICK_DEALS = "v1_click/deals/";
    public static final String API_BUSINESS_ORDER_DEALS = "v1_order/deals/";
    public static final String API_BUSINESS_VIEW_POSTS = "view/posts";
    public static final String API_BUSINESS_ADD_POST = "add-post";
    public static final String API_BUSINESS_ADD_COMMENT = "v1_post/add-comment";
    public static final String API_BUSINESS_BUS_PAYOUT_VIEW = "v1_buspayout/view";
    public static final String API_BUSINESS_AFFILIATE_LIST = "v1_afflist/affiliate-list";
    public static final String API_BUSINESS_OFFER_DEAL = "v1_offer/deal";
    public static final String API_BUSINESS_MY_DEALS = "v1_deal/my-deals";
    public static final String API_BUSINESS_OFFER_VIEW = "v1_offer/view";
    public static final String API_BUSINESS_AFFILIATE_WISE_EARNING = "v1_stat/business";

    //Affiliate Endpoints
    public static final String API_AFFILIATE_SOCIAL_ADD = "v1_social/add";
    public static final String API_AFFILIATE_STAT_AFFILIATE = "v1_stat/affiliate";
    public static final String API_AFFILIATE_STAT_AFFILIATIONS_AFFILIATE = "v1_stat/affiliations/affiliate";

    public static final String API_AFFILIATE_SOCIAL_VIEW = "v1_social/view";
    public static final String API_AFFILIATE_POST_VIEW = "v1_post/view";
    public static final String API_AFFILIATE_POST_VIEW_SINGLE = "v1_post/view/single";
    public static final String API_AFFILIATE_POST_COMMENT = "v1_post/comment";
    public static final String API_AFFILIATE_PAYOUT_REQUEST = "v1_payout/request";
    public static final String API_AFFILIATE_PAYOUT_VIEW = "affiliate/commission";
    public static final String API_BUSINESS_PAYOUT_VIEW = "business/commission";
    public static final String API_GET_COMMISSION_BY_BUSINESSES = "v1_stat/affiliate";
    public static final String API_AFFILIATE_VIEW_BUSINESS = "view/business";
    public static final String API_AFFILIATE_BUSINESS_LIST = "business-list";
    public static final String API_AFFILIATE_USER_FOLLOW = "v1_user/follow";
    public static final String API_AFFILIATE_USER_UNFOLLOW = "v1_user/unfollow";
    public static final String API_AFFILIATE_AFF_OFFER_VIEW = "v1_affoffer/view";
    public static final String API_AFFILIATE_AFF_OFFER_ACCEPT = "v1_affoffer/accept";
    public static final String API_AFFILIATE_APP_OFFER_REJECT = "v1_affoffer/reject";
    public static final String API_AFFILIATE_STATS_DEALS_DISCOVER = "v1_stat/deals/stats";
    public static final String API_AFFILIATE_DEAL_DETAIL = "v1_deal/view-deal";
    public static final String API_AFFILIATE_OFFER_SINGLE = "v1_offer/single";
    public static final String API_AFFILIATE_PAYOUT_THRESHOLD = "v1_setting";
    public static final String API_BUSINESS_BANK_ADD = "v1_bank/add";
    public static final String API_BUSINESS_ADD_MATERIAL = "v1_deal/add-media";
    public static final String API_BUSINESS_VIEW_MATERIAL = "v1_deal/view-media";

    public static String jsonObject = "";

    /*
        public static String dateInSimpleFormat(Date _date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy - h:mm aa");
            return simpleDateFormat.format(_date);
        }
    */
    public static String dateInSimpleFormatReveresed(Date _date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd - h:mm aa");
        return simpleDateFormat.format(_date);
    }
}
