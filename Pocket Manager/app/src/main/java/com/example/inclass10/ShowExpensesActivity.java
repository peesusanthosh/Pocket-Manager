package com.example.inclass10;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowExpensesActivity extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);
        getSupportActionBar().setTitle("Show Expense");
        tv1=(TextView) findViewById(R.id.tvname);
        tv2=(TextView) findViewById(R.id.tvcategory);
        tv3=(TextView) findViewById(R.id.tvamount);
        tv4=(TextView) findViewById(R.id.tvdate);

        if (getIntent().getExtras() != null) {
            Expense expense = (Expense) getIntent().getExtras().getSerializable("SelectedExpense");
            if (expense==null)
            {
                Toast.makeText(ShowExpensesActivity.this, "There are no expenses to be displayed.", Toast.LENGTH_SHORT).show();
            }
            else{
                tv1.setText(expense.expensename.toString());
                tv2.setText(expense.expensecategory);
                tv3.setText("$ "+expense.amount+"");
                tv4.setText(expense.date.toString());
            }

        }

    }





    public void finish(View view) {
        finish();
    }
}

