package com.example.ethereum_wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TransactionList_frag extends Fragment {

    //ProgressBar loadinglist;
    RecyclerView recyclerView;

    @Override
    public void onResume() {
        super.onResume();

        if(Utility.transactionList.size() == 0){
            Navigation.findNavController(getView()).navigate(R.id.wallet_frag);
            Toast.makeText(getContext(), "Still loading transaction list wait", Toast.LENGTH_SHORT).show();
        }

        TransactionListAdapter adapter = new TransactionListAdapter(getContext(), Utility.transactionList,Utility.exchangeRate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_list_frag, container, false);

        initviews(view);
        return view;
    }

    private void initviews(View view) {
        recyclerView = view.findViewById(R.id.recyclertrnsclist);
        //loadinglist = view.findViewById(R.id.loadinglist);
    }
}