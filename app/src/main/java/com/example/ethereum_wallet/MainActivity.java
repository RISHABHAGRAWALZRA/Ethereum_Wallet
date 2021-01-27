package com.example.ethereum_wallet;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.WalletUtils;

public class MainActivity extends AppCompatActivity {

    TextView newtxt;
    EditText keytxt;
    Button nxtbtn;


    final String password = "password";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();



        SharedPreferences preferences = MainActivity.this.getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);


        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = keytxt.getText().toString().toLowerCase();
                if(WalletUtils.isValidPrivateKey(key) && key.length() == 64){

                    Toast.makeText(MainActivity.this, "You are varified", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, PassSetActivity.class);
                    intent.putExtra("Private_Key",key);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this, "Invalid Private key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewWallet.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
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

    private void initview() {
        keytxt = findViewById(R.id.keytxt);
        nxtbtn = findViewById(R.id.nxtbtn);
        newtxt = findViewById(R.id.newtxt);
    }

}

