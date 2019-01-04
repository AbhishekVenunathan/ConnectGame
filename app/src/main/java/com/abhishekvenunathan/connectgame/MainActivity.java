package com.abhishekvenunathan.connectgame;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //0=yellow;1=red;2=blank

    int[] gameState={2,2,2,2,2,2,2,2,2};
    int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,6,8},{0,4,8},{2,4,6}};
    int universalCount=0;


    int activePlayer=0;
    boolean gameActive=true;
    boolean draw=true;

    public void dropIn(View view) {

        MediaPlayer coin = MediaPlayer.create(this,R.raw.jump);
        MediaPlayer win = MediaPlayer.create(this,R.raw.tada);

        ImageView counter = (ImageView) view;
        int taggedCounter =Integer.parseInt(counter.getTag().toString());

        if (gameState[taggedCounter]==2 && gameActive) {

            gameState[taggedCounter] = activePlayer;


            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
                universalCount++;

            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
                universalCount++;
            }
            coin.start();
            counter.animate().translationYBy(1500).rotation(360).setDuration(400);

            for (int winningPosition[] : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2
                        && gameState[winningPosition[1]] != 2
                        && gameState[winningPosition[2]] != 2){

                    gameActive=false;
                    String winner;

                    if (activePlayer == 1) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }
                    TextView winnerText = (TextView)findViewById(R.id.winnerView);
                    Button tryAgain = (Button)findViewById(R.id.tryAgain);

                    win.start();
                    winnerText.setText(winner+" has won!");
                    winnerText.setVisibility(View.VISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                    draw=false;

                }
            }
            if (universalCount==9 && draw)
            {
                TextView winnerText = (TextView)findViewById(R.id.winnerView);
                Button tryAgain = (Button)findViewById(R.id.tryAgain);

                winnerText.setText("Draw!");
                winnerText.setVisibility(View.VISIBLE);
                tryAgain.setVisibility(View.VISIBLE);
            }
        }

    }

    public void tryAgain(View view) {

        TextView winnerText = (TextView)findViewById(R.id.winnerView);
        Button tryAgain = (Button)findViewById(R.id.tryAgain);
        winnerText.setVisibility(View.INVISIBLE);
        tryAgain.setVisibility(View.INVISIBLE);
        GridLayout gridLayout=findViewById(R.id.gridLayout);

        universalCount=0;
        activePlayer=0;
        gameActive=true;
        draw=true;

       for (int i=0;i<gridLayout.getChildCount();i++)
       {

          ImageView child=(ImageView)gridLayout.getChildAt(i);
          child.setImageDrawable(null);
       }

        for(int i=0;i<gameState.length;i++)
        {
           gameState[i]=2;
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
