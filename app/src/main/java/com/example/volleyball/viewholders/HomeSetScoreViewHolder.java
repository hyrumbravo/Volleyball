package com.example.volleyball.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;

public class HomeSetScoreViewHolder extends RecyclerView.ViewHolder {

    public TextView setNumber, setScore;
    public HomeSetScoreViewHolder(@NonNull View itemView) {
        super(itemView);
        setNumber = itemView.findViewById(R.id.setNumber);
        setScore = itemView.findViewById(R.id.setScore);
    }
}
