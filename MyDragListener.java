package com.example.boxinggame2;

import java.util.ArrayList;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyDragListener implements OnDragListener {

	@Override
	public boolean onDrag(View view, DragEvent event) {
		int action = event.getAction();
		switch (action){
		case DragEvent.ACTION_DRAG_STARTED:
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			view.setBackgroundResource(R.drawable.drop_line_entered);
			break;
		case DragEvent.ACTION_DROP:
			addActionIconToTray(view, (FightAction)event.getLocalState());
			FightAction dummyAction = createNewFightActionInstance((FightAction)event.getLocalState());
			addActionToActionsList(dummyAction);
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			view.setBackgroundResource(R.drawable.drop_line);
			break;
		case DragEvent.ACTION_DRAG_ENDED:
			view.setBackgroundResource(R.drawable.drop_line);
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * Создает новый объект класса Attack или более общего FightAction в зависимости от полученного действия.
	 * Возвращает данный объект
	 * @param action - объект класса FightAction или подкласса Attack
	 * @return - новый объект
	 */
	private FightAction createNewFightActionInstance(FightAction action) {
		Log.d("myLogs", "Class: " + action.getClass());
		if (action.getClass() == Attack.class){
			Attack dummyPunch = new Attack((Attack)action);
			return dummyPunch;
		}
		else {
			FightAction dummyAction = new FightAction(action);
			return dummyAction;
		}
			
	}

	/**
	 * Добавляет полученное действие action, в массив player1ActionsList
	 * @param action - действие, которое необходимо добавить в массив
	 */
	private void addActionToActionsList(FightAction action) {	
		setActionIndices(action);
		Fight.player1ActionsList.add(action);
		for (int i = 0; i < Fight.player1ActionsList.size(); i++){
			Log.d("myLogs", "Action list: " + Fight.player1ActionsList.get(i).get_AP_icon_indices() + Fight.player1ActionsList.get(i));
		}
	}

	/**
	 * Добавляет иконку действия в строку действий
	 * @param view
	 * @param attack
	 */
	private void addActionIconToTray(View view, FightAction action) {
		LinearLayout container = (LinearLayout) view;
		ImageView ivPunch = new ImageView(container.getContext());
		ivPunch.setImageResource(action.getImageResource());
		ivPunch.setLayoutParams(view.getLayoutParams()); 
		container.addView(ivPunch);
		
	}

	/**
	 * Добавляет действию FightAction индексы по которым оно находится в переданной строке 
	 * @param actions - строка действий, по которой определяются индексы данного действия (действие в строку не
	 * добавляется)
	 */
	public void setActionIndices(FightAction action) {
		FightAction lastAction = getLastActionInList(Fight.player1ActionsList);
		Log.d("myLog", "Last action: " + lastAction);
		int lastIndex = getLastIndexOfLastAction(lastAction);
		Log.d("myLog", "Last index: " + lastIndex);
		int nextToLastIndex = lastIndex + 1;
		for (int i = nextToLastIndex; i < nextToLastIndex + action.get_AP_cost(); i++){
			action.addFightActionIndex(i);
		}

		Log.d("myLog", "Action: " + action + " indices: " + action.get_AP_icon_indices());
	}
	
	/**
	 * Возвращает последний индекс типа int переданного действия FightAction
	 * @param lastAction - действие, для которого проводится поиск последнего занимаемого индекса
	 * @return - последний занимаемый индекс
	 */
	private int getLastIndexOfLastAction(FightAction lastAction) {
		int lastIndex = -1;
		if (lastAction.get_AP_icon_indices().size() > 0){
			lastIndex = lastAction.get_AP_icon_indices().get(lastAction.get_AP_icon_indices().size() - 1);
		}
		return lastIndex;
	}


	/**
	 * Возвращает последний FightAction из переданного массива
	 * @param actions - массив FightAction, в котором проводится поиск последнего элемента
	 * @return - последний элемент массива
	 */
	private FightAction getLastActionInList(ArrayList<FightAction> actions) {
		FightAction lastAction = new FightAction(0, "", 0);
		if (actions.size() > 0){
			lastAction = actions.get(actions.size()-1);	
		}
		return lastAction;
	}
}
