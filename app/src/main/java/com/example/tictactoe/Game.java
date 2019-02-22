package com.example.tictactoe;

import android.service.quicksettings.Tile;
import android.util.Log;

import java.io.Serializable;

public class Game implements Serializable
{

    final private int BOARD_SIZE = 3;
    private TileState[][] board;

    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn

    private int movesPlayed;
    private Boolean gameOver;

    public Boolean getGameOver(){
        return gameOver;
    }


    /*public void setGameOver(Boolean bool)
    {
        gameOver = bool;
    }*/

    public Game()
    {
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        playerOneTurn = true;
        gameOver = false;
    }

    public TileState choose(int row, int column) {
        TileState position = board[row][column];
        if (board[row][column] == TileState.BLANK)
        {
            if (playerOneTurn)
            {
                board[row][column] = TileState.CROSS;

                playerOneTurn = false;
                return TileState.CROSS;
            }
            else
            {
                board[row][column] = TileState.CIRCLE;
                playerOneTurn = true;
                return TileState.CIRCLE;
            }


            // also in both cases, make sure to return TileState.CROSS or TileState.CIRCLE to allow the UI to update
        }
        else
        {
            board[row][column] = TileState.INVALID;
            return TileState.INVALID;
        }
    }
    // decides whether or not a player has won or the game has been drawn.
    public GameState won()
    {
        gameOver = false;
        // check horizontal winner set
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            if (board[i][0] == TileState.CROSS && board[i][1] == TileState.CROSS && board[i][2] == TileState.CROSS)
            {
                gameOver = true;
                return GameState.PLAYER_ONE;
            }
            else if (board[i][0] == TileState.CIRCLE && board[i][1] == TileState.CIRCLE && board[i][2] == TileState.CIRCLE)
            {
                gameOver = true;
                return GameState.PLAYER_TWO;
            }
        }
        // check vertical winner set
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == TileState.CROSS && board[1][i] == TileState.CROSS && board[2][i] == TileState.CROSS)
            {
                gameOver = true;
                return GameState.PLAYER_ONE;
            }
            else if (board[0][i] == TileState.CIRCLE && board[1][i] == TileState.CIRCLE && board[2][i] == TileState.CIRCLE)
            {
                gameOver = true;
                return GameState.PLAYER_TWO;
            }
        }
        // check diagonals
        if ((board[0][0] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][2] == TileState.CROSS)
                || (board[0][2] == TileState.CROSS && board[1][1] == TileState.CROSS && board[2][0] == TileState.CROSS))
        {
            gameOver = true;
            return GameState.PLAYER_ONE;
        }
        else if ((board[0][0] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][2] == TileState.CIRCLE)
            || (board[0][2] == TileState.CIRCLE && board[1][1] == TileState.CIRCLE && board[2][0] == TileState.CIRCLE))
        {
            gameOver = true;
            return GameState.PLAYER_TWO;
        }
        // check for draw
        else
        {
            // search for any blank states
            for (int i = 0; i < BOARD_SIZE; i++)
            {
                for (int j = 0; j < BOARD_SIZE; j++)
                {
                    if (board[j][i] == TileState.BLANK)
                    {
                        // game isn't over yet
                        return GameState.IN_PROGRESS;
                    }
                }
            }
        }
        gameOver = true;
        return GameState.DRAW;
    }
    public TileState[][] getBoard(){
        return board;
    }

}

