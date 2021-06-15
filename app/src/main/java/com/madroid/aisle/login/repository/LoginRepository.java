package com.madroid.aisle.login.repository;

import com.madroid.aisle.login.models.loginRequest;
import com.madroid.aisle.login.models.loginResponse;
import com.madroid.aisle.login.models.otpResponse;
import com.madroid.aisle.login.rest.LoginApiClient;

import androidx.lifecycle.MutableLiveData;

public class LoginRepository {

    private static  LoginRepository mRepository;
    private final LoginApiClient mClient;

    private LoginRepository(){
        mClient = LoginApiClient.getInstance();
    }

    public static LoginRepository getInstance(){
        if(mRepository == null){
            mRepository = new LoginRepository();
        }
        return mRepository;
    }

    public MutableLiveData<loginResponse> login(loginRequest request) {
        return mClient.login(request);
    }

    public MutableLiveData<otpResponse> verifyOtp(loginRequest request) {
        return mClient.verifyOtp(request);
    }
}
