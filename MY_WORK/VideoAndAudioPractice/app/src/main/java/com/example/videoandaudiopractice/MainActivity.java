package com.example.videoandaudiopractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

	private Button playButton;
	private Button pauseButton;
	private SeekBar volumeControl;
	private SeekBar playbackControl;
	private VideoView vidView;
	private AudioManager audioManager;
	private boolean isSeekingHeld;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/** Video portion **/
//		vidView = (VideoView)findViewById(R.id.videoView);
//		vidView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.stopwatch_the_movie);
//		MediaController mC = new MediaController(this);
//		mC.setAnchorView(vidView);
//		vidView.setMediaController(mC);
//		vidView.start();

		volumeControl = (SeekBar) findViewById(R.id.volumeBar);
		playbackControl = (SeekBar) findViewById(R.id.playbackBar);
		audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);;

		volumeControl.setMax(maxVolume);
		volumeControl.setProgress(currentVolume);
		volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});


		final MediaPlayer mP = MediaPlayer.create(this, R.raw.lalala);
		playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mP.start();
				Timer timer = new Timer();
				timer.schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						if(!isSeekingHeld) {
							playbackControl.setProgress(mP.getCurrentPosition());
						} else if(!mP.isPlaying()){
							this.cancel();
						}
					}
				}, 0, 100);
			}
		});
		pauseButton = (Button) findViewById(R.id.pauseButton);
		pauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mP.pause();
			}
		});

		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.lalala);
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(this, uri);
		String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		int maxPlaybackTime = Integer.parseInt(durationStr);
		Log.i("NATHAN_DEBUG", "maxPlaybackTime: " + maxPlaybackTime);
		playbackControl.setMax(maxPlaybackTime);
		playbackControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				isSeekingHeld = true;
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				isSeekingHeld = false;
				mP.seekTo(seekBar.getProgress());
			}
		});
	}
}