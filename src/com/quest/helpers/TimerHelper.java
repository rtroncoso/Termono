package com.quest.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

import com.quest.timers.Timer;

public class TimerHelper {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private HashMap<Timer, String> mTimersList;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public TimerHelper() {
		
		this.mTimersList = new HashMap<Timer, String>();
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the mTimersList
	 */
	public HashMap<Timer, String> getTimersList() {
		return mTimersList;
	}

	/**
	 * @param mTimersList the mTimersList to set
	 */
	public void setTimersList(HashMap<Timer, String> mTimersList) {
		this.mTimersList = mTimersList;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	
	public void addTimer(Timer timer, String pKey) {
		this.mTimersList.put(timer, pKey);
	}
	
	public void deleteTimer(String pKey) {
		if(this.getTimer(pKey) != null) {
			this.mTimersList.remove(this.getTimer(pKey));
		} else {
			Log.d("Quest!", "TimerHelper: Erase - No Timer matches key");
		}
	}
	
	public Timer getTimer(String pKey) {
		Iterator<Entry<Timer, String>> it = this.mTimersList.entrySet().iterator();
		Log.e("Quest!", pKey);
		while (it.hasNext()) {
			Map.Entry e = it.next();
			if(e.getKey() == pKey) return (Timer) e.getValue();
		}
		Log.e("Quest!", "TimerHelper: Search - No Timer matches key");
		return null;
	}


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
