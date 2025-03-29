package com.example.volleyball;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.volleyball.Utilities.Utility;
import com.example.volleyball.adapters.PlayerListAdapter;
import com.example.volleyball.adapters.PlayersTableAdapter;
import com.example.volleyball.databinding.ActivityMainBinding;
import com.example.volleyball.models.Player;
import com.example.volleyball.models.Stats;
import com.example.volleyball.selectListeners.PlayerListSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlayerListSelectListener {

    public static ActivityMainBinding binding;
    List<Player> homePlayersList, guestPlayersList;
    PlayersTableAdapter homeTableAdapter, guestTableAdapter;
    PlayerListAdapter actualHomePlayersAdapter, actualGuestPlayersAdapter;
    ArrayList<AppCompatButton> statsButtons;
    HashMap<String, Stats> homePlayersStats, guestPlayersStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // declarations
        homePlayersList = new ArrayList<>();
        guestPlayersList = new ArrayList<>();
        statsButtons = new ArrayList<>(Arrays.asList(binding.statButton1, binding.statButton2,
                binding.statButton3, binding.statButton4));
        homeTableAdapter = new PlayersTableAdapter(this, homePlayersList);
        guestTableAdapter = new PlayersTableAdapter(this, guestPlayersList);
        actualGuestPlayersAdapter = new PlayerListAdapter(this, guestPlayersList, this);
        actualHomePlayersAdapter = new PlayerListAdapter(this, homePlayersList, this);
        homePlayersStats = new HashMap<>();
        guestPlayersStats = new HashMap<>();

            // stats buttons onclicks
        binding.statButton1.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton1));
        binding.statButton2.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton2));
        binding.statButton3.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton3));
        binding.statButton4.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton4));
            // tables add buttons onclick
        binding.addPlayerButton1.setOnClickListener(view ->
                addPlayersToTable(binding.jerseyNumField1, binding.playerNameField1, homePlayersList, homeTableAdapter));
        binding.addPlayerButton2.setOnClickListener(view ->
                addPlayersToTable(binding.jerseyNumField2, binding.playerNameField2, guestPlayersList, guestTableAdapter));
            // addPlayer overlay onclick
        binding.addPlayersOverlay.setOnClickListener(view -> binding.addPlayersOverlay.setVisibility(View.GONE));
            // add player icon onclick
        binding.addButton.setOnClickListener(view -> binding.addPlayersOverlay.setVisibility(View.VISIBLE));
            // savePlayers button onclick
        binding.saveButton.setOnClickListener(view -> savePlayers());



        // player list 1
        binding.teamListRecyclerview1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview1.setAdapter(actualGuestPlayersAdapter);

        // player list 2
        binding.teamListRecyclerview2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview2.setAdapter(actualHomePlayersAdapter);

        // players table1

        binding.teamPlayersRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamPlayersRecyclerView1.setAdapter(homeTableAdapter);

        // players table2
        binding.teamPlayersRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamPlayersRecyclerView2.setAdapter(guestTableAdapter);

    }

    @Override
    public void onItemClicked(Player player) {
        Toast.makeText(this, player.getName(), Toast.LENGTH_SHORT).show();
    }

    void addPlayersToTable(EditText jerseyNumField1, EditText playerNameField1,
                       List<Player> playerList, PlayersTableAdapter adapter) {
        String jerseyNumber = jerseyNumField1.getText().toString();
        String playerName = playerNameField1.getText().toString();
        // check if fields are not empty
        if (jerseyNumber.isEmpty() || playerName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // else, add player to table
        playerList.add(new Player(jerseyNumber, playerName));
        adapter.addPlayer(playerList);
        // clear fields
        jerseyNumField1.setText("");
        playerNameField1.setText("");

    }

    void savePlayers() {
        // remove overlay
        binding.addPlayersOverlay.setVisibility(View.GONE);
        // set actual players list items
        actualGuestPlayersAdapter.updatePlayerList(guestPlayersList);
        actualHomePlayersAdapter.updatePlayerList(homePlayersList);
    }
}