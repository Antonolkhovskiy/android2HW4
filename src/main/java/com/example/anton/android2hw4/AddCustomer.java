package com.example.anton.android2hw4;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Calendar;

public class AddCustomer extends AppCompatActivity {

    private EditText addCustomerName;
    private EditText addCustomerDate;
    private EditText addCustomerBalance;
    private Button addCustomerButtonAdd;
    private Button addCustomerButtonReset;
    private DBHelper dbHelper;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        addCustomerName = findViewById(R.id.add_customer_name);
        addCustomerDate = findViewById(R.id.add_customer_date);
        addCustomerBalance = findViewById(R.id.add_customer_balance);

        addCustomerButtonAdd = findViewById(R.id.add_customer_button_add);
        addCustomerButtonReset = findViewById(R.id.add_customer_button_reset);

        dbHelper = new DBHelper(this);

        addCustomerButtonAdd.setOnClickListener((View)->addCustomer());

        addCustomerButtonReset.setOnClickListener((View)->reset());

        addCustomerDate.setInputType(InputType.TYPE_NULL);

        onDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String dateOfBirth = day + "/" + month + "/" + year;
            addCustomerDate.setText(dateOfBirth);
        };


        addCustomerDate.setOnTouchListener((view, motionEvent) -> {
            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(addCustomerDate.getRootView().getWindowToken(), 0);
            addCustomerDate.setInputType(InputType.TYPE_NULL);
            return false;
        });

        addCustomerDate.setOnClickListener(view -> {
            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(addCustomerDate.getRootView().getWindowToken(), 0);
            addCustomerDate.setInputType(InputType.TYPE_NULL);


            Calendar calendar = Calendar.getInstance();
             int year = calendar.get(Calendar.YEAR);
             int month = calendar.get(Calendar.MONTH) + 1;
             int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddCustomer.this,
                    android.R.style.Theme_Holo_Dialog_MinWidth,
                    onDateSetListener,
                    year,
                    month,
                    day

            );

            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        });

    }

    private void addCustomer(){

        Customer customer = null;

        try {
            String name = addCustomerName.getText().toString();
            String date = addCustomerDate.getText().toString();
            int balance = Integer.parseInt(addCustomerBalance.getText().toString());

            customer = new Customer(name,date, balance, -1);
            dbHelper.addCustomer(customer);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            Toast.makeText(this, "Customer " + name + " was added", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Incorrect Input", Toast.LENGTH_SHORT).show();
        }


    }

    private void reset(){
        addCustomerName.setText("");
        addCustomerDate.setText("");
        addCustomerBalance.setText("");
    }

}

