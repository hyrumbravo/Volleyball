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

    @NonNull
    @Override
    public PlayerListItemViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerListItemViewHolder1(LayoutInflater.from(context).inflate(R.layout.player_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerListItemViewHolder1 holder, int position) {
        holder.jerseyNumber.setText(items.get(position).getJerseyNumber());
        holder.playerName.setText(items.get(position).getName());
//        playerlist1 onItemClick
        holder.itemContainer.setOnClickListener(v -> {
            listener.onItemClicked(items.get(position));
            if (v.getTag().toString().equalsIgnoreCase("selected")) {
                v.setBackgroundResource(R.drawable.timer_bg); // set BG to default
                v.setTag("unselected"); // set tag to unselected
                Utility.setTextColors( // set text colors to violet
                        new ArrayList<>(Arrays.asList(holder.jerseyNumber, holder.playerName)),
                        ContextCompat.getColor(context, R.color.violet)
                );
            }
            else {
                v.setBackgroundResource(R.drawable.seleted_player_bg); // set BG to selected
                v.setTag("selected"); // set tag to selected
                Utility.setTextColors( // set text colors to white
                        new ArrayList<>(Arrays.asList(holder.jerseyNumber, holder.playerName)),
                        ContextCompat.getColor(context, R.color.white)
                );
            }

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
