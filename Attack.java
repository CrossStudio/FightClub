package com.example.boxinggame2;


public class Attack extends FightAction {
	
	/**
	 * Special moves
	 */
	static Attack SCORPION_FIRE_KICK = new Attack(3, "Fire Kick", R.drawable.fire_kick, 4);
	static Attack SUB_ZERO_FREEZE = new Attack(2, "Freeze", R.drawable.freeze, 0);
	static Attack KUNG_LAO_ROUND_KICK = new Attack (4, "Round Kick", R.drawable.round_kick, 5);
	static Attack SONYA_LEG_GRAB = new Attack(2, "Leg Grab", R.drawable.leg_grab, 3);
	
	static Attack QUICK_PUNCH = new Attack(3, "Quick Punch", R.drawable.quick_punch, 3);
	static Attack HARD_PUNCH = new Attack(4, "Hard Punch", R.drawable.hard_punch, 4);
	static Attack MEGA_PUNCH = new Attack(5, "Mega Punch", R.drawable.mega_punch, 5);
	
	private int damage;
	
	public Attack(int ap_cost, String title, int imageResource, int damage) {
		super(ap_cost, title, imageResource);
		this.damage = damage;
	}
	
	public Attack (Attack originalAttack){
		super(originalAttack);
		this.damage = originalAttack.damage;
	}

	public int get_damage(){
		return damage;
	}
	
	public void set_damage(int damage){
		this.damage = damage;
	}
	
}
