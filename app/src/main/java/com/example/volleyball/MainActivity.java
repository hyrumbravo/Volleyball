package com.example.volleyball;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    HashMap<Player, Stats> homePlayersStats, guestPlayersStats;
    String selectedStat;

    Player selectedPlayer;
    boolean homePlayer;

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
        selectedStat = "";

            // stats buttons onclicks
        binding.statButton1.setOnClickListener(v -> statsButtonOnClick(binding.statButton1));
        binding.statButton2.setOnClickListener(v -> statsButtonOnClick(binding.statButton2));
        binding.statButton3.setOnClickListener(v -> statsButtonOnClick(binding.statButton3));
        binding.statButton4.setOnClickListener(v -> statsButtonOnClick(binding.statButton4));

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
            // statsButtons onclicks
        binding.statsMinusButton.setOnClickListener(view -> deductStatToPlayer(selectedPlayer, selectedStat));
        binding.statsAddButton.setOnClickListener(view -> addStatToPlayer(selectedPlayer, selectedStat));
            // settings onclicks
        binding.settingsButton.setOnClickListener(view -> binding.settingsOverlay.setVisibility(View.VISIBLE));
        binding.settingsOverlay.setOnClickListener(view -> binding.settingsOverlay.setVisibility(View.GONE));
        binding.settingsPopup.setOnClickListener(view -> {});


        // player list 1
        homePlayersList.add(new Player("1", "Sample")); // add sample player
        homePlayersList.add(new Player("2", "Sample2")); // add sample playe
        homePlayersList.add(new Player("3", "Sample3")); // add sample player

        binding.teamListRecyclerview1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview1.setAdapter(actualHomePlayersAdapter);

        // player list 2
        guestPlayersList.add(new Player("1", "Sample")); // add sample player
        guestPlayersList.add(new Player("2", "Sample2")); // add sample player
        guestPlayersList.add(new Player("3", "Sample3")); // add sample player

        binding.teamListRecyclerview2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview2.setAdapter(actualGuestPlayersAdapter);

        // players table1
        binding.teamPlayersRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamPlayersRecyclerView1.setAdapter(homeTableAdapter);

        // players table2
        binding.teamPlayersRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamPlayersRecyclerView2.setAdapter(guestTableAdapter);

    }

    private void addStatToPlayer(Player selectedPlayer, String selectedStat) {
        // check if home player
        if (homePlayer) {
            Utility.addStat(homePlayersStats, selectedPlayer, selectedStat, this, binding.main, true);
        }
        else {
            Utility.addStat(guestPlayersStats, selectedPlayer, selectedStat, this, binding.main, false);
        }
    }

    private void deductStatToPlayer(Player selectedPlayer, String selectedStat) {
        // check if home player
        if (homePlayer) {
            Utility.deductStat(homePlayersStats, selectedPlayer, selectedStat, this, binding.main, true);
        }
        else {
            Utility.deductStat(guestPlayersStats, selectedPlayer, selectedStat, this, binding.main, false);
        }
    }

    @Override
    public void onItemClicked(Player player) {
        // select players
        selectedPlayer = player;
        if (homePlayersList.contains(player)) {
            homePlayer = true;
            selectPlayers(binding.teamListRecyclerview1, player, homePlayersList, binding.teamListRecyclerview2);
        }
        else {
            homePlayer = false;
            selectPlayers(binding.teamListRecyclerview2, player, guestPlayersList, binding.teamListRecyclerview1);
        }
    }

    void selectPlayers(RecyclerView recyclerView, Player player, List<Player> players, RecyclerView opposingRecyclerview) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            LinearLayout itemContainer = itemView.findViewById(R.id.itemContainer);
            TextView jerseyNumber = itemView.findViewById(R.id.jerseyNumber);
            TextView playerName = itemView.findViewById(R.id.playerName);
            // set to default style
            if (i != players.indexOf(player)) {
                itemContainer.setBackgroundResource(R.drawable.timer_bg);
                jerseyNumber.setTextColor(ContextCompat.getColor(this, R.color.violet));
                playerName.setTextColor(ContextCompat.getColor(this, R.color.violet));
            }
            // set to selected style
            else {
                itemContainer.setBackgroundResource(R.drawable.seleted_player_bg);
                jerseyNumber.setTextColor(ContextCompat.getColor(this, R.color.white));
                playerName.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
        }
        setPlayersListBGToDefault(opposingRecyclerview);
    }
    void setPlayersListBGToDefault(RecyclerView recyclerView) {
        // set recyclerview items' to default style
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            LinearLayout itemContainer = itemView.findViewById(R.id.itemContainer);
            TextView jerseyNumber = itemView.findViewById(R.id.jerseyNumber);
            TextView playerName = itemView.findViewById(R.id.playerName);
            itemContainer.setBackgroundResource(R.drawable.timer_bg);
            jerseyNumber.setTextColor(ContextCompat.getColor(this, R.color.violet));
            playerName.setTextColor(ContextCompat.getColor(this, R.color.violet));
        }
    }
    void addPlayersToTable(EditText jerseyNumField1, EditText playerNameField1,
                       List<Player> playerList, PlayersTableAdapter adapter) {
        // get jersey number and player name
        String jerseyNumber = jerseyNumField1.getText().toString();
        String playerName = playerNameField1.getText().toString();

        // check if jersey number already exists
        if (Utility.jerseyNumExists(playerList, jerseyNumber)) {
            Toast.makeText(this, "Jersey number is already taken", Toast.LENGTH_SHORT).show();
            return;
        }

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
        // set stats
        setUpStats();
    }
    void statsButtonOnClick(AppCompatButton statsButton) {
        Utility.setStatsBackground(statsButtons, statsButton); // set stats button accordingly
        selectedStat = Utility.toTitleCase(statsButton.getText().toString()); // get stats button text
    }
    void setUpStats() {
        // set up home players stats
        for (int i = 0; i < homePlayersList.size(); i++) {
            homePlayersStats.put(homePlayersList.get(i),
                    new Stats("home", homePlayersList.get(i).getName(), 0, 0, 0, 0));
        }
        // set up guest players stats
        for (int i = 0; i < guestPlayersList.size(); i++) {
            guestPlayersStats.put(guestPlayersList.get(i),
                    new Stats("guest", guestPlayersList.get(i).getName(), 0, 0, 0, 0));
        }
    }
}