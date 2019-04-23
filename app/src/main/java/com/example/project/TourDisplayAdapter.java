package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class TourDisplayAdapter extends RecyclerView.Adapter<TourDisplayAdapter.ViewHolder> {
    // private HashMap<String, String> mData = new HashMap<String, String>();
    Context context;
    List<Trips> tripsList;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Trips myTrips;

    public TourDisplayAdapter(Context context, List<Trips> tripsList) {
        this.context = context;
        this.tripsList = tripsList;
    }

    @NonNull
    @Override
    public TourDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_items, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TourDisplayAdapter.ViewHolder viewHolder, int i) {
        myTrips = tripsList.get(i);

        viewHolder.nameTV.setText(myTrips.getTripName());
        myTrips.getTripId();
        viewHolder.startDateTV.setText(myTrips.getStartDate());
        viewHolder.endDateTV.setText(myTrips.getEndDate());
        viewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog alertDialog = new AlertDialog.Builder(
                        context).create();

                alertDialog.setTitle("Delete Expense");


                alertDialog.setMessage("Do you want to delete this expense from list?");


                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userId = firebaseAuth.getCurrentUser().getUid();


                        databaseReference = FirebaseDatabase.getInstance().getReference().child("UsersList").child(userId).child("Trips").child(myTrips.getTripId());


                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

                    }
                });


                alertDialog.show();


                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV, startDateTV, endDateTV, budgetTV;
        private CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
            startDateTV = itemView.findViewById(R.id.startTV);
            endDateTV = itemView.findViewById(R.id.endTV);
            cv = itemView.findViewById(R.id.cv);

        }
    }


}
