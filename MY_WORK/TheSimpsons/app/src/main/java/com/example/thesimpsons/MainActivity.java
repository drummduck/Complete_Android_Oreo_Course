package com.example.thesimpsons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

	public void fade(View view){
		ImageView bartImage = (ImageView) findViewById(R.id.bartImageView);
		ImageView homerImage = (ImageView) findViewById(R.id.homerImageView);

		if(homerImage.getAlpha() == 0){
			bartImage.animate().alpha(0).setDuration(2000);
			homerImage.animate().alpha(1).setDuration(2000);
		} else if(bartImage.getAlpha() == 0){
			bartImage.animate().alpha(1).setDuration(2000);
			homerImage.animate().alpha(0).setDuration(2000);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}