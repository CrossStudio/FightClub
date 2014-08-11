package com.example.boxinggame2;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Fighter{
	
	private boolean isFirst;
	
	private String name;
	
	private int hitpoints;
	
	private ArrayList<FightAction> fighterActions;
	
	public Fighter (String name, int hitpoints, boolean first) {
		this.name = name;
		this.hitpoints = hitpoints;
		isFirst = first;
		fighterActions = new ArrayList<FightAction>();
	}
	
	public boolean getIsFirst(){
		return isFirst;
	}

	public void addAction(FightAction newAction){
		fighterActions.add(newAction);
	}
	
	public ArrayList<FightAction> getActions(){
		return fighterActions;			
	}
	
	public void removeAttack(FightAction action){
		fighterActions.remove(action);
	}
	
	public void removeAllActions(){
		fighterActions.clear();
	}
	
	public String getName(){
		return name;
	}
	
	public int getHP(){
		return hitpoints;
	}
	
	public void removeHP(int damage){
		hitpoints -= damage;
	}
	
}
