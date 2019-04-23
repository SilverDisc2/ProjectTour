package com.example.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.List;


public class listViewAdapterExpense extends ArrayAdapter<Expenses> {

    private  Context context;

    public listViewAdapterExpense(Context context,   List<Expenses> objects) {
        super(context, 0, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.expense_items, parent, false);

        }


Expenses expenses=getItem(position);


        //EventPojo eventPojo=eventPojoList.get(position);
        //Collections.reverse();

        TextView textVieweDetails=(TextView)listViewItem.findViewById(R.id.expenseNameET);
        TextView textVieweAmount=(TextView)listViewItem.findViewById(R.id.expenseAmountET);

        textVieweDetails.setText(expenses.getExpenseName());
        textVieweAmount.setText(expenses.getAmount());



        return listViewItem;
    }
}
