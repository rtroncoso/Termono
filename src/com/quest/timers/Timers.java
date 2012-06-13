package com.quest.timers;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.quest.entities.Mob;
import com.quest.game.Game;

// TODO Mover a una clase principal que los demás timers hereden de esto
// 		usando un updatehandler

public class Timers {
		// ===========================================================
		// Constants
		// ===========================================================
		
		
		// ===========================================================
		// Fields
		// ===========================================================
		private Mob mEnemy;
		private Mob mMob2;
		// ===========================================================
		// Constructors
		// ===========================================================
	
		public Timers(Mob pEnemy, Mob pMob2) {
			this.mEnemy = pEnemy;		
			this.mMob2 = pMob2;
			// TODO Auto-generated constructor stub
		}
		
		
	
	
		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================
		public void createMobMovementTimeHandler()
		{
		        //TimerHandler MobMovementTimerHandler;
		        
		        //this.mGame.getEngine().registerUpdateHandler(MobMovementTimerHandler = new TimerHandler(1 / 20.0f,true, new ITimerCallback()
				//this.mGame.getEngine().registerUpdateHandler(new TimerHandler(1 / 20.0f,true, new ITimerCallback()
				Game.getInstance().getEngine().registerUpdateHandler(new TimerHandler(3,true, new ITimerCallback()
		        {                      
		            
		        	@Override
		            public void onTimePassed(final TimerHandler pTimerHandler)
		            {
		        		Timers.this.mEnemy.randomPath();
		        		Timers.this.mMob2.randomPath();
		            }
		            
		            
		        }));
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
		
	
	
}
