package com.example.ethereum_wallet;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

import pl.droidsonroids.gif.GifImageView;

public class SendEtherActivity extends AppCompatActivity {

    View successdet;
    ImageView scan, dolimg, ethimg,imglogo;
    Button btnnxt, btnset, cnclbtn, tnsctbtn;
    ImageButton clsbtn, imgbtnbac, imgscan;
    EditText edtxtadrs, edtxtval;
    TextView txtbaleth, txtbaldol, txtrecadrs, txtrecamt,to,from,gsusd,trnscthsh,blckhsh,imgtxt;
    CardView adrscard, ethcard, tnsctdetcard;
    SwitchCompat currency;
    GifImageView loadinggif;
    View checkAnim;

    String transactionTAG = "Send_Transactioin message";

    Web3ClientVersion web3ClientVersion;

    String[] descriptionData = {"Recipient\nAddress", "Ether\nCount", "Confirmation"};
    StateProgressBar stateProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // inside your activity (if you did not enable transitions in your theme)
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        // set an enter transition
        Transition transition = new Slide();
        transition.setDuration(100);
        getWindow().setEnterTransition(transition);


        setContentView(R.layout.activity_send_ether);


        initviews();
        stateProgressBar.setStateDescriptionData(descriptionData);

        Intent intent = getIntent();
        String ether = intent.getStringExtra("ether");
        String dollar = intent.getStringExtra("dollar");
        txtbaleth.setText(ether);
        txtbaldol.setText(dollar);

        clsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cnclbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edtxtval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtxtval.setCursorVisible(true);
            }
        });

        btnnxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = edtxtadrs.getText().toString();
                if (address.length() == 42 && WalletUtils.isValidAddress(address)) {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(adrscard, View.TRANSLATION_X, 0, -700);
                    anim.setDuration(300);
                    anim.start();

                    ObjectAnimator nxtanim = ObjectAnimator.ofFloat(ethcard, View.TRANSLATION_X, -700, 0);
                    nxtanim.setDuration(300);
                    nxtanim.start();

                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);

                } else {
                    Toast.makeText(SendEtherActivity.this, "Invalid Public Key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ether = txtbaleth.getText().toString();
                String dollar = txtbaldol.getText().toString();
                String val = edtxtval.getText().toString();
                if (currency.isChecked()) {
                    if (val.length() > 0 && (new BigDecimal(val).compareTo(new BigDecimal(ether))) != 1) {
                        ObjectAnimator nxtanim = ObjectAnimator.ofFloat(ethcard, View.TRANSLATION_X, 0, -700);
                        nxtanim.setDuration(300);
                        nxtanim.start();

                        ObjectAnimator anim = ObjectAnimator.ofFloat(tnsctdetcard, View.TRANSLATION_X, -700, 0);
                        anim.setDuration(300);
                        anim.start();

                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        ethimg.setVisibility(View.VISIBLE);
                        txtrecamt.setText(edtxtval.getText().toString());
                        txtrecadrs.setText(edtxtadrs.getText().toString());

                    } else {
                        edtxtval.setError("Not Enough balance");
                        Toast.makeText(SendEtherActivity.this, "Balance is not sufficient to make it successful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (val.length() > 0 && (new BigDecimal(val).compareTo(new BigDecimal(dollar))) != 1) {
                        ObjectAnimator nxtanim = ObjectAnimator.ofFloat(ethcard, View.TRANSLATION_X, 0, -700);
                        nxtanim.setDuration(300);
                        nxtanim.start();

                        ObjectAnimator anim = ObjectAnimator.ofFloat(tnsctdetcard, View.TRANSLATION_X, -700, 0);
                        anim.setDuration(300);
                        anim.start();

                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        dolimg.setVisibility(View.VISIBLE);
                        txtrecamt.setText(edtxtval.getText().toString());
                        txtrecadrs.setText(edtxtadrs.getText().toString());

                    } else {
                        edtxtval.setError("Not Enough balance");
                        Toast.makeText(SendEtherActivity.this, "Balance is not sufficient to make it successful", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        tnsctbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(tnsctdetcard, View.TRANSLATION_X, 0, -700);
                anim.setDuration(300);
                anim.start();

                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //start your activity here
                        loadinggif.setVisibility(View.VISIBLE);

                        adrscard.setVisibility(View.GONE);
                        ethcard.setVisibility(View.GONE);
                        tnsctdetcard.setVisibility(View.GONE);
                        stateProgressBar.setVisibility(View.GONE);
                    }
                }, 300L);

                String val = edtxtval.getText().toString();

                sendTransaction(currency.isChecked(), val, new RepositoryCallback<TransactionReceipt>() {
                    @Override
                    public void onComplete(TransactionReceipt result) {
                        loadinggif.setVisibility(View.GONE);
                        imglogo.setVisibility(View.GONE);
                        imgtxt.setVisibility(View.GONE);
                        successdet.setVisibility(View.VISIBLE);
                        to.setText(result.getTo());
                        from.setText(result.getFrom());
                        gsusd.setText(result.getGasUsed().toString());
                        trnscthsh.setText(result.getTransactionHash());
                        blckhsh.setText(result.getBlockHash());
                    }
                }, Utility.mainThreadHandler);
            }
        });

        imgbtnbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjectAnimator anim = ObjectAnimator.ofFloat(adrscard, View.TRANSLATION_X, -700, 0);
                anim.setDuration(300);
                anim.start();

                edtxtval.setText("");
                ObjectAnimator nxtanim = ObjectAnimator.ofFloat(ethcard, View.TRANSLATION_X, 0, -700);
                nxtanim.setDuration(300);
                nxtanim.start();

                stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
        });

        imgscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(SendEtherActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan QR code");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();

            }
        });


    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                edtxtadrs.setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    interface RepositoryCallback<T> {
        void onComplete(TransactionReceipt result);
    }

    private void sendTransaction(Boolean sign, String val, RepositoryCallback<TransactionReceipt> callback, Handler handler) {

        Utility.executorService.execute(new Runnable() {
            @Override
            public void run() {
                BigDecimal amount = new BigDecimal(val);
                if (!sign) {
                    BigDecimal ether = new BigDecimal(txtbaleth.getText().toString());
                    BigDecimal dollar = new BigDecimal(txtbaldol.getText().toString());
                    amount = amount.divide(dollar.divide(ether));
                }

                TransactionReceipt transactionReceipt = null;
                try {
                    TransactionManager transactionManager = new RawTransactionManager(
                            Utility.web3j,
                            Utility.credentials
                    );
                    Transfer transfer = new Transfer(Utility.web3j, transactionManager);
                    transactionReceipt = transfer.sendFunds(
                            edtxtadrs.getText().toString(),
                            amount,
                            Convert.Unit.ETHER).send();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(transactionTAG, "onSendFunds: Something Went Wrong" + e.getMessage());
                }

                TransactionReceipt finalTransactionReceipt = transactionReceipt;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(finalTransactionReceipt);
                    }
                });

            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            edtxtval.setCursorVisible(false);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void initviews() {
        clsbtn = findViewById(R.id.close_button);
        imgscan = findViewById(R.id.scan);
        btnnxt = findViewById(R.id.btnnxt);
        btnset = findViewById(R.id.btnset);
        edtxtadrs = findViewById(R.id.edtxtadrs);
        stateProgressBar = findViewById(R.id.stepview);
        txtbaldol = findViewById(R.id.txtbaldol);
        txtbaleth = findViewById(R.id.txtbaleth);
        edtxtval = findViewById(R.id.ethcount);
        ethcard = findViewById(R.id.ethcard);
        adrscard = findViewById(R.id.adrscard);
        imgbtnbac = findViewById(R.id.imgbtnbac);
        currency = findViewById(R.id.currswitch);
        cnclbtn = findViewById(R.id.cnclbtn);
        tnsctbtn = findViewById(R.id.tnsctbtn);
        tnsctdetcard = findViewById(R.id.finaldetCrd);
        txtrecadrs = findViewById(R.id.recadrs);
        txtrecamt = findViewById(R.id.recamt);
        loadinggif = findViewById(R.id.loadgif);
        checkAnim = findViewById(R.id.checkedAnim);
        dolimg = findViewById(R.id.dolimg);
        ethimg = findViewById(R.id.ethimg);
        successdet = findViewById(R.id.successdet);
        to = findViewById(R.id.to);
        from = findViewById(R.id.from);
        gsusd = findViewById(R.id.gsusd);
        trnscthsh = findViewById(R.id.trscthsh);
        blckhsh = findViewById(R.id.blckhsh);
        imglogo = findViewById(R.id.imglogo);
        imgtxt = findViewById(R.id.imgtxt);
    }
}