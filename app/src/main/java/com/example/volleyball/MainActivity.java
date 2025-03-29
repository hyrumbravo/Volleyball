package com.example.volleyball;

import android.opengl.Visibility;
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
        actualHomePlayersAdapter = new PlayerListAdapter(this, homePlayersList, this);
        actualGuestPlayersAdapter = new PlayerListAdapter(this, guestPlayersList, this);
        homePlayersStats = new HashMap<>();
        guestPlayersStats = new HashMap<>();

            // stats buttons onclicks
        binding.statButton1.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton1));
        binding.statButton2.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton2));
        binding.statButton3.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton3));
        binding.statButton4.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton4));
            // tables add buttons onclick
        binding.addPlayerButton1.setOnClickListener(view ->
                addHomePlayer(binding.jerseyNumField1, binding.playerNameField1));
        binding.addPlayerButton2.setOnClickListener(view ->
                addGuestPlayers(binding.jerseyNumField2, binding.playerNameField2));
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
        binding.teamListRecyclerview2.setAdapter(actualGuestPlayersAdapter);

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

    void addHomePlayer(EditText jerseyNumField1, EditText playerNameField1) {
        String jerseyNumber = jerseyNumField1.getText().toString();
        String playerName = playerNameField1.getText().toString();
        // check if fields are not empty
        if (jerseyNumber.isEmpty() || playerName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // else, add player to table
        homePlayersList.add(new Player(jerseyNumber, playerName));
        homeTableAdapter.addPlayer(homePlayersList);
        // clear fields
        binding.jerseyNumField1.setText("");
        binding.playerNameField1.setText("");

    }

    void addGuestPlayers(EditText jerseyNumField2, EditText playerNameField2) {
        String jerseyNumber = jerseyNumField2.getText().toString();
        String playerName = playerNameField2.getText().toString();
        // check if fields are not empty
        if (jerseyNumber.isEmpty() || playerName.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // else, add player to table
        guestPlayersList.add(new Player(jerseyNumber, playerName));
        guestTableAdapter.addPlayer(guestPlayersList);
        // clear fields
        jerseyNumField2.setText("");
        playerNameField2.setText("");
    }

    void savePlayers() {
        // remove overlay
        binding.addPlayersOverlay.setVisibility(View.GONE);
        // set actual players list items
        actualHomePlayersAdapter.updatePlayerList(homePlayersList);
        actualGuestPlayersAdapter.updatePlayerList(guestPlayersList);
    }
}