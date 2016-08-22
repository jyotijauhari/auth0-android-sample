package com.auth0.customizinglockdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.Theme;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.auth0.customizinglockdemo.R;


public class LockActivity extends Activity {

    private Lock mLock;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Theme customizedLockTheme = Theme.newBuilder()
                .withDarkPrimaryColor(R.color.colorPrimaryDark)
                .withHeaderColor(R.color.colorAccent)
                .withHeaderTitle(R.string.app_name)
                .withHeaderTitleColor(R.color.headerTitleColor)
                .withHeaderColor(R.color.headerColor)
                .withPrimaryColor(R.color.colorPrimary)
                .withHeaderLogo(R.drawable.custom_logo)
                .build();

        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        this.mLock = Lock.newBuilder(auth0, mCallback)
                .withTheme(customizedLockTheme)
                .build();
        mLock.onCreate(this);
        startActivity(this.mLock.newIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your own Activity code
        mLock.onDestroy(this);
        mLock = null;
    }

    private final LockCallback mCallback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            Toast.makeText(getApplicationContext(), "Log In - Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        @Override
        public void onCanceled() {
            Toast.makeText(getApplicationContext(), "Log In - Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(LockException error) {
            Toast.makeText(getApplicationContext(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
        }
    };

}

