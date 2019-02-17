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
        android.support.v7.widget.GridLayout grid = (android.support.v7.widget.GridLayout)findViewById(R.id.gridd);

        Button resetButton = findViewById(R.id.resetButton);


        // check where in the GridLayout the buttons position is
        int row = 0;
        int column = 0;

        if (currentButton == findViewById(R.id.button2)) {
            row = 0; column = 1;
        }
        else if (currentButton == findViewById(R.id.button3)) {
            row = 0; column = 2;
        }
        else if (currentButton == findViewById(R.id.button4)) {
            row = 1; column = 0;
        }
        else if (currentButton == findViewById(R.id.button5)) {
            row = 1; column = 1;
        }
        else if (currentButton == findViewById(R.id.button8)) {
            row = 1; column = 2;
        }
        else if (currentButton == findViewById(R.id.button9)) {
            row = 2; column = 0;
        }
        else if (currentButton == findViewById(R.id.button10)) {
            row = 2; column = 1;
        }
        else if (currentButton == findViewById(R.id.button11)) {
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
                // does not change button but indicates that user is at fault with red coloring
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

    public void resetClicked(View view) {
        game = new Game();
        // (source for easy resetting of the whole view: https://stackoverflow.com/questions/34864682/how-can-i-reset-my-android-layout-back-to-default-state)
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tictactoe", game);
        // TO DO
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("tictactoe");
        // TO DO
    }
}
