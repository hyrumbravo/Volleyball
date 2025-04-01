package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;
import com.example.volleyball.models.Game;
import com.example.volleyball.viewholders.MatchHistoryItemViewHolder;

import java.util.List;

public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryItemViewHolder> {
    Context context;
    List<Game> items;

    public MatchHistoryAdapter(Context context, List<Game> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MatchHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MatchHistoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.match_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHistoryItemViewHolder holder, int position) {
        holder.homeScore.setText(String.valueOf(items.get(position).getTeam1Score()));
        holder.guestScore.setText(String.valueOf(items.get(position).getTeam2Score()));
        // delete button onclick
        holder.deleteButton.setOnClickListener(v -> {
            items.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
