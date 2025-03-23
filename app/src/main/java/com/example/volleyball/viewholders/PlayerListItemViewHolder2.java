package com.example.volleyball.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;

public class PlayerListItemViewHolder2 extends RecyclerView.ViewHolder {

    public TextView jerseyNumber, playerName;
    public LinearLayout itemContainer;
    public PlayerListItemViewHolder2(@NonNull View itemView) {
        super(itemView);
        jerseyNumber = itemView.findViewById(R.id.jerseyNumber);
        playerName = itemView.findViewById(R.id.playerName);
        itemContainer = itemView.findViewById(R.id.itemContainer);

    }
}
