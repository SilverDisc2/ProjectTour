package com.example.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class TourDisplayAdapter extends RecyclerView.Adapter<TourDisplayAdapter.ViewHolder> {
   // private HashMap<String, String> mData = new HashMap<String, String>();
    Context context;
    List<Trips> tripsList;

    public TourDisplayAdapter(Context context, List<Trips> tripsList) {
        this.context = context;
        this.tripsList = tripsList;
    }

    @NonNull
    @Override
    public TourDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_items,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TourDisplayAdapter.ViewHolder viewHolder, int i) {
Trips myTrips=tripsList.get(i);

viewHolder.nameTV.setText(myTrips.getTripName());
viewHolder.startDateTV.setText(myTrips.getStartDate());
viewHolder.endDateTV.setText(myTrips.getDescription());

    }

    @Override
    public int getItemCount() {
        return tripsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTV,startDateTV,endDateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.nameTV);
            startDateTV=itemView.findViewById(R.id.startTV);
            endDateTV=itemView.findViewById(R.id.endTV);
        }
    }
}
