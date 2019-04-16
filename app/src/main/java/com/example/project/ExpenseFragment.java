package com.example.project;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.databinding.FragmentExpenseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    private FragmentExpenseBinding fragmentExpenseBinding;
    private String expenseName, expenseAmount;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private String expName, expAmount, tripId;

    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        fragmentExpenseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expense, container, false);

        View view = fragmentExpenseBinding.getRoot();
        Bundle bundle = getArguments();

        tripId = bundle.getString("t");
        if (tripId.isEmpty()) {

        } else {

            Log.d("Got it", "onCreateView: " + tripId);
        }
        expName = fragmentExpenseBinding.expenseNameET.getText().toString();
        expAmount = fragmentExpenseBinding.expenseAmountET.getText().toString();
       // final double d = Double.parseDouble(expAmount);
        fragmentExpenseBinding.addExpenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addExpenseToDB(new Expenses(expenseName,expAmount ));

            }
        });

        return view;
    }

    private void addExpenseToDB(Expenses expenses) {
        String userId = firebaseAuth.getCurrentUser().getUid();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId)
                .child("Trips").child(tripId).child("Expense");
        databaseReference.setValue(expenses).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Expense Added Successfully" + tripId, Toast.LENGTH_LONG).show();
            }
        });

    }


}
