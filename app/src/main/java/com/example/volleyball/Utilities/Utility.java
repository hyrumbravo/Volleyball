package com.example.volleyball.Utilities;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.volleyball.R;

import java.util.ArrayList;

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
}
