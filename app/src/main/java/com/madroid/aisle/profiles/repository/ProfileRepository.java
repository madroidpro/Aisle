package com.madroid.aisle.profiles.repository;

import com.madroid.aisle.profiles.models.Profile;
import com.madroid.aisle.profiles.rest.ProfileClient;

import androidx.lifecycle.MutableLiveData;

public class ProfileRepository {
    private static ProfileRepository mRepository;
    public final ProfileClient mClient;

    private ProfileRepository() {
        mClient = ProfileClient.getInstance();
    }

    public static ProfileRepository getInstance() {
        if (mRepository == null) {
            mRepository = new ProfileRepository();
        }
        return mRepository;
    }

    public MutableLiveData<Profile> getProfile(String token) {
        return mClient.getProfiles(token);
    }
}
