package com.example.ethereum_wallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText keytxt;
    Button nxtbtn;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();

        SharedPreferences preferences = MainActivity.this.getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);


        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keytxt.getText().length() == 64){
                    //credentials = Credentials.create(keytxt.getText().toString());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("private_key",keytxt.getText().toString());
                    editor.commit();
                    Toast.makeText(MainActivity.this, "You are varified", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, PassSetActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Private key", Toast.LENGTH_SHORT).show();
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

    private void initview() {
        keytxt = findViewById(R.id.keytxt);
        nxtbtn = findViewById(R.id.nxtbtn);
    }

}

