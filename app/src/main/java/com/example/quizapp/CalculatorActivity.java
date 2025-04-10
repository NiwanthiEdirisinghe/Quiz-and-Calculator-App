package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class CalculatorActivity extends AppCompatActivity {

    EditText firstNumber, secondNumber;
    Button buttonAddition, buttonSubstraction, buttonGoBack;
    TextView result;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);

        this.initViews();

        buttonAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult("add");
            }
        });

        buttonSubstraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult("sub");
            }
        });

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        firstNumber = findViewById(R.id.editTextFirstNumber);
        secondNumber = findViewById(R.id.editTextSecondNumber);
        buttonAddition = findViewById(R.id.buttonAdd);
        buttonSubstraction = findViewById(R.id.buttonSubtract);
        buttonGoBack = findViewById(R.id.buttonGoBackCalculator);
        result = findViewById(R.id.calculatorResult);
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult(String operation) {
        String first = firstNumber.getText().toString().trim();
        String second = secondNumber.getText().toString().trim();

        if (first.isEmpty() || second.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(first);
            double num2 = Double.parseDouble(second);
            double res = 0;

            if (Objects.equals(operation, "add")) {
                res = num1 + num2;
            } else if (Objects.equals(operation, "sub")) {
                res = num1 - num2;
            }

            result.setText("Result: " + res);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }
}