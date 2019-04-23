package com.example.project;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class ShowMemoriesFragment extends Fragment {
    private RecyclerView recyclerViewMomories;
private MemoriesAdapter adapter;
private List<Upload> uploads;
private DatabaseReference mDatabase;
private ProgressDialog progressDialog;
private FirebaseAuth firebaseAuth;

    public ShowMemoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_show_memories, container, false);



        recyclerViewMomories=view.findViewById(R.id.recyclerViewMomories);
        recyclerViewMomories.setHasFixedSize(true);
        recyclerViewMomories.setLayoutManager(new LinearLayoutManager(getActivity()));


        progressDialog=new ProgressDialog(getActivity());
progressDialog.show();

uploads=new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        firebaseAuth=FirebaseAuth.getInstance();
        final String userId=firebaseAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("UsersList").child(userId).child("Trips").child("Memories");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                //creating adapter
                adapter = new MemoriesAdapter(getActivity(),uploads);

                //adding adapter to recyclerview
              recyclerViewMomories.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
progressDialog.dismiss();
            }
        });


        return view;
    }

}
