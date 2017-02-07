package com.example.inclass10;

import java.io.Serializable;

/**
 * Created by Rama Vamshi Krishna on 9/8/2016.
 */
public class Expense implements Serializable {

    String expensename,expensecategory,date;
    String amount,expensekey;
  public Expense(){

    }



    public Expense(String expensename, String expensecategory, String date,String amount,String expensekey) {
        super();
        this.expensename = expensename;
        this.expensecategory = expensecategory;
        this.date=date;
        this.amount = amount;
        this.expensekey=expensekey;

    }
}
