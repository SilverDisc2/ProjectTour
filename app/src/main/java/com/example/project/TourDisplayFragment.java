package com.example.project;


import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project.databinding.FragmentAddTourBinding;
import com.example.project.databinding.FragmentTourDisplayBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
    private String searchTour;


    public TourDisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        fragmentTourDisplayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tour_display, container, false);

        View view = fragmentTourDisplayBinding.getRoot();
        tripsList = new ArrayList<Trips>();
        //initRecyclerView();
        getDataFromDatabase();

        fragmentTourDisplayBinding.searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTour = fragmentTourDisplayBinding.searchTourET.getText().toString();

                if (searchTour.isEmpty()) {

                    Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
                    getDataFromDatabase();
                } else {
                    Toast.makeText(getActivity(), "" + searchTour, Toast.LENGTH_SHORT).show();

                    searchDB(searchTour);
                }
            }
        });
        fragmentTourDisplayBinding.lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Trips trips = tripsList.get(position);

                String getId = trips.getTripId();
                double getBudget = trips.getBudget();

                Bundle bundle = new Bundle();
                bundle.putString("evntID", getId);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ExpenseFragment expenseFragment = new ExpenseFragment();
                expenseFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.naviFL, expenseFragment);
                fragmentTransaction.commit();
            }
        });
      /*  fragmentTourDisplayBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
initRecyclerView();

               // initRecyclerView();

            }
        });*/
/*
    searchTour=  fragmentTourDisplayBinding..getText().toString();

    fragmentTourDisplayBinding.searchBTN.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchDB(searchTour);
        }
    });*/


        return view;
    }

    private void getDataFromDatabase() {
        String userId = firebaseAuth.getCurrentUser().getUid();


        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId).child("Trips");

        // Query query=databaseReference.orderByChild("tripName").equalTo("Now");

        // Query query = reference.child("questions").orderByChild("from").equalTo("this");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                List<String> list = new ArrayList<>();
                tripsList.clear();
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
                    Trips trips = dataSnapshot1.getValue(Trips.class);
                    tripsList.add(trips);
                    //  tourDisplayAdapter.notifyDataSetChanged();

                    ListViewAdapterTour tourAdapter = new ListViewAdapterTour(getActivity(), tripsList);
                    fragmentTourDisplayBinding.lV.setAdapter(tourAdapter);
                    tourAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRecyclerView() {
       /* fragmentTourDisplayBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tourDisplayAdapter = new TourDisplayAdapter(getActivity(), tripsList);
        fragmentTourDisplayBinding.recyclerView.setAdapter(tourDisplayAdapter);*/
    /*
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(userList, this);
        recyclerView.setAdapter(customAdapter);*/
   /* recyclerView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });*/

    }


    private void searchDB(String trip_name) {
        String userId = firebaseAuth.getCurrentUser().getUid();


        final DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId).child("Trips");

        Query query = databaseReference.orderByChild("tripName").equalTo(trip_name);

        // Query query = reference.child("questions").orderByChild("from").equalTo("this");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                List<String> list = new ArrayList<>();
                tripsList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                    Trips trips = dataSnapshot1.getValue(Trips.class);
                    tripsList.add(trips);

                    //    tourDisplayAdapter.notifyDataSetChanged();
                    ListViewAdapterTour tourAdapter = new ListViewAdapterTour(getActivity(), tripsList);
                    fragmentTourDisplayBinding.lV.setAdapter(tourAdapter);
                    tourAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private boolean updateArtist(String tripId, String trip_name) {
        //getting the specified artist reference


        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child("UsersList").child(userId).child("Trips").child(tripId);

        //updating artist
        Trips trips = new Trips(tripId, trip_name);
        dR.setValue(trips);
        Toast.makeText(getActivity(), "Trip Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String tripId, final String tripName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_xml, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);

        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(tripName);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();

                if (!TextUtils.isEmpty(name)) {
                    updateArtist(tripId, tripName);
                    b.dismiss();
                }
            }
        });
    }
}
