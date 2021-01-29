package com.example.ethereum_wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class SharePublicKey extends Fragment {

    ImageView qrimg;
    Button shr;
    TextView pubadrs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_share_public_key, container, false);
        initviews(view);
        showViews();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        shr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Share your Public Key With others");
                shareIntent.putExtra(Intent.EXTRA_TEXT,pubadrs.getText().toString());
                startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });

        pubadrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", pubadrs.getText().toString());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(), "Public Address Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showViews() {
        pubadrs.setText(Utility.credentials.getAddress());
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(Utility.credentials.getAddress(), BarcodeFormat.QR_CODE, 400, 400);
            qrimg.setImageBitmap(bitmap);
        } catch(Exception e) {

        }

    }

    private void initviews(View view) {
        shr = view.findViewById(R.id.btnshr);
        qrimg = view.findViewById(R.id.imgqr);
        pubadrs = view.findViewById(R.id.pubadrs);
    }

}