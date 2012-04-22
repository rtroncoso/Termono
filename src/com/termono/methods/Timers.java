package com.termono.methods;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.termono.game.Game;
import com.termono.player.Enemy;



public class Timers {
		// ===========================================================
		// Constants
		// ===========================================================
		
		
		// ===========================================================
		// Fields
		// ===========================================================
		private Game mGame;
		private Enemy mEnemy;
		
		// ===========================================================
		// Constructors
		// ===========================================================
	
		public Timers(Game pGame, Enemy pEnemy) {
			this.mGame = pGame;
			this.mEnemy = pEnemy;					
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
		        TimerHandler MobMovementTimerHandler;
		        
		        this.mGame.getEngine().registerUpdateHandler(MobMovementTimerHandler = new TimerHandler(100,true, new ITimerCallback()
		        {                      
		            
		        	@Override
		            public void onTimePassed(final TimerHandler pTimerHandler)
		            {
		        		Timers.this.mEnemy.RandomPath();
		        		
		            }
		            
		            
		        }));
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
		
	
	
}
