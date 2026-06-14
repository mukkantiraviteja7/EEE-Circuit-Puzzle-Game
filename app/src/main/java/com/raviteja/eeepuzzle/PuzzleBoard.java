package com.raviteja.eeepuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * EEE Circuit Puzzle — Core Game Logic
 * Implements a sliding tile puzzle (N×N grid)
 * Author: Mukkanti Ravi Teja
 */
public class PuzzleBoard {

    // EEE symbols displayed on tiles
    public static final String[] EEE_SYMBOLS = {
        "R",   "C",   "L",   "V",   "I",   "Ω",
        "W",   "Hz",  "DC",  "AC",  "GND", "Vcc",
        "EMF", "μF",  "kΩ",  "mH",  "kV",  "P",
        "Q",   "f",   "λ",   "η",   "φ",   "τ"
    };

    // Full names for EEE symbols
    public static final String[] EEE_NAMES = {
        "Resistor",   "Capacitor",  "Inductor",  "Voltage",
        "Current",    "Ohm",        "Watt",      "Hertz",
        "Direct C.",  "Alt. C.",    "Ground",    "Supply",
        "EMF",        "Microfarad", "Kilo-Ohm",  "Millihenry",
        "Kilovolt",   "Power",      "Charge",    "Frequency",
        "Wavelength", "Efficiency", "Phase",     "Time Const."
    };

    private final int size;
    private final int[][] board;
    private int emptyRow, emptyCol;
    private int moves;

    public PuzzleBoard(int size) {
        this.size  = size;
        this.board = new int[size][size];
        this.moves = 0;
        initBoard();
        shuffle();
    }

    /** Fill board in solved order: 1,2,...,N*N-1, 0(empty) */
    private void initBoard() {
        int num = 1;
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++)
                board[r][c] = (r == size-1 && c == size-1) ? 0 : num++;
        emptyRow = size - 1;
        emptyCol = size - 1;
    }

    /** Shuffle by making N*N*100 random valid moves (guarantees solvability) */
    public void shuffle() {
        int[] dR = {-1, 1, 0, 0};
        int[] dC = {0, 0, -1, 1};
        Random rng = new Random();
        int total = size * size * 100;
        for (int i = 0; i < total; i++) {
            List<int[]> valid = new ArrayList<>();
            for (int d = 0; d < 4; d++) {
                int nr = emptyRow + dR[d], nc = emptyCol + dC[d];
                if (nr >= 0 && nr < size && nc >= 0 && nc < size)
                    valid.add(new int[]{nr, nc});
            }
            int[] mv = valid.get(rng.nextInt(valid.size()));
            swapWithEmpty(mv[0], mv[1]);
        }
        moves = 0;
    }

    /**
     * Try to move tile at (row, col).
     * Returns true if the tile was adjacent to empty space and moved.
     */
    public boolean moveTile(int row, int col) {
        int dr = Math.abs(row - emptyRow);
        int dc = Math.abs(col - emptyCol);
        boolean adjacent = (dr == 1 && dc == 0) || (dr == 0 && dc == 1);
        if (adjacent) {
            swapWithEmpty(row, col);
            moves++;
            return true;
        }
        return false;
    }

    private void swapWithEmpty(int row, int col) {
        board[emptyRow][emptyCol] = board[row][col];
        board[row][col] = 0;
        emptyRow = row;
        emptyCol = col;
    }

    /** Returns true when all tiles are in correct order */
    public boolean isSolved() {
        int expected = 1;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (r == size-1 && c == size-1) {
                    if (board[r][c] != 0) return false;
                } else {
                    if (board[r][c] != expected++) return false;
                }
            }
        }
        return true;
    }

    public int getTile(int row, int col) { return board[row][col]; }
    public int getSize()  { return size; }
    public int getMoves() { return moves; }

    public static String getSymbol(int num) {
        if (num < 1 || num > EEE_SYMBOLS.length) return "";
        return EEE_SYMBOLS[num - 1];
    }

    public static String getName(int num) {
        if (num < 1 || num > EEE_NAMES.length) return "";
        return EEE_NAMES[num - 1];
    }
}