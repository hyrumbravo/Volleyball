package com.example.volleyball;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.volleyball.Database.DatabaseHelper;
import com.example.volleyball.adapters.GuestSetScoreAdapter;
import com.example.volleyball.adapters.HomeSetScoreAdapter;
import com.example.volleyball.adapters.MatchHistoryAdapter;
import com.example.volleyball.adapters.StatsTableAdapter;
import com.example.volleyball.databinding.MatchHistoryItemBinding;
import com.example.volleyball.databinding.MatchHistoryPageBinding;
import com.example.volleyball.models.Game;
import com.example.volleyball.models.SetScore;
import com.example.volleyball.models.Stats;
import com.example.volleyball.selectListeners.MatchHistoryItemSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.activity.OnBackPressedCallback;

public class MatchHistoryPage extends AppCompatActivity implements MatchHistoryItemSelectListener {

    MatchHistoryPageBinding binding;
    // List items
    List<Game> games;
    List<SetScore> homeSetScores;
    List<String> guestSetScores;
    List<Stats> homeStats, guestStats;
    // adapters
    MatchHistoryAdapter adapter;
    HomeSetScoreAdapter homeSetScoreAdapter;
    GuestSetScoreAdapter guestSetScoreAdapter;
    StatsTableAdapter homeStatsAdapter, guestStatsAdapter;
    DatabaseHelper dbHelper;

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
            // match history
        games = new ArrayList<>();
        adapter = new MatchHistoryAdapter(this, games, this);
            // home set scores
        homeSetScores = new ArrayList<>();
        homeSetScoreAdapter = new HomeSetScoreAdapter(this, homeSetScores);
            // guest set scores
        guestSetScores = new ArrayList<>();
        guestSetScoreAdapter = new GuestSetScoreAdapter(this, guestSetScores);
            // home stats
        homeStats = new ArrayList<>();
        homeStatsAdapter = new StatsTableAdapter(this, homeStats);
            // guest stats
        guestStats = new ArrayList<>();
        guestStatsAdapter = new StatsTableAdapter(this, guestStats);
            // database
        dbHelper = new DatabaseHelper(this);


            // onclicks
        binding.backButton.setOnClickListener(v -> whenBackIsPressed());
        binding.gameDetailsOverlay.setOnClickListener(view -> view.setVisibility(View.GONE));


        // match history
        games.addAll(dbHelper.getAllGames());

        binding.matchHistoryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.matchHistoryRecyclerview.setAdapter(adapter);

        // home set scores
        binding.homeSetScoreRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.homeSetScoreRecyclerview.setAdapter(homeSetScoreAdapter);

        //guest set scores
        binding.guestSetScoreRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.guestSetScoreRecyclerview.setAdapter(guestSetScoreAdapter);

        // home stats
        binding.homeStatsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.homeStatsRecyclerview.setAdapter(homeStatsAdapter);

        // guest stats
        binding.guestStatsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.guestStatsRecyclerview.setAdapter(guestStatsAdapter);

    }

    private void whenBackIsPressed() {
        finish();
    }

    @Override
    public void onItemClicked(Game game) {
        binding.gameDetailsOverlay.setVisibility(View.VISIBLE);
        // get home stats
        homeStats.addAll(dbHelper.getStatsByGameId(game.getId(), "home"));
        // get guest stats
        guestStats.addAll(dbHelper.getStatsByGameId(game.getId(), "guest"));
        // get home set scores
            // remove last comma
        String clickedGameSetScores = game.getHomeSetScores().substring(0, game.getHomeSetScores().length() - 1);
            // split to array list
        ArrayList<String>  clickedGameHomeSetScores = splitToArrayList(clickedGameSetScores, ",");
        // set home set scores
        for (int i = 0; i < clickedGameHomeSetScores.size(); i++) {
            homeSetScores.add(new SetScore( "Set "+ String.valueOf(i + 1), clickedGameHomeSetScores.get(i)));
        }
        // get guest set scores
            // remove last comma
        String clickedGuestSetScores = game.getGuestSetScores().substring(0, game.getGuestSetScores().length() - 1);
            // split to array list
        ArrayList<String>  clickedGuestHomeSetScores = splitToArrayList(clickedGuestSetScores, ",");
        // set guest set scores
        for (int i = 0; i < clickedGuestHomeSetScores.size(); i++) {
            guestSetScores.add(clickedGuestHomeSetScores.get(i));
        }

    }

    public static ArrayList<String> splitToArrayList(String str, String delimiter) {
        return new ArrayList<>(Arrays.asList(str.split(delimiter)));
    }
}