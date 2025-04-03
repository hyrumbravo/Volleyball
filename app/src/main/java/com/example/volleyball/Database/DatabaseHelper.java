package com.example.volleyball.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.volleyball.models.Game;
import com.example.volleyball.models.Stats;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "Volleyball.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_GAME = "Game";
    private static final String TABLE_STATS = "Stats";

    // Create Game Table
    private static final String CREATE_TABLE_GAME =
            "CREATE TABLE " + TABLE_GAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "team1Score INTEGER NOT NULL, " +
                    "team2Score INTEGER NOT NULL, " +
                    "timestamp TEXT NOT NULL, " +
                    "commaSeparatedHomeSetScores TEXT NOT NULL, " +
                    "commaSeparatedGuestSetScores TEXT NOT NULL);";

    // Create Stats Table
    private static final String CREATE_TABLE_STATS =
            "CREATE TABLE " + TABLE_STATS + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "gameId INTEGER NOT NULL, " +
                    "teamName TEXT NOT NULL, " +
                    "playerName TEXT NOT NULL, " +
                    "spike INTEGER DEFAULT 0, " +
                    "block INTEGER DEFAULT 0, " +
                    "dig INTEGER DEFAULT 0, " +
                    "ace INTEGER DEFAULT 0, " +
                    "FOREIGN KEY (gameId) REFERENCES " + TABLE_GAME + "(id) ON DELETE CASCADE);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GAME);
        db.execSQL(CREATE_TABLE_STATS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        onCreate(db);
    }


    public long insertGame(int team1Score, int team2Score, String timestamp, String homeSetScores, String guestSetScores) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("team1Score", team1Score);
        values.put("team2Score", team2Score);
        values.put("timestamp", timestamp);
        values.put("commaSeparatedHomeSetScores", homeSetScores);
        values.put("commaSeparatedGuestSetScores", guestSetScores);

        // Insert the row into the table
        long result = db.insert("Game", null, values);
        db.close();

        return result; // Returns the row ID if successful, -1 if failed
    }

    public long insertStat(long gameId, String teamName, String playerName, int spike, int block, int dig, int ace) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("gameId", gameId);
        values.put("teamName", teamName);
        values.put("playerName", playerName);
        values.put("spike", spike);
        values.put("block", block);
        values.put("dig", dig);
        values.put("ace", ace);

        // Insert the row into the Stats table
        long result = db.insert("Stats", null, values);
        db.close();

        return result; // Returns the row ID if successful, -1 if failed
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Fetch games sorted by timestamp in descending order (most recent first)
        Cursor cursor = db.rawQuery("SELECT * FROM Game ORDER BY timestamp DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int team1Score = cursor.getInt(cursor.getColumnIndexOrThrow("team1Score"));
                int team2Score = cursor.getInt(cursor.getColumnIndexOrThrow("team2Score"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
                String homeSetScores = cursor.getString(cursor.getColumnIndexOrThrow("commaSeparatedHomeSetScores"));
                String guestSetScores = cursor.getString(cursor.getColumnIndexOrThrow("commaSeparatedGuestSetScores"));

                // Create a Game object and add it to the list
                games.add(new Game(id, String.valueOf(team1Score), String.valueOf(team2Score), timestamp,
                        homeSetScores, guestSetScores));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return games;
    }

    public List<Stats> getStatsByGameId(int gameId, String teamNameToGet) {
        List<Stats> statsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT teamName, playerName, spike, block, dig, ace FROM Stats WHERE gameId = ? AND teamName = ?",
                new String[]{String.valueOf(gameId), teamNameToGet}
        );

        if (cursor.moveToFirst()) {
            do {
                String teamName = cursor.getString(cursor.getColumnIndexOrThrow("teamName"));
                String playerName = cursor.getString(cursor.getColumnIndexOrThrow("playerName"));
                int spike = cursor.getInt(cursor.getColumnIndexOrThrow("spike"));
                int block = cursor.getInt(cursor.getColumnIndexOrThrow("block"));
                int dig = cursor.getInt(cursor.getColumnIndexOrThrow("dig"));
                int ace = cursor.getInt(cursor.getColumnIndexOrThrow("ace"));

                // Create a Stat object and add it to the list
                statsList.add(new Stats(teamName, playerName, spike, block, dig, ace));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return statsList;
    }
    public boolean deleteGameById(int gameId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_GAME, "id = ?", new String[]{String.valueOf(gameId)});
        db.close();
        return deletedRows > 0; // Returns true if a game was deleted, false otherwise
    }


}
