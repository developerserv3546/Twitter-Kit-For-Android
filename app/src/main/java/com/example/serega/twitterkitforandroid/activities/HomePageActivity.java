package com.example.serega.twitterkitforandroid.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serega.twitterkitforandroid.R;
import com.example.serega.twitterkitforandroid.utils.ImageLoader;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

public class HomePageActivity extends AppCompatActivity {

    private LinearLayout profileBannerLayout;
    private ImageView profileImageView;
    private TextView userNameView;
    private TextView userScreenNameView;
    private TextView profileCreatedView;
    private TextView descriptionView;
    private TextView locationView;
    private TextView followingView;
    private TextView followersView;
    private TextView favouritesCountView;
    private TextView listedCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerLayout = navigationView.getHeaderView(0);
        profileBannerLayout = headerLayout.findViewById(R.id.profile_banner);
        profileImageView = headerLayout.findViewById(R.id.profile_image);
        userNameView = headerLayout.findViewById(R.id.user_name);
        userScreenNameView = headerLayout.findViewById(R.id.user_screen_name);
        descriptionView = headerLayout.findViewById(R.id.tx_description);
        locationView = headerLayout.findViewById(R.id.tx_location);
        profileCreatedView = headerLayout.findViewById(R.id.tx_profile_created);
        followingView = headerLayout.findViewById(R.id.tx_following);
        followersView = headerLayout.findViewById(R.id.tx_followers);
        favouritesCountView = headerLayout.findViewById(R.id.tx_favourites);
        listedCountView = headerLayout.findViewById(R.id.tx_lists);

        findViewById(R.id.action_new_twit).setOnClickListener(listener);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Call<User> userData = TwitterCore.getInstance()
                .getApiClient()
                .getAccountService()
                .verifyCredentials(true, false, false);

        userData.enqueue(userCallback);
    }

    private final Callback<User> userCallback = new Callback<User>() {
        @Override
        public void success(Result<User> result) {
            User user = result.data;
            initUserDetails(user);
        }

        @Override
        public void failure(TwitterException exception) {
            Toast.makeText(HomePageActivity.this,
                    getString(R.string.error), Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    };

    private void initUserDetails(User user) {

        ImageLoader.setProfileBanner(profileBannerLayout, user.profileBannerUrl);
        ImageLoader.setProfileImage(profileImageView, user.profileImageUrl);

        userNameView.setText(user.name);
        userScreenNameView.setText(user.screenName);

        String description = getString(R.string.description, user.description);
        descriptionView.setText(description);

        String location = getString(R.string.location, user.location);
        locationView.setText(location);

        String createdAt = getString(R.string.created_at, user.createdAt);
        profileCreatedView.setText(createdAt);

        String friendsCount = getString(R.string.following_count, user.friendsCount);
        followingView.setText(friendsCount);

        String favouritesCount = getString(R.string.favourites_count, user.favouritesCount);
        favouritesCountView.setText(favouritesCount);

        String followersCount = getString(R.string.followers_count, user.followersCount);
        followersView.setText(followersCount);

        String listedCount = getString(R.string.listed_count, user.listedCount);
        listedCountView.setText(listedCount);

    }

    private final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.action_new_twit:
                    break;
            }

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

}
