package com.pbl.mindakhalifah;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.snackbar.Snackbar;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private Button btnA, btnB, btnC, btnD;
    private ImageView ivResult;
    private ConstraintLayout rootLayout;

    private Question[] questions;
    private int currentIndex = 0;
    private int score = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = findViewById(R.id.rootLayout);
        tvQuestion = findViewById(R.id.tvQuestion);
        ivResult = findViewById(R.id.ivResult);
        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        ivResult.setVisibility(View.INVISIBLE);

        loadQuestions();
        loadQuestion(currentIndex);

        View.OnClickListener answerClick = v -> {
            int selected = 0;
            if (v == btnA) selected = 0;
            else if (v == btnB) selected = 1;
            else if (v == btnC) selected = 2;
            else if (v == btnD) selected = 3;
            checkAnswer(selected);
        };

        btnA.setOnClickListener(answerClick);
        btnB.setOnClickListener(answerClick);
        btnC.setOnClickListener(answerClick);
        btnD.setOnClickListener(answerClick);
    }

    private void loadQuestions() {
        // TODO: Replace these placeholder questions with actual Kerajaan Uthmaniyah questions
        questions = new Question[]{
                new Question(
                        "Pada tahun berapakah Kerajaan Uthmaniyah (Ottoman) secara tradisional didirikan?",
                        new String[]{"1299", "1453", "1517", "1683"},
                        0
                ),
                new Question(
                        "Siapakah pendiri awal Dinasti Uthmaniyah?",
                        new String[]{"Sultan Mehmed II", "Osman I", "Suleiman the Magnificent", "Selim I"},
                        1
                ),
                new Question(
                        "Ibu kota Kerajaan Uthmaniyah dipindahkan ke Konstantinopel pada tahun…",
                        new String[]{"1453", "1299", "1517", "1683"},
                        0
                ),
                // TODO: Add more questions here
        };
    }

    private void loadQuestion(int index) {
        ivResult.setVisibility(View.INVISIBLE);
        enableButtons(true);
        Question q = questions[index];
        tvQuestion.setText(q.getQuestionText());
        String[] opts = q.getOptions();
        btnA.setText("A. " + opts[0]);
        btnB.setText("B. " + opts[1]);
        btnC.setText("C. " + opts[2]);
        btnD.setText("D. " + opts[3]);
    }

    private void checkAnswer(int selectedIndex) {
        enableButtons(false);
        Question q = questions[currentIndex];
        boolean correct = selectedIndex == q.getCorrectIndex();

        if (correct) {
            score++;
            ivResult.setImageResource(R.drawable.tick);
            Snackbar.make(rootLayout, "Correct!", Snackbar.LENGTH_SHORT).show();
        } else {
            ivResult.setImageResource(R.drawable.cross);
            Snackbar.make(rootLayout, "Wrong!", Snackbar.LENGTH_SHORT).show();
        }
        ivResult.setVisibility(View.VISIBLE);

        handler.postDelayed(() -> {
            currentIndex++;
            if (currentIndex >= questions.length) {
                // Go to result
                Intent i = new Intent(MainActivity.this, ResultActivity.class);
                i.putExtra("score", score);
                i.putExtra("total", questions.length);
                startActivity(i);
                finish();
            } else {
                loadQuestion(currentIndex);
            }
        }, 1000); // 1 second delay
    }

    private void enableButtons(boolean enabled) {
        btnA.setEnabled(enabled);
        btnB.setEnabled(enabled);
        btnC.setEnabled(enabled);
        btnD.setEnabled(enabled);
    }
}