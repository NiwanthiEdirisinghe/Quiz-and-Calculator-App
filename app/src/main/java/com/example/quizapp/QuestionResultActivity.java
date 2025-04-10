package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QuestionResultActivity extends AppCompatActivity {

    private Question currentQuestion;
    private int selectedAnswerIndex;

    private int currentQuestionIndex;

    private TextView questionTitle, question, progress;

    private Button[] answers;

    private Button next;

    private ProgressBar progressBar;

    private String userName;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_result);

        this.currentQuestion = (Question) getIntent().getSerializableExtra("question");
        this.currentQuestionIndex = getIntent().getIntExtra("question_index", -1);
        this.selectedAnswerIndex = getIntent().getIntExtra("selected_answer", -1);
        this.score = getIntent().getIntExtra("score", 0);
        this.userName = getIntent().getStringExtra("user_name");

        this.initViews();
        this.setUpResults();
        this.setUpProgressBar();
        this.setupNextButton();
    }

    private void initViews(){
        this.question = findViewById(R.id.question);
        this.questionTitle = findViewById(R.id.questionTitle);
        this.answers = new Button[3];
        for(int i=0; i< this.answers.length; i++){
            int resId = getResources().getIdentifier("answer" + (i + 1), "id", getPackageName());
            this.answers[i] = findViewById(resId);
            this.answers[i].setText(this.currentQuestion.getOptions()[i]);
        }
        this.next = findViewById(R.id.result_next_button);
        this.progressBar = findViewById(R.id.progressBar);
        this.progress = findViewById(R.id.progress);
    }

    @SuppressLint("SetTextI18n")
    private void setUpResults(){
        this.question.setText(this.currentQuestion.getQuestionText());
        this.questionTitle.setText("Answer "+(this.currentQuestionIndex+1));
        if(this.currentQuestion.getCorrectAnswerIndex() == this.selectedAnswerIndex){
            ++this.score;
            this.answers[this.selectedAnswerIndex].setBackgroundColor(getResources().getColor(R.color.correctAnswer));
        }else{
            this.answers[this.selectedAnswerIndex].setBackgroundColor(getResources().getColor(R.color.wrongAnswer));
            this.answers[this.currentQuestion.getCorrectAnswerIndex()].setBackgroundColor(getResources().getColor(R.color.correctAnswer));
        }
    }

    private void setupNextButton(){
        next.setOnClickListener(v -> {
            if(this.currentQuestionIndex == 4){
                this.navigateToFinalResultScreen();
            }else{
                Intent intent = new Intent(QuestionResultActivity.this, QuizActivity.class);
                intent.putExtra("user_name", userName);
                intent.putExtra("currentQuestionIndex", ++this.currentQuestionIndex);
                intent.putExtra("score", this.score);
                startActivity(intent);
            }
        });
    }

    private void navigateToFinalResultScreen(){
        Intent intent = new Intent(QuestionResultActivity.this, FinalResultActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("score", this.score);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void setUpProgressBar(){
        this.progressBar.setMax(5);
        this.progressBar.setProgress(currentQuestionIndex + 1);
        this.progress.setText((this.currentQuestionIndex+1)+"/5");
    }
}