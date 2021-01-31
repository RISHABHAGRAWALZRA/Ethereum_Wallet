package com.example.ethereum_wallet;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.infura.InfuraHttpService;

import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class Network extends DialogFragment {

    SharedPreferences preferences;
    RadioGroup networkgrp;
    Button setnetwork;
    ProgressBar lod;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_network, null);

        initviews(view);

        preferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        String network = preferences.getString("network",null);

        if(network.equals("ropsten")){
            networkgrp.check(R.id.ropsten);
        }else if(network.equals("kovan")){
            networkgrp.check(R.id.kovan);
        }else if(network.equals("rinkeby")){
            networkgrp.check(R.id.rinkeby);
        }else if(network.equals("mainnet")){
            networkgrp.check(R.id.mainNet);
        }

        builder.setView(view);
        return builder.create();
    }


    @Override
    public void onResume() {
        super.onResume();


        setnetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setnetwork.setVisibility(View.GONE);
                lod.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = preferences.edit();
                String network=null;
                if(networkgrp.getCheckedRadioButtonId() == R.id.ropsten){
                    network="ropsten";
                    HomeActivity.ntclr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                    HomeActivity.ntwrk.setText("Ropsten Test Network");
                }else if(networkgrp.getCheckedRadioButtonId() == R.id.kovan){
                    network = "kovan";
                    HomeActivity.ntclr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                    HomeActivity.ntwrk.setText("Kovan Test Network");
                }else if(networkgrp.getCheckedRadioButtonId() == R.id.rinkeby){
                    network = "rinkeby";
                    HomeActivity.ntclr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    HomeActivity.ntwrk.setText("Rinkeby Test Network");
                }else if(networkgrp.getCheckedRadioButtonId() == R.id.mainNet){
                    network = "mainnet";
                    HomeActivity.ntclr.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    HomeActivity.ntwrk.setText("Ethereum Main Network");
                }
                editor.putString("network",network);
                editor.commit();

                createConnection(new Wallet_frag.ConnectionCallback<InfuraHttpService>() {
                    @Override
                    public void onComplete(InfuraHttpService service) {
                        Utility.web3j = Web3j.build(service);

                        try {
                            Web3ClientVersion web3ClientVersion = Utility.web3j.web3ClientVersion().sendAsync().get();
                            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
                            Toast.makeText(getActivity(), "ClientVersion: " + clientVersion, Toast.LENGTH_SHORT).show();
                            Log.d("Wallet_Frag", "onConnected to network: " + clientVersion);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        lod.setVisibility(View.GONE);

                        dismiss();
                    }
                },Utility.mainThreadHandler);

            }
        });


    }

    public void createConnection(Wallet_frag.ConnectionCallback<InfuraHttpService> callback, Handler handler){
        Utility.executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Connection
                String network = preferences.getString("network","ropsten");
                String Url = "https://"+network+".infura.io/v3/"+BuildConfig.infuraApi;
                InfuraHttpService infuraHttpService = new InfuraHttpService(Url);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(infuraHttpService);
                    }
                });
            }
        });
    }

    interface ConnectionCallback<T> {
        void onComplete(InfuraHttpService service);
    }


    private void initviews(View view) {
        networkgrp = view.findViewById(R.id.networkgrp);
        setnetwork = view.findViewById(R.id.setnetwork);
        lod = view.findViewById(R.id.progressBar2);
    }

}