package com.example.offlinequizzes;

import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewExam extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton, radioButton1, radioButton2, radioButton3, radioButton4;
    private ProgressBar progressBar;
    private Button nextQuestion, previousQuestion;
    private TextView questionNumber, generalQuestion, Timer;
    private int cureentQuestion;
    private ArrayList<Exam> exam = new ArrayList<Exam>(10);
    private String[] studentAnswer = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private String[] allCorrectAnswer = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private String[] Questions = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
    private String[] score = {"N", "N", "N", "N", "N", "N", "N", "N", "N", "N"};
    private CountDownTimer countDownTimer;
    private long time = 300000;
    private boolean timeRunning;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exam);

        db = new DatabaseHandler(this);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        questionNumber = (TextView) findViewById(R.id.question_number);
        generalQuestion = (TextView) findViewById(R.id.question);
        Timer = (TextView) findViewById(R.id.timer);
        nextQuestion = (Button) findViewById(R.id.nextQuestion);
        previousQuestion = (Button) findViewById(R.id.previous);
        cureentQuestion = 0;
        previousQuestion.setVisibility(View.GONE);

        deleteAllData();
        setData();
        generateExam(getExamType(), getExamPart() + "");
        Log.d("size_of_exam", exam.size() + "");
        if (exam.size() == 10) {
            startStop();
            showNextQuestion();
        } else {
            Toast.makeText(getApplicationContext(), "No Questions to Display", Toast.LENGTH_SHORT).show();
            backToHome();
        }


        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestionProcess();
            }
        });

        previousQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousQuestionProcess();
            }
        });

    }

    private void backToHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    //Done
    private void startStop() {
        if (timeRunning) {
            stopTime();
        } else {
            startTime();
        }
    }

    //Done
    private void stopTime() {
        countDownTimer.cancel();
        timeRunning = false;
    }

    //Done
    private void startTime() {
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                showFinalResault();
            }
        }.start();
        timeRunning = true;
    }

    //Done
    private void updateTimer(long millisUntilFinished) {

        int seconds = (int) (millisUntilFinished / 1000);

        int hours = seconds / (60 * 60);
        int tempMint = (seconds - (hours * 60 * 60));
        int minutes = tempMint / 60;
        seconds = tempMint - (minutes * 60);


        Timer.setText("الوقت المتبقي : " +
                String.format("%d", minutes)
                + ":" + String.format("%02d", seconds));
    }

    //Done
    private void showFinalResault() {
        Intent intent = new Intent(this, ViewResult.class);
        intent.putExtra("FinalResault", score);
        intent.putExtra("studentAnswer", studentAnswer);
        intent.putExtra("allCorrectAnswer", allCorrectAnswer);
        intent.putExtra("Questions", Questions);
        startActivity(intent);
    }

    //Done
    private void previousQuestionProcess() {

        if (cureentQuestion >= 0) {
            checkAnswer();
            showPreviousQuestion();
        }
        toggleButton();

    }

    //Done
    private void nextQuestionProcess() {

        changeTextOfNextButton();
        if (cureentQuestion < 10) {
            checkAnswer();
            showNextQuestion();
        } else {
            checkAnswer();
            showFinalResault();
        }
        toggleButton();
    }

    //Done
    private void checkAnswer() {

        if (selectedAnswer().equals(correctAnswer())) {
            score[cureentQuestion - 1] = "1";
            studentAnswer[cureentQuestion - 1] = selectedAnswer();
        } else {
            studentAnswer[cureentQuestion - 1] = selectedAnswer();
            score[cureentQuestion - 1] = "N";
        }

        allCorrectAnswer[cureentQuestion - 1] = correctAnswer();
    }

    //Done
    private void changeTextOfNextButton() {

        if (cureentQuestion >= 9)
            nextQuestion.setText("عرض النتيجة النهائية");
        else
            nextQuestion.setText("السؤال التالي");

    }

    //Done
    private void toggleButton() {

        if (cureentQuestion > 1)
            previousQuestion.setVisibility(View.VISIBLE);
        else
            previousQuestion.setVisibility(View.GONE);
    }

    //Done
    private void getPreviousQuestion() {
        if (cureentQuestion > 0)
            cureentQuestion--;
    }

    //Done
    private void getNextQuestion() {
        if (cureentQuestion <= 10)
            cureentQuestion++;
    }

    //Done
    private void showNextQuestion() {
        getNextQuestion();
        changeProgressBar();
        questionNumber.setText("رقم السؤال: " + cureentQuestion + ".");
        generalQuestion.setText(exam.get(cureentQuestion - 1).getQuestion());
        ArrayList<String> ANSWER = exam.get(cureentQuestion - 1).getAnswers();
        radioButton1.setText(ANSWER.get(0));
        radioButton2.setText(ANSWER.get(1));
        radioButton3.setText(ANSWER.get(2));
        radioButton4.setText(ANSWER.get(3));
    }

    //Done
    private void showPreviousQuestion() {
        getPreviousQuestion();
        changeProgressBar();
        questionNumber.setText("رقم السؤال: " + cureentQuestion + ".");
        generalQuestion.setText(exam.get(cureentQuestion - 1).getQuestion());
        ArrayList<String> ANSWER = exam.get(cureentQuestion - 1).getAnswers();
        radioButton1.setText(ANSWER.get(0));
        radioButton2.setText(ANSWER.get(1));
        radioButton3.setText(ANSWER.get(2));
        radioButton4.setText(ANSWER.get(3));
    }

    //Done
    private String correctAnswer() {

        return exam.get(cureentQuestion - 1).getCorrect_answer();
    }

    //Done
    private String selectedAnswer() {
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioButtonId);
        return radioButton.getText().toString();
    }

    //Done
    private void changeProgressBar() {

        progressBar.setProgress(cureentQuestion * 10);
    }

    //Done
    private String getExamType() {

        return getIntent().getExtras().getString("examType");
    }

    //Done
    private int getExamPart() {

        return getIntent().getExtras().getInt("examPart");
    }

    //Done
    private void generateExam(String examType, String examNumber) {
        switch (examType) {
            case "Religion":
                exam = getData("Religion", examNumber);
                exam = getRandomQuestions();
                break;

            case "Arabic":
                exam = getData("Arabic", examNumber);
                exam = getRandomQuestions();
                break;

            case "History":
                exam = getData("History", examNumber);
                exam = getRandomQuestions();
                break;

            case "Geography":
                exam = getData("Geography", examNumber);
                exam = getRandomQuestions();
                break;

            case "Biology":
                exam = getData("Biology", examNumber);
                exam = getRandomQuestions();
                break;

            case "Chemistry":
                exam = getData("Chemistry", examNumber);
                exam = getRandomQuestions();
                break;
        }
    }


    private void setReligionExam() {
        db.insertData("Religion", "1", "المقصود بقطعي الدلالة هو ", "ما كان ثبوته قطعي", "ما كان ثبوته قطعي", "ما يحتمل اكثر من معنى ", "ما لا يفسره العلماء", "ما يفيد معنى واحدا قطعا ");
        db.insertData("Religion", "1", "فضل الله تعالى ادم عن غيره من الخلق ب", " A", "الطاعة وعدم المعصية", "القوة", "العلم", "المال والغنى");
        db.insertData("Religion", "1", "من سمات سنن الله تعالى في المجتمعات ", "A", "التغير من حين لآخر", "خاصة بالمسلمين فقط", "قد تتخلف احيانا", "تخضع لها المجتمعات البشرية ");
        db.insertData("Religion", "1", "السنة المستنبطة من قوله تعالى \" لا يغرنك تقلب الذين كفروا في البلاد", "A", "الفتنة والابتلاء", "ربط النتائح بالاسباب", "اتباع الهدى او الاعراض عنه", "التدافع والتداول");
        db.insertData("Religion", "1", "من أخطر صور البدع", "A", "بدعة العقيدة", "بدعة في العبادة", "زيارة القبور يوم العيد", "ما لم يفعله الرسول");
        db.insertData("Religion", "1", "لا يعد من الصدقة الجارية", "A", "بناء المدارس", "حفر الابار", "اكرام الضيف", "وقف ثمار");
        db.insertData("Religion", "1", "الصحيفة الصادقة مؤلف جمع فيه", "A", "فتاوى الصحابة", "الآراء الفقهية", "أحاديث نبوية", "أقوال التابعين");
        db.insertData("Religion", "1", "أول من عرف بالشرك من البشرية قوم", "A", "ادم عليه السلام", "نوح عليه السلام", "إبراهيم عليه السلام", "عاد");
        db.insertData("Religion", "1", "حكم التجويد في قوله تعالى \"أثم اذا ما وقع ءامنتم به ءالأن وقد كنتم به تستعجلون\"", "A", "مد بدل و مد لازم كلمي مثقل", "مد متصل و مد لازم كلمي مخفف", "مد عارض للسكون و مد منفصل", "مد بدل ومد لازم كلمي مخفف ");
        db.insertData("Religion", "1", "المؤمن بقدرة الله المتوكل عليه سبحانه يصبح ", "A", "مستسلماً ضعيفاً", "عزيزاً شجاعا", "محبطاً يائساً", "جبانا ذليلاً");
        db.insertData("Religion", "2", "المقصود بالكتاب المنظور بوصفه ميداناً للنظر في مخلوقات الله", "A", "السماء وما فيها من كواكب", "القران الكريم", "الكون كله", "الأرض وحدها");
        db.insertData("Religion", "2", "نحول يدخل على صوت الحرف فلا يمتلئ الفم بصداه تعريف لصفة", "A", "التفخيم", "القلقلة", "الترقيق", "المد");
        db.insertData("Religion", "2", "المقصود بالفتنة في قوله تعالى\" ولقد فتنا الذين من قبلهم فليعلمن الله الذين صدقوا وليعلمن الكاذبين\"", "15", "الاختلاف", "الاصلاح بين الناس", "الاختبار والتمحيص", "التحذير");
        db.insertData("Religion", "2", "اللفتة البيانية في قوله تعالى\" فأردت أن أعيبها\" هي", "A", "ربط احدث العيب بالمشيئة", "التصميم على احداث الضرر", "التأدب مع الله بنسب الشر الى نفسه", "نسب الارادة الى نفسه");
        db.insertData("Religion", "2", "الخطاب في قوله تعالى\" وفيكم رسوله \" موجه ل", "A", "المسلمين كافة", "المهاجرين والانصار", "الاوس والخزرج دون غيرهم", "الصحابة وحدهم");
        db.insertData("Religion", "2", "نوع الاستفهام في قوله تعالى\" افحكم الجاهلية يبغون \"", "A", "تحقيق", "انكاري", "تقرير", "نفي");
        db.insertData("Religion", "2", "معنى التوكل على الله تعالى", "20.5", "الرضا بقضاء الله دون الاخذ بالأسباب", "الا يسعى العبد للتغلب على الصعاب", "الرضا بقضاء الله مع الاخذ بالأسباب", "ترك الاخذ بالأسباب");
        db.insertData("Religion", "2", "من مظاهر تكريم الله تعالى لادم كما بينتها الآيات الكريمة:  ", "A", "نهيه عن الاكل من الشجرة", "سجود الملائكة", "خروجه من الجنة", "نزوله الى الارض");
        db.insertData("Religion", "2", "المقصود بقطعي الدلالة هو", "A", "ما لا يفسره الا العلماء", "ما يحتمل اكثر من معنى", "ما يفيد معنى واحدا قطعا", "ما كان ثبوته ثبوتا قطعيا");
        db.insertData("Religion", "2", "العلم الذي يبحث بحال الرواة هو", "A", "احوال الرجال", "علم المتن", "الجرح والتعديل", "علم الحديث");
        db.insertData("Religion", "3", "يمد الواجب المتصل", "6", "من (2-4-5) حركات جوازا", "من (4-5) حركات جوازا", "حركتان جوازا", " من (4-5) حركات وجوبا");
        db.insertData("Religion", "3", "صلاة الله على الصابرين تعني أن الله ", "10", ".ينصرهم على أعدائهم", "يقبل عليهم بالمغفرة والثواب", "يأمر المسلمين بالصلاة عليهم", "يأمر الملائكة بالسجود لهم");
        db.insertData("Religion", "3", "واحدة من الآتية ليست من الأعمال التي يصل ثوابها من الولد إلى والديه", "15", "الدعاء لهما", "أداء فريضة الحج عنهما", "علم ينتفع به", "الصدقة عن روحهما");
        db.insertData("Religion", "3", "واحدة من الاتية تعتبر شرك ظاهر", "14", "التطير", "التوجه بالدعاء للأنبياء لطلب الرزق", "التولة", "الرياء");
        db.insertData("Religion", "3", "السبحة الالكترونية لا تدخل في البدع لأنها", "2.5", "مما فعله الصحابة", "تندرج تحت أدلة عامة", "لها اصل في الدين", "وسيلة تعين على ذكر الله");
        db.insertData("Religion", "3", "استشعار المؤمن رقابة الله تعالى في السر والعلن ينشا عنه اثر من اثار الايمان وهو", "2", "العزة", "الصبر والرضا", "النصر والتمكين", "الاستقامة");
        db.insertData("Religion", "3", "ورثة الأنبياء كما ورد بالحديث النبوي الشريف هم", "20.5", "الزهاد", "العبّاد", "العلماء", "أولوا الامر والحكام");
        db.insertData("Religion", "3", "موطأ الامام مالك مؤلف جمع فيه ", "16", "أحاديث نبوية وأقوال الصحابة وفتاوى التابعين", "فتاوى التابعين", "أحاديث نبوية", "أقوال الصحابة وفتاوى التابعين");
        db.insertData("Religion", "3", "العبرة المستفادة من قوله تعالى \" انك لن تستطيع معي صبرا\"", "1.5", "الصبر وتحمل المشاق في طلب العلم", "التسليم بالغيب", "التواضع في طلب العلم ", "التوكل على الله تعالى");
        db.insertData("Religion", "3", "استشعار المؤمن رقابة الله تعالى في السر والعلن ينشا عنه اثر من اثار الايمان وهو", "2", "العزة", "الصبر والرضا", "النصر والتمكين", "الاستقامة");
    }

    //Done
    private void setArabicExam() {
        /*
          use this following to insert Data
          db.insertData(Material,examPart,question,correctAnswer,answerOne, answerTwo, answerThree, answerFour);
        */

    }

    //Done
    private void setHistoryExam() {
        /*
          use the following to insert Data
          db.insertData(Material,examPart,question,correctAnswer,answerOne, answerTwo, answerThree, answerFour);
        */
    }

    private void setGeographyExam() {
        db.insertData("Geography", "1", "1 + 1 = ", "2", "1", "2", "3", "4");
        db.insertData("Geography", "1", "2 + 4 = ", "6", "5", "6", "3", "4");
        db.insertData("Geography", "1", "6 + 4 = ", "10", "10", "12", "3", "6");
        db.insertData("Geography", "1", "20 - 5 = ", "15", "11", "15", "19", "15");
        db.insertData("Geography", "1", "2 * 7 = ", "14", "14", "4", "10", "12");
        db.insertData("Geography", "1", "5 / 2 = ", "2.5", "2.2", "2.3", "2.4", "2.5");
        db.insertData("Geography", "1", "(1 + 1) / 2 = ", "1", "1", "0", "1.1", "all above");
        db.insertData("Geography", "1", "(20 * 5) / 5 + 2/4 = ", "20.5", "18.1", "100", "20.5", "20");
        db.insertData("Geography", "1", "2 ^ 4 = ", "16", "16", "8", "3", "7");
        db.insertData("Geography", "1", "2 ^ -1 + 1 = ", "1.5", "2", "2.5", "1.5", "4");
        db.insertData("Geography", "2", "2 + 4 = ", "6", "5", "6", "3", "4");
        db.insertData("Geography", "2", "6 + 4 = ", "10", "10", "12", "3", "6");
        db.insertData("Geography", "2", "20 - 5 = ", "15", "11", "15", "19", "15");
        db.insertData("Geography", "2", "2 * 7 = ", "14", "14", "4", "10", "12");
        db.insertData("Geography", "2", "5 / 2 = ", "2.5", "2.2", "2.3", "2.4", "2.5");
        db.insertData("Geography", "2", "(1 + 1) / 2 = ", "1", "1", "0", "1.1", "all above");
        db.insertData("Geography", "2", "(20 * 5) / 5 + 2/4 = ", "20.5", "18.1", "100", "20.5", "20");
        db.insertData("Geography", "2", "2 ^ 4 = ", "16", "16", "8", "3", "7");
        db.insertData("Geography", "2", "2 ^ -1 + 1 = ", "1.5", "2", "2.5", "1.5", "4");
        db.insertData("Geography", "2", "1 + 1 = ", "2", "1", "2", "3", "4");
        db.insertData("Geography", "3", "2 + 4 = ", "6", "5", "6", "3", "4");
        db.insertData("Geography", "3", "6 + 4 = ", "10", "10", "12", "3", "6");
        db.insertData("Geography", "3", "20 - 5 = ", "15", "11", "15", "19", "15");
        db.insertData("Geography", "3", "2 * 7 = ", "14", "14", "4", "10", "12");
        db.insertData("Geography", "3", "5 / 2 = ", "2.5", "2.2", "2.3", "2.4", "2.5");
        db.insertData("Geography", "3", "(1 + 1) / 2 = ", "1", "1", "0", "1.1", "all above");
        db.insertData("Geography", "3", "(20 * 5) / 5 + 2/4 = ", "20.5", "18.1", "100", "20.5", "20");
        db.insertData("Geography", "3", "2 ^ 4 = ", "16", "16", "8", "3", "7");
        db.insertData("Geography", "3", "2 ^ -1 + 1 = ", "1.5", "2", "2.5", "1.5", "4");
        db.insertData("Geography", "3", "1 + 1 = ", "2", "1", "2", "3", "4");
    }

    //Done
    private void setBiologyExam() {

        /*
          use this following to insert Data
          db.insertData(Material,examPart,question,correctAnswer,answerOne, answerTwo, answerThree, answerFour);
        */
    }

    //Done
    private void setChemistryExam() {
        /*
          use this following to insert Data
          db.insertData(Material,examPart,question,correctAnswer,answerOne, answerTwo, answerThree, answerFour);
        */
    }

    //Done
    private void setData() {
        deleteAllData();
        setReligionExam();
        setArabicExam();
        setHistoryExam();
        setGeographyExam();
        setBiologyExam();
        setChemistryExam();
    }

    //Done
    private ArrayList<Exam> getData(String exam_name, String exam_part) {
        ArrayList<Exam> Question = new ArrayList<Exam>();
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()) {
            if (exam_name.equals(cursor.getString(1)) && exam_part.equals(cursor.getString(2)))
                Question.add(new Exam(exam_name, cursor.getString(3)
                        , cursor.getString(4)
                        , new ArrayList<String>(Arrays.<String>asList(
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                ))));
        }
        return Question;
    }

    private void getQuestions() {
        for (int i = 0; i < 10; i++)
            Questions[i] = exam.get(i).getQuestion();
    }

    private void deleteAllData() {

        db.deleteData();
    }

    private ArrayList<Exam> getRandomQuestions() {
        ArrayList<Exam> randomQuestions = new ArrayList<Exam>();
        int[] numbers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int index;
        getQuestions();

        for (int i = 0; i < 10; i++) {
            Log.d("ahmadtrue", i + "");
            index = (int) (Math.random() * exam.size());
            if (numbers[index] == 0) {
                randomQuestions.add(exam.get(index));
                numbers[index] = 1;
                Log.d("index_", index + "");
            } else
                i--;
        }

        return randomQuestions;
    }

    @Override
    public void onBackPressed() {

    }


}
