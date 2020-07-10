package com.example.formattingtext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	public void convertCurrency(View view){
		EditText currencyText = (EditText) findViewById(R.id.currencyEditText);
		Toast.makeText(this, "Pounds converted to US: $" + Double.parseDouble(currencyText.getText().toString()) * 1.3, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}