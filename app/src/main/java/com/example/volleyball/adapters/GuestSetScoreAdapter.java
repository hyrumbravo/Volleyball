package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;
import com.example.volleyball.models.SetScore;
import com.example.volleyball.viewholders.GuestSetScoreViewHolder;

import java.util.List;

public class GuestSetScoreAdapter extends RecyclerView.Adapter<GuestSetScoreViewHolder> {

    Context context;
    List<String> items;

    public GuestSetScoreAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public GuestSetScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuestSetScoreViewHolder(LayoutInflater.from(context).inflate(R.layout.guest_set_score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuestSetScoreViewHolder holder, int position) {
        holder.setScore.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
