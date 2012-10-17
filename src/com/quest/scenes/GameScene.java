package com.quest.scenes;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;

import com.quest.constants.MobFlags;
import com.quest.display.hud.ControlsHud;
import com.quest.display.hud.MenuHud;
import com.quest.display.hud.SpellbarHud;
import com.quest.display.hud.StatsHud;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.entities.objects.Spell;
import com.quest.game.Game;
import com.quest.helpers.AsyncTaskLoader;
import com.quest.helpers.interfaces.IAsyncCallback;
import com.quest.timers.Timer;

public class GameScene extends Scene implements MobFlags{
		// ===========================================================
		// Constants
		// ===========================================================
			
		// ===========================================================
		// Fields
		// ===========================================================
		private MenuHud mMenuHud;
		private HUD mHud;
		private ControlsHud mControlsHud;
		private SpellbarHud mSpellbarHud;
		private StatsHud mStatsHud;
		private Entity mMapLayer;
		private Rectangle hpbar;
		
		
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
			    			for(int i = 0;i<Game.getPlayerHelper().getEntities().size();i++){
			    				Game.getPlayerHelper().getPlayerbyIndex(i).setTileAt(20+(1*i), 20);	
			    			}
			    			
			    			if(Game.isServer()){
				    			Game.getTimerHelper().addTimer(new Timer(3, new ITimerCallback() {			
				    				@Override
				    				public void onTimePassed(TimerHandler pTimerHandler) {
				    					// TODO Auto-generated method stub
				    					if(Game.getMobHelper().getMobs().size()<40){
				    						if(Game.getMobHelper().getMobs().size()%2==0){
				    							Game.getSceneManager().getGameScene().CreateMob(FLAG_MOB_BAT,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn()+getRandom(-10, 10),Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow()+getRandom(-10, 10),1);
				    						}else{
				    							Game.getSceneManager().getGameScene().CreateMob(FLAG_MOB_BEE,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn()+getRandom(-10, 10),Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow()+getRandom(-10, 10),1);
				    						}
				    					}else{
				    						Game.getMobHelper().deleteMobs(FLAG_MOB_BAT);
				    						Game.getMobHelper().deleteMobs(FLAG_MOB_BEE);
				    					}
				    				}
				    			}), "MobSpawner");
			    			}


			    			//***sacar
			    			 hpbar = new Rectangle(190, 40, 290, 45, Game.getInstance().getVertexBufferObjectManager());
			    			 hpbar.setColor(1,0,0);
			    			 setHPbar(-1);
			    			 
			    			
			    			GameScene.this.mHud = new HUD();
			    			GameScene.this.mStatsHud = new StatsHud();
			    			GameScene.this.mSpellbarHud = new SpellbarHud(GameScene.this.mHud);
			    			GameScene.this.mControlsHud = new ControlsHud((Player) Game.getPlayerHelper().getOwnPlayer());
			    			GameScene.this.mMenuHud = new MenuHud(mHud);
			    			
			    			GameScene.this.mHud.setChildScene(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpellBar());
			    			
			    			// Players
			    			for(int i = 0;i<Game.getPlayerHelper().getEntities().size();i++){
			    				GameScene.this.attachChild(Game.getPlayerHelper().getPlayerbyIndex(i));	
			    			}
			    			

			    			
			    			// HUD 
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpellBar());
			    			GameScene.this.mHud.attachChild(GameScene.this.mStatsHud.getTermono());
			    			GameScene.this.mHud.attachChild(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.attachChild(GameScene.this.mMenuHud.getMenuSprite());
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(1));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(2));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(3));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(1));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(2));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(3));
			    			GameScene.this.mHud.attachChild(hpbar);
			    			
			    			Game.getSceneManager().getDisplay().getCamera().setHUD(GameScene.this.mHud);
			    			Game.getSceneManager().getDisplay().doFocusCamera(Game.getPlayerHelper().getOwnPlayer());

			    	
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
		
		//sacar
		private int getRandom(int min, int max)	{
			Random rand = new Random();	
			int RandomNum = rand.nextInt(max - min + 1) + min;
			return RandomNum;
		}
		
		public void setHPbar(int width){
			if(width<1){
				this.hpbar.setVisible(false);
			}else{
				this.hpbar.setVisible(true);
				this.hpbar.setWidth(width*2);
			}
		}
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
		public void CreateMob(int MOB_FLAG,int tileX,int tileY,int map){
			if(Game.isServer())Game.getServer().sendSpawnMobMessage(MOB_FLAG, tileX, tileY, map);
			Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG);
			tmpMob.setTileAt(tileX,tileY);
			GameScene.this.attachChild(tmpMob);
			GameScene.this.registerTouchArea(tmpMob.getBodySprite());
			tempInt+=1;
		}

		public void CreateMob(int MOB_FLAG,int i){
			Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG);
			tmpMob.setTileAt(Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn()+i,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow());
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




		
