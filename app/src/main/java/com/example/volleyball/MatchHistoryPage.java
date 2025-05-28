package com.example.volleyball;

import android.app.AlertDialog;
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
import android.widget.Toast;

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
    int clickedGameId;

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
        binding.closeGameDetailsButton.setOnClickListener(view -> binding.gameDetailsOverlay.setVisibility(View.GONE));
        binding.gameDetailsPopup.setOnClickListener(view -> {});
        binding.deleteMatchHistoryButton.setOnClickListener(view -> deleteGame(clickedGameId));


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

        // check if match history is empty
        if (games.isEmpty()) {
            binding.emptyMatchHistoryText.setVisibility(View.VISIBLE);
        }

    }

    private void deleteGame(int clickedGameId) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this game?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    // Delete the log from the database
                    boolean isDeleted = dbHelper.deleteGameById(clickedGameId);
                    if (isDeleted) {
                        Toast.makeText(this, "Game deleted successfully.", Toast.LENGTH_SHORT).show();
                        recreate();

                    } else {
                        Toast.makeText(this, "Deletion unsuccessful.", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
        // delete clicked game

    }

    private void whenBackIsPressed() {
        finish();
    }

    @Override
    public void onItemClicked(Game game) {
        // set clicked game id
        clickedGameId = game.getId();
        // clear previous data
        homeSetScores.clear();
        guestSetScores.clear();
        homeStats.clear();
        guestStats.clear();
        // get home stats
        homeStats.addAll(dbHelper.getStatsByGameId(game.getId(), "home"));
        // get guest stats
        guestStats.addAll(dbHelper.getStatsByGameId(game.getId(), "guest"));
        // set home set scores
        setHomeSetScores(game);
        // set guest set scores
        setGuestSetScores(game);
        // update stats list
        homeStatsAdapter.updateStatsList(homeStats);
        guestStatsAdapter.updateStatsList(guestStats);
        // update guest set score list
        binding.gameDetailsOverlay.setVisibility(View.VISIBLE);

    }

    private void setHomeSetScores(Game game) {
        // get home set scores
            // remove last comma
        String clickedGameHomeSetScores = game.getHomeSetScores().substring(0, game.getHomeSetScores().length() - 1);
            // split to array list
        ArrayList<String>  homeSetScore = splitToArrayList(clickedGameHomeSetScores, ",");
        // set home set scores
        for (int i = 0; i < homeSetScore.size(); i++) {
            homeSetScores.add(new SetScore( "Set "+ String.valueOf(i + 1), homeSetScore.get(i)));
        }
        // update home set score list
        homeSetScoreAdapter.notifyDataSetChanged();
    }

    private void setGuestSetScores(Game game) {
        // get guest set scores
            // remove last comma
        String clickedGameGuestSetScores = game.getGuestSetScores().substring(0, game.getGuestSetScores().length() - 1);
            // split to array list
        ArrayList<String> guestSetScore = splitToArrayList(clickedGameGuestSetScores, ",");
        // set guest set scores
        for (int i = 0; i < guestSetScore.size(); i++) {
            guestSetScores.add(guestSetScore.get(i));
        }
        // update guest set score list
        guestSetScoreAdapter.notifyDataSetChanged();
    }

    public static ArrayList<String> splitToArrayList(String str, String delimiter) {
        return new ArrayList<>(Arrays.asList(str.split(delimiter)));
    }
}