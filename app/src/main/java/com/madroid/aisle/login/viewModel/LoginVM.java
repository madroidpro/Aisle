package com.madroid.aisle.login.viewModel;

import com.madroid.aisle.login.models.loginRequest;
import com.madroid.aisle.login.models.loginResponse;
import com.madroid.aisle.login.models.otpResponse;
import com.madroid.aisle.login.repository.LoginRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginVM extends ViewModel {

    private final LoginRepository mRepository;

    public LoginVM() {
        mRepository = LoginRepository.getInstance();
    }

    public MutableLiveData<loginResponse> login(loginRequest request) {

        return mRepository.login(request);

    }

    public MutableLiveData<otpResponse>verifyOtp(loginRequest request) {
        return mRepository.verifyOtp(request);
    }
}
