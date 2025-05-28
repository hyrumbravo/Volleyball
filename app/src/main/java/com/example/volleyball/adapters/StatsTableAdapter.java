package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;
import com.example.volleyball.models.Player;
import com.example.volleyball.models.Stats;
import com.example.volleyball.viewholders.StatsItemViewHolder;
import java.util.List;

public class StatsTableAdapter extends RecyclerView.Adapter<StatsItemViewHolder> {

    Context context;
    List<Stats> items;

    public StatsTableAdapter(Context context, List<Stats> items) {
        this.context = context;
        this.items = items;
    }

    public void updateStatsList(List<Stats> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StatsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StatsItemViewHolder(LayoutInflater.from(context).inflate(R.layout.stats_table_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StatsItemViewHolder holder, int position) {
        holder.playerName.setText(items.get(position).getPlayerName());
        holder.spike.setText(String.valueOf(items.get(position).getSpike()));
        holder.block.setText(String.valueOf(items.get(position).getBlock()));
        holder.dig.setText(String.valueOf(items.get(position).getDig()));
        holder.ace.setText(String.valueOf(items.get(position).getAce()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
