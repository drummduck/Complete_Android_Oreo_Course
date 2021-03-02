package com.example.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	AudioManager audioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	}

	public void soundClick(View view){
		Button button;
		if(view instanceof Button){
			button = (Button)view;
		} else return;

		MediaPlayer mediaPlayer = MediaPlayer.create(this, getResources().getIdentifier(button.getTag().toString(), "raw", getPackageName()));

		Log.i("BasicPhrases", "soundClick - resourceName: " + button.getResources().getResourceName(button.getId()));

//		try {
//			switch (button.getResources().getResourceName(button.getId())) {
//				case "com.example.basicphrases:id/button1":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.doyouspeakenglish);
//					break;
//				}
//				case "com.example.basicphrases:id/button2":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.hello);
//					break;
//				}
//				case "com.example.basicphrases:id/button3":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.mynameis);
//					break;
//				}
//				case "com.example.basicphrases:id/button4":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.welcome);
//					break;
//				}
//				case "com.example.basicphrases:id/button5":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.goodevening);
//					break;
//				}
//				case "com.example.basicphrases:id/button6":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.howareyou);
//					break;
//				}
//				case "com.example.basicphrases:id/button7":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.please);
//					break;
//				}
//				case "com.example.basicphrases:id/button8":{
//					mediaPlayer = MediaPlayer.create(this, R.raw.ilivein);
//					break;
//				}
//				default: break;
//			}
//		} catch (Exception e){
//			Log.i("BasicPhrases", "EXCEPTION: " + e.getLocalizedMessage());
//		}

		if(mediaPlayer != null) mediaPlayer.start();
	}
}