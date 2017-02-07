package com.example.inclass10;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {
ListView mylistview;
    MyAdapter adapter;
    TextView txtnoexpenses;
    String userid;
    ArrayList allexpenseslist=new ArrayList();
    ArrayList<Expense> alleexpenseslist=new ArrayList<Expense>();
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Expense deletedexpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        getSupportActionBar().setTitle("Expense App");
        findViewByIds();
        if (getIntent().getExtras() != null) {
            userid =  getIntent().getExtras().getString("UserID");
            mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference aexpref1= mDatabase.child("expenses").child(userid);
            aexpref1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    allexpenseslist.add(expense);
                    setListView();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    allexpenseslist.remove(deletedexpense);
                    setListView();
                    Toast.makeText(ExpenseActivity.this,"Expense Deleted",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
            setListView();
        }

    mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent in=new Intent(ExpenseActivity.this,ShowExpensesActivity.class);
        alleexpenseslist.addAll(allexpenseslist);
        Expense expense=alleexpenseslist.get(i);
        in.putExtra("SelectedExpense",expense);
        startActivity(in);
       setListView();
    }
});
mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
      deletedexpense=alleexpenseslist.get(i);
      String keyforexpensetobedeleted  =deletedexpense.expensekey;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference aexpref=mDatabase.child("expenses").child(userid).child(keyforexpensetobedeleted);
        aexpref.removeValue();
        return true;

    }
});
    }

    private void findViewByIds() {
        mylistview=(ListView) findViewById(R.id.mylistview);
        txtnoexpenses=(TextView) findViewById(R.id.txtnoexpenses);
    }
    private void setListView() {
        alleexpenseslist.clear();
        alleexpenseslist.addAll(allexpenseslist);
        if(alleexpenseslist.size()!=0){
            txtnoexpenses.setText("");
            mylistview.setVisibility(View.VISIBLE);
            adapter = new MyAdapter(ExpenseActivity.this, R.layout.row_layout, alleexpenseslist);
            mylistview.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }
        else{

            mylistview.setVisibility(View.GONE);
            txtnoexpenses.setText("There is no expense to show, Please add your expenses from the menu");

        }
    }

    public void addExpense(View view) {
        Intent in=new Intent(ExpenseActivity.this,AddExpenseActivity.class);
        in.putExtra("UserID",userid.toString());
        startActivityForResult(in,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){
            allexpenseslist= data.getExtras().getParcelableArrayList("ExpensesList");
            setListView();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Sign_Out) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent i=new Intent(ExpenseActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            } else {
                // No user is signed in
            }

        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
