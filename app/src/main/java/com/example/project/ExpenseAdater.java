package com.example.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdater extends RecyclerView.Adapter<ExpenseAdater.Viewholder> {
    Context context;
    List<Expenses> expenses;

    public ExpenseAdater(Context context, List<Expenses> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpenseAdater.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expense_items,viewGroup,false);

       // View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.,viewGroup,false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdater.Viewholder viewholder, int i) {

        Expenses currentExpense= expenses.get(i);

        viewholder.expenseNameTV.setText((currentExpense.getExpenseName()));
        viewholder.expenseAmountTV.setText((currentExpense.getAmount()));

    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView expenseNameTV,expenseAmountTV;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            expenseNameTV=itemView.findViewById(R.id.expense_nameET);
            expenseAmountTV=itemView.findViewById(R.id.expense_amountET);

        }
    }
}
