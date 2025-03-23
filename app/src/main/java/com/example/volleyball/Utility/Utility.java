package com.example.volleyball.Utility;

import android.widget.TextView;

import java.util.ArrayList;

public class Utility {
    public static void setTextColors(ArrayList<TextView> textViews, int color) {
        for (TextView textView: textViews) {
            textView.setTextColor(color);
        }
    }
}
