package com.example.guesscelebrityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

	private ImageView celebrityImageView;
	private ArrayList<String> celebrityNames;
	private ArrayList<Bitmap> celebrityImages;
	private Map<String,Bitmap> celebrityMatch;
	private ArrayList<Button> answerButtons;
	private Button answer1;
	private Button answer2;
	private Button answer3;
	private Button answer4;
	private String currentCorrectName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		celebrityNames = new ArrayList<>();
		celebrityImages = new ArrayList<>();
		answerButtons = new ArrayList<>();
		celebrityMatch = new HashMap<>();

		celebrityImageView = (ImageView) findViewById(R.id.celebrityImageView);
		answer1 = (Button) findViewById(R.id.celebrityAnswer1);
		answer2 = (Button) findViewById(R.id.celebrityAnswer2);
		answer3 = (Button) findViewById(R.id.celebrityAnswer3);
		answer4 = (Button) findViewById(R.id.celebrityAnswer4);
		answerButtons.addAll(Arrays.asList(answer1, answer2, answer3, answer4));

		HtmlDownloader htmlDownloader = new HtmlDownloader();

		try {
			String htmlString;
			htmlString = htmlDownloader.execute("https://www.imdb.com/list/ls052283250/").get();

			Pattern celebrityInfoPattern = Pattern.compile("<div class=\"lister-item-image\">(.*?)</div>", Pattern.DOTALL);
			Matcher celebrityInfoMatcher = celebrityInfoPattern.matcher(htmlString);
			while (celebrityInfoMatcher.find()) {
				String celebrityInfo = celebrityInfoMatcher.group(1);
				Pattern celebrityNamePattern = Pattern.compile("<img alt=\"(.*?)\"", Pattern.DOTALL);
				Matcher celebrityNameMatcher = celebrityNamePattern.matcher(celebrityInfo);
				while(celebrityNameMatcher.find()) {
					celebrityNames.add(celebrityNameMatcher.group(1));
				}
				Pattern celebrityImagePattern = Pattern.compile("src=\"(.*?)\"", Pattern.DOTALL);
				Matcher celebrityImageMatcher = celebrityImagePattern.matcher(celebrityInfo);
				while(celebrityImageMatcher.find()) {
					celebrityImages.add(new ImageDownloader().execute(celebrityImageMatcher.group(1)).get());
				}
				for(int i = 0; i < celebrityNames.size(); i++){
					celebrityMatch.put(celebrityNames.get(i), celebrityImages.get(i));
				}
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		setupQuestion();
	}

	private class ImageDownloader extends AsyncTask<String, Void, Bitmap>
	{
		@Override
		protected Bitmap doInBackground(String... strings) {
			Bitmap bitmap = null;
			try {
				URL url = new URL(strings[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				InputStream imgInputStream = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(imgInputStream);
				imgInputStream.close();
				conn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}
	}

	private class HtmlDownloader extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... strings) {
			String html = null;
			try {
				URL url = new URL(strings[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				InputStream htmlInputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(htmlInputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String readLine = null;
				while((readLine = bufferedReader.readLine()) != null){
					html += readLine;
				}
				bufferedReader.close();
				inputStreamReader.close();
				htmlInputStream.close();
				conn.disconnect();
			} catch (Exception e) {
				// print error
				e.printStackTrace();
			}
			return html;
		}
	}

	private void setupQuestion(){
		Random random = new Random();
		int numOfNames = celebrityNames.size();
		currentCorrectName = celebrityNames.get(random.nextInt(numOfNames));
		Bitmap celebrityImage = celebrityMatch.get(currentCorrectName);
		celebrityImageView.setImageBitmap(celebrityImage);
		Collections.shuffle(answerButtons);
		boolean answerSet = false;
		for(Button b: answerButtons){
			String s = celebrityNames.get(random.nextInt(numOfNames));
			if(!answerSet) {
				b.setText(currentCorrectName);
				answerSet = true;
			}
			else if(!s.equals(currentCorrectName)) b.setText(s);
		}
	}

	public void selectAnswer(View v){
		if(((Button)v).getText().equals(currentCorrectName)){
			Toast.makeText(this, "You are right!", Toast.LENGTH_LONG).show();
			setupQuestion();
		} else{
			Toast.makeText(this, "You are wrong!", Toast.LENGTH_LONG).show();
		}
	}
}