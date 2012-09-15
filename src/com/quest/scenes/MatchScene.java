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
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.database.DataHandler;
import com.quest.game.Game;
import com.quest.network.QClient;
import com.quest.network.QDiscoveryData.MatchesDiscoveryData;
import com.quest.network.QServer;
import com.quest.network.messages.client.ConnectionPingClientMessage;
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
	private QServer mServer;
	private QClient mClient;
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
	public Entity mDiscoveredMatchEntity;//------------------
	
	private DataHandler mDataHandler;	
	private ArrayList<MatchObject> mMatchList;
	
	private int LastUI;//Donde estuvo, own matches = 0, matches = 1  
	//Matches
	private Entity mMatchesEntity;
	private BitmapTextureAtlas mMatchesTextureAtlas;
		private ITextureRegion mRefreshTextureRegion;
		private ITextureRegion mDirectConnectTextureRegion;
		private ITextureRegion mOwnMatchesTextureRegion;
				
		private Sprite mRefreshSprite;
		private Sprite mDirectConnectSprite;
		private Sprite mOwnMatchesSprite;
		
	
	//Own Matches
	private Entity mOwnMatchesEntity;
	private BitmapTextureAtlas mOwnMatchesTextureAtlas;
		
		
	//New Match	
	private Entity mNewMatchEntity;
	private BitmapTextureAtlas mNewMatchTextureAtlas;
	
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
		this.mDataHandler = new DataHandler();
		
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
		this.mMatchBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "partyback.png", 455, 985);
		this.mSceneTextureAtlas.load();
		
		//Background
		this.mScrollBackSprite = new Sprite(0, 0, mScrollBackTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mScrollBackSprite);
		
		this.mUpperBarSprite = new Sprite(0, 0, mUpperBarTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mUpperBarSprite);
		
		this.mLowerBarSprite = new Sprite(0,this.mScrollBackSprite.getHeight()- 66,mLowerBarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mLowerBarSprite);
		
		
		
		
		

		mCurrentEntity = LoadMatchesEntity();
		MatchScene.this.attachChild(mCurrentEntity);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		//***********************************************************************************
		//                           FIN DEL MAIN
		//***********************************************************************************
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
						SwitchEntity(LoadLobbyEntity(false, "Roberto's"));
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
						MatchScene.this.mSocketServerDiscoveryClient.discoverAsync();
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
						//Set scene create new match
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
							MatchScene.this.clearTouchAreas();
							//MatchScene.this.SwitchEntity(LoadLobbyEntity());
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
	public Entity LoadLobbyEntity(Boolean pJoining, final String pName){//pedir datos de partida
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
					}
					break;
				}
			return true;	
			}
		};
		this.mLobbyEntity.attachChild(this.mBackSprite);
		this.registerTouchArea(mBackSprite);
		if(pJoining){
		/* chat?
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
							//chat?
						}
						break;
					}
				return true;	
				}
		};
		this.attachChild(this.mOkSprite);
		this.registerTouchArea(this.mOkSprite);
		*/
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
							//Iniciar la partida
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
						}
						break;
					}
					return true;	
				}
			};
			this.mLobbyEntity.attachChild(this.mCancelSprite);
			this.registerTouchArea(this.mCancelSprite);
		}
		
		if(pJoining){
			ShowLowerBar(false);
		}else{
			//inicio el server
			MatchScene.this.initServer();
			
			//inicio el discovery server
			try {
				MatchScene.this.mSocketServerDiscoveryServer = new SocketServerDiscoveryServer<MatchesDiscoveryData>(DISCOVERY_PORT, new ExampleSocketServerDiscoveryServerListener()) {
					@Override
					protected MatchesDiscoveryData onCreateDiscoveryResponse() {
						return new MatchesDiscoveryData(wifiIPv4Address, SERVER_PORT,pName);
					}
				};
				MatchScene.this.mSocketServerDiscoveryServer.start();
			} catch (final Throwable t) {
				Debug.e(t);
			}
			Log.d("Logd","Server started, port: "+String.valueOf(MatchScene.this.mSocketServerDiscoveryServer.getDiscoveryPort()));
		}
		
		return this.mLobbyEntity;
	}
	
	private void ShowLowerBar(Boolean pBool){
		this.mLowerBarSprite.setVisible(pBool);
	}
	
	
	private void SwitchEntity(Entity pEntity){
		this.detachChild(this.mCurrentEntity);
		this.mCurrentEntity = pEntity;//algun dispose para borrar lo viejo?
		this.attachChild(this.mCurrentEntity);
	}
	
	public void EnterMatch(String pIP,String pName){
		initClient(pIP);
		try {
			this.mClient.sendClientMessage(new ConnectionPingClientMessage());
		} catch (final IOException e) {
			Debug.e(e);
		}
		MatchScene.this.clearTouchAreas();
		SwitchEntity(this.LoadLobbyEntity(true,pName));
	}
	
	private void initServer() {
		this.mServer = new QServer(new ExampleClientConnectorListener());

		this.mServer.start();

		MatchScene.this.registerUpdateHandler(this.mServer);
	}
	
	
	private void initClient(String pIP) {
		try {
			this.mClient = new QClient(pIP, new ExampleServerConnectorListener());
			this.mClient.getConnection().start();
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
						Log.d("Logd","DiscoveryClient: Server discovered at: " + ipAddressAsString + ":" + pDiscoveryData.getServerPort()+" --- "+pDiscoveryData.getTest().trim());
						MatchScene.this.mMatchList.add(new MatchObject(mDataHandler, MatchScene.this.mMatchBackgroundTextureRegion,0, MatchScene.this.mMatchList.size()*143+20* MatchScene.this.mMatchList.size(), MatchScene.this, ipAddressAsString, MatchScene.this.mDiscoveredMatchEntity,true,pDiscoveryData.getTest().trim()));
					} catch (final UnknownHostException e) {
						Log.d("Logd","DiscoveryClient: IPException: " + e);
					}
				}

				@Override
				public void onTimeout(final SocketServerDiscoveryClient<MatchesDiscoveryData> pSocketServerDiscoveryClient, final SocketTimeoutException pSocketTimeoutException) {
					Debug.e(pSocketTimeoutException);
					Log.d("Logd","DiscoveryClient: Timeout: " + pSocketTimeoutException);
				}

				@Override
				public void onException(final SocketServerDiscoveryClient<MatchesDiscoveryData> pSocketServerDiscoveryClient, final Throwable pThrowable) {
					Debug.e(pThrowable);
					Log.d("Logd","DiscoveryClient: Exception: " + pThrowable);
				}
			});

			this.mSocketServerDiscoveryClient.discoverAsync();
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
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	private class ExampleServerConnectorListener implements ISocketConnectionServerConnectorListener {
		@Override
		public void onStarted(final ServerConnector<SocketConnection> pServerConnector) {
			Log.d("Logd", "CLIENT: Connected to server.");
		}

		@Override
		public void onTerminated(final ServerConnector<SocketConnection> pServerConnector) {
			Log.d("Logd","CLIENT: Disconnected from Server.");
			Game.getInstance().finish();
		}
	}

	private class ExampleClientConnectorListener implements ISocketConnectionClientConnectorListener {
		@Override
		public void onStarted(final ClientConnector<SocketConnection> pClientConnector) {
			Log.d("Logd", "SERVER: Client connected: " + pClientConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		}

		@Override
		public void onTerminated(final ClientConnector<SocketConnection> pClientConnector) {
			Log.d("Logd", "SERVER: Client disconnected: " + pClientConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		}
	}

//Discovery
	public class ExampleSocketServerDiscoveryServerListener implements ISocketServerDiscoveryServerListener<MatchesDiscoveryData> {
		@Override
		public void onStarted(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer) {
			Log.d("Logd","DiscoveryServer: Started.");
		}

		@Override
		public void onTerminated(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer) {
			Log.d("Logd","DiscoveryServer: Terminated.");
		}

		@Override
		public void onException(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer, final Throwable pThrowable) {
			Debug.e(pThrowable);
			Log.d("Logd","DiscoveryServer: Exception: " + pThrowable);
		}

		@Override
		public void onDiscovered(final SocketServerDiscoveryServer<MatchesDiscoveryData> pSocketServerDiscoveryServer, final InetAddress pInetAddress, final int pPort) {
			Log.d("Logd","DiscoveryServer: Discovered by: " + pInetAddress.getHostAddress() + ":" + pPort);
		}
	}

}
