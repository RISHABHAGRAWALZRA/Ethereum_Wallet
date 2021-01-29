package com.example.ethereum_wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;


public class TransactionList_frag extends Fragment {

    RecyclerView recyclerView;

    @Override
    public void onResume() {
        super.onResume();

        TransactionListAdapter adapter = new TransactionListAdapter(getContext(), Arrays.asList(Utility.transactionList),Utility.exchangeRate);
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
    }
}