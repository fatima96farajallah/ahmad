package com.example.offlinequizzes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewResult extends AppCompatActivity {
    private TextView examName, score, firstAnswer, secondAnswer, thridAnswer, fourthAnswer,
            fifthAnswer, sixthAnswer, sevenththAnswer, eighthAnswer, ninthtAnswer, lastAnswer;
    private Button backToHome;
    private String[] allCorrectAnswers, studentAnswers, Questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);


        score = (TextView) findViewById(R.id.score);
        firstAnswer = (TextView) findViewById(R.id.firstAnswer);
        secondAnswer = (TextView) findViewById(R.id.secondAnswer);
        thridAnswer = (TextView) findViewById(R.id.thridAnswer);
        fourthAnswer = (TextView) findViewById(R.id.fourthAnswer);
        fifthAnswer = (TextView) findViewById(R.id.fifthAnswer);
        sixthAnswer = (TextView) findViewById(R.id.sixthAnswer);
        sevenththAnswer = (TextView) findViewById(R.id.sevenththAnswer);
        eighthAnswer = (TextView) findViewById(R.id.eighthAnswer);
        ninthtAnswer = (TextView) findViewById(R.id.ninthtAnswer);
        lastAnswer = (TextView) findViewById(R.id.LastAnswer);
        backToHome = (Button) findViewById(R.id.backToHome);


        showScore();
        displayAllAnswer();

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });

    }

    private int getScore() {
        String[] score;
        int counter = 0;
        score = getIntent().getExtras().getStringArray("FinalResault");
        for (int i = 0; i < 10; i++) {
            if (score[i].equals("1")) {
                counter++;
            }
        }
        return counter;
    }

    private void showScore() {

        score.setText(score.getText() + " " + getScore() + ".");
    }

    private void backToHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private String[] getAllCorrectAnswers() {

        return getIntent().getExtras().getStringArray("allCorrectAnswer");
    }

    private String[] getStudentAnswers() {

        return getIntent().getExtras().getStringArray("studentAnswer");
    }

    private String[] getQuestions() {

        return getIntent().getExtras().getStringArray("Questions");
    }

    private void displayAllAnswer() {

        showAnswer(0, firstAnswer);
        showAnswer(1, secondAnswer);
        showAnswer(2, thridAnswer);
        showAnswer(3, fourthAnswer);
        showAnswer(4, fifthAnswer);
        showAnswer(5, sixthAnswer);
        showAnswer(6, sevenththAnswer);
        showAnswer(7, eighthAnswer);
        showAnswer(8, ninthtAnswer);
        showAnswer(9, lastAnswer);
    }

    private void showAnswer(int question, TextView Answers) {

        allCorrectAnswers = getAllCorrectAnswers();
        studentAnswers = getStudentAnswers();
        Questions = getQuestions();


        if ((studentAnswers[question]).equals(allCorrectAnswers[question])) {
            Answers.setText(Answers.getText() + " " + Questions[question] + ": \n");
            Answers.setText(Answers.getText() + "\n" + "اجابة الطالب: " + studentAnswers[question] + ". \n");
            Answers.setText(Answers.getText() + "\n" + "الاجابة الصحيحة: " + allCorrectAnswers[question] + ".");
            Answers.setBackgroundColor(Color.rgb(51, 204, 51));
        } else {
            Answers.setText(Answers.getText() + " " + Questions[question] + ": \n");
            Answers.setText(Answers.getText() + "\n" + "اجابة الطالب: " + studentAnswers[question] + ". \n");
            Answers.setText(Answers.getText() + "\n" + "الاجابة الصحيحة: " + allCorrectAnswers[question] + ".");
            Answers.setBackgroundColor(Color.rgb(255, 77, 77));
        }

    }

    @Override
    public void onBackPressed() {

        //prevent return to last activity
    }
}
