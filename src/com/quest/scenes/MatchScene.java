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
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.objects.InputText;
import com.quest.objects.MatchObject;

public class MatchScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int SERVER_PORT = 4444;
	private static final int DISCOVERY_PORT = 4445;
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
		
	
	//Own Matches
	private Entity mOwnMatchesEntity;
	private BitmapTextureAtlas mOwnMatchesTextureAtlas;
	private String mSelectedMatchName = "";
		
	//New Match	
	private Entity mNewMatchEntity;
	private BitmapTextureAtlas mNewMatchTextureAtlas;
	private Sprite mLobbyNewMatchSprite;
	private TiledTextureRegion mTiledTextureRegion;
	private String mMatchName;
	private String mMatchPassword;
	private InputText mMatchNameInput;
	private InputText mMatchPasswordInput;
	
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
		this.mDiscoveredMatchEntity = new Entity(110,61);
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
		this.mMatchBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "partyback.png", 490, 985);
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
						MatchScene.this.clearTouchAreas();
						SwitchEntity(LoadOwnMatchesEntity());//fijarse como funca					
					}
					break;
				}
			return true;	
			}
		};
		this.mMatchesEntity.attachChild(this.mOwnMatchesSprite);
		this.registerTouchArea(mOwnMatchesSprite);
		
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
						MatchScene.this.SwitchEntity(LoadNewMatchEntity());
					}
					break;
				}
			return true;	
			}
		};
		this.mMatchesEntity.attachChild(this.mNewGameSprite);
		this.registerTouchArea(this.mNewGameSprite);
		
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
						MatchScene.this.Discover();
					}
					break;
				}
				return true;	
			}
		};
		this.mMatchesEntity.attachChild(this.mRefreshSprite);
		this.registerTouchArea(this.mRefreshSprite);
		
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
						}
						break;
					}
				return true;	
				}
		};
		this.mMatchesEntity.attachChild(this.mDirectConnectSprite);
		this.registerTouchArea(this.mDirectConnectSprite);

		this.mMatchesEntity.attachChild(this.mDiscoveredMatchEntity);
		
		MatchScene.this.initServerDiscovery();
		return this.mMatchesEntity;
	}
	
	
	
	
	
	//Own Matches entity
	public Entity LoadOwnMatchesEntity(){
		this.mOwnMatchesEntity.detachChildren();
		this.LastUI=0;
		Log.d("Quest!",Game.getDataHandler().getUsername(1));
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
						MatchScene.this.SwitchEntity(LoadMatchesEntity());
					}
					break;
				}
			return true;	
			}
		};
		this.mOwnMatchesEntity.attachChild(this.mBackSprite);
		this.registerTouchArea(mBackSprite);
		
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
						MatchScene.this.SwitchEntity(LoadNewMatchEntity());
					}
					break;
				}
			return true;	
			}
		};
		this.mOwnMatchesEntity.attachChild(this.mNewGameSprite);
		this.registerTouchArea(this.mNewGameSprite);
		
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
					//borrar la entity de la partida seleccionada
					}
					break;
				}
				return true;	
			}
		};
		this.mOwnMatchesEntity.attachChild(this.mCancelSprite);
		this.registerTouchArea(this.mCancelSprite);
		
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
							if(MatchScene.this.mSelectedMatchName!=""){
								ShowLowerBar(true);
								MatchScene.this.clearTouchAreas();
								SwitchEntity(LoadLobbyEntity(false, MatchScene.this.mSelectedMatchName,"00:00:00:00:00:00"));//Game.getUserID()));							
							}
						}
						break;
					}
				return true;	
				}
		};
		this.mOwnMatchesEntity.attachChild(this.mOkSprite);
		this.registerTouchArea(this.mOkSprite);
		
		return this.mOwnMatchesEntity;
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
						if(LastUI==0){
							MatchScene.this.clearTouchAreas();
							MatchScene.this.SwitchEntity(LoadOwnMatchesEntity());
						}else{
							MatchScene.this.clearTouchAreas();
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
	
	
	
	public Entity LoadNewMatchEntity(){
		this.mNewMatchEntity.detachChildren();
		ShowLowerBar(false);
		
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
						if(LastUI==0){
							ShowLowerBar(true);
							MatchScene.this.clearTouchAreas();
							MatchScene.this.SwitchEntity(LoadOwnMatchesEntity());
						}else{
							ShowLowerBar(true);
							MatchScene.this.clearTouchAreas();
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
							if(MatchScene.this.mMatchNameInput.getText() == ""){
								MatchScene.this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(250, 350, "Please enter a valid name for the match", "MatchScene;Alert"));
							}else{
								if(!HasMatches || !Game.getDataHandler().MatchExists(MatchScene.this.mMatchNameInput.getText())){
									ShowLowerBar(true);
									MatchScene.this.clearTouchAreas();
									Game.getDataHandler().AddNewMatch(1,true,MatchScene.this.mMatchNameInput.getText(),MatchScene.this.mMatchPasswordInput.getText());
									SwitchEntity(LoadLobbyEntity(false, MatchScene.this.mMatchNameInput.getText(),"00:00:00:00:00:00"));//Game.getUserID()));
								}else{
									MatchScene.this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(200, 370, "You already have a match with that name, please choose another one.", "MatchScene;Alert2"));
								}
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
		
		
		
		//Texto
		this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 100, "Match name:", "MatchScene;MatchName"));
		this.mMatchNameInput = Game.getTextHelper().NewInputText(250, 100, "Match Name", "Choose a name for the match.", this.mTiledTextureRegion, 0, 0, false);
		this.mNewMatchEntity.attachChild(mMatchNameInput);
		
		this.mNewMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "Match password:", "MatchScene;MatchPassword"));
		this.mMatchPasswordInput = Game.getTextHelper().NewInputText(290, 150, "Match Password", "Choose a password for the match.", this.mTiledTextureRegion, 0, 0, true);
		this.mNewMatchEntity.attachChild(mMatchPasswordInput);
		
		this.registerTouchArea(mMatchNameInput);
		this.registerTouchArea(mMatchPasswordInput);
		
		
		return this.mNewMatchEntity;
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
	
	public void EnterMatch(String pIP,String pName,String pUserID,String pPassword){
		initClient(pIP);
		try {
			Game.getClient().sendClientMessage(new ConnectionPingClientMessage());
		} catch (final IOException e) {
			Debug.e(e);
		}
		MatchScene.this.clearTouchAreas();
		SwitchEntity(this.LoadLobbyEntity(true,pName,pUserID));
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
						Log.d("Quest!","DiscoveryClient: Server discovered at: " + ipAddressAsString + ":" + pDiscoveryData.getServerPort()+" Message: "+pDiscoveryData.getUserID()+" * "+pDiscoveryData.getUsername()+" - "+pDiscoveryData.getMatchName()+" - "+String.valueOf(pDiscoveryData.hasPassword()));
						boolean conts = false;
						for(int i=0;i<MatchScene.this.mMatchList.size();i++){
							if(MatchScene.this.mMatchList.get(i).getIP().equals(ipAddressAsString)){
								conts=true;
							}
						}
						if(Game.getTextHelper().getText("MatchScene;OwnIP").getText().equals(ipAddressAsString)){//lo pongo separado porque con || no funcatring pUserID,boolean pHasPassword,float pTextX,float pTextY, String pKey) 
							conts=true;
						}
						if(conts==false){
						MatchScene.this.mMatchList.add(new MatchObject(MatchScene.this.mMatchBackgroundTextureRegion,0, MatchScene.this.mMatchList.size()*163, MatchScene.this, ipAddressAsString, MatchScene.this.mDiscoveredMatchEntity,true,pDiscoveryData.getMatchName(),pDiscoveryData.getUserID(),pDiscoveryData.hasPassword(),200,MatchScene.this.mMatchList.size()*163+20,"MatchScene;"+String.valueOf(MatchScene.this.mMatchList.size())));
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

			//this.mSocketServerDiscoveryClient.discoverAsync();
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
