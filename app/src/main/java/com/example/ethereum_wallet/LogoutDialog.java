package com.example.ethereum_wallet;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class LogoutDialog extends DialogFragment {

    Button yes,no;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomAlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_logout_dialog, null);

        initviews(view);

        builder.setView(view);
        return builder.create();

    }

    @Override
    public void onStart() {
        super.onStart();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //erase all data
                SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                preferences.edit().clear().commit();


                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void initviews(View view) {
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);
    }
}