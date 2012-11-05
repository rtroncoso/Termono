package com.quest.game;

import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.KeyEvent;

import com.quest.data.MatchData;
import com.quest.data.ProfileData;
import com.quest.database.DataHandler;
import com.quest.database.QueryQueuer;
import com.quest.helpers.AttacksHelper;
import com.quest.helpers.BattleHelper;
import com.quest.helpers.LevelHelper;
import com.quest.helpers.MapHelper;
import com.quest.helpers.MobHelper;
import com.quest.helpers.PlayerHelper;
import com.quest.helpers.SceneHelper;
import com.quest.helpers.TextHelper;
import com.quest.helpers.TimerHelper;
import com.quest.network.QClient;
import com.quest.network.QServer;
import com.quest.objects.BooleanMessage;
import com.quest.scenes.MatchScene;

public class Game extends SimpleBaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private static SceneHelper mSceneManager;
	private static MapHelper mMapManager;
	private static Game mInstance;
	private static QServer mServer;
	private static QClient mClient;
	private static String mUserID;
	private static DataHandler mDataHandler;
	private static TextHelper mTextHelper;
	private static PlayerHelper mPlayerHelper;
	private static MobHelper mMobHelper;
	private static BattleHelper mBattleHelper;
	private static MatchData mMatchData;
	private static ProfileData mProfileData;
	private static boolean isServer = false;
	private static TimerHelper mTimerHelper;
	private static AttacksHelper mAttacksHelper;	
	private static Random rand;
	private static LevelHelper mLevelHelper;
	private static QueryQueuer mQueryQueuer;
	private static int pressCount = 0; 
	// ===========================================================
	// Constructors
	// ===========================================================
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//fijarse si es el home y guardar el estado de la partida 
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() {
		if(Game.getSceneManager().getCurrScene() == Game.getSceneManager().getMainMenuScene()){
			this.finish();
		}else if(Game.getSceneManager().getCurrScene() == Game.getSceneManager().getMatchScene()){
			new BooleanMessage("Quit", "What do you want to do?","Quit","Options", Game.getInstance()){
				@Override
				public void onOK() {
					Game.this.finish();
					super.onOK();
				}
				
				@Override
				public void onCancel() {
					Game.getTextHelper().FlushTexts("MainMenuScene");
					Game.getSceneManager().setOptionsScene();
					super.onCancel();
				}
			};
		}else if(Game.getSceneManager().getCurrScene() == Game.getSceneManager().getOptionsScene()){
			
			new BooleanMessage("ASD", "FIJARME CUAL ERA LA SCENE ANTERIOR","Finish","Cancel", Game.getInstance()){
				@Override
				public void onOK() {
					Game.this.finish();
					super.onOK();
				}
				
				@Override
				public void onCancel() {
					super.onCancel();
				}
			};
		}else if(Game.getSceneManager().getCurrScene() == Game.getSceneManager().getGameScene()){
			if(pressCount > 0){
				Game.this.finish();
			}else{
				new BooleanMessage("Quit", "What do you want to do?","Quit","Select Matches***", Game.getInstance()){
					@Override
					public void onOK() {
						//hacer bien el finish
						//mandar mensaje de desconexion
						//lo mismo que abajo
						Game.getQueryQueuer().executeQueries();//hacer un async asi hago el finish en el work complete
						//fijarme si ya termino de guardar
						super.onOK();
					}
					@Override
					public void onCancel() {
						//sacar la partida, guardar datos, clerear helper y server y eso (game.isserver tambien)
						super.onCancel();
					}
				};	
			}
			pressCount++;
		}
		return;
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Game.setInstance(this);
		
		// Init Objects
		Game.mSceneManager = new SceneHelper();
		Game.mDataHandler = new DataHandler();
		Game.mPlayerHelper = new PlayerHelper();
		Game.mTimerHelper = new TimerHelper();
		Game.mLevelHelper = new LevelHelper(35, 50, 1.0f/3.0f);
		
		WifiManager wifiMan = (WifiManager)Game.getInstance().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInf = wifiMan.getConnectionInfo();
		Game.mUserID = wifiInf.getMacAddress();
		
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(Game.getInstance().getWindowManager().getDefaultDisplay().getWidth(), 
						Game.getInstance().getWindowManager().getDefaultDisplay().getHeight()), 
				Game.mSceneManager.getDisplay().getCamera());
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		// Return the Engine Options
		return engineOptions;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// TODO Auto-generated method stub
		return new FixedStepEngine(pEngineOptions, 60);
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
	}

		
	
	@Override
	protected Scene onCreateScene() {
		Game.mMapManager = new MapHelper();
		Game.mSceneManager.setMainMenuScene();
		return Game.mSceneManager.getCurrScene();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public static SceneHelper getSceneManager() {
		return Game.mSceneManager;
	}

	public static void setSceneManager(SceneHelper pSceneManager) {
		Game.mSceneManager = pSceneManager;
	}
	
	/**
	 * @return the mMapManager
	 */
	public static MapHelper getMapManager() {
		return Game.mMapManager;
	}

	/**
	 * @param mMapManager the mMapManager to set
	 */
	public static void setMapManager(MapHelper pMapManager) {
		Game.mMapManager = pMapManager;
	}
	
	/**
	 * @return the mInstance
	 */
	public static Game getInstance() {
		if(mInstance == null) {
			mInstance = new Game();
		}
		return mInstance;
	}

	/**
	 * @param mInstance the mInstance to set
	 */
	public static void setInstance(Game mInstance) {
		Game.mInstance = mInstance;
	}

	public static QServer getServer() {
		return mServer;
	}

	public static void setServer(QServer mServer) {
		Game.mServer = mServer;
		isServer = true;
	}

	public static QClient getClient() {
		return mClient;
	}
	
	public static void setClient(QClient mClient) {
		Game.mClient = mClient;
		isServer = false;
	}

	public static DataHandler getDataHandler() {
		return mDataHandler;
	}

	public static void setDataHandler(DataHandler mDataHandler) {
		Game.mDataHandler = mDataHandler;
	}

	public static QueryQueuer getQueryQueuer() {
		return mQueryQueuer;
	}

	public static void setQueryQueuer(QueryQueuer mQueryQueuer) {
		Game.mQueryQueuer = mQueryQueuer;
	}

	public static TextHelper getTextHelper() {
		return mTextHelper;
	}

	public static void setTextHelper(TextHelper mTextHelper) {
		Game.mTextHelper = mTextHelper;
	}
	
	public static String getUserID(){
		return Game.mUserID;
	}

	/**
	 * @return the mEntityHelper
	 */
	public static PlayerHelper getPlayerHelper() {
		return mPlayerHelper;
	}

	/**
	 * @param mEntityHelper the mEntityHelper to set
	 */
	public static void setPlayerHelper(PlayerHelper mPlayerHelper) {
		Game.mPlayerHelper = mPlayerHelper;
	}
	
	public static MobHelper getMobHelper() {
		return mMobHelper;
	}

	public static void setMobHelper(MobHelper mMobHelper) {
		Game.mMobHelper = mMobHelper;
	}
	
	public static void setMatchData(MatchData pMatchData){
		Game.mMatchData = pMatchData;
	}
	
	public static MatchData getMatchData(){
		return Game.mMatchData;
	}
	
	public static ProfileData getProfileData() {
		return mProfileData;
	}

	public static void setProfileData(ProfileData mProfileData) {
		Game.mProfileData = mProfileData;
	}
	
	public static BattleHelper getBattleHelper() {
		return mBattleHelper;
	}

	public static void setBattleHelper(BattleHelper mBattleHelper) {
		Game.mBattleHelper = mBattleHelper;
	}

	/**
	 * @return the mTimerHelper
	 */
	public static TimerHelper getTimerHelper() {
		return mTimerHelper;
	}

	/**
	 * @param mTimerHelper the mTimerHelper to set
	 */
	public static void setTimerHelper(TimerHelper mTimerHelper) {
		Game.mTimerHelper = mTimerHelper;
	}
	
	public static AttacksHelper getAttacksHelper() {
		return mAttacksHelper;
	}

	public static void setAttacksHelper(AttacksHelper mAttacksHelper) {
		Game.mAttacksHelper = mAttacksHelper;
	}

	public static LevelHelper getLevelHelper() {
		return mLevelHelper;
	}

	public static void setLevelHelper(LevelHelper mLevelHelper) {
		Game.mLevelHelper = mLevelHelper;
	}

	public static boolean isServer(){
		return isServer;
	}


	// ===========================================================
	// Methods
	// ===========================================================
	public static int getRandomInt(int min, int max)
	{
		rand = new Random();	
		int RandomNum = rand.nextInt(max - min + 1) + min;
		return RandomNum;
	}
	
	public static float getRandomFloat()
	{
		rand = new Random();	
		float RandomNum = rand.nextFloat();
		return RandomNum;
	}




	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
