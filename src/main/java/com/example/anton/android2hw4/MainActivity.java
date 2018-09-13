package com.example.anton.android2hw4;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements UpdateDialog.UpdateDialogListener,
                                                                DeleteDialog.DeleteDialogListerner{
    private final String TAG = "bank_app";
    private ArrayList<Customer> customers;
    private DBHelper dbHelper;
    private UpdateDialog.UpdateDialogListener dialogListener;
    private DeleteDialog.DeleteDialogListerner deleteDialogListerner;
    private Bundle bundle;
    private Customer customerToUpdate, customerToDelete;
    private  CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



       // dialogListener = (name, dateOfBirth, accountBalance) -> showToast(name, dateOfBirth, accountBalance);

       // deleteDialogListerner = customerID -> showToast("user deleted", null, -1);

        dbHelper = new DBHelper(this);
        customers = dbHelper.retreiveData();
        bundle= new Bundle();

        showToastIfThereIsNoCustomers();

        customerAdapter  = new CustomerAdapter(this, customers);
        ListView customerListView = (ListView)findViewById(R.id.list_view_main);
        customerListView.setAdapter(customerAdapter);

        customerListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            customerToDelete = customers.get(i);
            openDeleteDialog();
            return true;
        });

        customerListView.setOnItemClickListener((adapterView, view, i, l) -> {
            customerToUpdate = customers.get(i);
            openUpdateDialog();
        });

        customerAdapter.notifyDataSetChanged();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_menu_button) {
            Intent intent = new Intent(this, AddCustomer.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void openUpdateDialog(){
        UpdateDialog updateDialog = new UpdateDialog();
        bundle.putString("name", customerToUpdate.getName());
        bundle.putString("date",customerToUpdate.getDateOfBirth());
        bundle.putInt("balance", customerToUpdate.getBalance());
        bundle.putInt("idToUpdate", customerToUpdate.getId());
        updateDialog.setArguments(bundle);

        updateDialog.show(getSupportFragmentManager(),"update dialog");
    }

    @Override
    public void applyData(Customer updatingCustomer) {
        dbHelper.updateCustomer(updatingCustomer);
        customers = dbHelper.retreiveData();
        customerAdapter.refreshView(customers);
        Toast.makeText(this, "Customer " + updatingCustomer.getName() + " Was Updated", Toast.LENGTH_SHORT).show();

    }

    public void openDeleteDialog(){
        DeleteDialog deleteDialog = new DeleteDialog();
        bundle.putString("customerDelete",customerToDelete.getName());
        bundle.putInt("customerID", customerToDelete.getId());
        deleteDialog.setArguments(bundle);

        deleteDialog.show(getSupportFragmentManager(),"delete dialog");
    }

    @Override
    public void confirmDeleting(int customerID) {
        dbHelper.deleteCustomer(customerToDelete);
        Toast.makeText(this, "Customer " + customerToDelete.getName() + " Was Deleted", Toast.LENGTH_SHORT).show();
        customers = dbHelper.retreiveData();
        customerAdapter.refreshView(customers);
        showToastIfThereIsNoCustomers();

    }

    private void showToastIfThereIsNoCustomers(){
        if(customers.isEmpty()){
            Toast.makeText(MainActivity.this,"No Customer Records", Toast.LENGTH_SHORT).show();
        }
    }



}
