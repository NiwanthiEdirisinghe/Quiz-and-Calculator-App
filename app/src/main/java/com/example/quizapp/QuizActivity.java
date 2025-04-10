package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView welcomeMessage, questionTitle, question, progress;

    private Button[] answers;

    private Button submit;

    private ProgressBar progressBar;
    private String userName;
    private int currentQuestionIndex;
    private List<Question> questionList;
    private Question currentQuestion;

    private int selectedAnswerIndex;

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.userName = getIntent().getStringExtra("user_name");
        this.currentQuestionIndex =  getIntent().getIntExtra("currentQuestionIndex", -1);
        this.score = getIntent().getIntExtra("score", 0);

        this.initViews();
        this.setUpWelcomeMessage();
        this.setupQuestions();
        this.displayCurrentQuestion();
        this.setUpProgressBar();
        this.setUpSubmitButton();
    }

    private void initViews() {
        this.welcomeMessage = findViewById(R.id.welcome_message);
        this.question = findViewById(R.id.question);
        this.questionTitle = findViewById(R.id.questionTitle);
        this.answers = new Button[3];
        for (int i = 0; i < this.answers.length; i++) {
            int resId = getResources().getIdentifier("answer" + (i + 1), "id", getPackageName());
            this.answers[i] = findViewById(resId);
            int index = i;
            this.answers[i].setOnClickListener(v -> selectAnswer(this.answers[index], index));
        }
        this.submit = findViewById(R.id.quiz_submit_button);
        this.progressBar = findViewById(R.id.progressBar);
        this.progress = findViewById(R.id.progress);
    }

    @SuppressLint("SetTextI18n")
    private void setUpWelcomeMessage(){
        if(this.currentQuestionIndex == 0){
            this.welcomeMessage.setText("Welcome "+userName+"!");
        }else{
            this.welcomeMessage.setVisibility(View.GONE);
        }
    }

    private void setupQuestions() {
        this.questionList = new ArrayList<>();

        questionList.add(new Question(
                "In the context of computer science, which of the following programming paradigms primarily emphasizes the use of objects and classes to model real-world entities?",
                new String[]{"Functional programming", "Object-oriented programming", "Procedural programming"},
                1));

        questionList.add(new Question(
                "Which famous historical invention, attributed to Johannes Gutenberg in the 15th century, revolutionized the spread of knowledge and the accessibility of books across Europe?",
                new String[]{"Printing press", "Steam engine", "Telephone"},
                0));

        questionList.add(new Question(
                "Among the following options, which astronomical phenomenon occurs when the moon moves directly between the Earth and the Sun, casting a shadow over a portion of the Earth's surface?",
                new String[]{"Lunar eclipse", "Solar eclipse", "Equinox"},
                1));

        questionList.add(new Question(
                "In the realm of biology, which macromolecule is primarily responsible for storing and transmitting genetic information across generations of living organisms?",
                new String[]{"Carbohydrates", "Proteins", "DNA"},
                2));

        questionList.add(new Question(
                "Which global agreement, adopted in 2015 and signed by numerous countries, aims to address climate change by reducing greenhouse gas emissions and limiting global warming?",
                new String[]{"Kyoto Protocol", "Paris Agreement", "Montreal Protocol"},
                1));

    }

    @SuppressLint("SetTextI18n")
    private void displayCurrentQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            this.currentQuestion = questionList.get(currentQuestionIndex);

            this.questionTitle.setText("Question "+ (currentQuestionIndex+1));
            this.question.setText(currentQuestion.getQuestionText());


            for (int i = 0; i < this.answers.length; i++) {
                answers[i].setText(currentQuestion.getOptions()[i]);
            }
        } else {
            System.out.println("go to final view");
        }
    }

    private void selectAnswer(Button selectedButton, int answerIndex) {
        this.selectedAnswerIndex= answerIndex;
        int color = getResources().getColor(R.color.selectedAnswer);
        selectedButton.setBackgroundColor(color);
    }

    private void setUpSubmitButton(){
        this.submit.setOnClickListener(v -> {
            if (selectedAnswerIndex == -1) {
                System.out.println("Please select an answer before submitting!");
                return;
            }else{
                Intent intent = new Intent(QuizActivity.this, QuestionResultActivity.class);
                intent.putExtra("question", this.currentQuestion);
                intent.putExtra("selected_answer", this.selectedAnswerIndex);
                intent.putExtra("question_index", this.currentQuestionIndex);
                intent.putExtra("user_name", userName);
                intent.putExtra("score", this.score);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setUpProgressBar(){
        this.progressBar.setMax(this.questionList.size());
        this.progressBar.setProgress(currentQuestionIndex + 1);
        this.progress.setText((this.currentQuestionIndex+1)+"/5");
    }
}