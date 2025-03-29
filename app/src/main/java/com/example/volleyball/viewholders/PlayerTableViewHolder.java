package com.example.volleyball.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;

public class PlayerTableViewHolder extends RecyclerView.ViewHolder{

    public TextView jerseyNumber, playerName;
    public ImageView deleteIcon;
    public PlayerTableViewHolder(@NonNull View itemView) {
        super(itemView);
        jerseyNumber = itemView.findViewById(R.id.jerseyNumber);
        playerName = itemView.findViewById(R.id.playerName);
        deleteIcon = itemView.findViewById(R.id.deleteButton);
    }
}
