package com.example.numbershapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	private EditText editTextNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editTextNumber = (EditText) findViewById(R.id.editTextNumber);
	}

	public void checkValue(View view){
		int numToCheck = Integer.parseInt(editTextNumber.getText().toString());
		String toastString = "";
		if(editTextNumber.getText().toString().isEmpty()){
			toastString = "Please enter a number!";
		} else if(isTriangular(numToCheck) && isSquare(numToCheck)){
			toastString = "Your number is triangular and square!";
		} else if(isSquare(numToCheck)){
			toastString = "Your number is square!";
		} else if(isTriangular(numToCheck)){
			toastString = "Your number is triangular";
		} else{
			toastString = "What is this?!";
		}

		Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
	}

	private boolean isTriangular(int numToCheck){
		if(numToCheck < 0)
			return false;

		int sum = 0;

		for(int n = 1; sum <= numToCheck; n++){
			sum = sum + n;
			if (sum == numToCheck)
				return true;
		}

		return false;
	}

	private boolean isSquare(int numToCheck){
		if(numToCheck < 0)
			return false;
		double sqrNum = Math.sqrt(numToCheck);
		if(sqrNum - Math.floor(sqrNum) == 0){
			return true;
		}
		return false;
	}
}