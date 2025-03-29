package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;
import com.example.volleyball.models.Player;
import com.example.volleyball.viewholders.PlayerListItemViewHolder1;
import com.example.volleyball.viewholders.PlayerTableViewHolder;

import java.util.List;

public class PlayersTableAdapter extends RecyclerView.Adapter<PlayerTableViewHolder> {

    Context context;
    List<Player> items;

    public PlayersTableAdapter(Context context, List<Player> items) {
        this.context = context;
        this.items = items;
    }

    public void addPlayer(List<Player> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerTableViewHolder(LayoutInflater.from(context).inflate(R.layout.players_table_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerTableViewHolder holder, int position) {
        holder.jerseyNumber.setText(items.get(position).getJerseyNumber());
        holder.playerName.setText(items.get(position).getName());
        holder.deleteIcon.setOnClickListener(view -> {
            items.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
