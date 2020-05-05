package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //0=yellow,red=1
    private int activePlayer=0;
    private boolean isGameActive=true;
    private int[] gameStates={2,2,2,2,2,2,2,2,2};
    private int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public void dropIn(View view){
        ImageView counter=(ImageView) view;
        //System.out.println(counter.getTag().toString());
        int tappedCounter=Integer.parseInt(counter.getTag().toString());
        if(gameStates[tappedCounter]==2 && isGameActive) {
            System.out.println("tttttttt");
            gameStates[tappedCounter]=activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(400);

            for(int[] winningPosition:winningPositions){
                if(gameStates[winningPosition[0]]==gameStates[winningPosition[1]] &&
                        gameStates[winningPosition[1]]==gameStates[winningPosition[2]] &&
                        gameStates[winningPosition[0]]!=2){
                    //System.out.println(gameStates[winningPosition[0]]);
                    isGameActive=false;
                    String winner;
                    if(gameStates[winningPosition[0]]==0){
                        winner="Yellow";
                    }else{
                        winner="Red";
                    }
                    TextView winningMessage=findViewById(R.id.winnerMessage);
                    winningMessage.setText(winner+" has won!");
                    LinearLayout layout=findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }else{
                    boolean isDraw=true;
                    for(int counterState:gameStates){
                        if(counterState==2){
                            isDraw=false;
                            break;
                        }
                    }
                    if(isDraw){
                        TextView winningMessage=findViewById(R.id.winnerMessage);
                        winningMessage.setText("Draw!");
                        LinearLayout layout=findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiii");
        isGameActive=true;
        LinearLayout layout=findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        activePlayer=0;
        for(int i=0;i<gameStates.length;i++){
            gameStates[i]=2;
        }
        androidx.gridlayout.widget.GridLayout gridLayout=findViewById(R.id.gridLayout);
        for(int i=0;i<gridLayout.getChildCount();i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
