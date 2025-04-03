package com.example.volleyball.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.volleyball.R;

public class StatsItemViewHolder extends RecyclerView.ViewHolder {

    public TextView playerName, spike, block, dig, ace;
    public StatsItemViewHolder(@NonNull View itemView) {
        super(itemView);
        playerName = itemView.findViewById(R.id.playerName);
        spike = itemView.findViewById(R.id.spike);
        block = itemView.findViewById(R.id.block);
        dig = itemView.findViewById(R.id.dig);
        ace = itemView.findViewById(R.id.ace);
    }
}
