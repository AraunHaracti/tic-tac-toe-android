package com.example.tic_tac_toe;

public class GameInfo {
    public static boolean isTurn = true;
    public static boolean isWin = false;
    public static String firstSymbol = "X";
    public static String secondSymbol = "O";
    public static int[][] winCombination =
            {
                    {0,1,2}, {3,4,5}, {6,7,8},
                    {0,3,6}, {1,4,7}, {2,5,8},
                    {0,4,8},{2,4,6}
            };
}
