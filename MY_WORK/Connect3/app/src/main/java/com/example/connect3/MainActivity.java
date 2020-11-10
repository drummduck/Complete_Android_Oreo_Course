package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private boolean firstPlayerTurn = true;
	private boolean gameFinished = false;
	private int[][] board = new int[3][3];
	private ArrayList<View> chipsOnBoard = new ArrayList<>();
	private TextView winnerText;
	private Button playAgainButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		winnerText = (TextView) findViewById(R.id.winnerText);
		playAgainButton = (Button) findViewById(R.id.playAgainButton);
	}

	public void cellClicked(View view){
		ImageView v;
		if(view instanceof ImageView) {
			v = (ImageView) view;
			int viewRowPos = Integer.parseInt(getResources().getResourceEntryName(v.getId()).split("_")[1]);
			int viewColPos = Integer.parseInt(getResources().getResourceEntryName(v.getId()).split("_")[2]);
			if(board[viewRowPos][viewColPos] != 0 || gameFinished){
				return;
			}
			ImageView viewCopy = new ImageView(findViewById(R.id.theTop).getContext());
			viewCopy.setMinimumHeight(v.getHeight() - 20);
			viewCopy.setMaxHeight(v.getHeight() - 20);
			viewCopy.setMaxWidth(v.getMeasuredWidth() - 20);
			viewCopy.setMinimumWidth(v.getMeasuredWidth() - 20);
			viewCopy.setAdjustViewBounds(true);
			if(firstPlayerTurn) {
				viewCopy.setImageResource(R.drawable.red);
				if(didMoveWin(viewRowPos,viewColPos)){
					winnerText.setText("Red has won!");
					playAgainButton.setVisibility(View.VISIBLE);
				} else {
					firstPlayerTurn = false;
				}
			} else{
				viewCopy.setImageResource(R.drawable.yellow);
				if(didMoveWin(viewRowPos,viewColPos)) {
					winnerText.setText("Yellow has won!");
					playAgainButton.setVisibility(View.VISIBLE);
				} else {
					firstPlayerTurn = true;
				}
			}
			viewCopy.setVisibility(View.VISIBLE);
			viewCopy.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			DisplayMetrics dm = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int topOffset = dm.heightPixels - findViewById(R.id.theTop).getMeasuredHeight();
			int[] locations = new int[2];
			v.getLocationOnScreen(locations);
			viewCopy.setX(locations[0] + 20);
			viewCopy.setY(-1000);
			viewCopy.bringToFront();
			((ConstraintLayout) findViewById(R.id.theTop)).addView(viewCopy);
			viewCopy.animate().translationY(locations[1] - topOffset + 10).setDuration(1000).start();
			chipsOnBoard.add(viewCopy);
		}
	}

	private boolean didMoveWin(int row, int col){
		int playerInt = firstPlayerTurn ? 1 : 2;
		board[row][col] = playerInt;
		switch(row) {
			case 0: {
				switch (col) {
					case 0: {
						if ((board[0][1] == playerInt && board[0][2] == playerInt) ||
								(board[1][0] == playerInt && board[2][0] == playerInt) ||
								(board[1][1] == playerInt && board[2][2] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					case 1: {
						if ((board[0][0] == playerInt && board[0][2] == playerInt) ||
								(board[1][1] == playerInt && board[2][1] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					case 2: {
						if ((board[0][0] == playerInt && board[0][1] == playerInt) ||
								(board[1][2] == playerInt && board[2][2] == playerInt) ||
								(board[1][1] == playerInt && board[2][0] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					default:
						break;
				}
				break;
			}
			case 1: {
				switch (col) {
					case 0: {
						if ((board[1][1] == playerInt && board[1][2] == playerInt) ||
								(board[0][0] == playerInt && board[2][0] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					case 1: {
						if ((board[1][0] == playerInt && board[1][2] == playerInt) ||
								(board[0][1] == playerInt && board[2][1] == playerInt) ||
								(board[0][2] == playerInt && board[2][0] == playerInt) ||
								(board[0][0] == playerInt && board[2][2] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					case 2: {
						if ((board[0][2] == playerInt && board[2][2] == playerInt) ||
								(board[1][0] == playerInt && board[1][1] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					default:
						break;
				}
				break;
			}
			case 2: {
				switch (col) {
					case 0: {
						if ((board[2][1] == playerInt && board[2][2] == playerInt) ||
								(board[0][0] == playerInt && board[1][0] == playerInt) ||
								(board[1][1] == playerInt && board[0][2] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					case 1: {
						if ((board[2][0] == playerInt && board[2][2] == playerInt) ||
								(board[1][1] == playerInt && board[0][1] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					case 2: {
						if ((board[2][0] == playerInt && board[2][1] == playerInt) ||
								(board[1][2] == playerInt && board[0][2] == playerInt) ||
								(board[1][1] == playerInt && board[0][0] == playerInt)) {
							gameFinished = true;
							return true;
						}
						break;
					}
					default:
						break;
				}
				break;
			}
			default:
				break;
		}

		return false;
	}

	public void playAgain(View view){
		for(View v: chipsOnBoard){
			((ViewGroup)v.getParent()).removeView(v);
		}
		chipsOnBoard.clear();
		winnerText.setText("");
		board = new int[3][3];
		playAgainButton.setVisibility(View.GONE);
		gameFinished = false;
	}
}