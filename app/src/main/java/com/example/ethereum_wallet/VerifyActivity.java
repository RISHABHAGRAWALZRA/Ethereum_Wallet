package com.example.ethereum_wallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class VerifyActivity extends AppCompatActivity {

    PinView pinView;
    ImageView imgeth;
    View anim;
    int canSet=0 ;
    String PIN =null;
    String RightPIN=null;
    private String TAG= "Varify";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varify);

        initviews();



        SharedPreferences preferences = this.getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        RightPIN = preferences.getString("pin",null);

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                canSet+=(i2-i1);
                PIN=charSequence.toString();
                if(canSet==4){
                    hideKeyboard(VerifyActivity.this);
                    if(PIN.equals(RightPIN)) {
                        pinView.setVisibility(View.INVISIBLE);
                        imgeth.setVisibility(View.INVISIBLE);
                        anim.setVisibility(View.VISIBLE);



                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //start your activity here
                                Intent intent = new Intent(VerifyActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }, 4000L);

                    } else{
                        YoYo.with(Techniques.Shake).duration(500).repeat(1).playOn(pinView);
                        Log.d(TAG, "onDone: "+RightPIN+" "+PIN);
                        pinView.setLineColor(Color.RED);
                        pinView.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    private void initviews() {
        pinView = findViewById(R.id.verifyPinView);
        anim = findViewById(R.id.crrct);
        imgeth = findViewById(R.id.imgeth);
    }


}
