package com.example.anton.android2hw4;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Anton on 11.05.2018.
 */

public class DeleteDialog extends AppCompatDialogFragment {

    private DeleteDialogListerner mLitener;
    private TextView textDeleteCustomer;
    private String TAG = "bank app";
    private int customerID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_delete_dialog, null);

        textDeleteCustomer = view.findViewById(R.id.text_delete_customer);



        if(getArguments() != null){
           textDeleteCustomer.setText(getArguments().getString("customerDelete"));
           customerID = getArguments().getInt("customerID");
            builder.setView(view)
                    .setTitle("Confirm Delete")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mLitener.confirmDeleting(customerID);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });



            Log.d(TAG, "onCreateDialog: builder returned");


            return builder.create();

        }else{
            Log.d(TAG, "onCreateDialog: arguments = null");
            Toast.makeText(getActivity().getApplicationContext(), "Error Creating Dialog", Toast.LENGTH_SHORT).show();
        }

        return null;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLitener = (DeleteDialogListerner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DeleteDialog listener");
        }
    }

    public interface DeleteDialogListerner{
        void confirmDeleting(int customerID);
    }
}
