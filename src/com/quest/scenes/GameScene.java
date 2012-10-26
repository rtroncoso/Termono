package com.quest.scenes;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.display.hud.ControlsHud;
import com.quest.display.hud.MenuHud;
import com.quest.display.hud.SpellbarHud;
import com.quest.display.hud.StatsHud;
import com.quest.entities.BaseEntity;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.entities.objects.Attack;
import com.quest.game.Game;
import com.quest.helpers.AsyncTaskLoader;
import com.quest.helpers.interfaces.IAsyncCallback;
import com.quest.timers.Timer;

public class GameScene extends Scene implements GameFlags,IOnSceneTouchListener{
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
		protected ArrayList<Attack> mAttackLayer;
		
		private int tempInt=-1;
		// ===========================================================
		// Constructors
		// ===========================================================
		public GameScene(){
			
			// TODO Auto-generated method stub
			Game.getInstance().getEngine().registerUpdateHandler(new FPSLogger());
			this.mMapLayer = new Entity();
			this.mAttackLayer = new ArrayList<Attack>();
		}
		
		
		public void initGame(final String pMapName) {
			
			// Loads everything in the background
			Game.getSceneManager().setLoadingScene();
			
			Game.getInstance().runOnUiThread(new Runnable() {
		        @Override
		        public void run() {
			        new AsyncTaskLoader().execute(new IAsyncCallback() {

			            @Override
			            public void workToDo() {
			            	GameScene.this.setTouchAreaBindingOnActionDownEnabled(true);
			            	GameScene.this.setOnSceneTouchListener(GameScene.this);
			            	// Load the Map and Attach it
			    			Game.getMapManager().loadMap(pMapName);
			    			GameScene.this.attachChild(GameScene.this.mMapLayer);
			    			

			    			if(Game.isServer()){
				    			Game.getTimerHelper().addTimer(new Timer(3, new ITimerCallback() {			
				    				@Override
				    				public void onTimePassed(TimerHandler pTimerHandler) {
				    					// TODO Auto-generated method stub
				    					if(Game.getMobHelper().getMobs().size()<5){
				    						if(Game.getMobHelper().getMobs().size()%2==0){
				    							Game.getSceneManager().getGameScene().CreateMob(FLAG_MOB_BAT,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn()+getRandom(-10, 10),Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow()+getRandom(-10, 10),1);
				    						}else{
				    							Game.getSceneManager().getGameScene().CreateMob(FLAG_MOB_BEE,Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileColumn()+getRandom(-10, 10),Game.getPlayerHelper().getOwnPlayer().getTMXTileAt().getTileRow()+getRandom(-10, 10),1);
				    						}
				    					}else{
				    						//Game.getMobHelper().deleteMobs(FLAG_MOB_BAT);
				    						//Game.getMobHelper().deleteMobs(FLAG_MOB_BEE);
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
			    				//si esta en mi mapa
			    				if(Game.getPlayerHelper().getPlayerbyIndex(i).getCurrentMap() == Game.getPlayerHelper().getOwnPlayer().getCurrentMap()){
			    					Player tmpPlayer = Game.getPlayerHelper().getPlayerbyIndex(i);
			    					tmpPlayer.setTileAt(tmpPlayer.getCoords()[0], tmpPlayer.getCoords()[1]);
			    					GameScene.this.attachChild(tmpPlayer);
			    				}
			    			}
			    			

			    			
			    			// HUD 
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpellBar());
			    			GameScene.this.mHud.attachChild(GameScene.this.mStatsHud.getTermono());
			    			GameScene.this.mHud.attachChild(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.attachChild(GameScene.this.mMenuHud.getMenuSprite());
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(1));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(2));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(3));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(4));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(1));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(2));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(3));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(4));
			    			GameScene.this.mHud.attachChild(hpbar);
			    			
			    			Game.getSceneManager().getDisplay().getCamera().setHUD(GameScene.this.mHud);
			    			Game.getSceneManager().getDisplay().doFocusCamera(Game.getPlayerHelper().getOwnPlayer());

			    	
			    			GameScene.this.setTouchAreaBindingOnActionDownEnabled(true);
			            }

			            @Override
			            public void onComplete() {
			            	Game.getSceneManager().getLoadingScene().loadingAnimation(false);
			            	Game.getSceneManager().setSpecificGameScene(GameScene.this);
			            }
			        });
		        }
			});
		}
		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================
		@Override
		public boolean onSceneTouchEvent(Scene pScene,
				TouchEvent pSceneTouchEvent) {
			switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					Log.d("Quest!","Action DOWN x: "+pSceneTouchEvent.getX()+" y: "+pSceneTouchEvent.getY());
				
					break;
				
				case TouchEvent.ACTION_UP:
					Log.d("Quest!","Action UP x: "+pSceneTouchEvent.getX()+" y: "+pSceneTouchEvent.getY());
					if(Game.isServer()){
						if(Game.getDataHandler().getAttackType(Game.getPlayerHelper().getOwnPlayer().getAttack_Flag())==2){
							TMXTile tmpTile = Game.getMapManager().getTMXTileAt(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
							Attack tmpAtt = Game.getAttacksHelper().addNewAttack(Game.getPlayerHelper().getOwnPlayer().getAttack_Flag());
							tmpAtt.setAnimationAtCenter(tmpTile.getTileX()+16,tmpTile.getTileY()+16);
							ArrayList<Mob> tmpMobsinArea = Game.getMobHelper().getMobsInArea(tmpTile.getTileColumn(), tmpTile.getTileRow(), (int)(tmpAtt.getEffect()[1]));
							for(int i = tmpMobsinArea.size()-1;i>=0;i--){
								Game.getBattleHelper().startAttack(Game.getPlayerHelper().getOwnPlayer(), tmpAtt.getAttackFlag(), tmpMobsinArea.get(i));	
							}
							Game.getServer().sendMessageDisplayAreaAttack(Game.getPlayerHelper().getOwnPlayer().getAttack_Flag(), tmpTile.getTileX()+16, tmpTile.getTileY()+16);
							this.mAttackLayer.add(tmpAtt);
						}
					}else{
						if(Game.getDataHandler().getAttackType(Game.getPlayerHelper().getOwnPlayer().getAttack_Flag())==2){
							TMXTile tmpTile = Game.getMapManager().getTMXTileAt(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
							Game.getClient().sendAreaAttackMessage(Game.getPlayerHelper().getOwnPlayer().getAttack_Flag(),tmpTile.getTileX()+16,tmpTile.getTileY()+16);
						}	
					}
					break;
		}
			return true;
		}
		
		
		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			// TODO Auto-generated method stub
			
			while(this.mAttackLayer.size()>0){
				for(int i = this.mAttackLayer.size()-1;i>=0; i--){
					final Attack mAttackToDraw = this.mAttackLayer.get(i);
						mAttackToDraw.setAnimationStatus(1);
						this.attachChild(mAttackToDraw.getAttackAnimation());
						mAttackToDraw.getAttackAnimation().animate(100,false,new IAnimationListener() {
							
							@Override
							public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
									int pInitialLoopCount) {		
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
									int pRemainingLoopCount, int pInitialLoopCount) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
									int pOldFrameIndex, int pNewFrameIndex) {
								// TODO Auto-generated method stub
							}
							
							@Override
							public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
								GameScene.this.detachChild(mAttackToDraw.getAttackAnimation());
								Game.getAttacksHelper().recycleAttack(mAttackToDraw);
								}
						
						});
						this.mAttackLayer.remove(mAttackToDraw);
				}
			}
			
			super.onManagedUpdate(pSecondsElapsed);
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
		
		/**
		 * @return the mAttackLayer
		 */
		public ArrayList<Attack> getAttackLayer() {
			return mAttackLayer;
		}


		/**
		 * @param mAttackLayer the mAttackLayer to set
		 */
		public void setAttackLayer(ArrayList<Attack> mAttackLayer) {
			this.mAttackLayer = mAttackLayer;
		}	
		// ===========================================================
		// Methods
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




		
