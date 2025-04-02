package com.example.volleyball.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.volleyball.R;
import com.example.volleyball.models.SetScore;
import com.example.volleyball.viewholders.HomeSetScoreViewHolder;

import java.util.List;

public class HomeSetScoreAdapter extends RecyclerView.Adapter<HomeSetScoreViewHolder> {

    Context context;
    List<SetScore> items;

    public HomeSetScoreAdapter(Context context, List<SetScore> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HomeSetScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeSetScoreViewHolder(LayoutInflater.from(context).inflate(R.layout.home_set_score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSetScoreViewHolder holder, int position) {
        holder.setNumber.setText(String.valueOf(items.get(position).getSetNumber()));
        holder.setScore.setText(String.valueOf(items.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
