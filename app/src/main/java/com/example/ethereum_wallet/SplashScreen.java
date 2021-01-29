package com.example.ethereum_wallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        Intent intent;
        if (preferences.getBoolean("logged", false)) {
            intent = new Intent(SplashScreen.this, VerifyActivity.class);
        } else {
            intent = new Intent(SplashScreen.this,MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
