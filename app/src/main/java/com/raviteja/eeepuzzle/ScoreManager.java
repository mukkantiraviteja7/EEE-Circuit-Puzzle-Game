package com.raviteja.eeepuzzle;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Stores and retrieves best scores using SharedPreferences.
 * Tracks best moves and best time per grid size.
 */
public class ScoreManager {

    private static final String PREFS = "EEEPuzzleScores";
    private final SharedPreferences prefs;

    public ScoreManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public int getBestMoves(int gridSize) {
        return prefs.getInt("best_moves_" + gridSize, Integer.MAX_VALUE);
    }

    public int getBestTime(int gridSize) {
        return prefs.getInt("best_time_" + gridSize, Integer.MAX_VALUE);
    }

    /** Returns true if this is a new best score */
    public boolean checkAndSave(int gridSize, int moves, int seconds) {
        int prevBest = getBestMoves(gridSize);
        if (moves < prevBest) {
            prefs.edit()
                .putInt("best_moves_" + gridSize, moves)
                .putInt("best_time_"  + gridSize, seconds)
                .apply();
            return true;
        }
        return false;
    }

    public String getBestDisplay(int gridSize) {
        int moves = getBestMoves(gridSize);
        if (moves == Integer.MAX_VALUE) return "No record yet";
        int secs  = getBestTime(gridSize);
        return moves + " moves | " + String.format("%02d:%02d", secs/60, secs%60);
    }

    public void resetAll() {
        prefs.edit().clear().apply();
    }
}