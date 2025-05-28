package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.Database.DatabaseHelper;
import com.example.volleyball.R;
import com.example.volleyball.models.Game;
import com.example.volleyball.selectListeners.MatchHistoryItemSelectListener;
import com.example.volleyball.viewholders.MatchHistoryItemViewHolder;

import java.util.List;

public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryItemViewHolder> {
    Context context;
    List<Game> items;

    MatchHistoryItemSelectListener listener;

    public MatchHistoryAdapter(Context context, List<Game> items, MatchHistoryItemSelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MatchHistoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.match_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHistoryItemViewHolder holder, int position) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        
        holder.homeScore.setText(String.valueOf(items.get(position).getTeam1Score()));
        holder.guestScore.setText(String.valueOf(items.get(position).getTeam2Score()));

        // item container onclick
        holder.itemContainer.setOnClickListener(v -> {
            listener.onItemClicked(items.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
