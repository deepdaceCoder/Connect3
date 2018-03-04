package com.arghadeep.connect3;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int activePlayer = 0; //0-yellow 1-red
    private int[] gameState = {2,2,2,2,2,2,2,2,2}; //2 - unplayed
    private  boolean gameIsActive = true;
    private int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},
                                        {0,3,6},{1,4,7},{2,5,8},
                                        {0,4,8},{2,4,6}};
    private LinearLayout linearLayout;
    private TextView winMessage;
    private TextView turnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.playAgainLayout);
        winMessage = (TextView)findViewById(R.id.winnerMessage);
        turnView = (TextView)findViewById(R.id.turnText);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.valueOf(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive==true) {
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                gameState[tappedCounter]=activePlayer;
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                gameState[tappedCounter]=activePlayer;
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationY(0f).rotation(360).setDuration(300);
            if(activePlayer==0)
                turnView.setText("Player Yellow's turn");
            else
                turnView.setText("Player Red's turn");
            checker();
        }
    }

    private void checker() {
        for(int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]]!=2) {
                gameIsActive=false;
                if(gameState[winningPosition[0]]==0) {
                    winMessage.setText("Player Yellow has won!");
                    linearLayout.setBackgroundColor(Color.YELLOW);
                }
                else if(gameState[winningPosition[0]]==1) {
                    winMessage.setText("Player Red has won!");
                    linearLayout.setBackgroundColor(Color.RED);
                }
                linearLayout.setVisibility(View.VISIBLE);
            }

            else {
                boolean gameIsOver = true;
                for(int counterState : gameState)
                    if(counterState==2)
                        gameIsOver=false;
                if(gameIsOver) {
                    winMessage.setText("Draw!");
                    linearLayout.setBackgroundColor(Color.GREEN);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        linearLayout.setVisibility(View.INVISIBLE);
        activePlayer=0;
        gameIsActive=true;
        for(int i=0;i<gameState.length;i++)
            gameState[i]=2;

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for (int i=0;i<gridLayout.getChildCount();i++)
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
    }
}
