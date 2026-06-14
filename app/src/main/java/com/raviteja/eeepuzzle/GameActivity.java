package com.raviteja.eeepuzzle;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Main game screen — renders the puzzle board and handles user interaction.
 */
public class GameActivity extends AppCompatActivity {

    private int gridSize;
    private PuzzleBoard puzzleBoard;
    private Button[][] tileBtns;
    private GridLayout gridLayout;
    private TextView tvMoves, tvTimer;

    // Timer
    private int seconds = 0;
    private final Handler timerHandler = new Handler();
    private final Runnable timerTick = new Runnable() {
        @Override public void run() {
            seconds++;
            tvTimer.setText(String.format("⏱ %02d:%02d", seconds / 60, seconds % 60));
            timerHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridSize   = getIntent().getIntExtra("GRID_SIZE", 4);
        tvMoves    = findViewById(R.id.tvMoves);
        tvTimer    = findViewById(R.id.tvTimer);
        gridLayout = findViewById(R.id.gridLayout);

        TextView tvTitle = findViewById(R.id.tvGridTitle);
        tvTitle.setText(gridSize + "×" + gridSize + " — " +
            (gridSize == 3 ? "Easy" : gridSize == 4 ? "Medium" : "Hard"));

        findViewById(R.id.btnBack)   .setOnClickListener(v -> finish());
        findViewById(R.id.btnShuffle).setOnClickListener(v -> reshuffleBoard());

        startNewGame();
    }

    private void startNewGame() {
        stopTimer();
        seconds = 0;
        tvMoves.setText("Moves: 0");
        tvTimer.setText("⏱ 00:00");

        puzzleBoard = new PuzzleBoard(gridSize);
        buildGrid();
        updateBoard();
        startTimer();
    }

    private void reshuffleBoard() {
        stopTimer();
        seconds = 0;
        tvMoves.setText("Moves: 0");
        tvTimer.setText("⏱ 00:00");
        puzzleBoard.shuffle();
        updateBoard();
        startTimer();
    }

    /** Programmatically create grid buttons */
    private void buildGrid() {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(gridSize);
        gridLayout.setRowCount(gridSize);
        tileBtns = new Button[gridSize][gridSize];

        int screenW  = getResources().getDisplayMetrics().widthPixels;
        int padding  = dpToPx(16);
        int margin   = dpToPx(3);
        int tileSize = (screenW - padding * 2 - margin * 2 * gridSize) / gridSize;

        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                final int row = r, col = c;
                Button btn = new Button(this);
                btn.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                btn.setGravity(Gravity.CENTER);
                btn.setAllCaps(false);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(
                    GridLayout.spec(r), GridLayout.spec(c));
                lp.width  = tileSize;
                lp.height = tileSize;
                lp.setMargins(margin, margin, margin, margin);
                btn.setLayoutParams(lp);

                btn.setOnClickListener(v -> onTileClick(row, col));

                tileBtns[r][c] = btn;
                gridLayout.addView(btn);
            }
        }
    }

    private void onTileClick(int row, int col) {
        if (puzzleBoard.moveTile(row, col)) {
            updateBoard();
            tvMoves.setText("Moves: " + puzzleBoard.getMoves());
            if (puzzleBoard.isSolved()) showWinDialog();
        }
    }

    private void updateBoard() {
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                int val    = puzzleBoard.getTile(r, c);
                Button btn = tileBtns[r][c];

                if (val == 0) {
                    // Empty tile — invisible
                    btn.setText("");
                    btn.setBackgroundColor(Color.parseColor("#0A0E1A"));
                    btn.setEnabled(false);
                    btn.setVisibility(View.INVISIBLE);
                } else {
                    String sym  = PuzzleBoard.getSymbol(val);
                    String name = PuzzleBoard.getName(val);
                    int    tsSym  = gridSize <= 3 ? 18 : gridSize == 4 ? 14 : 12;
                    int    tsName = gridSize <= 3 ? 10 : 8;

                    btn.setText(sym + "
" + name);
                    btn.setTextSize(tsSym);
                    btn.setTextColor(Color.parseColor("#00FF88"));
                    btn.setBackgroundResource(R.drawable.tile_normal);
                    btn.setEnabled(true);
                    btn.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void showWinDialog() {
        stopTimer();
        ScoreManager sm   = new ScoreManager(this);
        boolean newBest   = sm.checkAndSave(gridSize, puzzleBoard.getMoves(), seconds);
        String timeStr    = String.format("%02d:%02d", seconds / 60, seconds % 60);

        new AlertDialog.Builder(this)
            .setTitle("⚡ Circuit Solved!")
            .setMessage(
                "🎉 Excellent work!

"
                + "⚙️  Grid: " + gridSize + "×" + gridSize + "
"
                + "🔢  Moves: " + puzzleBoard.getMoves() + "
"
                + "⏱  Time: " + timeStr + "

"
                + (newBest ? "🌟 NEW BEST SCORE! 🌟" : "Best: " + sm.getBestDisplay(gridSize))
            )
            .setPositiveButton("Play Again", (d, w) -> startNewGame())
            .setNegativeButton("Main Menu",  (d, w) -> finish())
            .setCancelable(false)
            .show();
    }

    private void startTimer() { timerHandler.postDelayed(timerTick, 1000); }
    private void stopTimer()  { timerHandler.removeCallbacks(timerTick); }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    @Override protected void onDestroy() { super.onDestroy(); stopTimer(); }
    @Override protected void onPause()   { super.onPause();   stopTimer(); }
    @Override protected void onResume()  { super.onResume();  if (puzzleBoard != null && !puzzleBoard.isSolved()) startTimer(); }
}