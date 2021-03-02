package com.example.timestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

	private static int MAX_NUM_VALUES = 20;

	private SeekBar numSeekBar;
	private ListView timesTableListView;
	private ArrayAdapter<Integer> tableAdapter;
	private ArrayList<Integer> tableValues;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numSeekBar = findViewById(R.id.numberSeekBar);
		timesTableListView = findViewById(R.id.timesTableListView);
		tableValues = new ArrayList<Integer>();
		for(int i = 1; i <= MAX_NUM_VALUES; i++){
			tableValues.add(i*1);
		}
		tableAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tableValues);
		timesTableListView.setAdapter(tableAdapter);
		numSeekBar.setMax(MAX_NUM_VALUES+1);
		numSeekBar.setMin(1);

		numSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				tableValues.clear();
				for(int j = 1; j <= MAX_NUM_VALUES; j++){
					tableValues.add(j*i);
				}
				tableAdapter.notifyDataSetChanged();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}
}