package com.quest.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLayer;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.quest.display.hud.ControlsHud;
import com.quest.display.hud.MenuHud;
import com.quest.display.hud.SpellbarHud;
import com.quest.display.hud.StatsHud;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.helpers.MapHelper;
import com.quest.timers.Timers;

public class GameScene extends Scene {
		// ===========================================================
		// Constants
		// ===========================================================
			
		// ===========================================================
		// Fields
		// ===========================================================
		private MapHelper mMapManager;
		private Mob mMob2;
		private Player mHero;
		private Mob mEnemy;
		private Timers mTimers;
		private MenuHud mMenuHud;
		private HUD mHud;
		private ControlsHud mControlsHud;
		private SpellbarHud mSpellbarHud;
		private StatsHud mStatsHud;
		
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(){
			
			// TODO Auto-generated method stub
			Game.getInstance().getEngine().registerUpdateHandler(new FPSLogger());

			this.mMapManager = new MapHelper();
			this.mMapManager.loadMap("desert");
			
			// Create the Player
			this.mHero = new Player(256, 196, "Mage.png", 128, 256, 0, 0, 4, 4);
			
			//Enemies
			this.mEnemy = new Mob(196, 256, "Mob.png", 128, 256, 0, 0, 4, 4);
			this.mMob2 = new Mob(288, 256, "Mob2.png", 128, 256, 0, 0, 4, 4);
			
			//Timer
			this.mTimers = new Timers(mEnemy, mMob2);
			this.mTimers.createMobMovementTimeHandler();
			
			this.mHud = new HUD();
			this.mStatsHud = new StatsHud();
			this.mSpellbarHud = new SpellbarHud(this.mHud);
			this.mControlsHud = new ControlsHud(this.mHero);
			this.mMenuHud = new MenuHud(mHud);
			
			this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.registerTouchArea(this.mSpellbarHud.getSpellBar());

			// Map Layers
			for(final TMXLayer tLayer : this.mMapManager.getCurrentMap().getTMXLayers()) {
				this.attachChild(tLayer);
			}
			
			// Players
			this.attachChild(this.mHero);
			this.attachChild(this.mEnemy);
			this.attachChild(this.mMob2);
			
			// HUD
			this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mStatsHud.getTermono());
			this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.attachChild(this.mMenuHud.getMenuEntity());
			
			Game.getSceneManager().getDisplay().getCamera().setHUD(this.mHud);
			Game.getSceneManager().getDisplay().doFocusCamera(this.mHero);
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
		
		public Player getHero() {
			return this.mHero;
		}
		
		/**
		 * @return the mMapManager
		 */
		public MapHelper getMapManager() {
			return mMapManager;
		}

		/**
		 * @param mMapManager the mMapManager to set
		 */
		public void setMapManager(MapHelper pMapManager) {
			this.mMapManager = pMapManager;
		}

		// ===========================================================
		// Methods
		// ===========================================================
		

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}




		