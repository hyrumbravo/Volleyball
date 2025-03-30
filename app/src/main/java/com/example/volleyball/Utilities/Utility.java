package com.example.volleyball.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.volleyball.R;
import com.example.volleyball.models.Player;
import com.example.volleyball.models.Stats;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utility {
    public static void setTextColors(ArrayList<TextView> textViews, int color) {
        for (TextView textView: textViews) {
            textView.setTextColor(color);
        }
    }

    public static void enableButtons(ArrayList<AppCompatButton> buttons, Context context) {
        for (AppCompatButton button: buttons) {
            button.setEnabled(true);
        }
    }
    public static void disableButtons(ArrayList<AppCompatButton> buttons, Context context) {
        for (AppCompatButton button: buttons) {
            button.setEnabled(false);
        }
    }

    public static void enableImageButtons(ArrayList<ImageView> buttons, Context context) {
        for (ImageView button: buttons) {
            button.setEnabled(true);
        }
    }

    public static void disableImageButtons(ArrayList<ImageView> buttons, Context context) {
        for (ImageView button: buttons) {
            button.setEnabled(false);
        }
    }

    public static void setStatsBackground(ArrayList<AppCompatButton> buttons, AppCompatButton selectedButton) {
        for(AppCompatButton button: buttons) {
            if(button == selectedButton){
                button.setBackgroundResource(R.drawable.outlined_square_gray);
                continue;
            }
            button.setBackgroundResource(R.drawable.square_gray);
        }
    }

    public static boolean jerseyNumExists(List<Player> playerList, String jerseyNum) {
        for (Player player: playerList) {
            if (player.getJerseyNumber().equals(jerseyNum)) {
                return true;
            }
        }
        return false;
    }

    public static void addStat(HashMap<Player, Stats> playerStatsHashMap, Player player, String stat,
                               Context context, ConstraintLayout rootActivity, boolean homePlayer) {

        Snackbar snackbar = Snackbar.make(rootActivity, "", 500);
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.table_header_gray)); // Change background color
        View snackbarView = snackbar.getView();
        // set text color
            // check if home player
        if (homePlayer) {
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.team1_violet)); // Change text color
        }
        else {
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.darker_yellow)); // Change text color
        }
        // Apply custom font
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
        textView.setTypeface(typeface);

        if (stat.equalsIgnoreCase("spike")) {
            playerStatsHashMap.get(player).addSpike();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getSpike()));
        }
        else if (stat.equalsIgnoreCase("block")) {
            playerStatsHashMap.get(player).addBlock();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getBlock()));
        }
        else if (stat.equalsIgnoreCase("dig")) {
            playerStatsHashMap.get(player).addDig();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getDig()));
        }
        else if (stat.equalsIgnoreCase("ace")) {
            playerStatsHashMap.get(player).addAce();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getAce()));
        }

        snackbar.show();
    }

    public static void deductStat(HashMap<Player, Stats> playerStatsHashMap, Player player, String stat,
                                  Context context, ConstraintLayout rootActivity, boolean homePlayer) {

        Snackbar snackbar = Snackbar.make(rootActivity, "", 500);
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.table_header_gray)); // Change background color
        View snackbarView = snackbar.getView();
        // set text color
            // check if home player
        if (homePlayer) {
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.team1_violet)); // Change text color
        }
        else {
            snackbar.setTextColor(ContextCompat.getColor(context, R.color.darker_yellow)); // Change text color

        }
        // Apply custom font
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.roboto_bold);
        textView.setTypeface(typeface);


        if (stat.equalsIgnoreCase("spike")) {
            playerStatsHashMap.get(player).deductSpike();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getSpike()));
        }
        else if (stat.equalsIgnoreCase("block")) {
            playerStatsHashMap.get(player).deductBlock();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getBlock()));
        }
        else if (stat.equalsIgnoreCase("dig")) {
            playerStatsHashMap.get(player).deductDig();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getDig()));
        }
        else if (stat.equalsIgnoreCase("ace")) {
            playerStatsHashMap.get(player).deductAce();
            snackbar.setText(String.format("%s %ss: %d",
                    playerStatsHashMap.get(player).getPlayerName(), stat, playerStatsHashMap.get(player).getAce()));
        }

        snackbar.show();
    }

    public static String toTitleCase(String input) {
        String[] words = input.toLowerCase().split(" ");
        StringBuilder titleCase = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return titleCase.toString().trim();
    }
}
