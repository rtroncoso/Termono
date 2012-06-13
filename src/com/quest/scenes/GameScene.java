package com.quest.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;

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
import com.quest.entities.BaseEntity;
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
		private PhysicsWorld mPhysicsWorld;
		
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(){
			
			// TODO Auto-generated method stub
			Game.getInstance().getEngine().registerUpdateHandler(new FPSLogger());
		}
		
		public void loadMap(String pMap) {

			this.mMapManager = new MapHelper();
			this.mMapManager.loadMap("desert");
			this.attachChild(this.mMapManager.getTMXTiledMap().getTMXLayers().get(0));
		}
		
		public void loadEntities() {

			// Create the Player
			this.mHero = new Player();
			this.mHero.load("Mage.png", 128, 256, 0, 0, 4, 4);
			this.mHero.setPosition(0, 0 - (this.mHero.getTiledTextureRegion().getHeight() - 32));
			Game.getSceneManager().getDisplay().doFocusCamera(this.mHero);
			this.attachChild(this.mHero);
			
			//Enemies
			this.mEnemy = new Mob();
			this.mEnemy.load("Mob.png", 128, 256, 0, 0, 4, 4);
			this.mEnemy.setPosition(64, 64);
			this.attachChild(this.mEnemy);
			
			this.mMob2 = new Mob();
			this.mMob2.load("Mob2.png", 128, 256, 0, 0, 4, 4);
			this.mMob2.setPosition(96, 96);
			this.attachChild(this.mMob2);
			
			//Timer
			this.mTimers = new Timers(mEnemy, mMob2);
			this.mTimers.createMobMovementTimeHandler();
		}
		
		
		
		public void loadHUD() {
			this.mHud = new HUD();
			this.mStatsHud = new StatsHud();
			this.mSpellbarHud = new SpellbarHud(this.mHud);
			this.mControlsHud = new ControlsHud(this.mHero);
			this.mMenuHud = new MenuHud(mHud);
			
			this.mHud.setChildScene(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.registerTouchArea(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mSpellbarHud.getSpellBar());
			this.mHud.attachChild(this.mStatsHud.getTermono());
			this.mHud.attachChild(this.mControlsHud.getDigitalOnScreenControl());
			this.mHud.attachChild(this.mMenuHud.getMenuEntity());
			
			Game.getSceneManager().getDisplay().getCamera().setHUD(this.mHud);
		}
		
	        
		public void unloadHUD()	{
			this.mHud.detachChild(this.mSpellbarHud);
			this.mHud.detachChild(this.mStatsHud);
			this.mHud.detachChild(this.mControlsHud);
			this.mHud.detachChild(this.mMenuHud);
		}
		
		public void initPhysics() {
			
			this.mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false);
			this.registerUpdateHandler(this.mPhysicsWorld);
			
			this.mPhysicsWorld.setContactListener(new ContactListener() {

				@Override
				public void beginContact(Contact contact) {
					// TODO Auto-generated method stub
					
	                final Fixture x1 = contact.getFixtureA();
	                final Fixture x2 = contact.getFixtureB();
	                
	                Log.d("CONTACT", "Contact between " + x1.getUserData() + " and " + x2.getUserData());
				}

				@Override
				public void endContact(Contact contact) {
					// TODO Auto-generated method stub

	                final Fixture x1 = contact.getFixtureA();
	                final Fixture x2 = contact.getFixtureB();
				}

				@Override
				public void preSolve(Contact contact, Manifold oldManifold) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void postSolve(Contact contact, ContactImpulse impulse) {
					// TODO Auto-generated method stub
					
				}
				
				
			});
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
		
		public PhysicsWorld getPhysicsWorld() {
			return this.mPhysicsWorld;
		}

		// ===========================================================
		// Methods
		// ===========================================================
		

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}




		