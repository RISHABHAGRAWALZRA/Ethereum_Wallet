package com.example.ethereum_wallet;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Utility {

    public static Web3j web3j;

    public static Credentials credentials;

    public static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public static List<TransactionDetail> transactionList = new ArrayList<>();

    public static String exchangeRate = null;

}
