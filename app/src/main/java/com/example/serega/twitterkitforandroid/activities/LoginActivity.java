package com.example.serega.twitterkitforandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.serega.twitterkitforandroid.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);

        TwitterSession session = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();

        if (session == null) {
            loginButton = findViewById(R.id.login_button);
            loginButton.setCallback(twitterSessionCallback);
        } else {
            startHomePageActivity();
        }
    }

    private void startHomePageActivity() {
        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    private final Callback<TwitterSession> twitterSessionCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            startHomePageActivity();
        }

        @Override
        public void failure(TwitterException exception) {
            Toast.makeText(LoginActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            loginButton.onActivityResult(requestCode, resultCode, data);
        }

    }
}
