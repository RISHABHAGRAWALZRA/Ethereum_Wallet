package com.example.ethereum_wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class Send_frag extends BottomSheetDialogFragment {

    ImageView scan;
    Button btnnxt;
    EditText edtxtadrs;

    String[] descriptionData = {"Recipient\nAddress", "Ether\nCount", "Confirmation"};
    StateProgressBar stateProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getContext(), R.style.BottomSheetDialogTheme);

        // clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);


        // Inflate the layout for this fragment
        View view = localInflater.inflate(R.layout.fragment_send_frag, container, false);
        initviews(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        stateProgressBar.setStateDescriptionData(descriptionData);

    }

    private void initviews(View view) {
        scan = view.findViewById(R.id.scan);
        btnnxt = view.findViewById(R.id.btnnxt);
        edtxtadrs = view.findViewById(R.id.edtxtadrs);
        stateProgressBar = view.findViewById(R.id.stepview);
    }

}