package com.example.inclass10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    EditText txtboxname, txtboxamt, txtboxdate;
    Spinner spinner;
    Uri imageuri;
    String recpimg;
    private TextView tvDisplayDate;
    ImageView imageView ;
    private int year;
    private int month;
    private int day;
    ImageView preview;
    static final int DATE_DIALOG_ID = 999;
    String expensename, expensecategory, date;
    Uri outputFileUri;
    static final int RESULT_LOAD_IMAGE=1;
    User user=new User();
    String userid;
    String amount;
    private DatabaseReference mDatabase;
    ArrayList<Expense> allexpenseslist=new ArrayList<Expense>();
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        getSupportActionBar().setTitle("Add Expense");
        txtboxname = (EditText) findViewById(R.id.txtboxexpensename);
        txtboxamt=(EditText) this.findViewById(R.id.txtboxamountf);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (getIntent().getExtras() != null) {
            userid = getIntent().getExtras().getString("UserID");
        }
    }
    public void addExpense(View view) {
        expensename = txtboxname.getText().toString();
        expensecategory=spinner.getSelectedItem().toString();
        amount = (txtboxamt.getText().toString());
        if (expensename.length() == 0 || expensecategory.length() == 0 || amount.length()==0) {
            if(expensename.length() == 0){
                setResult(RESULT_CANCELED);
                Toast.makeText(this, "Please enter expense name to create an expense", Toast.LENGTH_SHORT).show();
            }
            else if(amount.length()==0){
                setResult(RESULT_CANCELED);
                Toast.makeText(this, "Please enter the expense amount to create an expense", Toast.LENGTH_SHORT).show();
            }
            else {
                setResult(RESULT_CANCELED);
                Toast.makeText(this, "Please enter all the values to create an expense", Toast.LENGTH_SHORT).show();
            }
        }
        else  if(spinner.getSelectedItem().toString().equals("Select a category"))
        {
            Toast.makeText(this, "Please select a valid expense category to proceed further.", Toast.LENGTH_SHORT).show();

        }
        else {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            date= sdf.format(cal.getTime()).toString();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference aexpref=mDatabase.child("expenses").child(userid).push();
            String expensekey= aexpref.getKey();
            Expense expense = new Expense(expensename, expensecategory,date, amount,expensekey);
            aexpref.setValue(expense);
            Intent in=new Intent(AddExpenseActivity.this,ExpenseActivity.class);
            in.putExtra("UserID",userid.toString());
            startActivity(in);
            finish();
        }


    }
public void goToExpenseActivity(){
    Intent in=new Intent(AddExpenseActivity.this,ExpenseActivity.class);
    in.putExtra("UserID",userid.toString());
    startActivity(in);
}
    @Override
    protected void onStart() {
        super.onStart();

    }

    public void finishActivity(View view) {
        finish();
    }
}


