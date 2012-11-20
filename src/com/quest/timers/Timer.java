package com.quest.timers;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.quest.entities.Mob;
import com.quest.game.Game;
import com.quest.timers.interfaces.ITimerAction;

// TODO Mover a una clase principal que los demás timers hereden de esto
// 		usando un updatehandler

public class Timer {
		// ===========================================================
		// Constants
		// ===========================================================
		
		
		// ===========================================================
		// Fields
		// ===========================================================
		private ITimerCallback mTimerCallback;
		private TimerHandler mTimerHandler;
	
		// ===========================================================
		// Constructors
		// ===========================================================
	
		public Timer(float pTimePerTick, ITimerCallback iTimerCallback) {
			
			this.mTimerHandler = new TimerHandler(pTimePerTick, true, iTimerCallback);
			Game.getInstance().getEngine().registerUpdateHandler(this.mTimerHandler);
		}
	
	
		// ===========================================================
		// Getter & Setter
		// ===========================================================
		/**
		 * @return the mTimerCallback
		 */
		public ITimerCallback getTimerCallback() {
			return mTimerCallback;
		}


		/**
		 * @param mTimerCallback the mTimerCallback to set
		 */
		public void setTimerCallback(ITimerCallback mTimerCallback) {
			this.mTimerCallback = mTimerCallback;
		}

		
		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public void unregisterTimer() {
			Game.getInstance().getEngine().unregisterUpdateHandler(this.mTimerHandler);
		}
		
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
		
	
	
}
