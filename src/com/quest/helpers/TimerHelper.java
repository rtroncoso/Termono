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
	private HashMap<String, Timer> mTimersList;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public TimerHelper() {
		
		this.mTimersList = new HashMap<String, Timer>();
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
	public HashMap<String, Timer> getTimersList() {
		return mTimersList;
	}

	/**
	 * @param mTimersList the mTimersList to set
	 */
	public void setTimersList(HashMap<String, Timer> mTimersList) {
		this.mTimersList = mTimersList;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	
	public void addTimer(Timer timer, String pKey) {
		this.mTimersList.put(pKey, timer);
	}
	
	public void deleteTimer(String pKey) {
		final Timer tmpTimer = this.getTimer(pKey);
		if(tmpTimer != null) {
			this.mTimersList.remove(pKey);
			tmpTimer.unregisterTimer();
		} else {
			Log.d("Quest!", "TimerHelper: Erase - No Timer matches key");
		}
	}
	
	public Timer getTimer(String pKey) {
		Iterator<Entry<String, Timer>> it = this.mTimersList.entrySet().iterator();
		Log.e("Quest!", pKey);
		while (it.hasNext()) {
			Map.Entry e = it.next();
			if(e.getKey().equals(pKey)) return (Timer) e.getValue();
		}
		Log.e("Quest!", "TimerHelper: Search - No Timer matches key");
		return null;
	}


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
