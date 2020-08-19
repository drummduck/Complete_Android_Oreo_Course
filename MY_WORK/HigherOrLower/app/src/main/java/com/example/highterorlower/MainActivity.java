package com.example.highterorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText numberEntered = (EditText) findViewById(R.id.editTextNumber);
		Button guessButton = (Button) findViewById(R.id.buttonGuess);


		guessButton.setOnClickListener(new View.OnClickListener() {
			int randomNum = (int) (Math.random() * 20);
			@Override
			public void onClick(View view) {
				int guess = Integer.parseInt(numberEntered.getText().toString());
				if(guess == randomNum){
					Toast.makeText(getApplicationContext(), "You guessed correctly! Setting new number!", Toast.LENGTH_LONG).show();
					randomNum = (int)(Math.random() * 20);
				} else if(guess > randomNum){
					Toast.makeText(getApplicationContext(), "Sorry! Too high! Guess again!", Toast.LENGTH_LONG).show();
				} else{
					Toast.makeText(getApplicationContext(), "Sorry! Too low! Guess again!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}