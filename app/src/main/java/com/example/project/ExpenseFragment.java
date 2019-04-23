package com.example.project;


import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.databinding.FragmentExpenseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    private FragmentExpenseBinding fragmentExpenseBinding;
    private ExpenseAdater expenseAdater;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private String expName, expAmount, tripId, eventIdT;
    private RecyclerView recyclerView;
    int amount, amountCheck;
    Boolean isFirst = false;
    int estimatedBudgetInteger;


    public ExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        fragmentExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense, container, false);

        View view = fragmentExpenseBinding.getRoot();





        fragmentExpenseBinding.addExpenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expName = fragmentExpenseBinding.expenseNameET.getText().toString();
                expAmount = fragmentExpenseBinding.expenseAmountET.getText().toString();
               // expenseCalculator(expName,expAmount);
                addExpenseToDB(new Expenses(expName, expAmount));

            }
        });

        return view;
    }

    public void addExpenseToDB(Expenses expenses) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        Bundle bundle = getArguments();


        eventIdT = bundle.getString("evntID");

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId)
                .child("Trips").child(eventIdT).child("Expense");
        String expensetId = databaseReference.push().getKey();
        databaseReference.child(expensetId).setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Expense Added Successfully" + eventIdT, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void expenseCalculator(String expesneName, String expenseAmount) {


        if (amountCheck > 0) {

            int totalExpenseWithCurrent = amountCheck + Integer.valueOf(expAmount);
            if (totalExpenseWithCurrent <= estimatedBudgetInteger) {


                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please waiting...");
                progressDialog.show();

                addExpenseToDB(new Expenses(expesneName, expenseAmount));
            } else {

                Toast.makeText(getActivity(), "Budget Exceded!!", Toast.LENGTH_SHORT).show();
            }

        } else if (isFirst) {
            int totalExpenseWithCurrent = amountCheck + Integer.valueOf(expAmount);
            if (totalExpenseWithCurrent <= estimatedBudgetInteger) {


                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please waiting...");
                progressDialog.show();
                addExpenseToDB(new Expenses(expesneName, expenseAmount));

            } else {

                Toast.makeText(getActivity(), "Budget Exceded!!", Toast.LENGTH_SHORT).show();
            }

        }


    }

   /* private void initRecyclerView() {
        fragmentExpenseBinding.expenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        expenseAdater = new ExpenseAdater(getActivity(), expensesList);
        fragmentExpenseBinding.expenseRecyclerView.setAdapter(expenseAdater);


    }*/






}

