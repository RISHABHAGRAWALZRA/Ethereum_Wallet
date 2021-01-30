package com.example.ethereum_wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    Context context;
    List<TransactionDetail> transactionList;
    String exchange_rate;

    public TransactionListAdapter(Context context, List<TransactionDetail> transactionList, String exchange_rate) {
        this.context = context;
        this.transactionList = transactionList;
        this.exchange_rate = exchange_rate;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.transaction_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionDetail transaction = transactionList.get(position);

        if(transaction.getFrom().equals(Utility.credentials.getAddress())){
            if(transaction.getTo().equals("")){
                holder.adrs.setText(transaction.getContractAddress());
                holder.img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.contract));
            }else{
                holder.adrs.setText(transaction.getTo());
                holder.img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.send_arrow));
            }
        }else{
            holder.adrs.setText(transaction.getFrom());
            holder.img.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.receive_arrow));
        }

        BigDecimal nbalance = new BigDecimal(String.valueOf(Convert.fromWei(transaction.getValue(), Convert.Unit.ETHER))).setScale(3, RoundingMode.DOWN);
        BigDecimal eth_to_val = new BigDecimal(String.valueOf(nbalance.multiply(new BigDecimal(exchange_rate)))).setScale(3, RoundingMode.DOWN);

        holder.eth.setText(String.valueOf(nbalance));
        holder.dol.setText(String.valueOf(eth_to_val));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView adrs,eth,dol;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.trnscimg);
            adrs = itemView.findViewById(R.id.trnscadrs);
            eth = itemView.findViewById(R.id.trnsceth);
            dol = itemView.findViewById(R.id.trnscdol);

        }
    }
}
