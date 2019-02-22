package com.example.tictactoe;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.service.quicksettings.Tile;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Space;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game();
    }


    public void tapped(View view)
    {
        // remember tapped button
        int id = view.getId();
        Button currentButton = findViewById(id);

        // get information of certain objects for later use
        TextView infoText = findViewById(R.id.information);
        TextView winText = findViewById(R.id.winText);
        Space spacing = findViewById(R.id.spacing);


        // (source casting gridLayout https://stackoverflow.com/questions/48760862/android-gridlayout-android-support-v7-widget-gridlayout-cannot-be-cast-to-andr)
        android.support.v7.widget.GridLayout grid = findViewById(R.id.gridd);

        Button resetButton = findViewById(R.id.resetButton);

        // check position of button in GridLayout
        int row = 0;
        int column = 0;
        if (currentButton == findViewById(R.id.button01))
        {
            row = 0; column = 1;
        }
        else if (currentButton == findViewById(R.id.button02))
        {
            row = 0; column = 2;
        }
        else if (currentButton == findViewById(R.id.button10))
        {
            row = 1; column = 0;
        }
        else if (currentButton == findViewById(R.id.button11))
        {
            row = 1; column = 1;
        }
        else if (currentButton == findViewById(R.id.button12))
        {
            row = 1; column = 2;
        }
        else if (currentButton == findViewById(R.id.button20))
        {
            row = 2; column = 0;
        }
        else if (currentButton == findViewById(R.id.button21))
        {
            row = 2; column = 1;
        }
        else if (currentButton == findViewById(R.id.button22))
        {
            row = 2; column = 2;
        }
        TileState state = game.choose(row, column);
        switch(state)
        {
            case CROSS:
                // change button to croos
                currentButton.setBackgroundResource(R.drawable.cross);
                // reset color background when valid button has been tapped
                infoText.setText("");
                break;
            case CIRCLE:
                // change button to circle
                currentButton.setBackgroundResource(R.drawable.rondje);
                infoText.setText("");
                break;
            case INVALID:
                // does not change button but indicates that user is at fault with a red colored string
                // (parsing colors source: https://stackoverflow.com/questions/8489990/how-to-set-color-using-integer)
                infoText.setText("Invalid move!");
                infoText.setTextColor(Color.parseColor("#FF0000"));
                break;
        }

        // deciding win condition for end of turn
        GameState endTurn = game.won();

        // different view changes according to what layout is used
        // (source for checking current orientation: https://stackoverflow.com/questions/2795833/check-orientation-on-android-phone)
        int landOrPortrait = getResources().getConfiguration().orientation;
            switch(endTurn)
            {
                case IN_PROGRESS:
                    grid.setVisibility(View.VISIBLE);
                    winText.setVisibility(View.GONE);
                    // game should go on for another turn
                    break;
                case DRAW:
                    grid.setVisibility(View.GONE);
                    winText.setVisibility(View.VISIBLE);
                    winText.setText("DRAW...");
                    // portrait or landscape?
                    if (landOrPortrait == Configuration.ORIENTATION_LANDSCAPE)
                    {
                        // give visual indication to user: in what condition has the game been ended?
                        winText.setTextSize(50);
                        resetButton.setTextSize(50);
                        spacing.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        resetButton.setTextSize(75);
                    }
                    break;
                case PLAYER_ONE:
                    grid.setVisibility(View.GONE);
                    winText.setVisibility(View.VISIBLE);
                    winText.setText("P1 WINS!");
                    if (landOrPortrait == Configuration.ORIENTATION_LANDSCAPE)
                    {
                        winText.setTextSize(50);
                        resetButton.setTextSize(50);
                        spacing.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        resetButton.setTextSize(75);
                    }
                    break;
                case PLAYER_TWO:
                    grid.setVisibility(View.GONE);
                    winText.setVisibility(View.VISIBLE);
                    winText.setText("P2 WINS!");
                    if (landOrPortrait == Configuration.ORIENTATION_LANDSCAPE)
                    {
                        winText.setTextSize(50);
                        resetButton.setTextSize(50);
                        spacing.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        resetButton.setTextSize(75);
                    }
                    break;
            }
        }

    public void resetClicked(View view)
    {
        game = new Game();
        // (source for easy resetting of the whole view: https://stackoverflow.com/questions/34864682/how-can-i-reset-my-android-layout-back-to-default-state)
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        // put current Game object in outstate
        outState.putSerializable("tictactoe", game);

        // put all of the necessary visual settings in outstate
        TextView text = findViewById(R.id.winText);
        outState.putFloat("size", text.getTextSize());
        outState.putString("text", (String) text.getText());
        outState.putInt("visibility", text.getVisibility());
        android.support.v7.widget.GridLayout grid = findViewById(R.id.gridd);
        outState.putInt("visibilityLayout", grid.getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        // recover game
        game = (Game) savedInstanceState.getSerializable("tictactoe");
        TileState[][] board = game.getBoard();

        // recreate visual status of previous game board from Game object
        Button zeroZero = findViewById(R.id.button00);
        if (board[0][0] == TileState.CROSS)
        {
            zeroZero.setBackgroundResource(R.drawable.cross);
        }
        else if (board[0][0] == TileState.CIRCLE)
        {
            zeroZero.setBackgroundResource(R.drawable.rondje);
        }
        Button zeroOne = findViewById(R.id.button01);
        if (board[0][1] == TileState.CROSS)
        {
            zeroOne.setBackgroundResource(R.drawable.cross);
        }
        else if (board[0][1] == TileState.CIRCLE)
        {
            zeroOne.setBackgroundResource(R.drawable.rondje);
        }
        Button zeroTwo = findViewById(R.id.button02);
        if (board[0][2] == TileState.CROSS)
        {
            zeroTwo.setBackgroundResource(R.drawable.cross);
        }
        else if (board[0][2] == TileState.CIRCLE)
        {
            zeroTwo.setBackgroundResource(R.drawable.rondje);
        }
        Button oneZero = findViewById(R.id.button10);
        if (board[1][0] == TileState.CROSS)
        {
            oneZero.setBackgroundResource(R.drawable.cross);
        }
        else if (board[1][0] == TileState.CIRCLE)
        {
            oneZero.setBackgroundResource(R.drawable.rondje);
        }
        Button oneOne = findViewById(R.id.button11);
        if (board[1][1] == TileState.CROSS)
        {
            oneOne.setBackgroundResource(R.drawable.cross);
        }
        else if (board[1][1] == TileState.CIRCLE)
        {
            oneOne.setBackgroundResource(R.drawable.rondje);
        }
        Button oneTwo = findViewById(R.id.button12);
        if (board[1][2] == TileState.CROSS)
        {
            oneTwo.setBackgroundResource(R.drawable.cross);
        }
        else if (board[1][2] == TileState.CIRCLE)
        {
            oneTwo.setBackgroundResource(R.drawable.rondje);
        }
        Button twoZero = findViewById(R.id.button20);
        if (board[2][0] == TileState.CROSS)
        {
            twoZero.setBackgroundResource(R.drawable.cross);
        }
        else if ((board[2][0] == TileState.CIRCLE))
        {
            twoZero.setBackgroundResource(R.drawable.rondje);
        }
        Button twoOne = findViewById(R.id.button21);
        if (board[2][1] == TileState.CROSS)
        {
            twoOne.setBackgroundResource(R.drawable.cross);
        }
        else if ((board[2][1] == TileState.CIRCLE))
        {
            twoOne.setBackgroundResource(R.drawable.rondje);
        }
        Button twoTwo = findViewById(R.id.button22);
        if (board[2][2] == TileState.CROSS)
        {
            twoTwo.setBackgroundResource(R.drawable.cross);
        }
        else if (board[2][2] == TileState.CIRCLE)
        {
            twoTwo.setBackgroundResource(R.drawable.rondje);
        }

        // set all of the other necessary visual information
        TextView winText = findViewById(R.id.winText);
        int visibility = savedInstanceState.getInt("visibility");
        winText.setVisibility(visibility);
        String text = savedInstanceState.getString("text");
        winText.setText(text);

        // determine whether or not grid should be visible based on game over or not
        android.support.v7.widget.GridLayout grid = findViewById(R.id.gridd);
        if (game.getGameOver())
        {
            grid.setVisibility(View.GONE);
            // switches back to new game (auto reset) if layout is changed
            setContentView(R.layout.activity_main);
            game = new Game();
        }
        else
        {
            grid.setVisibility(View.VISIBLE);
        }
        int visibilityLayout = savedInstanceState.getInt("visibilityLayout");
        grid.setVisibility(visibilityLayout);
    }
}
