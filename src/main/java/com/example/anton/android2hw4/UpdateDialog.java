package com.example.anton.android2hw4;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


/**
 * Created by Anton on 09.05.2018.
 */

public class UpdateDialog extends AppCompatDialogFragment {
    private EditText updateName;
    private EditText updateDate;
    private EditText updateBalance;
    private UpdateDialogListener listener;
    private final String TAG = "bank app";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_update_dialog, null);

        updateName = view.findViewById(R.id.update_name);
        updateDate = view.findViewById(R.id.update_date);
        updateBalance = view.findViewById(R.id.update_balance);


        if (getArguments() != null) {
            updateName.setText(getArguments().getString("name"));
            updateDate.setText(getArguments().getString("date"));
            updateBalance.setText(String.valueOf(getArguments().getInt("balance")));

            builder.setView(view)
                    .setTitle("Update Info")
                    .setPositiveButton("Udpate", (dialogInterface, i) -> {
                        int updateID = getArguments().getInt("idToUpdate");
                        String date = updateDate.getText().toString();
                        String name = updateName.getText().toString();
                        int balance = Integer.parseInt(updateBalance.getText().toString());
                        listener.applyData(new Customer(name, date, balance, updateID));
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    });

            return builder.create();
        } else {
            Log.d(TAG, "onCreateDialog: arguments = null");
        }


        return builder.create();
    }

    public interface UpdateDialogListener {
        void applyData(Customer customerToUpdate);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (UpdateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UpdateDialog listener");
        }
    }
}
