package com.madroid.aisle.profiles.rest;

import com.madroid.aisle.profiles.models.Profile;
import com.madroid.aisle.utils.RetrofitClient;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileClient {
    private static ProfileClient mClient;

    public static ProfileClient getInstance() {
        if (mClient == null) {
            mClient = new ProfileClient();
        }
        return mClient;
    }

    public MutableLiveData<Profile> getProfiles(String token) {
        MutableLiveData<Profile> profile = new MutableLiveData<>();
        RetrofitClient client = RetrofitClient.getInstance();
        client.mApi.getProfiles(token).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                profile.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                //TODO Handle api failure case
            }
        });
        return profile;
    }
}
