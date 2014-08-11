package com.example.boxinggame2;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Fight extends Activity{

	/**
	 * Максимальное количество свободных очков действия
	 */
	final static int MAX_AP = 10;
	
	Fighter SubZero;
	
	Fighter playerFighter;
	
	ArrayList<Fighter> fightersList = new ArrayList<Fighter>();
	
	Attack attack;
	
	final static boolean FIRST_PLAYER = true;
	final static boolean SECOND_PLAYER = false;
	
	final static int DEFAULT_HP = 10;

	Button btnEndTurn;
	
	ArrayList<ImageView> player1ActionIcons = new ArrayList<ImageView>();
	ArrayList<ImageView> player2ActionIcons = new ArrayList<ImageView>();
	
	public static ArrayList<FightAction> player1ActionsList = new ArrayList<FightAction>();
	public static ArrayList<FightAction> player2ActionsList = new ArrayList<FightAction>();
	
	DamageCalculatorTask newTask;
	
	ArrayList<Boolean> player1FilledImages = new ArrayList<Boolean>();
	ArrayList<Boolean> player2FilledImages = new ArrayList<Boolean>();
	
	LinearLayout player1Layout;
	LinearLayout player2Layout;
	LinearLayout.LayoutParams llParams;
	
	ProgressBar pbFirstPlayerHealth;
	ProgressBar pbSecondPlayerHealth;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fight);
	
		Intent intent = getIntent();
		String playerFighterName = intent.getStringExtra("name");
		
		playerFighter = new Fighter(playerFighterName, DEFAULT_HP, true);
		fightersList.add(playerFighter);
		playerFighter.addAction(Attack.QUICK_PUNCH);
		playerFighter.addAction(Attack.MEGA_PUNCH);
		playerFighter.addAction(Defence.BASIC_BLOCK);
		
		
		SubZero = new Fighter("Sub Zero", DEFAULT_HP, false);
		SubZero.addAction(Attack.HARD_PUNCH);
		SubZero.addAction(Attack.MEGA_PUNCH);
		SubZero.addAction(Defence.BASIC_BLOCK);
		fightersList.add(SubZero);
		
		assignSpecialMoves();
		
		llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llParams.setMargins(5, 5, 5, 5);
		
		pbFirstPlayerHealth = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
		pbSecondPlayerHealth = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
		assignViews();
		
		addFighterIcons();
		addActionLines();
		
		addHealthBars(playerFighter, SubZero);
		
		newTask = new DamageCalculatorTask();
		newTask.execute();
	}
	
	/**
	 * Добавляет область в которую будут помещаться действия игрока
	 */
	private void addActionLines() {
		LinearLayout llActionLine = new LinearLayout(this);
		llActionLine.setBackgroundResource(R.drawable.drop_line);
		llActionLine.setLayoutParams(llParams);
		llActionLine.setOrientation(LinearLayout.HORIZONTAL);
		player1Layout.addView(llActionLine);
		llActionLine.setId(R.id.first_player_action_line);
		llActionLine.setOnDragListener(new MyDragListener());
	}

	private void assignSpecialMoves() {
		for (int i = 0; i < fightersList.size(); i++){
			Fighter fighter = fightersList.get(i);
			if (fighter.getName().equals("Scorpion")){
				fighter.addAction(Attack.SCORPION_FIRE_KICK);
			}
			else if (fighter.getName().equals("Sub Zero")){
				fighter.addAction(Attack.SUB_ZERO_FREEZE);
			}
			else if (fighter.getName().equals("Kung Lao")){
				fighter.addAction(Attack.KUNG_LAO_ROUND_KICK);
			}
			else if (fighter.getName().equals("Sonya")){
				fighter.addAction(Attack.SONYA_LEG_GRAB);
			}
		}
	}
	
	private void addHealthBars(Fighter firstFighter, Fighter secondFighter) {
		
		pbFirstPlayerHealth.setLayoutParams(llParams);
		pbFirstPlayerHealth.setMax(firstFighter.getHP());
		pbFirstPlayerHealth.setProgress(firstFighter.getHP());
		pbFirstPlayerHealth.setId(R.id.first_player_health);
		player1Layout.addView(pbFirstPlayerHealth);
		
		pbSecondPlayerHealth.setLayoutParams(llParams);
		pbSecondPlayerHealth.setMax(secondFighter.getHP());
		pbSecondPlayerHealth.setProgress(secondFighter.getHP());
		pbSecondPlayerHealth.setId(R.id.second_player_health);
		player2Layout.addView(pbSecondPlayerHealth);
	}

	/**
	 * Создает кнопки ударов в зависимости от типов ударов (Attack), выбранных игроком для бойца (Fighter)
	 * 
	 */
	private void addFighterIcons() {
		
		ImageView newIcon;
		int playerCount = 1;
		for (Fighter fighter : fightersList){
			for (FightAction action : fighter.getActions()){
				newIcon = new ImageView(this);
				newIcon.setLayoutParams(llParams);
				newIcon.setOnTouchListener(new MyOnTouchListener(action));
				newIcon.setContentDescription(action.get_title());
				
				if (playerCount == 1){
					newIcon.setId(action.getActionID());
					newIcon.setBackgroundResource(action.getImageResource());
					player1ActionIcons.add(newIcon);
					player1Layout.addView(newIcon);
				}
				
				else if (playerCount == 2){
					if (action.get_title().equals("Quick Punch")){
						newIcon.setId(R.id.second_player_quick_punch);
					}
					else if (action.get_title().equals("Hard Punch")){
						newIcon.setId(R.id.second_player_hard_punch);
					}
					else if (action.get_title().equals("Mega Punch")){
						newIcon.setId(R.id.second_player_mega_punch);
					}
					else if (action.get_title().equals("Fire Kick")){
						newIcon.setId(R.id.second_player_fire_kick);
					}
					else if (action.get_title().equals("Freeze")){
						newIcon.setId(R.id.second_player_freeze);
					}
					else if (action.get_title().equals("Round Kick")){
						newIcon.setId(R.id.second_player_round_kick);
					}
					else if (action.get_title().equals("Leg Grab")){
						newIcon.setId(R.id.second_player_leg_grab);
					}
					newIcon.setBackgroundResource(action.getImageResource());
					player2ActionIcons.add(newIcon);
					player2Layout.addView(newIcon);
				}
		}
		playerCount = 2;
		}
	}

	
	
	/**
	 * Присваивает layout'ы определенным переменным
	 */
	private void assignViews() {
		player1Layout = (LinearLayout) findViewById(R.id.player1Layout);
		player2Layout = (LinearLayout) findViewById(R.id.player2Layout);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fight, menu);
		return true;
	}
	
	/**
	 * Класс создан для эмуляции боя (расчета попадений, урона и т. п.)
	 * На вход принимает действие типа FightAction
	 * @author XAM
	 *
	 */
	public class DamageCalculatorTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(Void...params) {

				try {
					TimeUnit.SECONDS.sleep(1);
					publishProgress();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void...voids){
			spendOneAP();
		}
		
		/**
		 * Функция эмулирует использование одного очка действия
		 * @param newAction - действие типа FightAction
		 */
		private void spendOneAP() {
				for (FightAction action : player1ActionsList){
					shift_AP_icon_indices(action);
					Log.d("myLogs", "First Player AP_icon_indices after shift " + action.get_AP_icon_indices());
				}
				for (FightAction action : player2ActionsList){
					shift_AP_icon_indices(action);
					Log.d("myLogs", "Second Player AP_icon_indices after shift " + action.get_AP_icon_indices());
				}
		}

		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			actionResult();
			if (!winLoseCheck()){
				newTask = new DamageCalculatorTask();
				newTask.execute();
			}
		}
		
		/**
		 * Функция проверяет, победил ли кто-то после последнего действия
		 */
		private boolean winLoseCheck() {
			if (SubZero.getHP() <= 0 && playerFighter.getHP() <= 0){
				Toast.makeText(Fight.this, "DRAW!", Toast.LENGTH_SHORT).show();
				return true;
			}
			else if (SubZero.getHP() <= 0){
				Toast.makeText(Fight.this, playerFighter.getName() + " wins!", Toast.LENGTH_SHORT).show();
				return true;
			}
			else if (playerFighter.getHP() <= 0){
				Toast.makeText(Fight.this, "SubZero wins!", Toast.LENGTH_SHORT).show();
				return true;
			}
			return false;
		}

		/**
		 * Функция обрабатывает результат действия FightAction, которое закончилось на последнем использованном очке действий
		 */
		private void actionResult() {
			
			
			if (player1ActionsList.size() > 0){
				FightAction currentAction = player1ActionsList.get(0);
				int currentAPCost = currentAction.get_AP_cost();
				int currentAPSpent;
				currentAction.spendAP();
				currentAPSpent = currentAction.getAP_spent();
				if (currentAPCost <= currentAPSpent){
					Log.d("myLogs", "First Player's action has finished");
					damageCalculator(currentAction, null);
					player1ActionsList.remove(0);
					LinearLayout llActionLine = (LinearLayout)findViewById(R.id.first_player_action_line);
					llActionLine.removeViewAt(0);
				}
				
			}
			if (player2ActionsList.size() > 0){
				FightAction currentAction = player2ActionsList.get(0);
				int currentAPCost = currentAction.get_AP_cost();
				int currentAPSpent;
				currentAction.spendAP();
				currentAPSpent = currentAction.getAP_spent();
				if (currentAPCost <= currentAPSpent){
					Log.d("myLogs", "Second Player's action has finished");
					damageCalculator(null, currentAction);
					player2ActionsList.remove(0);
				}
			}
			Дописать расчет блоков
			/*if (player1ActionsList.size() > 0 && player2ActionsList.size() > 0){
				FightAction firstPlayerCurrentAction = player1ActionsList.get(0);
				FightAction secondPlayerCurrentAction = player2ActionsList.get(0);
				
				int firstPlayerCurrentAPCost = firstPlayerCurrentAction.get_AP_cost();
				int secondPlayerCurrentAPCost = secondPlayerCurrentAction.get_AP_cost();
				
				int firstPlayerCurrentAPSpent;
				int secondPlayerCurrentAPSpent;
				
				firstPlayerCurrentAction.spendAP();
				secondPlayerCurrentAction.spendAP();
				
				firstPlayerCurrentAPSpent = firstPlayerCurrentAction.getAP_spent();
				secondPlayerCurrentAPSpent = secondPlayerCurrentAction.getAP_spent();
				
				
				damageCalculator(firstPlayerCurrentAction, secondPlayerCurrentAction);
			}*/
			
		}

		/**
		 * Функция расчитывает нанесенный урон от последнего действия
		 * @param isFirst - булевый параметр, определяющий, кто завершил действие: true - Player 1, false - Player 2
		 * @param currentAction - параметр типа FightAction, закончевшееся действие
		 */
		private void damageCalculator(FightAction firstPlayerCurrentAction, 
				FightAction secondPlayerCurrentAction) {
			if (firstPlayerCurrentAction.getClass() == Attack.class){			
				Log.d("myLogs", "Player 1 has punched Player 2");
				SubZero.removeHP(((Attack)firstPlayerCurrentAction).get_damage());
				refreshHPBar(FIRST_PLAYER);
			}
			else if (secondPlayerCurrentAction.getClass() == Attack.class){
				Log.d("myLogs", "Player 2 has punched Player 1");
				playerFighter.removeHP(((Attack)secondPlayerCurrentAction).get_damage());
				refreshHPBar(SECOND_PLAYER);
			}
		}

		/**
		 * Функция обновляет показатель здоровья бойца после последнего действия и проверяет, случилась ли победа
		 * @param isFirst - булевый параметр, определяющий, кто завершил действие: true - Player 1, false - Player 2
		 */
		private void refreshHPBar(boolean isFirst) {
			if (isFirst){
				if (SubZero.getHP() > 0){
					pbSecondPlayerHealth.setProgress(SubZero.getHP());
				}
				else {
					pbSecondPlayerHealth.setProgress(0);
				}
			}
			else {
				if (playerFighter.getHP() > 0){
					pbFirstPlayerHealth.setProgress(playerFighter.getHP());
				}
				else {
					pbFirstPlayerHealth.setProgress(0);
				}
			}
		}
		
	}

	public void shift_AP_icon_indices(FightAction action){
		int counter = 0;
		int index = action.get_AP_icon_indices().get(0);
		if (index == 0){
			while (index < action.get_AP_icon_indices().size()){
				action.get_AP_icon_indices().set(index, index - 1);
				index++;
			}
			action.get_AP_icon_indices().remove(0);
		}
		else {
			while (counter < action.get_AP_icon_indices().size()){
				action.get_AP_icon_indices().set(counter, index - 1);
				counter++;
				index++;
			}
		}
	
	}
	
	@Override
	public void onBackPressed() {
	    
	}
	
}
