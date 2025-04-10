package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button submitButton, goToCalculatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initViews();
        this.setupSubmitButton();

        goToCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameEditText.getText().toString();

                if (!userName.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                    startActivity(intent);
                } else {
                    nameEditText.setError("Please enter your name");
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        nameEditText = findViewById(R.id.name_input);
        submitButton = findViewById(R.id.submit_button);
        goToCalculatorButton = findViewById(R.id.calculator_button_quiz);
    }

    private void setupSubmitButton(){
        submitButton.setOnClickListener(v -> {
            String userName = nameEditText.getText().toString();

            if (!userName.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                intent.putExtra("user_name", userName);
                intent.putExtra("currentQuestionIndex", 0);
                intent.putExtra("score", 0);
                startActivity(intent);
            } else {
                nameEditText.setError("Please enter your name");
                Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}