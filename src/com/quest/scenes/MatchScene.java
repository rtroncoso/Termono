package com.quest.scenes;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.multiplayer.protocol.client.SocketServerDiscoveryClient;
import org.andengine.extension.multiplayer.protocol.client.SocketServerDiscoveryClient.ISocketServerDiscoveryClientListener;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.server.SocketServerDiscoveryServer;
import org.andengine.extension.multiplayer.protocol.server.SocketServerDiscoveryServer.ISocketServerDiscoveryServerListener;
import org.andengine.extension.multiplayer.protocol.server.connector.ClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector.ISocketConnectionClientConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.IPUtils;
import org.andengine.extension.multiplayer.protocol.util.WifiUtils;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.quest.database.DataHandler;
import com.quest.game.Game;
import com.quest.network.QClient;
import com.quest.network.QDiscoveryData.MatchesDiscoveryData;
import com.quest.network.QServer;
import com.quest.network.messages.client.ConnectionPangClientMessage;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.objects.BooleanMessage;
import com.quest.objects.InputText;
import com.quest.objects.MatchObject;

public class MatchScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int SERVER_PORT = 4444;
	private static final int DISCOVERY_PORT = 4445;
	protected static final boolean AVD_DEBUGGING = true;//CAMBIAR A FALSE PARA EL CELU
	final byte[] wifiIPv4Address = WifiUtils.getWifiIPv4AddressRaw(Game.getInstance());
	
	// ===========================================================
	// Fields
	// ===========================================================
	//acordarme de hacer que no se pueda tocar una partida tapada por las bars

	
	//Scene
	private Entity mScrollEntity;
	private Entity mCurrentEntity;
		private BitmapTextureAtlas mSceneTextureAtlas;
		private ITextureRegion mScrollBackTextureRegion;
		private ITextureRegion mUpperBarTextureRegion;
		private ITextureRegion mLowerBarTextureRegion;
		private Sprite mScrollBackSprite;
		private Sprite mUpperBarSprite;
		private Sprite mLowerBarSprite;
	//comunes
	private ITextureRegion mNewGameTextureRegion;
	private ITextureRegion mBackTextureRegion;
	private ITextureRegion mOkTextureRegion;
	private ITextureRegion mCancelTextureRegion;
	private ITextureRegion mMatchBackgroundTextureRegion;
	private Sprite mBackSprite;
	private Sprite mNewGameSprite;
	private Sprite mOkSprite;
	private Sprite mCancelSprite;
	private Entity mDiscoveredMatchEntity;
	private DataHandler mDataHandler;	
	private ArrayList<MatchObject> mMatchList;
	private int LastUI;//Donde estuvo, own matches = 0, matches = 1
	private boolean HasMatches;
	
	//Matches
	private Entity mMatchesEntity;
	private BitmapTextureAtlas mMatchesTextureAtlas;
		private ITextureRegion mRefreshTextureRegion;
		private ITextureRegion mDirectConnectTextureRegion;
		private ITextureRegion mOwnMatchesTextureRegion;
		private ITextureRegion mLockTextureRegion;
		private Sprite mRefreshSprite;
		private Sprite mDirectConnectSprite;
		private Sprite mOwnMatchesSprite;
	private ITextureRegion mUpbarTextureRegion;
	private ITextureRegion mLowbarTextureRegion;
	private Sprite mUpbarSprite;
	private Sprite mLowbarSprite;
	
	//Own Matches
	private Entity mOwnMatchesEntity;
	private BitmapTextureAtlas mOwnMatchesTextureAtlas;
	private String mSelectedMatchName = "";
	private Entity mLoadedOwnMatchesEntity;
	private ArrayList<MatchObject> mOwnMatchesList;
	
	//New Match	
	private Entity mNewMatchEntity;
	private BitmapTextureAtlas mNewMatchTextureAtlas;
	private Sprite mLobbyNewMatchSprite;
	private TiledTextureRegion mTiledTextureRegion;
	private String mMatchName;
	private String mMatchPassword;
	private InputText mMatchNameInput;
	private InputText mMatchPasswordInput;
	private ITextureRegion mPreviousTextureRegion;
	private ITextureRegion mNextTextureRegion;
	private Sprite mPreviousSprite;
	private Sprite mNextSprite;
	private int Step;
	private ITextureRegion mOrcTextureRegion;
	private ITextureRegion mPaladinTextureRegion;
	private ITextureRegion mMageTextureRegion;
	private ITextureRegion mArcherTextureRegion;
	private Sprite mOrcSprite;
	private Sprite mPaladinSprite;
	private Sprite mMageSprite;
	private Sprite mArcherSprite;
	private String mMessage[];
	
	//Direct Connect
	private Entity mDirectEntity;
	private BitmapTextureAtlas mDirectConnectTextureAtlas;
	
	//Lobby
	private Entity mLobbyEntity;	
	private BitmapTextureAtlas mLobbyTextureAtlas;
		private Sprite mKickSprite;
		private Sprite mMessageSprite;


	
	private SocketServerDiscoveryServer<MatchesDiscoveryData> mSocketServerDiscoveryServer;
	private SocketServerDiscoveryClient<MatchesDiscoveryData> mSocketServerDiscoveryClient;	
	// ===========================================================
	// Constructors
	// ===========================================================
	public MatchScene(){
		this.mScrollEntity = new Entity(0,0);
		this.mMatchesEntity = new Entity(0,0);
		this.mOwnMatchesEntity = new Entity(0,0);
		this.mNewMatchEntity = new Entity(0,0);
		this.mLobbyEntity = new Entity(0,0);
		this.mDirectEntity = new Entity(0,0);
		this.mDiscoveredMatchEntity = new Entity(125,61);
		this.mLoadedOwnMatchesEntity = new Entity(125,61);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/MatchScene/Main/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 2036,2036, TextureOptions.BILINEAR);
		this.mScrollBackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "scroll.png", 0, 0);
		this.mUpperBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "upperbar.png", 0, 768);
		this.mLowerBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "lowerbar.png", 0, 880);
		this.mNewGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "new.png", 0, 985);
		this.mBackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Back.png", 65, 985);
		this.mOkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Ok.png", 130, 985);
		this.mCancelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Cancel.png", 195, 985);
		this.mDirectConnectTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "DC.png", 260, 985);
		this.mRefreshTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "refresh.png", 325, 985);
		this.mOwnMatchesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "OwnMatches.png", 390, 985);
		this.mLockTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "lock.png", 455, 985);
		this.mPreviousTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Previous.png", 490, 985);
		this.mNextTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Next.png", 565, 985);
		this.mMatchBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "partyback.png", 640, 985);
		this.mOrcTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Orc.png", 640, 1130);
		this.mPaladinTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Paladin.png", 690, 1130);
		this.mMageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Mage.png", 725, 1130);
		this.mArcherTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Archer.png", 760, 1130);
		this.mUpbarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "upbar.png", 795, 1130);
		this.mLowbarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "lowbar.png", 795, 1185);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "ButtonSprite.png", 0, 0, 1, 1);
		this.mSceneTextureAtlas.load();	
		
		
		
		//Background
		this.mScrollBackSprite = new Sprite(0, 0, mScrollBackTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mScrollBackSprite);
		
		this.mUpperBarSprite = new Sprite(0, 0, mUpperBarTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mUpperBarSprite);
		
		this.mLowerBarSprite = new Sprite(0,this.mScrollBackSprite.getHeight()- 66,mLowerBarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mLowerBarSprite);
		
		
		CheckIfFirstTime();
		HasMatches = Game.getDataHandler().hasMatches();
		mCurrentEntity = LoadMatchesEntity();
		MatchScene.this.attachChild(mCurrentEntity);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		//***********************************************************************************
		//                           FIN DEL MAIN
		//***********************************************************************************
	}
	
	private void CheckIfFirstTime(){
		if(!Game.getDataHandler().CheckUsername(1)){
			showUsernameInput();
		}
		
	}
	
	//Matches entity
	public Entity LoadMatchesEntity(){
		this.mMatchesEntity.detachChildren();//fijarme si siguen existiendo los sprites despues de cambiar de entidades, arreglarlo(hacer que se eliminen)
		this.LastUI=1;
		this.mMatchList = new ArrayList<MatchObject>();
		this.mDiscoveredMatchEntity.detachChildren();//cambiar a dispose o algo
		
		this.mOwnMatchesSprite = new Sprite(16,12,this.mOwnMatchesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						MatchScene.this.mMatchList.clear();
						MatchScene.this.clearTouchAreas();
						SwitchEntity(LoadOwnMatchesEntity());//fijarse como funca					
					}
					break;
				}
			return true;	
			}
		};
		
		this.mNewGameSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,12,this.mNewGameTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						MatchScene.this.clearTouchAreas();
						MatchScene.this.SwitchEntity(LoadNewMatchEntity(false));
					}
					break;
				}
			return true;	
			}
		};
		
		this.mRefreshSprite = new Sprite(16, this.mScrollBackSprite.getHeight()-10-45, this.mRefreshTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						MatchScene.this.mMatchList.clear();
						MatchScene.this.mDiscoveredMatchEntity.detachChildren();//hacer que haga dispose o algo
						MatchScene.this.mDiscoveredMatchEntity.setY(61);
						MatchScene.this.Discover();
					}
					break;
				}
				return true;	
			}
		};
		
		this.mDirectConnectSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,	this.mScrollBackSprite.getHeight()-45-10, this.mDirectConnectTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
							//Direct Connect entity
							Game.getServer().terminate();
							MatchScene.this.mSocketServerDiscoveryServer.terminate();
						}
						break;
					}
				return true;	
				}
		};
		
		this.mUpbarSprite = new Sprite(110,0,mUpbarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//nada, tengo que registrar para blockear el touch de los matches
			return true;	
			}		
		};
		
		this.mLowbarSprite = new Sprite(115,this.mScrollBackSprite.getHeight()- 41,mLowbarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//nada, tengo que registrar para blockear el touch de los matches
			return true;	
			}
		};
		
		this.registerTouchArea(mOwnMatchesSprite);
		this.registerTouchArea(this.mNewGameSprite);
		this.registerTouchArea(this.mRefreshSprite);
		this.registerTouchArea(this.mDirectConnectSprite);
		this.registerTouchArea(this.mLowbarSprite);
		this.registerTouchArea(this.mUpbarSprite);
		
		this.mDiscoveredMatchEntity.setY(61);
		this.mMatchesEntity.attachChild(this.mDiscoveredMatchEntity);
		
		this.mMatchesEntity.attachChild(this.mUpbarSprite);
		this.mMatchesEntity.attachChild(this.mLowbarSprite);
		this.mMatchesEntity.attachChild(this.mOwnMatchesSprite);
		this.mMatchesEntity.attachChild(this.mNewGameSprite);
		this.mMatchesEntity.attachChild(this.mRefreshSprite);
		this.mMatchesEntity.attachChild(this.mDirectConnectSprite);
		
		MatchScene.this.initServerDiscovery();
		return this.mMatchesEntity;
	}
		
	
	
	//Own Matches entity
	public Entity LoadOwnMatchesEntity(){
		this.mOwnMatchesEntity.detachChildren();
		this.LastUI=0;
		this.mOwnMatchesList = new ArrayList<MatchObject>();
		this.mLoadedOwnMatchesEntity.detachChildren();//cambiar a dispose o algo
		this.mSelectedMatchName = "";
		
		this.mBackSprite = new Sprite(16,12,this.mBackTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						MatchScene.this.mOwnMatchesList.clear();
						MatchScene.this.clearTouchAreas();
						MatchScene.this.SwitchEntity(LoadMatchesEntity());
					}
					break;
				}
			return true;	
			}
		};
		
		this.mNewGameSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,12,this.mNewGameTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						MatchScene.this.clearTouchAreas();
						MatchScene.this.SwitchEntity(LoadNewMatchEntity(false));
					}
					break;
				}
			return true;	
			}
		};
	
		//Delete match
		this.mCancelSprite = new Sprite(16, this.mScrollBackSprite.getHeight()-10-45, this.mCancelTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						if(!MatchScene.this.mSelectedMatchName.equals("")){
							new BooleanMessage("Confirm", "Are you sure you want to delete this match?","Yes","No", Game.getInstance()){
								@Override
								public void onOK() {
									Game.getDataHandler().DeleteMatch(Game.getDataHandler().getMatchID(mSelectedMatchName));
									mSelectedMatchName="";
									MatchScene.this.mOwnMatchesList.clear();
									MatchScene.this.mLoadedOwnMatchesEntity.detachChildren();
									LoadOwnMatchesEntity();
									super.onOK();
								}
								@Override
								public void onCancel() {
									//Narinas
									super.onCancel();
								}
							};
						}
					}
					break;
				}
				return true;	
			}
		};
		
		
		this.mOkSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,	this.mScrollBackSprite.getHeight()-45-10, this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
							if(!MatchScene.this.mSelectedMatchName.equals("")){
								MatchScene.this.clearTouchAreas();
								if(AVD_DEBUGGING){//sacar despues
								SwitchEntity(LoadLobbyEntity(false, MatchScene.this.mSelectedMatchName,"00:00:00:00:00:00"));
								}else{
								SwitchEntity(LoadLobbyEntity(false, MatchScene.this.mSelectedMatchName,Game.getUserID()));
								}
							}
						}
						break;
					}
				return true;	
				}
		};

		this.mUpbarSprite = new Sprite(110,0,mUpbarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//nada, tengo que registrar para blockear el touch de los matches
			return true;	
			}		
		};
		
		this.mLowbarSprite = new Sprite(115,this.mScrollBackSprite.getHeight()- 41,mLowbarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//nada, tengo que registrar para blockear el touch de los matches
			return true;	
			}
		};

		
		this.registerTouchArea(mBackSprite);
		this.registerTouchArea(this.mNewGameSprite);
		this.registerTouchArea(this.mCancelSprite);
		this.registerTouchArea(this.mOkSprite);
		this.registerTouchArea(this.mLowbarSprite);
		this.registerTouchArea(this.mUpbarSprite);
		
		this.mLoadedOwnMatchesEntity.setY(61);
		this.mOwnMatchesEntity.attachChild(LoadOwnMatches());
		
		this.mOwnMatchesEntity.attachChild(this.mUpbarSprite);
		this.mOwnMatchesEntity.attachChild(this.mLowbarSprite);
		this.mOwnMatchesEntity.attachChild(this.mBackSprite);
		this.mOwnMatchesEntity.attachChild(this.mNewGameSprite);
		this.mOwnMatchesEntity.attachChild(this.mCancelSprite);
		this.mOwnMatchesEntity.attachChild(this.mOkSprite);
			
		return this.mOwnMatchesEntity;
	}
	
	private Entity LoadOwnMatches(){
		for(int i = 0;i<Game.getDataHandler().getMatchesAmount();i++){
			int ID = Game.getDataHandler().getMatchID(i);
			MatchScene.this.mOwnMatchesList.add(new MatchObject(MatchScene.this.mMatchBackgroundTextureRegion,0, MatchScene.this.mOwnMatchesList.size()*163, MatchScene.this, null, MatchScene.this.mLoadedOwnMatchesEntity,false,Game.getDataHandler().getMatchName(ID),Game.getUserID(),Game.getDataHandler().hasPassword(ID),"MatchScene;"+String.valueOf(MatchScene.this.mOwnMatchesList.size())));	
		}
		return this.mLoadedOwnMatchesEntity;
	}
	
	
	//Loby entity
	public Entity LoadLobbyEntity(Boolean pJoining, final String pMatchName,final String pUserID){//pedir datos de partida
		this.mLobbyEntity.detachChildren();
		
		this.mBackSprite = new Sprite(16,12,this.mBackTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						ShowLowerBar(true);
						MatchScene.this.clearTouchAreas();
						if(LastUI==0){
							MatchScene.this.SwitchEntity(LoadOwnMatchesEntity());
						}else{
							MatchScene.this.SwitchEntity(LoadMatchesEntity());
						}
						Game.getServer().terminate();
						MatchScene.this.mSocketServerDiscoveryServer.terminate();
					}
					break;
				}
			return true;	
			}
		};
		this.mLobbyEntity.attachChild(this.mBackSprite);
		this.registerTouchArea(mBackSprite);
		if(pJoining){
			ShowLowerBar(false);
		}else{
			//Start Match
			this.mOkSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,12,this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
							Game.getTextHelper().FlushText("MatchScene");
							Game.getSceneManager().setGameScene();
						}
						break;
					}
				return true;	
				}
			};
			this.mLobbyEntity.attachChild(this.mOkSprite);
			this.registerTouchArea(this.mOkSprite);
			
			//Kick player
			this.mCancelSprite = new Sprite(16, this.mScrollBackSprite.getHeight()-10-45, this.mCancelTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
						//rechazar conexiones del player seleccionado
							MatchScene.this.initServerDiscovery();
						}
						break;
					}
					return true;	
				}
			};
			this.mLobbyEntity.attachChild(this.mCancelSprite);
			this.registerTouchArea(this.mCancelSprite);
		
			
			
			//inicio el server
			MatchScene.this.initServer();
			
			//inicio el discovery server
			try {
				MatchScene.this.mSocketServerDiscoveryServer = new SocketServerDiscoveryServer<MatchesDiscoveryData>(DISCOVERY_PORT, new ExampleSocketServerDiscoveryServerListener()) {
					@Override
					protected MatchesDiscoveryData onCreateDiscoveryResponse() {
						return new MatchesDiscoveryData(wifiIPv4Address, SERVER_PORT,";"+pUserID+";"+Game.getDataHandler().getUsername(1)+";"+pMatchName+";"+String.valueOf(Game.getDataHandler().hasPassword(Game.getDataHandler().getMatchID(pMatchName, pUserID)))+";");
					}
				};
				MatchScene.this.mSocketServerDiscoveryServer.start();
			} catch (final Throwable t) {
				Debug.e(t);
			}
			Log.d("Quest!","Server started, port: "+String.valueOf(MatchScene.this.mSocketServerDiscoveryServer.getDiscoveryPort()));
		}
		try {
			this.mLobbyEntity.attachChild(Game.getTextHelper().NewText(150, 150, IPUtils.ipAddressToString(wifiIPv4Address), "MatchScene;OwnIP"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.mLobbyEntity;
	}
	
	
	
	public Entity LoadNewMatchEntity(final boolean pJoining){
		this.mNewMatchEntity.detachChildren();
		Step = 0;
		this.mBackSprite = new Sprite(16,12,this.mBackTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;					
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						MatchScene.this.clearTouchAreas();
						if(LastUI==0){
							ShowLowerBar(true);
							MatchScene.this.SwitchEntity(LoadOwnMatchesEntity());
						}else{
							ShowLowerBar(true);
							MatchScene.this.SwitchEntity(LoadMatchesEntity());
						}
					}
					break;
				}
			return true;	
			}
		};
		this.mNewMatchEntity.attachChild(this.mBackSprite);
		this.registerTouchArea(mBackSprite);
		
		
		this.mLobbyNewMatchSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,12,this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(MatchScene.this.mLobbyNewMatchSprite.isVisible()){
						mGrabbed = true;				
					}
					break;
				case TouchEvent.ACTION_UP:
					if(MatchScene.this.mLobbyNewMatchSprite.isVisible()){
						if(mGrabbed) {
							mGrabbed = false;
							MatchScene.this.clearTouchAreas();
							if(!pJoining){
								Game.getDataHandler().AddNewMatch(1,MatchScene.this.mMatchNameInput.getText(),MatchScene.this.mMatchPasswordInput.getText());
								if(AVD_DEBUGGING){//sacar despues
									SwitchEntity(LoadLobbyEntity(false, MatchScene.this.mMatchNameInput.getText(),"00:00:00:00:00:00"));
								}else{
									SwitchEntity(LoadLobbyEntity(false, MatchScene.this.mMatchNameInput.getText(),Game.getUserID()));
								}
							}else{
								//mandar mensaje con el chara elegido
								SwitchEntity(MatchScene.this.LoadLobbyEntity(true,null,null));
							}
						}
					}
					break;
				}
			return true;	
			}
		};
		this.mNewMatchEntity.attachChild(this.mLobbyNewMatchSprite);
		this.registerTouchArea(this.mLobbyNewMatchSprite);
		
		//Previous
		this.mPreviousSprite = new Sprite(16, this.mScrollBackSprite.getHeight()-10-45, this.mPreviousTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
		      boolean mGrabbed = false;
		      @Override
		      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		        switch(pSceneTouchEvent.getAction()) {
		        case TouchEvent.ACTION_DOWN:
		          if(MatchScene.this.mPreviousSprite.isVisible()){
		            mGrabbed = true; 
		          }
		          break;
		        case TouchEvent.ACTION_UP:
		          if(MatchScene.this.mPreviousSprite.isVisible()){
		            if(mGrabbed) {
		              mGrabbed = false;
		              Step-=1;
		              MatchScene.this.StepChange(false,false,pJoining);
		            }
		          }
		          break;
		        }
		        return true;  
		      }
		    };
		    this.mNewMatchEntity.attachChild(this.mPreviousSprite);
		    this.registerTouchArea(this.mPreviousSprite);

	
	        //Next
	        this.mNextSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,  this.mScrollBackSprite.getHeight()-45-10, this.mNextTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
	          boolean mGrabbed = false;
	          @Override
	          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	            switch(pSceneTouchEvent.getAction()) {
	            case TouchEvent.ACTION_DOWN:
	              if(MatchScene.this.mNextSprite.isVisible()){
	                mGrabbed = true;        
	              }
	              break;
	            case TouchEvent.ACTION_UP:
	              if(MatchScene.this.mNextSprite.isVisible()){
	                if(mGrabbed) {
	                  mGrabbed = false;
	                  if(Step==0 && !pJoining){
						if(MatchScene.this.mMatchNameInput.getText().equals(null)||MatchScene.this.mMatchNameInput.getText().equals("")){
							MatchScene.this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(250, 350, "Please enter a valid name for the match", "MatchScene;Alert"));
						}else{
							if(!HasMatches || !Game.getDataHandler().MatchExists(MatchScene.this.mMatchNameInput.getText(),1)){
								Game.getTextHelper().ClearText("MatchScene;Alert");
								Game.getTextHelper().ClearText("MatchScene;Alert2");
								Step+=1;
								MatchScene.this.StepChange(false,true,pJoining);
							}else{
								MatchScene.this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(200, 370, "You already have a match with that name, please choose another one.", "MatchScene;Alert2"));
							}
						}
	                  }else{
	                  Step+=1;
	                  MatchScene.this.StepChange(false,true,pJoining);
	                  }
	                }
	              }
	              break;
	            }
	            return true;  
	          }
	        };
	        this.mNewMatchEntity.attachChild(this.mNextSprite);
	        this.registerTouchArea(this.mNextSprite);

		
			this.StepChange(true,true,pJoining);
			return this.mNewMatchEntity;
	}
	
		private void StepChange(boolean pStart,boolean nextstep,boolean pJoining){
		    if(pStart){
		      this.mLobbyNewMatchSprite.setVisible(false);
		      this.mMessage = new String[3];
		      this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText"));
		      this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText1"));
		      this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText2"));
		      this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText3"));
		      this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText4"));
		      Game.getTextHelper().ClearText("MatchScene;StepText1");
		      Game.getTextHelper().ClearText("MatchScene;StepText2");
		      Game.getTextHelper().ClearText("MatchScene;StepText3");
		      Game.getTextHelper().ClearText("MatchScene;StepText4");
		    }
		    this.clearTouchAreas();
		    RegisterNewMatchTouchAreas();
		    if(!pJoining){
		    switch (Step) {
		    case 0:
		      this.mPreviousSprite.setVisible(false);		
		      this.mNextSprite.setVisible(true);
		      if(nextstep){
				this.mMatchNameInput = Game.getTextHelper().NewInputText(250, 100, "Match Name", "Choose a name for the match.", this.mTiledTextureRegion, 0, 0, false);
				this.mNewMatchEntity.attachChild(mMatchNameInput);
				this.mMatchPasswordInput = Game.getTextHelper().NewInputText(290, 150, "Match Password", "Choose a password for the match.", this.mTiledTextureRegion, 0, 0, true);
				this.mNewMatchEntity.attachChild(mMatchPasswordInput);
		      }
		      
				Game.getTextHelper().ChangeText("Follow the instructions to create a new match", "MatchScene;StepText", 100, 50);	
				Game.getTextHelper().ChangeText("Match name:", "MatchScene;StepText1",100, 100);
				Game.getTextHelper().ChangeText("Match password:", "MatchScene;StepText2",100, 150);
				this.registerTouchArea(mMatchNameInput);
				this.registerTouchArea(mMatchPasswordInput);
				
			  if(!nextstep){
				this.mMatchNameInput.setVisible(true);
				this.mMatchPasswordInput.setVisible(true);
				this.mNewMatchEntity.detachChild(mPaladinSprite);
			  	this.mNewMatchEntity.detachChild(mMageSprite);
				this.mNewMatchEntity.detachChild(mOrcSprite);
				this.mNewMatchEntity.detachChild(mArcherSprite);
				Game.getTextHelper().ClearText("MatchScene;StepText3");
				Game.getTextHelper().ClearText("MatchScene;StepText4");
			  }
		      break; 
		      
		    case 1:
		      this.mPreviousSprite.setVisible(true);
	    	  if(nextstep){
					this.mMatchNameInput.setVisible(false);
					this.mMatchPasswordInput.setVisible(false);	 
					
			    	  this.mPaladinSprite = new Sprite(100, 250, this.mPaladinTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				          boolean mGrabbed = false;
				          @Override
				          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				            switch(pSceneTouchEvent.getAction()) {
				            case TouchEvent.ACTION_DOWN:
				                mGrabbed = true;   
				              break;
				            case TouchEvent.ACTION_UP:
				                if(mGrabbed) {
				                  mGrabbed = false;
				                  mPaladinSprite.setAlpha(1f);
				                  mArcherSprite.setAlpha(0.5f);
				                  mOrcSprite.setAlpha(0.5f);
				                  mMageSprite.setAlpha(0.5f);
				                  mNextSprite.setVisible(true);
				                  mMessage[0] = "Paladin";
				                }
				              break;
				            }
				            return true;  
				          }
				        };
				    	this.mMageSprite = new Sprite(250, 250, this.mMageTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
					          boolean mGrabbed = false;
					          @Override
					          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					            switch(pSceneTouchEvent.getAction()) {
					            case TouchEvent.ACTION_DOWN:
					                mGrabbed = true;   
					              break;
					            case TouchEvent.ACTION_UP:
					                if(mGrabbed) {
					                  mGrabbed = false;
					                  mPaladinSprite.setAlpha(0.5f);
					                  mArcherSprite.setAlpha(0.5f);
					                  mOrcSprite.setAlpha(0.5f);
					                  mMageSprite.setAlpha(1f);
					                  mNextSprite.setVisible(true);
					                  mMessage[0] = "Mage";
					                }
					              break;
					            }
					            return true;  
					          }
					        };
				    	this.mOrcSprite = new Sprite(335, 246, this.mOrcTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
					          boolean mGrabbed = false;
					          @Override
					          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					            switch(pSceneTouchEvent.getAction()) {
					            case TouchEvent.ACTION_DOWN:
					                mGrabbed = true;   
					              break;
					            case TouchEvent.ACTION_UP:
					                if(mGrabbed) {
					                  mGrabbed = false;
					                  mPaladinSprite.setAlpha(0.5f);
					                  mArcherSprite.setAlpha(0.5f);
					                  mOrcSprite.setAlpha(1f);
					                  mMageSprite.setAlpha(0.5f);
					                  mNextSprite.setVisible(true);
					                  mMessage[0] = "Orc";
					                }
					              break;
					            }
					            return true;  
					          }
					        };
				    	this.mArcherSprite = new Sprite(455, 250, this.mArcherTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
						          boolean mGrabbed = false;
						          @Override
						          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						            switch(pSceneTouchEvent.getAction()) {
						            case TouchEvent.ACTION_DOWN:
						                mGrabbed = true;   
						              break;
						            case TouchEvent.ACTION_UP:
						                if(mGrabbed) {
						                  mGrabbed = false;
						                  mPaladinSprite.setAlpha(0.5f);
						                  mArcherSprite.setAlpha(1f);
						                  mOrcSprite.setAlpha(0.5f);
						                  mMageSprite.setAlpha(0.5f);
						                  mNextSprite.setVisible(true);
						                  mMessage[0] = "Archer";
						                }
						              break;
						            }
						            return true;  
						          }
						        };
	    	  }
	    	  Game.getTextHelper().ChangeText("Choose your character", "MatchScene;StepText", 100, 50);
	    	  this.mNextSprite.setVisible(false);
	    	  Game.getTextHelper().ChangeText("Paladin", "MatchScene;StepText1",100, 300);
	    	  Game.getTextHelper().ChangeText("Mage", "MatchScene;StepText2",250, 300);
	    	  Game.getTextHelper().ChangeText("Orc", "MatchScene;StepText3",335, 300);
	    	  Game.getTextHelper().ChangeText("Archer", "MatchScene;StepText4",455, 300);
			  
	
			    
				mNewMatchEntity.attachChild(mPaladinSprite);
				mNewMatchEntity.attachChild(mMageSprite);
				mNewMatchEntity.attachChild(mOrcSprite);
				mNewMatchEntity.attachChild(mArcherSprite);
				this.registerTouchArea(mPaladinSprite);
				this.registerTouchArea(mMageSprite);
				this.registerTouchArea(mOrcSprite);
				this.registerTouchArea(mArcherSprite);
				
				if(!nextstep){
					int a = 0;
					if(mMessage[0].equals("Paladin"))a=0;
					if(mMessage[0].equals("Mage"))a=1;
					if(mMessage[0].equals("Orc"))a=2;
					if(mMessage[0].equals("Archer"))a=3;
					this.mPaladinSprite.setAlpha(0.5f);
					this.mMageSprite.setAlpha(0.5f);
					this.mOrcSprite.setAlpha(0.5f);
					this.mArcherSprite.setAlpha(0.5f);
					switch(a){
					case 0:this.mPaladinSprite.setAlpha(1f);break; 
					case 1:this.mMageSprite.setAlpha(1f);break;
					case 2:this.mOrcSprite.setAlpha(1f);break;
					case 3:this.mArcherSprite.setAlpha(1f);break;
					}
					this.mNextSprite.setVisible(true);
				}
		      break;
		      
		    case 2:
		      this.mNextSprite.setVisible(true);  
	      	  if(nextstep){
				this.mNewMatchEntity.detachChild(mPaladinSprite);
				this.mNewMatchEntity.detachChild(mMageSprite);
	  			this.mNewMatchEntity.detachChild(mOrcSprite);
	  			this.mNewMatchEntity.detachChild(mArcherSprite);
	  			Game.getTextHelper().ClearText("MatchScene;StepText1");
	  			Game.getTextHelper().ClearText("MatchScene;StepText2");
	  			Game.getTextHelper().ClearText("MatchScene;StepText3");
	  			Game.getTextHelper().ClearText("MatchScene;StepText4");
		      }
	      	  
	      	  
		    	  
		      if(!nextstep){
		    	  
		      }
		      this.mLobbyNewMatchSprite.setVisible(false);//el ante ultimo tiene que tener esto
		      break;
		    case 3:
		      this.mNextSprite.setVisible(false);
		      if(nextstep){
		    	  
		      }
		      
		      
		      
		      if(!nextstep){
		    	  
		      }
		      this.mLobbyNewMatchSprite.setVisible(true);
		      break;
		    }
		    
		    }else{
		    	
			    switch (Step) {
			    case 0:
			    	if(nextstep){
					      this.mPreviousSprite.setVisible(false);		      
				    	  Game.getTextHelper().ChangeText("Choose your character", "MatchScene;StepText", 100, 50);
				    	  this.mNextSprite.setVisible(false);
				    	  Game.getTextHelper().ChangeText("Paladin", "MatchScene;StepText1",100, 300);
				    	  Game.getTextHelper().ChangeText("Mage", "MatchScene;StepText2",250, 300);
				    	  Game.getTextHelper().ChangeText("Orc", "MatchScene;StepText3",335, 300);
				    	  Game.getTextHelper().ChangeText("Archer", "MatchScene;StepText4",455, 300);
						  
				    	  this.mPaladinSprite = new Sprite(100, 250, this.mPaladinTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
					          boolean mGrabbed = false;
					          @Override
					          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					            switch(pSceneTouchEvent.getAction()) {
					            case TouchEvent.ACTION_DOWN:
					                mGrabbed = true;   
					              break;
					            case TouchEvent.ACTION_UP:
					                if(mGrabbed) {
					                  mGrabbed = false;
					                  mPaladinSprite.setAlpha(1f);
					                  mArcherSprite.setAlpha(0.5f);
					                  mOrcSprite.setAlpha(0.5f);
					                  mMageSprite.setAlpha(0.5f);
					                  mNextSprite.setVisible(true);
					                  mMessage[0] = "Paladin";
					                }
					              break;
					            }
					            return true;  
					          }
					        };
					    	this.mMageSprite = new Sprite(250, 250, this.mMageTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
						          boolean mGrabbed = false;
						          @Override
						          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						            switch(pSceneTouchEvent.getAction()) {
						            case TouchEvent.ACTION_DOWN:
						                mGrabbed = true;   
						              break;
						            case TouchEvent.ACTION_UP:
						                if(mGrabbed) {
						                  mGrabbed = false;
						                  mPaladinSprite.setAlpha(0.5f);
						                  mArcherSprite.setAlpha(0.5f);
						                  mOrcSprite.setAlpha(0.5f);
						                  mMageSprite.setAlpha(1f);
						                  mNextSprite.setVisible(true);
						                  mMessage[0] = "Mage";
						                }
						              break;
						            }
						            return true;  
						          }
						        };
					    	this.mOrcSprite = new Sprite(335, 246, this.mOrcTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
						          boolean mGrabbed = false;
						          @Override
						          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						            switch(pSceneTouchEvent.getAction()) {
						            case TouchEvent.ACTION_DOWN:
						                mGrabbed = true;   
						              break;
						            case TouchEvent.ACTION_UP:
						                if(mGrabbed) {
						                  mGrabbed = false;
						                  mPaladinSprite.setAlpha(0.5f);
						                  mArcherSprite.setAlpha(0.5f);
						                  mOrcSprite.setAlpha(1f);
						                  mMageSprite.setAlpha(0.5f);
						                  mNextSprite.setVisible(true);
						                  mMessage[0] = "Orc";
						                }
						              break;
						            }
						            return true;  
						          }
						        };
					    	this.mArcherSprite = new Sprite(455, 250, this.mArcherTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
							          boolean mGrabbed = false;
							          @Override
							          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
							            switch(pSceneTouchEvent.getAction()) {
							            case TouchEvent.ACTION_DOWN:
							                mGrabbed = true;   
							              break;
							            case TouchEvent.ACTION_UP:
							                if(mGrabbed) {
							                  mGrabbed = false;
							                  mPaladinSprite.setAlpha(0.5f);
							                  mArcherSprite.setAlpha(1f);
							                  mOrcSprite.setAlpha(0.5f);
							                  mMageSprite.setAlpha(0.5f);
							                  mNextSprite.setVisible(true);
							                  mMessage[0] = "Archer";
							                }
							              break;
							            }
							            return true;  
							          }
							        };
			    	}
				    
					mNewMatchEntity.attachChild(mPaladinSprite);
					mNewMatchEntity.attachChild(mMageSprite);
					mNewMatchEntity.attachChild(mOrcSprite);
					mNewMatchEntity.attachChild(mArcherSprite);
					this.registerTouchArea(mPaladinSprite);
					this.registerTouchArea(mMageSprite);
					this.registerTouchArea(mOrcSprite);
					this.registerTouchArea(mArcherSprite);
			      
			      break; 
			      
			    case 1:
			      this.mPreviousSprite.setVisible(true);
			      if(nextstep){
						this.mNewMatchEntity.detachChild(mPaladinSprite);
						this.mNewMatchEntity.detachChild(mMageSprite);
			  			this.mNewMatchEntity.detachChild(mOrcSprite);
			  			this.mNewMatchEntity.detachChild(mArcherSprite);
			  			Game.getTextHelper().ClearText("MatchScene;StepText1");
			  			Game.getTextHelper().ClearText("MatchScene;StepText2");
			  			Game.getTextHelper().ClearText("MatchScene;StepText3");
			  			Game.getTextHelper().ClearText("MatchScene;StepText4");
			      }
			        
			      if(!nextstep){
			    	  
			      }
			      break;
			    case 2:
			      this.mNextSprite.setVisible(true);
			      if(nextstep){
			    	  
			      }
			    	  
			      if(!nextstep){
			    	  
			      }
			      this.mLobbyNewMatchSprite.setVisible(false);//el ante ultimo tiene que tener esto
			      break;
			    case 3:
			      this.mNextSprite.setVisible(false);
			      if(nextstep){
			    	  
			      }
			    	  
			      if(!nextstep){
			    	  
			      }
			      this.mLobbyNewMatchSprite.setVisible(true);
			      break;
			    }
		    	
		    }
		  }
		  
		  private void RegisterNewMatchTouchAreas(){
		    this.registerTouchArea(this.mBackSprite);
		    this.registerTouchArea(this.mLobbyNewMatchSprite);
		    this.registerTouchArea(this.mPreviousSprite);
		    this.registerTouchArea(this.mNextSprite);
		  }
		
	private void ShowLowerBar(Boolean pBool){
		this.mLowerBarSprite.setVisible(pBool);
	}
	
	private void Discover(){//fijarme como hacer esto bien
		MatchScene.this.mSocketServerDiscoveryClient.discoverAsync();
		MatchScene.this.mSocketServerDiscoveryClient.discoverAsync();
		MatchScene.this.mSocketServerDiscoveryClient.discoverAsync();
		MatchScene.this.mSocketServerDiscoveryClient.discoverAsync();
		MatchScene.this.mSocketServerDiscoveryClient.discoverAsync();
	}
	
	private void SwitchEntity(Entity pEntity){
		this.detachChild(this.mCurrentEntity);
		this.mCurrentEntity = pEntity;//algun dispose para borrar lo viejo?
		this.attachChild(this.mCurrentEntity);
	}
	
	public void EnterMatch(String pIP,String pPassword){
		initClient(pIP);
		//mandar que me uni, despues desde el new match entity mando que elegi character
		//hacer que checkee si ya tiene un chara
		//ConnectionPangClientMessage mensaje = new ConnectionPangClientMessage(5555);
		//mensaje.setTimestamp(55555);
		try {
			//Game.getClient().sendClientMessage(mensaje);
			Game.getClient().sendClientMessage(new ConnectionPangClientMessage(5555));
			Game.getClient().sendClientMessage(new ConnectionPingClientMessage());
		} catch (final IOException e) {
			Debug.e(e);
		}
		MatchScene.this.clearTouchAreas();
		SwitchEntity(LoadNewMatchEntity(true));
	}
	
	private void initServer() {
		Game.setServer(new QServer(new ExampleClientConnectorListener()));

		Game.getServer().start();

		MatchScene.this.registerUpdateHandler(Game.getServer());
	}
	
	
	private void initClient(String pIP) {
		try {
			Game.setClient(new QClient(pIP, new ExampleServerConnectorListener()));
			Game.getClient().getConnection().start();
		} catch (final Throwable t) {
			Debug.e(t);
		}
	}
	
	private void initServerDiscovery() {
		try {
			this.mSocketServerDiscoveryClient = new SocketServerDiscoveryClient<MatchesDiscoveryData>(WifiUtils.getBroadcastIPAddressRaw(Game.getInstance()),DISCOVERY_PORT, SERVER_PORT, MatchesDiscoveryData.class, new ISocketServerDiscoveryClientListener<MatchesDiscoveryData>() {
				@Override
				public void onDiscovery(final SocketServerDiscoveryClient<MatchesDiscoveryData> pSocketServerDiscoveryClient, final MatchesDiscoveryData pDiscoveryData) {
					try {
						final String ipAddressAsString = IPUtils.ipAddressToString(pDiscoveryData.getServerIP());
						Log.d("Quest!","DiscoveryClient: Server discovered at: " + ipAddressAsString + ":" + pDiscoveryData.getServerPort()+" Message: "+pDiscoveryData.getUserID()+" - "+pDiscoveryData.getUsername()+" - "+pDiscoveryData.getMatchName()+" - "+String.valueOf(pDiscoveryData.hasPassword()));
						boolean conts = false;
						for(int i=0;i<MatchScene.this.mMatchList.size();i++){
							if(MatchScene.this.mMatchList.get(i).getIP().equals(ipAddressAsString)){
								conts=true;
							}
						}
						/*if(Game.getTextHelper().getText("MatchScene;OwnIP").getText().equals(ipAddressAsString)){//lo pongo separado porque con || no funcatring pUserID,boolean pHasPassword,float pTextX,float pTextY, String pKey) 
							conts=true;
						}*///comentado para el AVD, sacar coment para el celu
						if(conts==false){
							Game.getDataHandler().CheckAndAddProfile(pDiscoveryData.getUserID(),pDiscoveryData.getUsername());
							//if(MatchScene.this.mCurrentEntity == MatchScene.this.mMatchesEntity){//lo pongo separado porque con || no funcatring pUserID,boolean pHasPassword,float pTextX,float pTextY, String pKey)
							MatchScene.this.mMatchList.add(new MatchObject(MatchScene.this.mMatchBackgroundTextureRegion,0, MatchScene.this.mMatchList.size()*163, MatchScene.this, ipAddressAsString, MatchScene.this.mDiscoveredMatchEntity,true,pDiscoveryData.getMatchName(),pDiscoveryData.getUserID(),pDiscoveryData.hasPassword(),"MatchScene;"+String.valueOf(MatchScene.this.mMatchList.size())));
							//}
						}
					} catch (final UnknownHostException e) {
						Log.d("Quest!","DiscoveryClient: IPException: " + e);
					}
				}

				@Override
				public void onTimeout(final SocketServerDiscoveryClient<MatchesDiscoveryData> pSocketServerDiscoveryClient, final SocketTimeoutException pSocketTimeoutException) {
					Debug.e(pSocketTimeoutException);
					Log.d("Quest!","DiscoveryClient: Timeout: " + pSocketTimeoutException);
				}

				@Override
				public void onException(final SocketServerDiscoveryClient<MatchesDiscoveryData> pSocketServerDiscoveryClient, final Throwable pThrowable) {
					Debug.e(pThrowable);
					Log.d("Quest!","DiscoveryClient: Exception: " + pThrowable);
				}
			});

			MatchScene.this.Discover();
		} catch (final Throwable t) {
			Debug.e(t);
		}
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public ITextureRegion getLockTexture(){
		return this.mLockTextureRegion;
	}

	public void setSelectedMatch(String pSelectedMatchName){
		this.mSelectedMatchName = pSelectedMatchName;
		Log.d("Quest!","Selected Match: "+this.mSelectedMatchName);
		for(int i = 0;i<this.mOwnMatchesList.size();i++){
			if(this.mSelectedMatchName.equals(this.mOwnMatchesList.get(i).getMatchName())){
				this.mOwnMatchesList.get(i).changeAlpha(1f);
			}else{
				this.mOwnMatchesList.get(i).changeAlpha(0.5f);
			}
		}
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public void showUsernameInput() {
		Game.getInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(Game.getInstance());

				alert.setTitle("Enter a username");
				alert.setMessage("Welcome to Quest! please enter the name that will be displayed to other players");
				final EditText editText = new EditText(Game.getInstance());
				editText.setTextSize(15f);
				editText.setText("");
				editText.setGravity(Gravity.CENTER_HORIZONTAL);
				alert.setView(editText);
				alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						Game.getDataHandler().setUsername(1,editText.getText().toString());
					}
				});
				alert.setCancelable(false);
				final AlertDialog dialog = alert.create();
				dialog.setOnShowListener(new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						editText.requestFocus();
						final InputMethodManager imm = (InputMethodManager) Game.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				dialog.show();
			}
		});
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	private class ExampleServerConnectorListener implements ISocketConnectionServerConnectorListener {
		@Override
		public void onStarted(final ServerConnector<SocketConnection> pServerConnector) {
			Log.d("Quest!", "Client: " + MatchScene.this.wifiIPv4Address.toString() + " Connected to server.");
		}

		@Override
		public void onTerminated(final ServerConnector<SocketConnection> pServerConnector) {
			Log.d("Quest!","Client: " + MatchScene.this.wifiIPv4Address.toString() + " Disconnected from Server.");
			Game.getInstance().finish();
		}
	}

	private class ExampleClientConnectorListener implements ISocketConnectionClientConnectorListener {
		@Override
		public void onStarted(final ClientConnector<SocketConnection> pClientConnector) {
			Log.d("Quest!", "Server: " + MatchScene.this.wifiIPv4Address.toString() + " Client connected: " + pClientConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		}

		@Override
		public void onTerminated(final ClientConnector<SocketConnection> pClientConnector) {
			Log.d("Quest!", "Server: " + MatchScene.this.wifiIPv4Address.toString() + " Client disconnected: " + pClientConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		}
	}

//Discovery
	public class ExampleSocketServerDiscoveryServerListener implements ISocketServerDiscoveryServerListener<MatchesDiscoveryData> {
		@Override
		public void onStarted(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer) {
			Log.d("Quest!","DiscoveryServer: " + MatchScene.this.wifiIPv4Address.toString() + " Started.");
		}

		@Override
		public void onTerminated(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer) {
			Log.d("Quest!","DiscoveryServer: " + MatchScene.this.wifiIPv4Address.toString() + " Terminated.");
		}

		@Override
		public void onException(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer, final Throwable pThrowable) {
			Debug.e(pThrowable);
			Log.d("Quest!","DiscoveryServer: " + MatchScene.this.wifiIPv4Address.toString() + " Exception: " + pThrowable);
		}

		@Override
		public void onDiscovered(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer, final InetAddress pInetAddress, final int pPort) {
			Log.d("Quest!","DiscoveryServer: " + MatchScene.this.wifiIPv4Address.toString() + " Discovered by: " + pInetAddress.getHostAddress() + ":" + pPort);
		}
	}

}
