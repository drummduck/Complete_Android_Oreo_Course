package com.example.thesimpsons2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageView bartImage = (ImageView) findViewById(R.id.bartImageView);

		final float scale = getApplication().getApplicationContext().getResources().getDisplayMetrics().density;
		int pixels = (int) (0 * scale + 0.5f);

		bartImage.setX(-3000);
		bartImage.setY(pixels);
		bartImage.animate().translationX(pixels).rotation(3600).setDuration(2000);
	}
}