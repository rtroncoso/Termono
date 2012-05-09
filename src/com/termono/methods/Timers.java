package com.termono.methods;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.termono.game.Game;
import com.termono.player.Mob;



public class Timers {
		// ===========================================================
		// Constants
		// ===========================================================
		
		
		// ===========================================================
		// Fields
		// ===========================================================
		private Game mGame;
		private Mob mEnemy;
		private Mob mMob2;
		// ===========================================================
		// Constructors
		// ===========================================================
	
		public Timers(Game pGame, Mob pEnemy, Mob pMob2) {
			this.mGame = pGame;
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
				this.mGame.getEngine().registerUpdateHandler(new TimerHandler(3,true, new ITimerCallback()
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
