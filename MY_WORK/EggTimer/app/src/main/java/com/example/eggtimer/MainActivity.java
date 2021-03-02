package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private SeekBar setTimeSeekBar;
    private TextView timerView;
    private Button startStopButton;
    private boolean timerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTimeSeekBar = findViewById(R.id.timeSetSeekBar);
        timerView = findViewById(R.id.timerView);
        startStopButton = findViewById(R.id.startStopButton);

        setTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                long minutesValue = TimeUnit.SECONDS.toMinutes(i);
                if(minutesValue > 0){
                    long secondsValue = i - (60*minutesValue);;
                    if(secondsValue > 9){
                        timerView.setText(minutesValue + ":" + secondsValue);
                    } else{
                        timerView.setText(minutesValue + ":0" + secondsValue);
                    }

                } else{
                    if(i > 9){
                        timerView.setText("0:" + i);
                    } else{
                        timerView.setText("0:0" + i);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerRunning){
                    setTimeSeekBar.setEnabled(false);
                    timerRunning = true;
                    startStopButton.setText("Stop");
                    new CountDownTimer(setTimeSeekBar.getProgress() * 1000, 1000){

                        @Override
                        public void onTick(long l) {
                            if(!timerRunning) cancel();
                            else setTimeSeekBar.setProgress(setTimeSeekBar.getProgress() - 1);
                        }

                        @Override
                        public void onFinish() {
                            MediaPlayer.create(getApplicationContext(), getResources().getIdentifier("napalm_death", "raw", getPackageName())).start();
                        }
                    }.start();
                } else{
                   setTimeSeekBar.setEnabled(true);
                   startStopButton.setText("Start");
                   timerRunning = false;
                }
            }
        });
    }
}