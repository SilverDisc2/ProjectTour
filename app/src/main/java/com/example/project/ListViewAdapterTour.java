package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListViewAdapterTour extends ArrayAdapter<Trips> {
    private Context context;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private String tid;

    public ListViewAdapterTour(Context context, List<Trips> objects) {
        super(context, 0, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = convertView;
        if (listViewItem == null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.tour_items, parent, false);

        }
        final Trips trips = getItem(position);


        //EventPojo eventPojo=eventPojoList.get(position);
        //Collections.reverse();

        TextView textViewPlace = (TextView) listViewItem.findViewById(R.id.nameTV);
        TextView textViewFdate = (TextView) listViewItem.findViewById(R.id.startTV);
        TextView textViewTdate = (TextView) listViewItem.findViewById(R.id.endTV);
        // CardView cardView=listViewItem.findViewById(R.id.cv);
        final ImageView popupIV = listViewItem.findViewById(R.id.popUpIV);
        textViewPlace.setText(trips.getTripName());
        textViewFdate.setText(trips.getStartDate());
        textViewTdate.setText(trips.getEndDate());

        popupIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tid = trips.getTripId();
                Toast.makeText(getContext(), "HELLOOOOO" + tid, Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(getContext(), popupIV);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.delete:
                                Toast.makeText(getContext(), "Again" + tid, Toast.LENGTH_SHORT).show();
                                deleteTrip();

                        }


                        return false;
                    }
                });
            }
        });
        return listViewItem;
    }

    private boolean deleteTrip() {
        //getting the specified artist reference
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId)
                .child("Trips").child(tid);


        databaseReference.removeValue();
        notifyDataSetChanged();

        Toast.makeText(getContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

        return true;
    }


    private boolean updateArtist(String tname,String startDate, String endDate, String description,double budget) {
        //getting the specified artist reference

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("UsersList").child(userId)
                .child("Trips").child(tid);
    //    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);

        //updating artist
        Trips artist = new Trips(tname,startDate,endDate,description,budget);
        databaseReference.setValue(artist);
        Toast.makeText(getContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }




}
