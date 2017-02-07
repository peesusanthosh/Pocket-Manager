package com.example.inclass10;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Santhosh Reddy Peesu on 10/4/2016.
 */
public class MyAdapter extends ArrayAdapter<Expense> {
    Context mycontext;
    int myresource;
    List<Expense> expenselist;
    String useremail;
    SharedPreferences mPrefs;

    public MyAdapter(Context context, int resource, List<Expense> objects) {
        super(context, resource, objects);
        this.mycontext=context;
        this.expenselist=objects;
        this.myresource=resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) throws NullPointerException {

        if (convertView==null){
            LayoutInflater inflater=(LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(myresource,parent,false);
        }

        Expense expense=expenselist.get(position);
        TextView name=(TextView) convertView.findViewById(R.id.name);
        TextView amount=(TextView) convertView.findViewById(R.id.amount);
        name.setText(" "+expense.expensename.toString());
        amount.setText(expense.amount.toString() + " $");
        convertView.setBackgroundColor(Color.parseColor("#F0F8FF"));
        return convertView;
    }

}
