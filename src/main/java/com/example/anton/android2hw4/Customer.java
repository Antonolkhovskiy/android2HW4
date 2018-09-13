package com.example.anton.android2hw4;

import java.util.Date;

/**
 * Created by Anton on 09.05.2018.
 */

public class Customer {
    private String name;
    private String dateOfBirth;
    private int balance;
    private int id;

    public Customer(String name, String dateOfBirth, int balance, int ID) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.id = ID;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getBalance() {
        return balance;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
