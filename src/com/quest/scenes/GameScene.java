package com.quest.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;

import com.quest.display.hud.ControlsHud;
import com.quest.display.hud.MenuHud;
import com.quest.display.hud.SpellbarHud;
import com.quest.display.hud.StatsHud;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.timers.Timers;

public class GameScene extends Scene {
		// ===========================================================
		// Constants
		// ===========================================================
			
		// ===========================================================
		// Fields
		// ===========================================================
		private Mob mMob2;
		private Mob mEnemy;
		private Timers mTimers;
		private MenuHud mMenuHud;
		private HUD mHud;
		private ControlsHud mControlsHud;
		private SpellbarHud mSpellbarHud;
		private StatsHud mStatsHud;
		private Entity mMapLayer;
		
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(){
			
			// TODO Auto-generated method stub
			Game.getInstance().getEngine().registerUpdateHandler(new FPSLogger());
			this.mMapLayer = new Entity();
		}
		
		
		public void initGame(String pMapName) {
			
			// Load the Map and Attach it
			Game.getMapManager().loadMap(pMapName);
			this.attachChild(this.mMapLayer);
			
			// Create the Player and insert in helper
			Game.getPlayerHelper().addPlayer(new Player(20, 20, "Mage.png", 128, 256, 0, 0, 4, 4), "Player");
			
			//Enemies
			this.mEnemy = new Mob(15, 15, "Mob.png", 128, 256, 0, 0, 4, 4);
			this.mMob2 = new Mob(25, 15, "Mob2.png", 128, 256, 0, 0, 4, 4);
			
			//Timer
			this.mTimers = new Timers(mEnemy, mMob2);
			this.mTimers.createMobMovementTimeHandler();
			
			this.mHud = new HUD();
			this.mStatsHud = new StatsHud();
			this.mSpellbarHud = new SpellbarHud(this.mHud);
			this.mControlsHud = new ControlsHud((Player) Game.getPlayerHelper().getPlayer("Player"));
			this.mMenuHud = new MenuHud(mHud);
			
			this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.registerTouchArea(this.mSpellbarHud.getSpellBar());
			
			// Players
			this.attachChild(Game.getPlayerHelper().getPlayer("Player"));
			this.attachChild(this.mEnemy);
			this.attachChild(this.mMob2);
			
			// HUD 
			this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mStatsHud.getTermono());
			this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.attachChild(this.mMenuHud.getMenuSprite());
			
			Game.getSceneManager().getDisplay().getCamera().setHUD(this.mHud);
			Game.getSceneManager().getDisplay().doFocusCamera(Game.getPlayerHelper().getPlayer("Player"));

			this.registerTouchArea(this.mEnemy.getBodySprite());
			this.registerTouchArea(this.mMob2.getBodySprite());
		}
		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================
		

		// ===========================================================
		// Getter & Setter
		// ===========================================================
		public ControlsHud getControlsHud() {
			return mControlsHud;
		}

		public void setControlsHud(ControlsHud pControlsHud) {
			this.mControlsHud = pControlsHud;
		}

		public StatsHud getStatsHud() {
			return mStatsHud;
		}

		public void setStatsHud(StatsHud pStatsHud) {
			this.mStatsHud = pStatsHud;
		}

		/**
		 * @return the mMapLayer
		 */
		public Entity getMapLayer() {
			return mMapLayer;
		}


		/**
		 * @param mMapLayer the mMapLayer to set
		 */
		public void setMapLayer(Entity mMapLayer) {
			this.mMapLayer = mMapLayer;
		}


		public void unloadHUD(){
			Game.getSceneManager().getDisplay().getCamera().setHUD(null);
			this.mHud.clearTouchAreas();			
		}
		// ===========================================================
		// Methods
		// ===========================================================
		

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}




		