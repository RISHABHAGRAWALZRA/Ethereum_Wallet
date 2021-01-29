package com.example.ethereum_wallet;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.chaos.view.PinView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import static android.content.Context.MODE_PRIVATE;

public class ChangePIN_frag extends Fragment {

    ConstraintLayout checkpass,changepass;
    Button setbtn;
    PinView checkpinView,changepinView;
    int canSet=0 ;
    String PIN =null;
    String RightPIN=null;
    private String TAG= "Varify";

    @Override
    public void onStart() {
        super.onStart();
        ObjectAnimator nxtanim = ObjectAnimator.ofFloat(checkpass, View.TRANSLATION_X, -700, 0);
        nxtanim.setDuration(300);
        nxtanim.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_p_i_n_frag, container, false);

        initviews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        RightPIN = preferences.getString("pin",null);

        checkpinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                canSet+=(i2-i1);
                PIN=charSequence.toString();
                if(canSet==4){
                    hideKeyboard(getActivity());
                    if(PIN.equals(RightPIN)) {
                        canSet=0;

                        ObjectAnimator anim = ObjectAnimator.ofFloat(checkpass, View.TRANSLATION_X, 0, -700);
                        anim.setDuration(300);
                        anim.start();

                        ObjectAnimator nxtanim = ObjectAnimator.ofFloat(changepass, View.TRANSLATION_X, -700, 0);
                        nxtanim.setDuration(300);
                        nxtanim.start();

                    } else{
                        YoYo.with(Techniques.Shake).duration(500).repeat(1).playOn(checkpinView);
                        Log.d(TAG, "onDone: "+RightPIN+" "+PIN);
                        checkpinView.setLineColor(Color.RED);
                        checkpinView.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canSet==4){
                    Toast.makeText(getContext(), "Your PIN is set and your wallet is safe  PIN: "+PIN, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("pin",PIN);
                    editor.commit();

                    Navigation.findNavController(getView()).navigate(R.id.wallet_frag);
                }else{
                    Toast.makeText(getContext(), "Pls fill the pin first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changepinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                canSet+=(i2-i1);
                PIN=charSequence.toString();
                Log.d(TAG, "onTextChanged: called"+i1+" "+i2+" Char: "+charSequence);
                if(canSet==4){
                    hideKeyboard(getActivity());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    private void initviews(View view) {
        checkpinView = view.findViewById(R.id.verifyPinView);
        checkpass = view.findViewById(R.id.check_passcode);
        changepinView = view.findViewById(R.id.changePinView);
        setbtn = view.findViewById(R.id.btn);
        changepass = view.findViewById(R.id.change_passcode);
    }
}