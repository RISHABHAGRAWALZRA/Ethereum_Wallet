package com.example.ethereum_wallet;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.infura.InfuraHttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class Wallet_frag extends Fragment {

    TextView txtadrs, txtethval, txtethdol;
    Button sndbtn, rcvbtn;
    ImageView dollarimg;
    SwipeRefreshLayout swipeRefreshLayout;

    Web3ClientVersion web3ClientVersion;

    RequestQueue queue;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        InfuraHttpService infuraHttpService = new InfuraHttpService(BuildConfig.ropstenURL);
        Utility.web3j = Web3j.build(infuraHttpService);

        try {
            web3ClientVersion = Utility.web3j.web3ClientVersion().sendAsync().get();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Toast.makeText(getActivity(), "ClientVersion: " + clientVersion, Toast.LENGTH_SHORT).show();
            Log.d("Wallet_Frag", "onConnected to network: " + clientVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String key = preferences.getString("private_key", null);
        Utility.credentials = Credentials.create(key);
        //credentials = WalletUtils.loadBip39Credentials("password",)

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getContext());


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume Called", "onRefresh called from SwipeRefreshLayout");

        BigDecimal balance = getEthBalance();
        requestCallForExchangeRates(balance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_frag, container, false);
        initviews(view);

        BigDecimal balance = getEthBalance();
        requestCallForExchangeRates(balance);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();



        sndbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),SendEtherActivity.class);
                intent.putExtra("ether",txtethval.getText().toString());
                intent.putExtra("dollar",txtethdol.getText().toString());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        rcvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new ReceiveFragment(txtadrs.getText().toString());

                dialogFragment.show(getParentFragmentManager(),"Dialog");
            }
        });

        txtadrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", txtadrs.getText().toString());
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(), "Public Address Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("SwipeRefresh Called", "onRefresh called from SwipeRefreshLayout");

                BigDecimal balance = getEthBalance();
                requestCallForExchangeRates(balance);
            }
        });

    }

    private BigDecimal getEthBalance() {
        BigDecimal nbalance = null;
        try {
            EthGetBalance ethGetBalance = Utility.web3j.ethGetBalance(Utility.credentials.getAddress(), DefaultBlockParameter.valueOf("latest")).sendAsync().get();

            BigDecimal balance = new BigDecimal(ethGetBalance.getBalance().divide(new BigInteger("10000000000000")).toString());
            nbalance = balance.divide(new BigDecimal("100000"), 3, BigDecimal.ROUND_DOWN);
            txtethval.setText(nbalance.toString());
            txtethval.setVisibility(View.VISIBLE);
            txtadrs.setText(Utility.credentials.getAddress());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return nbalance;
    }


    private void requestCallForExchangeRates(BigDecimal nbalance) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://api.coinbase.com/v2/exchange-rates?currency=ETH", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String excahnge_rate = response.getJSONObject("data").getJSONObject("rates").getString("USD");
                            BigDecimal eth_to_val = new BigDecimal(String.valueOf(nbalance.multiply(new BigDecimal(excahnge_rate)))).setScale(3,RoundingMode.DOWN);
                            txtethdol.setText(String.valueOf(eth_to_val));
                            txtethdol.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setRefreshing(false);
                            Log.d("answer", "onResponse: answer is loaded completely");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON Response", "onResponse: Getting false data");
                            Toast.makeText(getActivity(), "Get some exception", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("JSON err", "onErrorResponse: " + error.toString());
                        Toast.makeText(getActivity(), "Why err", Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }


    private void initviews(View view) {
        txtadrs = view.findViewById(R.id.txtadres);
        txtethval = view.findViewById(R.id.txtethval);
        txtethdol = view.findViewById(R.id.txtethdol);
        sndbtn = view.findViewById(R.id.sndbtn);
        rcvbtn = view.findViewById(R.id.rcvbtn);
        dollarimg = view.findViewById(R.id.dollarimg);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.web3j.shutdown();
        Utility.executorService.shutdown();
    }

}