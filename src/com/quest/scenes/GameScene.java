package com.quest.scenes;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;

import com.quest.constants.GameFlags;
import com.quest.database.QueryQueuer;
import com.quest.display.hud.AttackHud;
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
		private StatsHud mOtherStatsHud;
		private AttackHud mAttackHud;
		private Entity mMapLayer;
		protected ArrayList<Attack> mAttackLayer;
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
			            	GameScene.this.attachChild(GameScene.this.mMapLayer);
			            	Game.getMapManager().loadMap(pMapName);
			    			
			            	
			    			//Allocate pools
			    			Game.getSceneManager().getLoadingScene().changeCurrentTaskText("Allocating Texts in pool");
			    			Game.getTextHelper().allocateDefaultTexts();
			    			Game.getSceneManager().getLoadingScene().changeCurrentTaskText("Allocating Attacks in pool");
			    			Game.getAttacksHelper().allocateAttack(FLAG_ATTACK_SPELL_BLAST, 3);
			    			Game.getAttacksHelper().allocateAttack(FLAG_ATTACK_SPELL_FIREBALL, 3);
			    			Game.getAttacksHelper().allocateAttack(FLAG_ATTACK_SPELL_THUNDER, 3);
			    			Game.getAttacksHelper().allocateAttack(FLAG_ATTACK_SPELL_ICE_RING, 5);
			    			Game.getAttacksHelper().allocateAttack(FLAG_ATTACK_SPELL_ICE_BASH, 3);
			    			Game.getAttacksHelper().allocateAttack(FLAG_ATTACK_MOB_DEATH, 2);
			    			
			    			Game.getSceneManager().getLoadingScene().changeCurrentTaskText("Allocating Items in pool");
			    			for(int i = Game.getPlayerHelper().getEntities().size()-1;i>=0;i--){
			    				Game.getPlayerHelper().getPlayerbyIndex(i).getInventory().allocateItems();
			    			}
			    			
			            				    			 
			    			Game.getSceneManager().getLoadingScene().changeCurrentTaskText("Loading HUD");
			    			GameScene.this.mHud = new HUD();
			    			GameScene.this.mStatsHud = new StatsHud(GameScene.this.mHud,Game.getPlayerHelper().getOwnPlayer());
			    			GameScene.this.mSpellbarHud = new SpellbarHud(GameScene.this.mHud);
			    			GameScene.this.mControlsHud = new ControlsHud((Player) Game.getPlayerHelper().getOwnPlayer());
			    			GameScene.this.mMenuHud = new MenuHud(mHud);
			    			GameScene.this.mAttackHud = new AttackHud();
			    			
			    			GameScene.this.mHud.setChildScene(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpellBar());
			    			
			    			// Players
			    			for(int i = 0;i<Game.getPlayerHelper().getEntities().size();i++){
			    				//si esta en mi mapa
			    				if(Game.getPlayerHelper().getPlayerbyIndex(i).getCurrentMap() == Game.getPlayerHelper().getOwnPlayer().getCurrentMap()){
			    					Player tmpPlayer = Game.getPlayerHelper().getPlayerbyIndex(i);
			    					tmpPlayer.setTileAt(tmpPlayer.getCoords()[0], tmpPlayer.getCoords()[1]);
		    						if(tmpPlayer.getUserID() == Game.getUserID() || Game.isServer())tmpPlayer.startRecoveryTimer();
			    					GameScene.this.attachChild(tmpPlayer);
			    					GameScene.this.registerTouchArea(tmpPlayer.getBodySprite());
			    				}
			    			}
			    			

			    			
			    			// HUD
			    			GameScene.this.mHud.attachChild(GameScene.this.mStatsHud.getStatsEntity());
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpellBar());
			    			GameScene.this.mHud.attachChild(GameScene.this.mControlsHud.getDigitalOnScreenControl());
			    			GameScene.this.mHud.attachChild(GameScene.this.mMenuHud.getMenuSprite());
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(0));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(1));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(2));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(3));
			    			GameScene.this.mHud.attachChild(GameScene.this.mSpellbarHud.getSpells(4));
			    			GameScene.this.mHud.attachChild(GameScene.this.mAttackHud.getAttackSprite());
			    			
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(0));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(1));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(2));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(3));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mSpellbarHud.getSpells(4));
			    			GameScene.this.mHud.registerTouchArea(GameScene.this.mAttackHud.getAttackSprite());
			    			
			    			Game.getSceneManager().getDisplay().getCamera().setHUD(GameScene.this.mHud);
			    			Game.getSceneManager().getDisplay().doFocusCamera(Game.getPlayerHelper().getOwnPlayer());

			            	if(Game.isServer()){
			            		Game.getSceneManager().getLoadingScene().changeCurrentTaskText("Starting QueryQueuer");
			            		Game.setQueryQueuer(new QueryQueuer());
			            		Game.getQueryQueuer().allocateQueryinPool(FLAG_QUERY_REGISTER_PLAYER_POSITION, Game.getPlayerHelper().getEntities().size());
			            		Game.getQueryQueuer().allocateQueryinPool(FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE, Game.getPlayerHelper().getEntities().size());
			            		Game.getQueryQueuer().allocateQueryinPool(FLAG_QUERY_PLAYER_LEVEL_UP, 1);
			            	}
			    			
			            	Game.getSceneManager().getLoadingScene().changeCurrentTaskText("Done!");
			            	
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
					break;
				case TouchEvent.ACTION_UP:
					Player tmpPlayer = Game.getPlayerHelper().getOwnPlayer();
					if(Game.isServer()){
						if(Game.getDataHandler().getAttackType(tmpPlayer.getAttack_Flag())==2){
							if(Game.getAttacksHelper().canAttack(tmpPlayer, tmpPlayer.getAttack_Flag())){
								tmpPlayer.decreaseMP(Game.getAttacksHelper().getAttackManaCost(tmpPlayer.getAttack_Flag()));
								TMXTile tmpTile = Game.getMapManager().getTMXTileAt(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
								Attack tmpAtt = Game.getAttacksHelper().addNewAttack(tmpPlayer.getAttack_Flag());
								tmpAtt.setAnimationAtCenter(tmpTile.getTileX()+16,tmpTile.getTileY()+16);
								this.mAttackLayer.add(tmpAtt);
								ArrayList<Mob> tmpMobsinArea = Game.getMobHelper().getMobsInArea(tmpTile.getTileColumn(), tmpTile.getTileRow(), (int)(tmpAtt.getEffect()[1]));
								for(int i = tmpMobsinArea.size()-1;i>=0;i--){
									Game.getBattleHelper().startAttack(tmpPlayer, tmpAtt.getAttackFlag(), tmpMobsinArea.get(i));	
								}
								Game.getServer().sendMessageDisplayAreaAttack(tmpPlayer.getAttack_Flag(), tmpTile.getTileX()+16, tmpTile.getTileY()+16,tmpPlayer.getCurrentMap(),Game.getUserID());
							}
						}
					}else{
						if(Game.getDataHandler().getAttackType(tmpPlayer.getAttack_Flag())==2){
							if(Game.getAttacksHelper().canAttack(tmpPlayer, tmpPlayer.getAttack_Flag())){
								tmpPlayer.decreaseMP(Game.getAttacksHelper().getAttackManaCost(tmpPlayer.getAttack_Flag()));
								TMXTile tmpTile = Game.getMapManager().getTMXTileAt(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
								Game.getClient().sendAreaAttackMessage(tmpPlayer.getAttack_Flag(),tmpTile.getTileX()+16,tmpTile.getTileY()+16);
							}
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

		public StatsHud getOtherStatsHud() {
			return mOtherStatsHud;
		}

		public void setOtherStatsHud(StatsHud pOtherStatsHud) {
			this.mOtherStatsHud= pOtherStatsHud;
		}
		
		public HUD getHUD(){
			return this.mHud;
		}
		
		public void setHUD(HUD pHUD){
			this.mHud = pHUD;
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
		}
		
		public void loadHUD(){
			Game.getSceneManager().getDisplay().getCamera().setHUD(this.mHud);
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
		public void CreateMob_Server(int MOB_FLAG,int tileX,int tileY,int pMap){//Server
				Game.getServer().sendSpawnMobMessage(MOB_FLAG,Game.getMobHelper().getMobCount(),tileX, tileY, pMap);
				Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG,pMap,tileX,tileY);
				if(pMap == Game.getPlayerHelper().getOwnPlayer().getCurrentMap()){//Solo lo agrega graficamente si esta en el mismo mapa
					GameScene.this.attachChild(tmpMob);
					GameScene.this.registerTouchArea(tmpMob.getBodySprite());
				}
		}
		
		public void CreateMob_Client(int MOB_FLAG,int pMobID,int tileX,int tileY){//Client new mob
			Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG,Game.getPlayerHelper().getOwnPlayer().getCurrentMap(),tileX,tileY,pMobID);
			GameScene.this.attachChild(tmpMob);
			GameScene.this.registerTouchArea(tmpMob.getBodySprite());
		}

		public void CreateMob_Client(int MOB_FLAG,int pMobID,int tileX,int tileY,int currHp,int currMp,byte facing_direction){//Client existing mob
			Mob tmpMob = Game.getMobHelper().addNewMob(MOB_FLAG,Game.getPlayerHelper().getOwnPlayer().getCurrentMap(),tileX,tileY,pMobID);
			tmpMob.setCurrHP(currHp);
			tmpMob.setCurrMana(currMp);
			boolean loop = false;
			if(tmpMob.getMobType()==1)loop=true;
			tmpMob.setAnimationDirection(facing_direction, true, loop);//*** fijarse si sirve para que apunte a donde debe
			GameScene.this.attachChild(tmpMob);
			GameScene.this.registerTouchArea(tmpMob.getBodySprite());
		}
		
		public void changeMobHUD(BaseEntity pEntity){
			if(this.mOtherStatsHud == null){
				GameScene.this.mOtherStatsHud = new StatsHud(GameScene.this.mHud,pEntity);
				GameScene.this.mHud.attachChild(GameScene.this.mOtherStatsHud.getStatsEntity());
			}else{
			//	if(mOtherStatsHud.getBaseEntity() != null)
					if(mOtherStatsHud.getBaseEntity() == null || !mOtherStatsHud.getBaseEntity().getUserData().equals(pEntity.getUserData()))
							mOtherStatsHud.ChangeEntity(pEntity);
			}
		}
		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================

		
		
		
}




		
