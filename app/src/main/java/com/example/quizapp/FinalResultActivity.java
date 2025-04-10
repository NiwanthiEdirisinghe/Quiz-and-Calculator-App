package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalResultActivity extends AppCompatActivity {

    private String userName;

    private int score;

    private TextView finalResult, scoreView;

    private Button buttonTakeNewQuiz, buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final_result);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.userName = getIntent().getStringExtra("user_name");
        if (this.userName == null) this.userName = "User";

        this.score = getIntent().getIntExtra("score", 0);

        this.initView();
        this.setUpValues();
    }

    private void initView() {
        this.finalResult = findViewById(R.id.finalResult);
        this.scoreView = findViewById(R.id.score);
        this.buttonTakeNewQuiz = findViewById(R.id.takeNewQuiz);
        this.buttonFinish = findViewById(R.id.finish);
    }

    private void setUpValues() {
        String result = score > 2 ? "Congratulations " + this.userName + "!" : "Sorry! Try again.";
        this.finalResult.setText(result);
        this.scoreView.setText(String.valueOf(this.score+"/5"));

        buttonTakeNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalResultActivity.this, QuizActivity.class);
                intent.putExtra("user_name", userName);
                intent.putExtra("currentQuestionIndex", 0);
                intent.putExtra("score", 0);
                startActivity(intent);
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}