package com.example.offlinequizzes;

import java.util.ArrayList;

public class Exam {
    public Exam(String exam_name, String question, String correct_answer, ArrayList answers) {
        this.exam_name = exam_name;
        this.question = question;
        this.correct_answer = correct_answer;
        this.answers = answers;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "exam_name='" + exam_name + '\'' +
                ", question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", answers=" + answers +
                '}';
    }

    private String exam_name;
    private String question;
    private String correct_answer;
    private ArrayList<String> answers = new ArrayList<String>();
}
