package com.quest.game;

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
import com.quest.helpers.BattleHelper;
import com.quest.helpers.MapHelper;
import com.quest.helpers.MobHelper;
import com.quest.helpers.PlayerHelper;
import com.quest.helpers.SceneHelper;
import com.quest.helpers.TextHelper;
import com.quest.helpers.TimerHelper;
import com.quest.network.QClient;
import com.quest.network.QServer;

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
	// ===========================================================
	// Constructors
	// ===========================================================
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
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
		//Game.mMobHelper = new MobHelper(); NO TEXTURE MANAGER TO INITIALIZE THE POOL ***
		//Game.setBattleHelper(new BattleHelper());
		
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
	
	public static boolean isServer(){
		return isServer;
	}
	
	// ===========================================================
	// Methods
	// ===========================================================






	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
