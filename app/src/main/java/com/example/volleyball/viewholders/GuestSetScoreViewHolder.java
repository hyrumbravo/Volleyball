package com.example.volleyball.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;

public class GuestSetScoreViewHolder extends RecyclerView.ViewHolder {

    public TextView setScore;
    public GuestSetScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        setScore = itemView.findViewById(R.id.setScore);
    }
}
