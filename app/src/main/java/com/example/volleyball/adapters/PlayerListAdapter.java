package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.MainActivity;
import com.example.volleyball.R;
import com.example.volleyball.Utilities.Utility;
import com.example.volleyball.databinding.ActivityMainBinding;
import com.example.volleyball.models.Player;
import com.example.volleyball.selectListeners.PlayerListSelectListener;
import com.example.volleyball.viewholders.PlayerListItemViewHolder1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListItemViewHolder1> {

    Context context;
    List<Player> items;
    ActivityMainBinding binding;
    PlayerListSelectListener listener;
    public PlayerListAdapter(Context context, List<Player> items, PlayerListSelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        binding = MainActivity.binding;
    }

    public void updatePlayerList(List<Player> newList) {
        this.items = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerListItemViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerListItemViewHolder1(LayoutInflater.from(context).inflate(R.layout.player_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListItemViewHolder1 holder, int position) {
        holder.jerseyNumber.setText(items.get(position).getJerseyNumber());
        holder.playerName.setText(items.get(position).getName());

        // playerlist1 onItemClick
        holder.itemContainer.setOnClickListener(v -> {
            listener.onItemClicked(items.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
