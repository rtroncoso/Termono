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
import com.quest.helpers.AsyncTaskLoader;
import com.quest.helpers.interfaces.IAsyncCallback;
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
		
		
		private int tempInt=-1;
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(){
			
			// TODO Auto-generated method stub
			Game.getInstance().getEngine().registerUpdateHandler(new FPSLogger());
			this.mMapLayer = new Entity();
		}
		
		
		public void initGame(final String pMapName) {
			
			// Loads everything in the background
			Game.getSceneManager().setLoadingScene();
			//Looper.myLooper().prepare();//tira ctrl shift o

			Game.getInstance().runOnUiThread(new Runnable() {
		        @Override
		        public void run() {
			        new AsyncTaskLoader().execute(new IAsyncCallback() {

			            @Override
			            public void workToDo() {
			            	// Load the Map and Attach it
			    			Game.getMapManager().loadMap(pMapName);
			    			GameScene.this.attachChild(GameScene.this.mMapLayer);
			    			
			    			// Create the Player and insert in helper
			    			//Game.getPlayerHelper().addPlayer(new Player(20, 20, "Mage.png", 128, 256, 0, 0, 4, 4), "Player");
			    			//Game.getPlayerHelper().addPlayer(new Player(20, 20, "Players/Animations/Paladin.png", 128, 256, 0, 0, 4, 4), "Player");
			    			Game.getPlayerHelper().getPlayerbyIndex(0).setTileAt(20, 20);
			    			//Enemies
			    		//	GameScene.this.mEnemy = new Mob("Players/Animations/Paladin.png", 256, 256, 0, 0, 5, 4);
			    		//	GameScene.this.mMob2 = new Mob("Players/Animations/Archer.png", 256, 256, 0, 0, 5, 4);
			    		//	GameScene.this.mEnemy.setTileAt(15, 15);
			    		//	GameScene.this.mMob2.setTileAt(25, 15);
			    			
			    			//Timer
			    		//	GameScene.this.mTimers = new Timers(mEnemy, mMob2);
			    	//		GameScene.this.mTimers.createMobMovementTimeHandler();
			    			
			    			GameScene.this.mHud = new HUD();
			    			GameScene.this.mStatsHud = new StatsHud();
			    			GameScene.this.mSpellbarHud = new SpellbarHud(GameScene.this.mHud);
			    			GameScene.this.mControlsHud = new ControlsHud((Player) Game.getPlayerHelper().getPlayer(Game.getPlayerHelper().getPlayerbyIndex(0).getUserID()));
			    			GameScene.this.mMenuHud = new MenuHud(mHud);
			    			
			    			GameScene.this.mHud.setChildScene(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpellBar());
			    			
			    			// Players
			    			GameScene.this.attachChild(Game.getPlayerHelper().getPlayer(Game.getPlayerHelper().getPlayerbyIndex(0).getUserID()));
			    	//		GameScene.this.attachChild(GameScene.this.mEnemy);
			    	//		GameScene.this.attachChild(GameScene.this.mMob2);
			    			
			    			// HUD 
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpellBar());
			    			GameScene.this.mHud.attachChild(GameScene.this.mStatsHud.getTermono());
			    			GameScene.this.mHud.attachChild(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.attachChild(GameScene.this.mMenuHud.getMenuSprite());
			    			
			    			Game.getSceneManager().getDisplay().getCamera().setHUD(GameScene.this.mHud);
			    			Game.getSceneManager().getDisplay().doFocusCamera(Game.getPlayerHelper().getPlayer(Game.getPlayerHelper().getPlayerbyIndex(0).getUserID()));

			    	//		GameScene.this.registerTouchArea(GameScene.this.mEnemy.getBodySprite());
			    	//		GameScene.this.registerTouchArea(GameScene.this.mMob2.getBodySprite());
			    			
			    			GameScene.this.setTouchAreaBindingOnActionDownEnabled(true);
			            }

			            @Override
			            public void onComplete() {
			            	Game.getSceneManager().setSpecificGameScene(GameScene.this);
			            }
			        });
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
		public void CreateMob(int MOB_FLAG){
			Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG);
			tmpMob.setTileAt(Game.getPlayerHelper().getPlayerbyIndex(0).getTMXTileAt().getTileColumn(),Game.getPlayerHelper().getPlayerbyIndex(0).getTMXTileAt().getTileRow());
			GameScene.this.attachChild(tmpMob);
			GameScene.this.registerTouchArea(tmpMob.getBodySprite());
			tempInt+=1;
		}

		public void CreateMob(int MOB_FLAG,int i){
			Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG);
			tmpMob.setTileAt(Game.getPlayerHelper().getPlayerbyIndex(0).getTMXTileAt().getTileColumn()+i,Game.getPlayerHelper().getPlayerbyIndex(0).getTMXTileAt().getTileRow());
			GameScene.this.attachChild(tmpMob);
			GameScene.this.registerTouchArea(tmpMob.getBodySprite());
			tempInt+=1;
		}
		
		public void DeleteMob(int pMobKey){
			Game.getMobHelper().deleteMob(tempInt);
			tempInt-=1;
		}
		
		public void DeleteMobs(int pMobKey){
			Game.getMobHelper().deleteMobs(tempInt);
			tempInt-=1;
		}
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
}




		