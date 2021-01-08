package com.example.ethereum_wallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

public class PassSetActivity extends AppCompatActivity {

    final static String TAG = "Hello";

    PinView pinView;
    int canSet=0 ;
    String PIN =null;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_set);

        pinView = findViewById(R.id.firstPinView);
        btn = findViewById(R.id.btn);

        SharedPreferences preferences = PassSetActivity.this.getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);


        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                canSet+=(i2-i1);
                PIN=charSequence.toString();
                Log.d(TAG, "onTextChanged: called"+i1+" "+i2+" Char: "+charSequence);
                if(canSet==4){
                    hideKeyboard(PassSetActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canSet==4){
                    Toast.makeText(PassSetActivity.this, "Your PIN is set and your wallet is safe  PIN: "+PIN, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pin",PIN);
                    editor.putBoolean("logged",true);
                    editor.commit();

                    Intent intent = new Intent(PassSetActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(PassSetActivity.this, "Pls fill the pin first", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}