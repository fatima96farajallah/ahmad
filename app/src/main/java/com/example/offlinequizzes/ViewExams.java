package com.example.offlinequizzes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewExams extends AppCompatActivity {

    Button first_exam, secand_exam, thrid_exam;
    String exam_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exams);

        first_exam = (Button) findViewById(R.id.first_exam);
        secand_exam = (Button) findViewById(R.id.second_exam);
        thrid_exam = (Button) findViewById(R.id.thrid_exam);

        exam_type = getIntent().getExtras().getString("Material");

        first_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyToExam(exam_type,1);
            }
        });

        secand_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyToExam(exam_type,2);
            }
        });

        thrid_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyToExam(exam_type,3);
            }
        });
    }

    private void applyToExam(String examType, int examPart){
        Intent intent = new Intent(this,ViewExam.class);
        intent.putExtra("examType",examType);
        intent.putExtra("examPart", examPart);
        startActivity(intent);
    }

}
