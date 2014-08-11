package com.example.boxinggame2;

public class Defence extends FightAction {

	private int damageBlocked;
	
	static Defence BASIC_BLOCK = new Defence(1, "Basic Block", R.drawable.basic_block, 2);
	
	public Defence(int ap_cost, String title, int imageResource, int damageBlocked){
		super(ap_cost, title, imageResource);
		this.damageBlocked = damageBlocked;
	}
	
	public Defence(Defence originalDefence) {
		super(originalDefence);
		this.damageBlocked = originalDefence.damageBlocked;
	}
	
	public void setDamagaBlocked(int damageBlocked){
		this.damageBlocked = damageBlocked;
	}
	
	public int getDamageBlocked(){
		return this.damageBlocked;
	}

}
