package com.raviteja.eeepuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Home screen — difficulty selection and high scores display.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScores();
    }

    private void refreshScores() {
        ScoreManager sm = new ScoreManager(this);

        TextView tv3 = findViewById(R.id.tvBest3);
        TextView tv4 = findViewById(R.id.tvBest4);
        TextView tv5 = findViewById(R.id.tvBest5);

        tv3.setText("Best: " + sm.getBestDisplay(3));
        tv4.setText("Best: " + sm.getBestDisplay(4));
        tv5.setText("Best: " + sm.getBestDisplay(5));

        // Difficulty buttons
        Button btnEasy   = findViewById(R.id.btnEasy);
        Button btnMedium = findViewById(R.id.btnMedium);
        Button btnHard   = findViewById(R.id.btnHard);

        btnEasy  .setOnClickListener(v -> launchGame(3));
        btnMedium.setOnClickListener(v -> launchGame(4));
        btnHard  .setOnClickListener(v -> launchGame(5));
    }

    private void launchGame(int gridSize) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("GRID_SIZE", gridSize);
        startActivity(intent);
    }
}