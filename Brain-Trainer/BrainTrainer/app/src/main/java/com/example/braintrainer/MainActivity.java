package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private Button button0,button1,button2,button3;
    private Button playAgainButton;
    private TextView sumTextView;
    private TextView resultTextView;
    private TextView timeTextView;
    private TextView pointsTextView;
    private int locationOfCorrectAnswer;
    private int score=0;
    private int numberOfQuestions=0;
    private ArrayList<Integer> answers=new ArrayList<>();
    public void playAgain(View view){
        score=0;
        numberOfQuestions=0;
        timeTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestion();
        new CountDownTimer(15100,1000){

            @Override
            public void onTick(long l) {
                timeTextView.setText(String.valueOf(l/1000)+"s");
            }

            @Override
            public void onFinish() {
                timeTextView.setText("0s");
                resultTextView.setText("You answered "+score+" correct from "+numberOfQuestions+" questions!");
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();

    }
    public void generateQuestion(){
        Random rand=new Random();
        int a=rand.nextInt(21);
        int b=rand.nextInt(21);
        sumTextView.setText(String.valueOf(a)+" + "+String.valueOf(b));
        locationOfCorrectAnswer=rand.nextInt(4);
        answers.clear();
        for(int i=0;i<4;i++){
            if(i==locationOfCorrectAnswer){
                answers.add(a+b);
            }else{
                int incorrectAnswer=rand.nextInt(41);
                while(incorrectAnswer==a+b){
                    incorrectAnswer=rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(String.valueOf(answers.get(0)));
        button1.setText(String.valueOf(answers.get(1)));
        button2.setText(String.valueOf(answers.get(2)));
        button3.setText(String.valueOf(answers.get(3)));
    }
    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(String.valueOf(locationOfCorrectAnswer))){
            score++;
            resultTextView.setText("Correct!");
        }else{
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        pointsTextView.setText(String.valueOf(score)+"/"+String.valueOf(numberOfQuestions));
        generateQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sumTextView=findViewById(R.id.sumTextView);
        resultTextView=findViewById(R.id.resultTextView);
        timeTextView=findViewById(R.id.timerTextView);
        pointsTextView=findViewById(R.id.pointsTextView);
        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        playAgainButton=findViewById(R.id.playAgainButton);
        playAgain(findViewById(R.id.playAgainButton));

    }
}
