package com.bridge.pmt.api;

import com.bridge.pmt.models.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {

    @FormUrlEncoded
    @POST("api/v5/authenticate")
    Call<BaseResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );



    @FormUrlEncoded
    @POST("api/v5/getHourReportWeekly")
    Call<BaseResponse> getWeekReport(
            @Query("token") String token,

            @Field("id") int id
    );



    @POST("api/v5/activity")
    Call<BaseResponse> getUserActivity(
            @Query("token") String token

    );

    @FormUrlEncoded
    @POST("api/v5/list_user_project_name")
    Call<BaseResponse> getUserProjects(
            @Query("token") String token,
            @Field("id") int id
    );




}
