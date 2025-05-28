package com.example.volleyball.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;

public class MatchHistoryItemViewHolder extends RecyclerView.ViewHolder {

    public TextView homeScore, guestScore;
    public ConstraintLayout itemContainer;
    public ImageView deleteButton;
    public MatchHistoryItemViewHolder(@NonNull View itemView) {
        super(itemView);
        homeScore = itemView.findViewById(R.id.homeScore);
        guestScore = itemView.findViewById(R.id.guestScore);
        itemContainer = itemView.findViewById(R.id.itemContainer);
    }
}
