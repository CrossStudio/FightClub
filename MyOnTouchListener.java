package com.example.boxinggame2;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class MyOnTouchListener implements OnTouchListener{
	
	private FightAction action;
	
	public MyOnTouchListener(FightAction currentAction) {
		this.action = currentAction;
	}

	public boolean onTouch(View view, MotionEvent motionEvent) {
		// TODO Auto-generated method stub
		ClipData data = ClipData.newPlainText("", "");
	    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
	    view.startDrag(data, shadowBuilder, action, 0);
		return false;
	}
}
