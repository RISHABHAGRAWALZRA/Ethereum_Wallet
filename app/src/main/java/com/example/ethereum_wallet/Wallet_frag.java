package com.example.ethereum_wallet;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class Wallet_frag extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView txtadrs, txtethval, txtethdol;
    Button sndbtn, rcvbtn;
    ImageView dollarimg;
    SwipeRefreshLayout swipeRefreshLayout;

    Web3ClientVersion web3ClientVersion;

    RequestQueue queue;

    Boolean conn=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(getContext());

        //Loading Credentials
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String key = preferences.getString("private_key", null);
        Utility.credentials = Credentials.create(key);

        createConnection(new ConnectionCallback<InfuraHttpService>() {
            @Override
            public void onComplete(InfuraHttpService service) {
                Utility.web3j = Web3j.build(service);

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

                conn = true;
                getEthBalance();
            }
        },Utility.mainThreadHandler);
    }

    interface ConnectionCallback<T> {
        void onComplete(InfuraHttpService service);
    }

    public void createConnection(ConnectionCallback<InfuraHttpService> callback,Handler handler){
        Utility.executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Connection
                InfuraHttpService infuraHttpService = new InfuraHttpService(BuildConfig.ropstenURL);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(infuraHttpService);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume Called", "getEthBalance called from here");

        if(conn){
            getEthBalance();
        }
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

        sndbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SendEtherActivity.class);
                intent.putExtra("ether", txtethval.getText().toString());
                intent.putExtra("dollar", txtethdol.getText().toString());
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        rcvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new ReceiveFragment(txtadrs.getText().toString());

                dialogFragment.show(getParentFragmentManager(), "Dialog");
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

        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("SwipeRefresh Called", "onRefresh called from SwipeRefreshLayout");

                getEthBalance();
            }
        });

    }

    interface EthBalanceCallback<T> {
        void onComplete(EthGetBalance result);
    }

    private BigDecimal getEthBalance() {
        final BigDecimal[] nbalance = {null};

        fetchBalance(new EthBalanceCallback<EthGetBalance>() {
            @Override
            public void onComplete(EthGetBalance ethGetBalance) {

                BigDecimal balance = new BigDecimal(ethGetBalance.getBalance().divide(new BigInteger("10000000000000")).toString());
                nbalance[0] = balance.divide(new BigDecimal("100000"), 3, BigDecimal.ROUND_DOWN);
                txtethval.setText(nbalance[0].toString());
                txtethval.setVisibility(View.VISIBLE);
                txtadrs.setText(Utility.credentials.getAddress());

                requestCallForExchangeRates(nbalance[0]);
            }
        }, Utility.mainThreadHandler);

        return nbalance[0];
    }

    private void fetchBalance(EthBalanceCallback<EthGetBalance> ethGetBalanceEthBalanceCallback, Handler mainThreadHandler) {
        Utility.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EthGetBalance ethGetBalance = Utility.web3j.ethGetBalance(Utility.credentials.getAddress(), DefaultBlockParameter.valueOf("latest")).sendAsync().get();

                    mainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ethGetBalanceEthBalanceCallback.onComplete(ethGetBalance);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    private void fetchForAllTransactions() {
        String network = "ropsten";
        String url = "https://api-"+ network +".etherscan.io/api?module=account&action=txlist&address="+ Utility.credentials.getAddress() +"&startblock=0&endblock=99999999&sort=asc&apikey=" + BuildConfig.detailApi;

        JsonObjectRequest transactionListJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                try {
                    JSONArray array = response.getJSONArray("result");
                    Log.d("TransactionList", "onResponseFromEtherScan: "+array.toString());
                    TransactionDetail[] detail = gson.fromJson(String.valueOf(array),TransactionDetail[].class);

                    Utility.transactionList = detail;
                    List<TransactionDetail> smallTransactionList = new ArrayList<>();
                    for(int i=1;i<=5 && i<=detail.length;i++){
                        smallTransactionList.add(detail[detail.length - i]);
                    }

                    TransactionListAdapter adapter = new TransactionListAdapter(getContext(),smallTransactionList,Utility.exchangeRate);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(transactionListJsonObjectRequest);

    }


    private void requestCallForExchangeRates(BigDecimal nbalance) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://api.coinbase.com/v2/exchange-rates?currency=ETH", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String excahnge_rate = response.getJSONObject("data").getJSONObject("rates").getString("USD");
                            BigDecimal eth_to_val = new BigDecimal(String.valueOf(nbalance.multiply(new BigDecimal(excahnge_rate)))).setScale(3, RoundingMode.DOWN);
                            txtethdol.setText(String.valueOf(eth_to_val));
                            txtethdol.setVisibility(View.VISIBLE);

                            Utility.exchangeRate = excahnge_rate;
                            fetchForAllTransactions();
                            Log.d("answer", "onResponse: answer is loaded completely");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("JSON Response", "onResponse: Getting false data");
                            Toast.makeText(getActivity(), "Get some exception", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
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
        recyclerView = view.findViewById(R.id.recycler_transac);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.web3j.shutdown();
        Utility.executorService.shutdown();
    }

}




//interface FetchTransaction<T> {
//    void onComplete(List<Pair<Integer, Transaction>> result);
//}


//        fetchTransactionList(new FetchTransaction<List<Pair<Integer, Transaction>>>() {
//            @Override
//            public void onComplete(List<Pair<Integer, Transaction>> result) {
//                Utility.transactionList = result;
//
//                List<Pair<Integer, Transaction>> smallTransactionList = new ArrayList<>();
//                for (int i = 0; i < 5; i++) {
//                    smallTransactionList.add(result.get(i));
//                }
//
//                Toast.makeText(getContext(), "We got the list", Toast.LENGTH_SHORT).show();
//                list.setText(smallTransactionList.toString());
//                TransactionListAdapter adapter = new TransactionListAdapter(getContext(), smallTransactionList, excahnge_rate);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//            }
//        }, Utility.mainThreadHandler);





//    private void fetchTransactionList(FetchTransaction<List<Pair<Integer, Transaction>>> listFetchTransaction, Handler mainThreadHandler) {
//        Utility.executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                EthBlockNumber ethBlockNumber = new EthBlockNumber();
//                try {
//                    ethBlockNumber = Utility.web3j.ethBlockNumber()
//                            .sendAsync()
//                            .get();
//
//                    List<Pair<Integer, Transaction>> transactionList = new ArrayList<>();
//
//                    for (int i = ethBlockNumber.getBlockNumber().intValue(); i > 0; i--) {
//                        EthBlock block = Utility.web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(i)), true).sendAsync().get();
//
//                        for (int j = 1; j <= block.getBlock().getTransactions().size(); j++) {
//                            Transaction transaction = (Transaction) block.getBlock().getTransactions().get(j).get();
//                            if (transaction.getFrom().equals(Utility.credentials.getAddress())) {
//                                transactionList.add(new Pair<>(0, transaction));
//                            } else if (transaction.getTo().equals(Utility.credentials.getAddress())) {
//                                transactionList.add(new Pair<>(1, transaction));
//                            }
//                        }
//                    }
//
//                    mainThreadHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            listFetchTransaction.onComplete(transactionList);
//                        }
//                    });
//
//
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }