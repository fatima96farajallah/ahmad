package com.example.offlinequizzes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button Scientific, Literary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Scientific = (Button) findViewById(R.id.Scientific);
        Literary = (Button) findViewById(R.id.Literary);

        Scientific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScientificPart();
            }
        });

        Literary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLiterary();
            }
        });

    }

    void goToScientificPart() {
        Intent intent = new Intent(this, ScientificPart.class);
        startActivity(intent);
    }

    void goToLiterary() {
        Intent intent = new Intent(this, LiteraryPart.class);
        startActivity(intent);
    }
}
