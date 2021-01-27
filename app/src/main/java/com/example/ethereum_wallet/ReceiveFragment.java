package com.example.ethereum_wallet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class ReceiveFragment extends DialogFragment {

    String adrs;
    ImageView qrimg;
    TextView adrstxt;
    Button shrbtn;

    ReceiveFragment(String adrs){
        this.adrs =adrs;
    }

    @Override
    public void onStart() {
        super.onStart();

        shrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Share your Public Key With others");
                shareIntent.putExtra(Intent.EXTRA_TEXT,adrstxt.getText().toString());
                startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_receive, null);

        initviews(view);
        showViews();

        builder.setView(view);
        return builder.create();
    }

    private void showViews() {
        adrstxt.setText(adrs);
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(adrs, BarcodeFormat.QR_CODE, 400, 400);
            qrimg.setImageBitmap(bitmap);
        } catch(Exception e) {

        }

    }

    private void initviews(View view) {
        shrbtn = view.findViewById(R.id.shrbtn);
        qrimg = view.findViewById(R.id.qrimg);
        adrstxt = view.findViewById(R.id.adrstxt);
    }
}