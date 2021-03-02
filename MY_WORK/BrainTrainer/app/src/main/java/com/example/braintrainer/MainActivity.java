package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	private TextView timer;
	private TextView question;
	private TextView score;
	private Button answer1;
	private Button answer2;
	private Button answer3;
	private Button answer4;
	private TextView answerResponse;
	private Button restartButton;
	private Button goButton;

	private int correctAnswer;
	private ArrayList<Button> answerButtons;
	private int questionCount;
	private int correctAnswerCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		timer = findViewById(R.id.timerTextView);
		question = findViewById(R.id.questionTextView);
		score = findViewById(R.id.scoreTextView);
		answer1 = findViewById(R.id.answer1);
		answer2 = findViewById(R.id.answer2);
		answer3 = findViewById(R.id.answer3);
		answer4 = findViewById(R.id.answer4);
		answerResponse = findViewById(R.id.answerResponseView);
		restartButton = findViewById(R.id.restartButton);
		goButton = findViewById(R.id.goButton);
		answerButtons = new ArrayList<Button>(Arrays.asList(answer1, answer2, answer3, answer4));

		timer.setVisibility(View.GONE);
		question.setVisibility(View.GONE);
		score.setVisibility(View.GONE);
		answer1.setVisibility(View.GONE);
		answer2.setVisibility(View.GONE);
		answer3.setVisibility(View.GONE);
		answer4.setVisibility(View.GONE);
		answerResponse.setVisibility(View.GONE);
		restartButton.setVisibility(View.GONE);

		goButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goButton.setVisibility(View.GONE);
				timer.setVisibility(View.VISIBLE);
				timer.setText("30s");
				score.setVisibility(View.VISIBLE);
				score.setText("0/0");
				question.setVisibility(View.VISIBLE);
				for(final Button b : answerButtons){
					b.setVisibility(View.VISIBLE);
					b.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							questionCount++;
							if(Integer.parseInt(b.getText().toString()) == correctAnswer){
								answerResponse.setText("Correct!");
								correctAnswerCount++;
							} else{
								answerResponse.setText("Wrong :(");
							}
							score.setText(correctAnswerCount + "/" + questionCount);
							setupQuestion();
						}
					});
				}
				setupQuestion();
				startCountDownTimer();
			}
		});

		restartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				restartButton.setVisibility(View.GONE);
				timer.setText("30s");
				score.setText("0/0");
				for(Button b : answerButtons) b.setClickable(true);
				setupQuestion();
				startCountDownTimer();
			}
		});
	}

	private void setupQuestion(){
		question.setText(generateRandomQuestion());
		Collections.shuffle(answerButtons);
		boolean answerSet = false;
		for(Button b: answerButtons){
			if(!answerSet){
				b.setText(String.valueOf(correctAnswer));
				answerSet = true;
			} else{
				b.setText(generateRandomAnswer());
			}
		}
	}

	private String generateRandomQuestion(){
		int firstInt = ((int)(Math.random() * 10)) * (Math.random() > 0.5d ? 1 : -1);
		int secondInt = ((int)(Math.random() * 10)) * (Math.random() > 0.5d ? 1 : -1);
		boolean isAdding = Math.random() > 0.5d ? true : false;

		correctAnswer = (isAdding ? (firstInt + secondInt) : (firstInt - secondInt));

		return ((firstInt < 0 ? "(" : "") + firstInt + (firstInt < 0 ? ")" : "") + (isAdding ? " + " : " - ") + (secondInt < 0 ? "(" : "") + secondInt + (secondInt < 0 ? ")" : ""));
	}

	private String generateRandomAnswer(){
		boolean isNotCorrectAnswer = false;
		int fakeAnswer = -1;
		while(!isNotCorrectAnswer) {
			int numToAddOrSubtract = (int) (Math.random() * 5);
			boolean isAdding = Math.random() > 0.5d ? true : false;
			fakeAnswer = (isAdding ? correctAnswer + numToAddOrSubtract : correctAnswer - numToAddOrSubtract);
			if(fakeAnswer != correctAnswer) isNotCorrectAnswer = true;
		}
		return String.valueOf(fakeAnswer);
	}

	private void startCountDownTimer(){
		new CountDownTimer(30000, 1000) {
			@Override
			public void onTick(long l) {
				timer.setText(l/1000 + "s");
			}

			@Override
			public void onFinish() {
				answerResponse.setText("Done!");
				for(Button b : answerButtons) b.setClickable(false);
				restartButton.setVisibility(View.VISIBLE);
				restartButton.setText("Play Again!");
				questionCount = 0;
				correctAnswerCount = 0;
			}
		}.start();
	}
}