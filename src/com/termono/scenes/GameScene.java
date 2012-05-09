package com.termono.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.input.touch.TouchEvent;

import com.termono.display.hud.ControlsHud;
import com.termono.display.hud.MenuHud;
import com.termono.display.hud.SpellbarHud;
import com.termono.display.hud.StatsHud;
import com.termono.game.Game;
import com.termono.map.Map;
import com.termono.methods.Timers;
import com.termono.player.Enemy;
import com.termono.player.Player;

public class GameScene extends Scene {
		// ===========================================================
		// Constants
		// ===========================================================
		
		
		// ===========================================================
		// Fields
		// ===========================================================
		private Game mGame;
		private Map mMap;
		private Enemy mMob2;
		private Player mHero;
		private Enemy mEnemy;
		private Timers mTimers;
		private MenuHud mMenuHud;
		private HUD mHud;
		private ControlsHud mControlsHud;
		private SpellbarHud mSpellbarHud;
		private StatsHud mStatsHud;
		
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(Game pGame){
			this.mGame = pGame;

			// TODO Auto-generated method stub
			this.mGame.getEngine().registerUpdateHandler(new FPSLogger());

			/*
			 * LAYER - MAP
			 */
			this.mMap = new Map(this.mGame, "desert");
			this.attachChild(this.mMap.getTMXTiledMap().getTMXLayers().get(0));
			
			/*
			 * LAYER - ENTITIES
			 */
			// Create the Player
			this.mHero = new Player(this.mGame);
			this.mHero.loadTexture("Mage.png", 128, 256, 0, 0, 4, 4);

			// Center the Player in the Screen
			this.mHero.getAnimatedSprite().setPosition(0, 0 - (this.mHero.getTiledTextureRegion().getHeight() - 32));
			this.mGame.getDisplay().doFocusCamera(this.mHero);
			
			// Attach it
			this.attachChild(this.mHero.getAnimatedSprite());
			
			
			
			//Enemy
			
			this.mEnemy = new Enemy(this.mGame);
			this.mEnemy.loadTexture("Mob.png", 128, 256, 0, 0, 4, 4);
			this.mEnemy.getAnimatedSprite().setPosition(64, 64);
			this.attachChild(this.mEnemy.getAnimatedSprite());
			
			this.mMob2 = new Enemy(this.mGame);
			this.mMob2.loadTexture("Mob2.png", 128, 256, 0, 0, 4, 4);
			this.mMob2.getAnimatedSprite().setPosition(96, 96);
			this.attachChild(this.mMob2.getAnimatedSprite());
			
			//Timer
			this.mTimers = new Timers(this.mGame, mEnemy, mMob2);
			this.mTimers.createMobMovementTimeHandler();
			
			
			/*
			 * LAYER - HUDs
			 */
			this.mHud = new HUD();
			this.mStatsHud = new StatsHud(this.mGame, mEnemy);
			this.mSpellbarHud = new SpellbarHud(this.mGame, this.mHud);
			this.mControlsHud = new ControlsHud(this.mGame, this.mHero);
			this.mMenuHud = new MenuHud(this.mGame, mHud);
			
			this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.registerTouchArea(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mStatsHud.getTermono());
			this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.attachChild(this.mMenuHud.getMenuEntity());
			
			this.mGame.getDisplay().getCamera().setHUD(this.mHud);
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
		

		// ===========================================================
		// Methods
		// ===========================================================
		

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}




		