package com.example.ethereum_wallet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.infura.InfuraHttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class Wallet_frag extends Fragment {

    TextView txtadrs,txtethval,txtethdol;
    Button sndbtn,rcvbtn;

    Credentials credentials;
    Web3j web3j;
    Web3ClientVersion web3ClientVersion;

    SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        InfuraHttpService infuraHttpService = new InfuraHttpService(Utility.ropstenUrl);
        web3j = Web3j.build(infuraHttpService);

        try {
            web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            Toast.makeText(getActivity(), "ClientVersion: " + clientVersion, Toast.LENGTH_SHORT).show();
            Log.d("Wallet_Frag", "onConnected to network: "+clientVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String key = preferences.getString("private_key",null);
        credentials = Credentials.create(key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_frag, container, false);
        initviews(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameter.valueOf("latest")).sendAsync().get();

            BigDecimal balance = new BigDecimal(ethGetBalance.getBalance().divide(new BigInteger("10000000000000")).toString());
            BigDecimal nbalance = balance.divide(new BigDecimal("100000"), 8, BigDecimal.ROUND_DOWN);
            txtethval.setText(nbalance.toString());
            txtadrs.setText(credentials.getAddress());

            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        sndbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rcvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void initviews(View view) {
        txtadrs = view.findViewById(R.id.txtadres);
        txtethval = view.findViewById(R.id.txtethval);
        txtethdol = view.findViewById(R.id.txtethdol);
        sndbtn = view.findViewById(R.id.sndbtn);
        rcvbtn = view.findViewById(R.id.rcvbtn);
    }
}