package com.quest.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
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
		private static int CAMERA_WIDTH = 800;
		private static int CAMERA_HEIGHT = 480;
			
		// ===========================================================
		// Fields
		// ===========================================================
		private Game mGame;
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
		private PhysicsWorld mPhysicsWorld;
		
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(Game pGame){
			this.mGame = pGame;
			
			// TODO Auto-generated method stub
			this.mGame.getEngine().registerUpdateHandler(new FPSLogger());
		}
		
		public void loadMap(String pMap) {

			this.mMapManager = new MapHelper(this.mGame);
			this.mMapManager.loadMap("desert");
			this.attachChild(this.mMapManager.getTMXTiledMap().getTMXLayers().get(0));
		}
		
		public void loadEntities() {

			// Create the Player
			this.mHero = new Player(this.mGame);
			this.mHero.load("Mage.png", 128, 256, 0, 0, 4, 4);

			// Center the Player in the Screen
			this.mHero.getAnimatedSprite().setPosition(0, 0 - (this.mHero.getTiledTextureRegion().getHeight() - 32));
			this.mGame.getSceneManager().getDisplay().doFocusCamera(this.mHero);
			
			// Attach it
			this.attachChild(this.mHero.getAnimatedSprite());
			
			//Enemy
			this.mEnemy = new Mob(this.mGame);
			this.mEnemy.load("Mob.png", 128, 256, 0, 0, 4, 4);
			this.mEnemy.getAnimatedSprite().setPosition(64, 64);
			this.attachChild(this.mEnemy.getAnimatedSprite());
			
			this.mMob2 = new Mob(this.mGame);
			this.mMob2.load("Mob2.png", 128, 256, 0, 0, 4, 4);
			this.mMob2.getAnimatedSprite().setPosition(96, 96);
			this.attachChild(this.mMob2.getAnimatedSprite());
			
			//Timer
			this.mTimers = new Timers(this.mGame, mEnemy, mMob2);
			this.mTimers.createMobMovementTimeHandler();
		}
		
		public void loadHUD() {
			this.mHud = new HUD();
			this.mStatsHud = new StatsHud(this.mGame);
			this.mSpellbarHud = new SpellbarHud(this.mGame, this.mHud);
			this.mControlsHud = new ControlsHud(this.mGame, this.mHero);
			this.mMenuHud = new MenuHud(this.mGame, mHud);
			
			this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.registerTouchArea(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mStatsHud.getTermono());
			this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.attachChild(this.mMenuHud.getMenuEntity());
			
			this.mGame.getSceneManager().getDisplay().getCamera().setHUD(this.mHud);
		}
		
		public void unloadHUD()	{
			this.mHud.detachChild(this.mSpellbarHud);
			this.mHud.detachChild(this.mStatsHud);
			this.mHud.detachChild(this.mControlsHud);
			this.mHud.detachChild(this.mMenuHud);
		}
		
		public void initPhysics() {
			
			this.mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH), false);
			this.registerUpdateHandler(this.mPhysicsWorld);
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

		// ===========================================================
		// Methods
		// ===========================================================
		

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}




		