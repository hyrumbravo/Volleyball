package com.example.volleyball;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.example.volleyball.Database.DatabaseHelper;
import com.example.volleyball.Utilities.Utility;
import com.example.volleyball.adapters.PlayerListAdapter;
import com.example.volleyball.adapters.PlayersTableAdapter;
import com.example.volleyball.databinding.ActivityMainBinding;
import com.example.volleyball.models.Player;
import com.example.volleyball.models.Settings;
import com.example.volleyball.models.Stats;
import com.example.volleyball.selectListeners.PlayerListSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PlayerListSelectListener {

    public static ActivityMainBinding binding;
    List<Player> homePlayersList, guestPlayersList;
    PlayersTableAdapter homeTableAdapter, guestTableAdapter;
    PlayerListAdapter actualHomePlayersAdapter, actualGuestPlayersAdapter;
    ArrayList<AppCompatButton> statsButtons;
    HashMap<Player, Stats> homePlayersStats, guestPlayersStats;
    String selectedStat, commaSeparatedHomeSetScores, commaSeparatedGuestSetScores;
    Player selectedPlayer;
    boolean homePlayer;
    int homeCurrentScore, guestCurrentScore, homeSetScore, guestSetScore, setNumber;
    DatabaseHelper dbHelper;
    Settings settings;

    int homeRemainingTimeouts, guestRemainingTimeouts;
    CountDownTimer countDownTimer;
    boolean isTimeoutOngoing = false;

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
        // restrict activity's landscape view
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        commaSeparatedGuestSetScores = "";
        commaSeparatedHomeSetScores = "";
        setNumber = 0;
            // database
        dbHelper = new DatabaseHelper(this);

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
        binding.addPlayersOverlay.setOnClickListener(view -> {});
        // savePlayers button onclick
        binding.saveButton.setOnClickListener(view -> savePlayers());
        // statsButtons onclicks
        binding.statsMinusButton.setOnClickListener(view -> deductStatToPlayer(selectedPlayer, selectedStat));
        binding.statsAddButton.setOnClickListener(view -> addStatToPlayer(selectedPlayer, selectedStat));
        // settingsSaveButton onclick
        binding.settingsSaveButton.setOnClickListener(view -> saveSettings());
        // settings overlay onclick
        binding.settingsOverlay.setOnClickListener(view -> {});
        // home score add and minus buttons onclicks
        binding.team1AddButton.setOnClickListener(view -> addScoreToHome());
        binding.team1MinusButton.setOnClickListener(view -> deductScoreFromHome());
        // guest score add and minus buttons onclicks
        binding.team2AddButton.setOnClickListener(view -> addScoreToGuest());
        binding.team2MinusButton.setOnClickListener(view -> deductScoreFromGuest());
        // timeouts onlicks
        binding.teamTimeoutIcon1.setOnClickListener(view -> timeoutOnclick(binding.teamTimeout1, settings.getTimeoutDuration(), true));
        binding.teamTimeoutIcon2.setOnClickListener(view -> timeoutOnclick(binding.teamTimeout2, settings.getTimeoutDuration(), false));
        // match history onclick
        binding.matchHistory.setOnClickListener(view -> Utility.navigateToActivity(this, new Intent(this, MatchHistoryPage.class)));
        // endgame button onclick
        binding.endGameButton.setOnClickListener(view -> endGameButtonOnClick());

        // player list 1
//        homePlayersList.add(new Player("1", "Delos Santos")); // add sample player
//        homePlayersList.add(new Player("2", "Parale")); // add sample playe
//        homePlayersList.add(new Player("3", "Carrido")); // add sample player
//        homePlayersList.add(new Player("5", "Delos Santos")); // add sample player
//        homePlayersList.add(new Player("6", "Parale")); // add sample playe
//        homePlayersList.add(new Player("7", "Carrido")); // add sample player

        binding.teamListRecyclerview1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview1.setAdapter(actualHomePlayersAdapter);

        // player list 2
//        guestPlayersList.add(new Player("1", "Dela Cruz")); // add sample player
//        guestPlayersList.add(new Player("2", "Paynado")); // add sample player
//        guestPlayersList.add(new Player("3", "Calbario")); // add sample player
//        guestPlayersList.add(new Player("4", "Dela Cruz")); // add sample player
//        guestPlayersList.add(new Player("5", "Paynado")); // add sample player
//        guestPlayersList.add(new Player("6", "Calbario")); // add sample player

        binding.teamListRecyclerview2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview2.setAdapter(actualGuestPlayersAdapter);

        // players table1
        binding.teamPlayersRecyclerView1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamPlayersRecyclerView1.setAdapter(homeTableAdapter);

        // players table2
        binding.teamPlayersRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamPlayersRecyclerView2.setAdapter(guestTableAdapter);

    }

    private void endGameButtonOnClick() {
        binding.endGameOverlay.setVisibility(View.GONE);
        binding.addPlayersOverlay.setVisibility(View.VISIBLE);
        binding.settingsOverlay.setVisibility(View.VISIBLE);
    }

    private void timeoutOnclick(TextView teamTimeout, int timeoutDuration, boolean homeTeam) {
        startCountdown(teamTimeout, timeoutDuration, this, homeTeam);
    }
    private void saveSettings() {
        String setsToWin = binding.setsTowin.getText().toString();
        String pointsPerSet = binding.pointsPerSet.getText().toString();
        String pointsForFinalSet = binding.pointsForFinalSet.getText().toString();
        String timeOuts = binding.timeOuts.getText().toString();
        String timeoutDuration = binding.timeoutDuration.getText().toString();

        // check if all setting is field
        if (!Utility.allEditTextAreFilled(new ArrayList<>(Arrays.asList(binding.setsTowin, binding.pointsPerSet,
                binding.pointsForFinalSet, binding.timeOuts, binding.timeoutDuration)))) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // check if all settings are > 0
        if (!Utility.allStringsAreGreaterThanZero(new ArrayList<>(Arrays.asList(setsToWin, pointsPerSet,
                pointsForFinalSet, timeOuts, timeoutDuration)))) {
            Toast.makeText(this, "Please enter a value greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }
        // set Settings
        int finalSet = (Integer.parseInt(setsToWin) - 1) *2;
        settings = new Settings(Integer.parseInt(setsToWin), Integer.parseInt(pointsPerSet), Integer.parseInt(pointsForFinalSet),
                Integer.parseInt(timeOuts), Integer.parseInt(timeoutDuration), finalSet);
        // set actual players list items
        actualGuestPlayersAdapter.updatePlayerList(guestPlayersList);
        actualHomePlayersAdapter.updatePlayerList(homePlayersList);
        // set remaining timeouts
        homeRemainingTimeouts = settings.getTimeouts();
        guestRemainingTimeouts = settings.getTimeouts();
        // set stats
        setUpStats();
        // remove overlay
        binding.settingsOverlay.setVisibility(View.GONE);
    }
    private void deductScoreFromGuest() {
        // check if current score is 0
        if (guestCurrentScore == 0) {
            return;
        }
        guestCurrentScore--;
        setGuestScore();
    }
    private void deductScoreFromHome() {
        // check if current score is 0
        if (homeCurrentScore == 0) {
            return;
        }
        homeCurrentScore--;
        setHomeScore();
    }
    private void addScoreToGuest() {
        guestCurrentScore++;
        setGuestScore();
        // check if score is equal to points per set
        if (guestCurrentScore == settings.getPointsPerSet()) {
            // set guest set score
            guestSetScore++;
            binding.team2SetScore.setText(String.valueOf(guestSetScore));
            resetScore(); // reset score
        }
    }
    private void addScoreToHome() {
        homeCurrentScore++;
        setHomeScore();
        // check if score is equal to points per set
        if (homeCurrentScore == settings.getPointsPerSet()) {
            // set home set score
            homeSetScore++;
            binding.team1SetScore.setText(String.valueOf(homeSetScore));
            resetScore(); // reset score
        }
    }
    private void resetScore() {
        // add comma separated set scores
        commaSeparatedHomeSetScores += String.valueOf(homeCurrentScore) + ",";
        commaSeparatedGuestSetScores += String.valueOf(guestCurrentScore) + ",";
        // reset score
        homeCurrentScore = 0;
        guestCurrentScore = 0;
        setNumber++;
        // set points per set to final set points if set number is equal to final set
        if (setNumber == settings.getFinalSet()) {
            settings.setPointsPerSet(settings.getFinalSetPoints());
        }
        setHomeScore();
        setGuestScore();
        // check if set score is equal to sets to win
        if (homeSetScore == settings.getSetsToWin()) {
            // display endgame overlay
            binding.endgameTitle.setText("Home Wins!");
            binding.endGameOverlay.setVisibility(View.VISIBLE);
            saveGameToDB();
        }
        else if (guestSetScore == settings.getSetsToWin()){
            binding.endgameTitle.setText("Guest Wins!");
            binding.endGameOverlay.setVisibility(View.VISIBLE);
            saveGameToDB();
        }
    }

    private void saveGameToDB() {
        // save game to DB
        long gameId = dbHelper.insertGame(homeSetScore, guestSetScore, Utility.getCurrentTimestamp()
                , commaSeparatedHomeSetScores, commaSeparatedGuestSetScores);
        // show saved data to log
        Log.d("Database", "Home Set Score: " + homeSetScore);
        Log.d("Database", "Guest Set Score: " + guestSetScore);
        Log.d("Database", "Timestamp: " + Utility.getCurrentTimestamp());
        Log.d("Database", "Comma Separated Home Set Scores: " + commaSeparatedHomeSetScores);
        Log.d("Database", "Comma Separated Guest Set Scores: " + commaSeparatedGuestSetScores);
        Log.d("Database", "Game ID: " + gameId);
        // save home stats
        for (Map.Entry<Player, Stats> entry : homePlayersStats.entrySet()) {
            Stats stats = entry.getValue();
            long result = dbHelper.insertStat(gameId, "home", stats.getPlayerName(),
                    stats.getSpike(), stats.getBlock(), stats.getDig(), stats.getAce());
            Log.d("Database", "Result: " + result);
        }
        // save guest stats
        for (Map.Entry<Player, Stats> entry : guestPlayersStats.entrySet()) {
            Stats stats = entry.getValue();
            long result = dbHelper.insertStat(gameId, "guest", stats.getPlayerName(),
                    stats.getSpike(), stats.getBlock(), stats.getDig(), stats.getAce());
            Log.d("Database", "Result: " + result);
        }
        // reset game variables
            // player list
        homePlayersList.clear();
        guestPlayersList.clear();
            // notify adapter
        homeTableAdapter.notifyDataSetChanged();
        guestTableAdapter.notifyDataSetChanged();
            // current scores
        homeCurrentScore = 0;
        guestCurrentScore = 0;
            // set scores
        homeSetScore = 0;
        guestSetScore = 0;
            // set number
        setNumber = 0;
            // comma separated set scores
        commaSeparatedHomeSetScores = "";
        commaSeparatedGuestSetScores = "";
            // stats
        homePlayersStats.clear();
        guestPlayersStats.clear();
            // reset UI elements current data
        binding.team1SetScore.setText("0");
        binding.team2SetScore.setText("0");
        binding.team1SetScore.setText("0");
        binding.team2SetScore.setText("0");
    }

    private void setHomeScore() {
        String homeScore = String.format("%2s", String.valueOf(homeCurrentScore)).replace(' ', '0');
        binding.teamScore1.setText(homeScore);
    }
    private void setGuestScore() {
        String guestScore = String.format("%2s", String.valueOf(guestCurrentScore)).replace(' ', '0');
        binding.teamScore2.setText(guestScore);
    }
    private void addStatToPlayer(Player selectedPlayer, String selectedStat) {
        // check if selections there are selections
        if (selectedPlayer == null || selectedStat.isEmpty()) {
            return;
        }
        // check if home player
        if (homePlayer) {
            Utility.addStat(homePlayersStats, selectedPlayer, selectedStat, this, binding.main, true);
        }
        else {
            Utility.addStat(guestPlayersStats, selectedPlayer, selectedStat, this, binding.main, false);
        }
    }
    private void deductStatToPlayer(Player selectedPlayer, String selectedStat) {
        // check if selections there are selections
        if (selectedPlayer == null || selectedStat.isEmpty()) {
            return;
        }
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
        // chech if both teams have minimum of 6 players
        if (homePlayersList.size() < 6 || guestPlayersList.size() < 6) {
            Toast.makeText(this, "Please add at least 6 players to each team", Toast.LENGTH_SHORT).show();
            return;
        }
        // remove overlay
        binding.addPlayersOverlay.setVisibility(View.GONE);
    }
    void statsButtonOnClick(AppCompatButton statsButton) {
        Utility.setStatsBackground(statsButtons, statsButton); // set stats button accordingly
        selectedStat = Utility.toTitleCase(statsButton.getText().toString()); // get stats button text
    }
    void setUpStats() {
        // set up home players stats
        for (int i = 0; i < homePlayersList.size(); i++) {
            homePlayersStats.put(homePlayersList.get(i),
                    new Stats("home", homePlayersList.get(i).getJerseyNumber() + " " +
                            homePlayersList.get(i).getName(), 0, 0, 0, 0));
        }
        // set up guest players stats
        for (int i = 0; i < guestPlayersList.size(); i++) {
            guestPlayersStats.put(guestPlayersList.get(i),
                    new Stats("guest", guestPlayersList.get(i).getJerseyNumber() + " " +
                            guestPlayersList.get(i).getName(), 0, 0, 0, 0));
        }
    }
    void startCountdown(TextView textView, int seconds, Context context, boolean homeTeam) {
        if (!isTimeoutOngoing && (homeTeam && homeRemainingTimeouts > 0 || !homeTeam && guestRemainingTimeouts > 0)) {
            isTimeoutOngoing = true;
            // check if home team and deduct 1 to team's remaining timeouts
            if (homeTeam) {
                homeRemainingTimeouts--;
                binding.homeRemainingTimeout.setText(String.valueOf(homeRemainingTimeouts));
            }
            else {
                guestRemainingTimeouts--;
                binding.guestRemainingTimeout.setText(String.valueOf(guestRemainingTimeouts));
            }
            countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000);
                    textView.setText(String.format("%02d", secondsRemaining));
                }

                @Override
                public void onFinish() {
                    Utility.playSound(context);
                    textView.setText("00"); // Set text to "0" when countdown finishes
                    isTimeoutOngoing = false;
                }
            }.start();
        }
    }
}