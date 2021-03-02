package com.example.listviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

//		final ListView listView = (ListView) findViewById(R.id.listView);
//
//		final ArrayList<String> myFamily = new ArrayList<String>();
//		myFamily.add("Nathan");
//		myFamily.add("Bryce");
//		myFamily.add("Jori");
//		myFamily.add("Kaden");
//		myFamily.add("Jake");
//		myFamily.add("Debbie");
//		myFamily.add("Kelly");
//
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myFamily);
//		listView.setAdapter(arrayAdapter);
//
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				Log.i("NATHAN_DEBUG", "Item: " + myFamily.get(i));
//			}
//		});

		ListView myListView = (ListView) findViewById(R.id.listView);

		final ArrayList<String> myFriends = new ArrayList<>(asList("Trevor", "Cody", "Jake","Kolton","Seath","Kat","Kortnie","Kornie","Taylor"));

		final ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myFriends);

		myListView.setAdapter(myAdapter);

		myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Toast.makeText(getApplicationContext(), myFriends.get(i), Toast.LENGTH_LONG).show();
			}
		});
	}
}