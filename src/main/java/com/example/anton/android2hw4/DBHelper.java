package com.example.anton.android2hw4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 11.05.2018.
 */

public class DBHelper extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "Customers";
    static final String CUSTOMERS_TABLE_NAME = "customer";
    static final int DATABASE_VERSION = 1;
    static final String COLUMN_CUSTOMER_NAME = "name";
    static final String COLUMN_CUSTOMER_DATE = "date";
    static final String COLUMN_CUSTOMER_BALANCE = "balance";
    static final String ID = "_id";
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + CUSTOMERS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT NOT NULL, " +
                    " date TEXT NOT NULL, " +
                    " balance INTEGER NOT NULL);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  CUSTOMERS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, customer.getName());
        values.put(COLUMN_CUSTOMER_DATE, customer.getDateOfBirth().toString());
        values.put(COLUMN_CUSTOMER_BALANCE, customer.getBalance());

        SQLiteDatabase database = getWritableDatabase();
        database.insert(CUSTOMERS_TABLE_NAME, null, values);
        database.close();
    }
    public void updateCustomer(Customer customer){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, customer.getName());
        values.put(COLUMN_CUSTOMER_DATE, customer.getDateOfBirth().toString());
        values.put(COLUMN_CUSTOMER_BALANCE, customer.getBalance());

        SQLiteDatabase database = getWritableDatabase();
        database.update(CUSTOMERS_TABLE_NAME, values, "_id = ?",new String[]{String.valueOf(customer.getId())});
        database.close();

        ArrayList<Customer> arrayList = retreiveData();

    }

    public void deleteCustomer(Customer customer){
        int customerID = customer.getId();
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM " + CUSTOMERS_TABLE_NAME + " WHERE " + ID + "=\"" + customerID + "\";");
        database.close();
    }

    public ArrayList<Customer> retreiveData(){
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        String query = "SELECT * FROM " + CUSTOMERS_TABLE_NAME + " WHERE 1";
        Cursor recordSet = database.rawQuery(query, null);
        recordSet.moveToFirst();
        Customer customer;

        while (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex(ID)) != null) {
                int id = recordSet.getInt(recordSet.getColumnIndex(ID));
                int balance = recordSet.getInt(recordSet.getColumnIndex(COLUMN_CUSTOMER_BALANCE));
                String name = recordSet.getString(recordSet.getColumnIndex(COLUMN_CUSTOMER_NAME));
                String date = recordSet.getString(recordSet.getColumnIndex(COLUMN_CUSTOMER_DATE));
                customer = new Customer(name, date, balance, id);
                customers.add(customer);
            }
            recordSet.moveToNext();
        }
        database.close();
        return customers;
    }
}
