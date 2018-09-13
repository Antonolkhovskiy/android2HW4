package com.example.anton.android2hw4;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 09.05.2018.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private ArrayList<Customer> customers;

    public CustomerAdapter(@NonNull Context context, @NonNull ArrayList<Customer> customerList) {
        super(context, R.layout.single_item, customerList);
        customers = customerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.single_item, parent, false);

        Customer customer = (Customer) getItem(position);

        TextView nameView = (TextView) customView.findViewById(R.id.text_name);
        TextView dateOfBirthView = (TextView) customView.findViewById(R.id.text_date_of_birth);
        TextView balanceView = (TextView) customView.findViewById(R.id.text_account_balance);

        nameView.setText(customer.getName());
        dateOfBirthView.setText(String.valueOf(customer.getDateOfBirth()));
        balanceView.setText(String.valueOf(customer.getBalance()));
        return customView;
    }

    public void refreshView(ArrayList<Customer> customers){
        this.customers.clear();
        this.customers.addAll(customers);
        notifyDataSetChanged();
    }
}
