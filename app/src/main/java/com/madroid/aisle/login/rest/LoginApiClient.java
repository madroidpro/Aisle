package com.madroid.aisle.login.rest;

import com.madroid.aisle.login.models.loginRequest;
import com.madroid.aisle.login.models.loginResponse;
import com.madroid.aisle.login.models.otpResponse;
import com.madroid.aisle.utils.RetrofitClient;

import androidx.lifecycle.MutableLiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginApiClient {

    private static LoginApiClient mApiClient;


    public static LoginApiClient getInstance() {
        if (mApiClient == null) {
            mApiClient = new LoginApiClient();
        }
        return mApiClient;
    }

    public MutableLiveData<loginResponse> login(loginRequest request) {
        MutableLiveData<loginResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient client = RetrofitClient.getInstance();

        client.mApi.login(request).enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                mutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                //TODO handle failure case
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<otpResponse> verifyOtp(loginRequest request) {
        MutableLiveData<otpResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient client = RetrofitClient.getInstance();
        client.mApi.verifyOTP(request).enqueue(new Callback<otpResponse>() {
            @Override
            public void onResponse(Call<otpResponse> call, Response<otpResponse> response) {
                mutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<otpResponse> call, Throwable t) {
                //TODO Handle Failure condition
            }
        });

        return mutableLiveData;
    }
}
