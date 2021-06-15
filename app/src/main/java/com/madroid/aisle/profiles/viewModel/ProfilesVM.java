package com.madroid.aisle.profiles.viewModel;

import com.madroid.aisle.profiles.models.Profile;
import com.madroid.aisle.profiles.repository.ProfileRepository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfilesVM extends ViewModel {

    private final ProfileRepository mRepository;

    public ProfilesVM() {
        mRepository = ProfileRepository.getInstance();
    }


    public MutableLiveData<Profile> getProfile(String token){
        return mRepository.getProfile(token);
    }

}
