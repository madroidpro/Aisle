package com.madroid.aisle.utils;

import com.madroid.aisle.login.models.loginRequest;
import com.madroid.aisle.login.models.loginResponse;
import com.madroid.aisle.login.models.otpResponse;
import com.madroid.aisle.profiles.models.Profile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AisleApi {

    @Headers("Content-Type: application/json")
    @POST("users/phone_number_login")
    Call<loginResponse> login(@Body loginRequest request);


    @Headers("Content-Type: application/json")
    @POST("users/verify_otp")
    Call<otpResponse> verifyOTP(@Body loginRequest request);


    @Headers("Content-Type: application/json")
    @GET("users/test_profile_list")
    Call<Profile> getProfiles(@Header("Authorization") String auth);
}
