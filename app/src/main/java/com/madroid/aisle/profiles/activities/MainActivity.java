package com.madroid.aisle.profiles.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;

import com.madroid.aisle.R;
import com.madroid.aisle.databinding.ActivityMainBinding;
import com.madroid.aisle.profiles.models.MainProfile;
import com.madroid.aisle.profiles.models.Profile;
import com.madroid.aisle.profiles.viewModel.ProfilesVM;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.madroid.aisle.utils.Constants.TOKEN;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private String mToken = "";
    private ProfilesVM mProfileVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mProfileVM = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ProfilesVM.class);
        getIntentData();
    }

    private void getIntentData() {
        if (!getIntent().getExtras().isEmpty()) {
            mToken = getIntent().getExtras().getString(TOKEN);
            Log.d(TAG, "getIntentData: " + mToken);
            //TODO show a loader
            //TODO call profile api
            mProfileVM.getProfile(mToken).observe(this, profile -> {
                //TODO Hide loader
                fillData(profile);
            });

        }
    }

    private void fillData(Profile profile) {
        MainProfile mainProfile = profile.getInvites().getProfiles().get(0);
        if (mainProfile != null) {
            String nameAndAge = mainProfile.getGeneralInformation().getFirstName() + " ," + mainProfile.getGeneralInformation().getAge();
            mBinding.mainImage.imageName.setText(nameAndAge);
            circularBorderImage(mainProfile.getPhotos().get(0).getPhoto());
        }
        mBinding.bottomImage1.imageName.setText(profile.getLikes().getProfiles().get(0).getFirstName());
        mBinding.bottomImage2.imageName.setText(profile.getLikes().getProfiles().get(1).getFirstName());
        // Set images and blur to sub profiles
        blurImages(profile.getLikes().getProfiles().get(0).getAvatar(), mBinding.bottomImage1.image1);
        blurImages(profile.getLikes().getProfiles().get(1).getAvatar(), mBinding.bottomImage2.image1);
    }

    /*To load main profile image*/
    private void circularBorderImage(String photo) {
        Transformation roundedCornersTransformation = new RoundedCornersTransformation(10, 10);
        Picasso.get()
                .load(photo)
                .transform(roundedCornersTransformation)
                .fit().centerCrop(Gravity.CENTER)
                .into(mBinding.mainImage.image1);
    }

    /*To Blur images*/
    private void blurImages(String photo, ImageView view) {
        BlurTransformation transformation = new BlurTransformation(this);
        Transformation roundedCornersTransformation = new RoundedCornersTransformation(5, 5);
        Picasso.get()
                .load(photo)
                .transform(transformation)
                .transform(roundedCornersTransformation)
                .into(view);

    }
}