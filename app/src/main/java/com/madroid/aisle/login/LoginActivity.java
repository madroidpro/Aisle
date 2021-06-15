package com.madroid.aisle.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;

import com.madroid.aisle.R;
import com.madroid.aisle.databinding.ActivityLoginBinding;
import com.madroid.aisle.login.models.loginRequest;
import com.madroid.aisle.login.models.loginResponse;
import com.madroid.aisle.login.models.otpResponse;
import com.madroid.aisle.login.viewModel.LoginVM;
import com.madroid.aisle.profiles.activities.MainActivity;
import com.madroid.aisle.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    private LoginVM mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initViewModel();
        initViews();

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginVM.class);
    }

    private void initViews() {
        mBinding.setPresenter(this);
        mBinding.setLifecycleOwner(this);
    }

    /*Method call on continue button clicked*/
    public void loginClicked() {
        /*check if phone numbers are filled*/
        if (mBinding.phoneCode.getText() != null && !mBinding.phoneCode.getText().toString().isEmpty()
                && mBinding.phoneNumber.getText() != null && !mBinding.phoneNumber.getText().toString().isEmpty()) {
            String phoneNumber = mBinding.phoneCode.getText().toString() + mBinding.phoneNumber.getText().toString();
            /*call login API*/
            loginRequest request = new loginRequest();
            request.setNumber(phoneNumber);
            mViewModel.login(request).observe(this, new Observer<loginResponse>() {
                @Override
                public void onChanged(loginResponse loginResponse) {
                    if (loginResponse.getStatus()) {
                        requestOTP(phoneNumber);
                    }
                }
            });

        }
    }

    /*Button call back from OTP screen submit*/
    public void submitOtp() {
        if (mBinding.phoneCode.getText() != null && !mBinding.phoneCode.getText().toString().isEmpty()
                && mBinding.phoneNumber.getText() != null && !mBinding.phoneNumber.getText().toString().isEmpty()) {
            String phoneNumber = mBinding.phoneCode.getText().toString() + mBinding.phoneNumber.getText().toString();
            loginRequest request = new loginRequest();
            request.setNumber(phoneNumber);

            if (mBinding.phoneOtp.getText() != null && !mBinding.phoneOtp.getText().toString().isEmpty()) {
                request.setOtp(mBinding.phoneOtp.getText().toString());
                verifyOtp(request);
            }
        }
    }

    private void verifyOtp(loginRequest request) {
        mViewModel.verifyOtp(request).observe(this, new Observer<otpResponse>() {
            @Override
            public void onChanged(otpResponse otpResponse) {
                if (otpResponse != null && !otpResponse.getToken().isEmpty()) {
                    //TODO save token for later use
                    loginSuccess(otpResponse.getToken());
                }

            }
        });
    }

    /*Show OTP Screen*/
    private void requestOTP(String phoneNumber) {
        mBinding.phoneOtp.setVisibility(View.VISIBLE);
        mBinding.phoneNumberEdit.setVisibility(View.VISIBLE);
        mBinding.phoneNumberEdit.setText(phoneNumber);
        mBinding.submitOtp.setVisibility(View.VISIBLE);
        mBinding.tv2.setText(R.string.enter_otp);
        mBinding.counter.setVisibility(View.VISIBLE);
        mBinding.phoneNumber.setVisibility(View.INVISIBLE);
        mBinding.phoneCode.setVisibility(View.INVISIBLE);
        mBinding.tv1.setVisibility(View.INVISIBLE);
        mBinding.submit.setVisibility(View.INVISIBLE);
        startCounter();

    }

    private void startCounter() {
        mBinding.counter.setBase(SystemClock.elapsedRealtime() + 60_000);
        mBinding.counter.start();
        mBinding.counter.setOnChronometerTickListener(chronometer -> {
            if (chronometer.getText().toString().equalsIgnoreCase("00:00")) {  // Stop countdown on 0
                mBinding.counter.stop();
                //TODO resend OTP method can be called here later
            }
        });
    }

    /*Open Notes Screen */
    private void loginSuccess(String token) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOKEN, token);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}