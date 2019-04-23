package com.example.project;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class MemoriesAdapter extends RecyclerView.Adapter<MemoriesAdapter.Viewholder> {
    private Context context;
    private List<Upload> uploads;

    public MemoriesAdapter(Context context, List<Upload> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public MemoriesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemVieew= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.memories_item,viewGroup,false);

        return new Viewholder(itemVieew);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoriesAdapter.Viewholder viewholder, int i) {

        Upload upload=uploads.get(i);

        viewholder.textView.setText(upload.getName());
        Glide.with(context).load(upload.getUrl()).into(viewholder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class
    Viewholder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.textViewName);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
