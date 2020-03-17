package com.acuity.Splick.apiConfiguration.ApiInterfaces;

import com.acuity.Splick.models.Auth;
import com.acuity.Splick.models.Register;
import com.acuity.Splick.models.Tag;

import java.io.File;
import java.nio.file.spi.FileTypeDetector;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public interface ApiInterface {
    //to check login
    @POST("v1_user/authenticate")
    @FormUrlEncoded
    Call<Auth>getAuth(@Field("user_email") String mail, @Field("user_pass") String pass);
    //to register mail
    @POST("v1_user/register")
    @FormUrlEncoded
    Call<Register> signUp(@Field("full_name") String user_name, @Field("user_email")String user_mail, @Field("user_pass")String user_pass, @Field("user_role")String user_role);
    //Update userAbout
    @POST("v1_user/update")
    @FormUrlEncoded
    Call<Register> update(@FieldMap HashMap<String,Object> mapUpdate);
    @GET("business/tags")
    Call<Tag>getTags();

    @Multipart
    @FormUrlEncoded
    @POST()
    Call<Register>addPortfolio(@Field("user_id")String userID, @Field("title")String title, @Part("media_item") File file);

}
