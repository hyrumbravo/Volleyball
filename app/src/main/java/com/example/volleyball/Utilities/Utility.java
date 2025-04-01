package com.example.volleyball.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
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

    public static CountDownTimer countDownTimer;
    public static boolean isTimeoutOngoing = false;

    public static MediaPlayer mediaPlayer;
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

    public static boolean allEditTextAreFilled(ArrayList<EditText> fields) {
        for (EditText field: fields) {
            if (field.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean allStringsAreGreaterThanZero(ArrayList<String> strings) {
        for (String string: strings) {
            if (Integer.parseInt(string) <= 0) {
                return false;
            }
        }
        return true;
    }

    public static void startCountdown(TextView textView, int seconds, Context context) {
        if (!isTimeoutOngoing) {
            isTimeoutOngoing = true;
            if (countDownTimer != null) {
                countDownTimer.cancel(); // Cancel any existing countdown
            }

            countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000);
                    textView.setText(String.format("%02d", secondsRemaining));
                }

                @Override
                public void onFinish() {
                    playSound(context);
                    textView.setText("00"); // Set text to "0" when countdown finishes
                    isTimeoutOngoing = false;
                }
            }.start();
        }
    }

    public static void playSound(Context context) {
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release any previous MediaPlayer instance
        }

        mediaPlayer = MediaPlayer.create(context, R.raw.buzzer);
        mediaPlayer.start();
    }

    public static void navigateToActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
