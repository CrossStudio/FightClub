package com.example.boxinggame2;

import java.util.ArrayList;

import android.util.Log;

public class FightAction implements Cloneable{
	
	// "Стоимость" действия в очках действия (Action Points = AP)
	private int AP_cost;
	
	// Наименование действия
	private String title;
	
	// Идентификатор ресурса с картинкой для данного действия
	private int imageResource;
	
	private int actionID;
	
	// Индексы очков действия, которые занимает данное действие
	protected ArrayList<Integer>AP_icon_indices = new ArrayList<Integer>();
	
	// Использовано очков действия на данное действие (когда AP_spent == AP_cost действие завершается)
	private int AP_spent = 0;
	
	public FightAction (int ap_cost, String title, int imageResource){
		AP_cost = ap_cost;
		this.title = title;
		this.imageResource = imageResource;
		assignID();
	}
	
	private void assignID() {
		if (this.get_title().equals("Quick Punch")){
			this.actionID = R.id.first_player_quick_punch;				
		}
		else if (this.get_title().equals("Hard Punch")){
			this.actionID = R.id.first_player_hard_punch;			
		}
		else if (this.get_title().equals("Mega Punch")){
			this.actionID = R.id.first_player_mega_punch;
		}
		else if (this.get_title().equals("Fire Kick")){
			this.actionID = R.id.first_player_fire_kick;
		}
		else if (this.get_title().equals("Freeze")){
			this.actionID = R.id.first_player_freeze;
		}
		else if (this.get_title().equals("Round Kick")){
			this.actionID = R.id.first_player_round_kick;
		}
		else if (this.get_title().equals("Leg Grab")){
			this.actionID = R.id.first_player_leg_grab;
		}
		
	}

	public int getActionID(){
		return actionID;
	}
	
	public FightAction(FightAction originalAction){
		this.AP_cost = originalAction.AP_cost;
		this.title = originalAction.title;
		this.imageResource = originalAction.imageResource;
	}
	
	/**
	 * Добавляет полученное целочисленное значение в строку индексов данного действия
	 * @param index - целочисленное значение индекса для добавления в строку
	 */
	public void addFightActionIndex(int index){
		AP_icon_indices.add(index);
	}


	/**
	 * Получить стоимость данного действия в очках действия
	 * @return
	 */
	public int get_AP_cost(){
		return AP_cost;
	}
	
	public String get_title(){
		return title;
	}
	
	public void set_title(String title){
		this.title = title;
	}
	
	/**
	 * Получить количество использованных очков действия на данное действие
	 * @return
	 */
	public int getAP_spent(){
		return AP_spent;
	}
	
	public void setAP_cost(int ap_cost){
		this.AP_cost = ap_cost;
	}
	
	public ArrayList<Integer> get_AP_icon_indices(){
		return AP_icon_indices;
	}
	
	public void spendAP(){
		AP_spent++;
	}

	public int getImageResource(){
		return imageResource;
	}
	
	public FightAction getFightAction(){
		return this;
	}
}
