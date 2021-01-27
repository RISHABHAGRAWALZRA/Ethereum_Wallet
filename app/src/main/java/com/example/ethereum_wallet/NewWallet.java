package com.example.ethereum_wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.WalletUtils;

public class NewWallet extends AppCompatActivity {

    ImageButton imgbtnbac,cpyadrs,cpykey;
    TextView txtadrs,txtkey;
    Button nxtbtn;
    String password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        // set an enter transition
        Transition transition = new Slide();
        transition.setDuration(100);
        getWindow().setEnterTransition(transition);


        setContentView(R.layout.activity_new_wallet);

        initviews();



//        String walletPath = getFilesDir().getAbsolutePath();
//        File walletDir = new File(walletPath);
//        try {
//            String fileName = WalletUtils.generateNewWalletFile(password, walletDir);
//            Credentials credentials = WalletUtils.loadCredentials(password, walletDir);
//
//            Toast.makeText(this, "Public_Key "+credentials.getAddress(), Toast.LENGTH_SHORT).show();
//
//            txtadrs.setText(credentials.getAddress());
//            txtkey.setText(credentials.getEcKeyPair().getPrivateKey().toString());
//        } catch (CipherException e) {
//            e.printStackTrace();
//            Log.d("Decryption", "onNewWalletCreation: "+e.getMessage());
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//            Log.d("Decryption", "onNewWalletCreation: "+e.getMessage());
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            Log.d("Decryption", "onNewWalletCreation: "+e.getMessage());
//
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//            Log.d("Decryption", "onNewWalletCreation: "+e.getMessage());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d("Decryption", "onNewWalletCreation: "+e.getMessage());
//
//        }

//        try {
//            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//            Toast.makeText(this, "Public_Key "+ecKeyPair.getPublicKey(), Toast.LENGTH_SHORT).show();
//            txtadrs.setText(ecKeyPair.getPublicKey().toString());
//            txtkey.setText(ecKeyPair.getPrivateKey().toString());
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        }

//        ECKey key = new ECKey();
//        byte[] addr = key.getAddress();
//        byte[] priv = key.getPrivKeyBytes();
//        String addrBase16 = Hex.toHexString(addr);
//        String privBase16 = Hex.toHexString(priv);
//        Log.d("address",addrBase16);
//        Log.d("priv",privBase16);
//        Toast.makeText(this, "Public_Key "+addrBase16, Toast.LENGTH_SHORT).show();
//        txtadrs.setText(addrBase16);
//        txtkey.setText(privBase16);


        imgbtnbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cpyadrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Public Address", txtadrs.getText().toString());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);

                Toast.makeText(NewWallet.this, "Public Address Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        cpykey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Private Key", txtkey.getText().toString());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);

                Toast.makeText(NewWallet.this, "Public Key Copied to Clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = txtkey.getText().toString().toLowerCase();
                if(WalletUtils.isValidPrivateKey(key) && key.length() == 64){
                    Toast.makeText(NewWallet.this, "You are varified", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NewWallet.this, PassSetActivity.class);
                    intent.putExtra("Private_Key",key);
                    startActivity(intent);

                }else{
                    Toast.makeText(NewWallet.this, "Invalid Private key", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void initviews() {
        imgbtnbac = findViewById(R.id.imgbtnbac);
        txtkey = findViewById(R.id.txtkey);
        txtadrs = findViewById(R.id.txtadres);
        cpyadrs = findViewById(R.id.cpyadrs);
        cpykey = findViewById(R.id.cpykey);
        nxtbtn = findViewById(R.id.nxtbtn);
    }
}