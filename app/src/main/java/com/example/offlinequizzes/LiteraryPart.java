package com.example.offlinequizzes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LiteraryPart extends AppCompatActivity {
    Button Religion, Arabic, History, Geography, BackToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literary_part);

        Religion = (Button) findViewById(R.id.Religion);
        Arabic = (Button) findViewById(R.id.Arabic);
        History = (Button) findViewById(R.id.History);
        Geography = (Button) findViewById(R.id.Geography);
        BackToHome = (Button) findViewById(R.id.Back);

        Religion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewExams("Religion");
            }
        });

        Arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewExams("Arabic");
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewExams("History");
            }
        });

        Geography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewExams("Geography");
            }
        });

        BackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });
    }

    void backToHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    void viewExams(String Material){
        Intent intent = new Intent(this,ViewExams.class);
        intent.putExtra("Material",Material);
        startActivity(intent);
    }

}
