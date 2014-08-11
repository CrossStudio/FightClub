package com.example.boxinggame2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseFighter extends Activity {

	
	Button btnScorpion;
	Button btnSubZero;
	Button btnKungLao;
	Button btnSonya;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_fighter);
		
		assignViews();
	}
	


	private void assignViews() {
		btnScorpion = (Button)findViewById(R.id.btnScorpion);
		btnSubZero = (Button)findViewById(R.id.btnSubZero);
		btnKungLao = (Button)findViewById(R.id.btnKungLao);
		btnSonya = (Button)findViewById(R.id.btnSonya);
		
		ChooseFighterOnClickListener listener = new ChooseFighterOnClickListener();
		
		btnScorpion.setOnClickListener(listener);
		btnSubZero.setOnClickListener(listener);
		btnKungLao.setOnClickListener(listener);
		btnSonya.setOnClickListener(listener);
		
	}

	public class ChooseFighterOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ChooseFighter.this, Fight.class);
			switch (v.getId()){
			case R.id.btnScorpion:
				intent.putExtra("name", "Scorpion");
				Log.d("myLogs", "Scorpion chosen");
				startActivity(intent);
				break;
			case R.id.btnSubZero:
				intent.putExtra("name", "Sub Zero");
				Log.d("myLogs", "Sub Zero chosen");
				startActivity(intent);
				break;
			case R.id.btnKungLao:
				intent.putExtra("name", "Kung Lao");
				Log.d("myLogs", intent.getStringExtra("name") + " chosen");
				startActivity(intent);
				break;
			case R.id.btnSonya:
				intent.putExtra("name", "Sonya");
				Log.d("myLogs", "Sonya chosen");
				startActivity(intent);
				break;
			default:
				break;
			}
			
		}
		
	}
}
