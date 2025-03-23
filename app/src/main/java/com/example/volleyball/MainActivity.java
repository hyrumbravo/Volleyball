package com.example.volleyball;

import android.os.Bundle;
import android.view.View;
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
import com.example.volleyball.databinding.ActivityMainBinding;
import com.example.volleyball.models.Player;
import com.example.volleyball.selectListeners.PlayerListSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlayerListSelectListener {

    public static ActivityMainBinding binding;
    List<Player> playerListItems1, playerListItems2;
    ArrayList<AppCompatButton> statsButtons;
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
        playerListItems1 = new ArrayList<>();
        playerListItems2 = new ArrayList<>();
        statsButtons = new ArrayList<>(Arrays.asList(binding.statButton1, binding.statButton2,
                binding.statButton3, binding.statButton4));
            // stats buttons onclicks
        binding.statButton1.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton1));
        binding.statButton2.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton2));
        binding.statButton3.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton3));
        binding.statButton4.setOnClickListener(v -> Utility.setStatsBackground(statsButtons, binding.statButton4));

        // player list 1
        playerListItems1.add(new Player("1", "Pile1"));
        playerListItems1.add(new Player("1", "Del Rosario1"));
        playerListItems1.add(new Player("1", "Pile1"));
        playerListItems1.add(new Player("1", "Del Rosario"));
        playerListItems1.add(new Player("1", "Pile"));
        playerListItems1.add(new Player("1", "Del Rosario"));

        binding.teamListRecyclerview1.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview1.setAdapter(new PlayerListAdapter(this, playerListItems1, this));

        // player list 2
        playerListItems2.add(new Player("1", "Pile"));
        playerListItems2.add(new Player("1", "Del Rosario2"));
        playerListItems2.add(new Player("1", "Pil2"));
        playerListItems2.add(new Player("1", "Del Rosariooo"));
        playerListItems2.add(new Player("1", "Pile"));
        playerListItems2.add(new Player("1", "Del Rosario"));

        binding.teamListRecyclerview2.setLayoutManager(new LinearLayoutManager(this));
        binding.teamListRecyclerview2.setAdapter(new PlayerListAdapter(this, playerListItems2, this));
    }

    @Override
    public void onItemClicked(Player player) {
        Toast.makeText(this, player.getName(), Toast.LENGTH_SHORT).show();
    }

}