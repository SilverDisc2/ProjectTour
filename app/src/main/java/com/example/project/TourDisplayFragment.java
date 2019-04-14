package com.example.project;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.databinding.FragmentAddTourBinding;
import com.example.project.databinding.FragmentTourDisplayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TourDisplayFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FragmentTourDisplayBinding fragmentTourDisplayBinding;
    private RecyclerView recyclerView;
    private TourDisplayAdapter tourDisplayAdapter;
    private List<Trips> tripsList;

    public TourDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
   tripsList=new ArrayList<>();
        fragmentTourDisplayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tour_display, container, false);

        View view = fragmentTourDisplayBinding.getRoot();
        initRecyclerView();
        getDataFromDatabase();
      /*  fragmentTourDisplayBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
initRecyclerView();

               // initRecyclerView();

            }
        });*/


        return view;
    }

    private void getDataFromDatabase() {
        String userId = firebaseAuth.getCurrentUser().getUid();


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId).child("Trips");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<String> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String start = dataSnapshot1.child("startDate").getValue(String.class);
                    String end = dataSnapshot1.child("endDate").getValue(String.class);
                    String name = dataSnapshot1.child("name").getValue(String.class);
                    Log.d("TAG", start + " / " + end + " / " + name);
//String key=dataSnapshot1.getKey();
                    list.add(name);


                       // tripsList.clear();

                        //  .child("UsersList").child(uerId).child("Trips")

                          //  Tri user = data.getValue(User.class);
                           // userList.add(user);
                            //customAdapter.notifyDataSetChanged();
                        Trips trips=dataSnapshot1.getValue(Trips.class);
                        tripsList.add(trips);
                        tourDisplayAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRecyclerView() {
        fragmentTourDisplayBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tourDisplayAdapter = new TourDisplayAdapter(getActivity(), tripsList);
        fragmentTourDisplayBinding.recyclerView.setAdapter(tourDisplayAdapter);
    /*
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(userList, this);
        recyclerView.setAdapter(customAdapter);*/
    }

}

   /* List<String> list=new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                        String start=dataSnapshot1.child("startDate").getValue(String.class);
        String end=dataSnapshot1.child("endDate").getValue(String.class);
        String name=dataSnapshot1.child("name").getValue(String.class);
        Log.d("TAG", start + " / " + end  + " / " + name);
        Log.d("TAG", name);
        list.add(name);
        Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();*/