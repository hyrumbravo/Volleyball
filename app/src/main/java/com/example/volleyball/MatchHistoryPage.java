package com.example.volleyball;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.volleyball.adapters.GuestSetScoreAdapter;
import com.example.volleyball.adapters.HomeSetScoreAdapter;
import com.example.volleyball.adapters.MatchHistoryAdapter;
import com.example.volleyball.databinding.MatchHistoryItemBinding;
import com.example.volleyball.databinding.MatchHistoryPageBinding;
import com.example.volleyball.models.Game;
import com.example.volleyball.models.SetScore;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ActivityInfo;
import androidx.activity.OnBackPressedCallback;

public class MatchHistoryPage extends AppCompatActivity {

    MatchHistoryPageBinding binding;
    List<Game> games;
    List<SetScore> homeSetScores;
    List<String> guestSetScores;
    MatchHistoryAdapter adapter;
    HomeSetScoreAdapter homeSetScoreAdapter;
    GuestSetScoreAdapter guestSetScoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = MatchHistoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // restrict activity's landscape view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // This callback will intercept the back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                whenBackIsPressed();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        // declarations
        games = new ArrayList<>();
        adapter = new MatchHistoryAdapter(this, games);
            // home set scores
        homeSetScores = new ArrayList<>();
        homeSetScoreAdapter = new HomeSetScoreAdapter(this, homeSetScores);
            // guest set scores
        guestSetScores = new ArrayList<>();
        guestSetScoreAdapter = new GuestSetScoreAdapter(this, guestSetScores);

            // onclicks
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());


        // match history
        games.add(new Game("2", "1", "some time", "1,2,3", "1,2,3"));
        games.add(new Game("2", "1", "some time", "1,2,3", "1,2,3"));
        games.add(new Game("2", "1", "some time", "1,2,3", "1,2,3"));
        games.add(new Game("2", "1", "some time", "1,2,3", "1,2,3"));
        games.add(new Game("2", "1", "some time", "1,2,3", "1,2,3"));

        binding.matchHistoryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        binding.matchHistoryRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.matchHistoryRecyclerview.setAdapter(adapter);

        // home set scores
        homeSetScores.add(new SetScore("Set 1", "20"));
        homeSetScores.add(new SetScore("Set 2", "20"));
        homeSetScores.add(new SetScore("Set 3", "20"));
        homeSetScores.add(new SetScore("Set 4", "20"));
        homeSetScores.add(new SetScore("Set 5", "20"));

        binding.homeSetScoreRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.homeSetScoreRecyclerview.setAdapter(homeSetScoreAdapter);

        //guest set scores
        guestSetScores.add("20");
        guestSetScores.add("20");
        guestSetScores.add("20");
        guestSetScores.add("20");
        guestSetScores.add("20");

        binding.guestSetScoreRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.guestSetScoreRecyclerview.setAdapter(guestSetScoreAdapter);





    }

    private void whenBackIsPressed() {
        finish();
    }
}