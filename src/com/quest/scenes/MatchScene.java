package com.quest.scenes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.SocketServerDiscoveryClient;
import org.andengine.extension.multiplayer.protocol.client.SocketServerDiscoveryClient.ISocketServerDiscoveryClientListener;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.server.SocketServer;
import org.andengine.extension.multiplayer.protocol.server.SocketServer.ISocketServerListener;
import org.andengine.extension.multiplayer.protocol.server.SocketServerDiscoveryServer;
import org.andengine.extension.multiplayer.protocol.server.SocketServerDiscoveryServer.ISocketServerDiscoveryServerListener;
import org.andengine.extension.multiplayer.protocol.server.connector.ClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector.ISocketConnectionClientConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.IDiscoveryData.DefaultDiscoveryData;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.IPUtils;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;
import org.andengine.extension.multiplayer.protocol.util.WifiUtils;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.game.Game;

public class MatchScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int SERVER_PORT = 14649;
	private static final int DISCOVERY_PORT = 14650;
	private static final int LOCAL_PORT = 14651;
	
	public static final short FLAG_MESSAGE_SERVER_GAME_FOUND = 1;
	static final short FLAG_MESSAGE_SERVER_CONNECTION_CLOSE = Short.MIN_VALUE;
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mSceneTextureAtlas;
	private ITextureRegion mScrollBackTextureRegion;
	private ITextureRegion mUpperBarTextureRegion;
	private ITextureRegion mLowerBarTextureRegion;
	private ITextureRegion mNewGameTextureRegion;
	private ITextureRegion mRefreshTextureRegion;
	private ITextureRegion mDirectConnectTextureRegion;
	private ITextureRegion mLoadedGamesTextureRegion;
	private ITextureRegion mDownloadedGamesTextureRegion;
	private ITextureRegion mLoadGameTextureRegion;
	private ITextureRegion mDeleteGameTextureRegion;
	private ITextureRegion mGameBackTextureRegion;
	private ITextureRegion mScrollBarTextureRegion;
	
	private Sprite mScrollBackSprite;
	private Sprite mUpperBarSprite;
	private Sprite mLowerBarSprite;
	private Sprite mNewGameSprite;
	private Sprite mRefreshSprite;
	private Sprite mDirectConnectSprite;
	private Sprite mLoadedGamesSprite;
	private Sprite mDownloadedGamesSprite;
	private Sprite mLoadGameSprite;
	private Sprite mDeleteGameSprite;
	private Sprite mGameBackSprite;
	private Sprite mScrollBarSprite;
	
	private Entity mScrollEntity;
	
	private float mScale;
		
	private SocketServer<SocketConnectionClientConnector> mSocketServer;
	private SocketServerDiscoveryServer<DefaultDiscoveryData> mSocketServerDiscoveryServer;
	
	private ServerConnector<SocketConnection> mServerConnector;
	private SocketServerDiscoveryClient<DefaultDiscoveryData> mSocketServerDiscoveryClient;	
	
	private final MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();

	// ===========================================================
	// Constructors
	// ===========================================================
	public MatchScene(){
		this.mScrollEntity = new Entity(0,0);
		this.initMessagePool();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/temp/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 2036,2036, TextureOptions.BILINEAR);
		this.mScrollBackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "scroll.png", 0, 0);
		this.mUpperBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "upperbar.png", 0, 768);
		this.mLowerBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "lowerbar.png", 0, 880);
		this.mNewGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "new.png", 0, 985);
		this.mRefreshTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "refresh.png", 100, 985);
		this.mDirectConnectTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "IP.png", 200, 985);
		this.mLoadedGamesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "loaded.png", 300, 985);
		this.mDownloadedGamesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "download.png", 400, 985);
		this.mLoadGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "load.png", 500, 985);
		this.mDeleteGameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "delete.png", 600, 985);
		this.mGameBackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "partyback.png", 0, 1057);
		this.mScrollBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "scrollbar.png", 968, 1057);
		this.mSceneTextureAtlas.load();
		
		this.mScrollBackSprite = new Sprite(0, 0, mScrollBackTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mScrollBackSprite);
		//this.mScale = this.mScrollBackSprite.getHeightScaled() / this.mScrollBackSprite.getHeight();//consigo el scale
		//this.mScrollBackSprite.getHeightScaled()-(105*this.mScale) aca pongo bien el Y
		//Log.d("Logd","Height: " + String.valueOf(this.mScrollBackSprite.getHeight()) + "  Scaled Height: "+String.valueOf(this.mScrollBackSprite.getHeightScaled())+"  Scale: "+String.valueOf(this.mScale));
		//Log.d("Logd", "Height Scaled: " + String.valueOf(this.mLowerBarSprite.getHeightScaled()) + "  Position: "+ String.valueOf(this.mLowerBarSprite.getY()));
		
		this.mUpperBarSprite = new Sprite(0, 0, mUpperBarTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mUpperBarSprite);
		
		this.mLowerBarSprite = new Sprite(0,this.mScrollBackSprite.getHeight()- 66,mLowerBarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mLowerBarSprite);
		
		this.mLoadedGamesSprite = new Sprite(16,12,this.mLoadedGamesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
						Game.getInstance().finish();
						
					}
					break;
				}
			return true;	
			}
		};
		this.attachChild(this.mLoadedGamesSprite);
		this.registerTouchArea(mLoadedGamesSprite);
		
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
						MatchScene.this.newGame();
					}
					break;
				}
			return true;	
			}
		};
		this.attachChild(this.mNewGameSprite);
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
						MatchScene.this.Refresh();
					}
					break;
				}
				return true;	
			}
		};
		this.attachChild(this.mRefreshSprite);
		this.registerTouchArea(this.mRefreshSprite);
		
		this.mDirectConnectSprite = new Sprite(this.mScrollBackSprite.getWidth()-12-63,	this.mScrollBackSprite.getHeight()-45-10, this.mDirectConnectTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mGrabbed = true;					
						break;
					case TouchEvent.ACTION_UP://ver como es el send del discovery/ usar ese para probar
						if(mGrabbed) {
							mGrabbed = false;
							if(MatchScene.this.mSocketServer != null) {
								try {
									final GameFoundServerMessage gameFoundServerMessage = (GameFoundServerMessage) MatchScene.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_GAME_FOUND);
									gameFoundServerMessage.set(String.valueOf(pTouchAreaLocalX));
									Log.d("Logd",String.valueOf(gameFoundServerMessage.getFlag()));
									MatchScene.this.mSocketServer.sendBroadcastServerMessage(gameFoundServerMessage);
									MatchScene.this.mMessagePool.recycleMessage(gameFoundServerMessage);
									
									MatchScene.this.createGameSprite(String.valueOf(pTouchAreaLocalX));																	
								} catch (final IOException e) {
									Debug.e(e);
								}
							}
						}
						break;
					}
				return true;	
				}
		};
		this.attachChild(this.mDirectConnectSprite);
		this.registerTouchArea(this.mDirectConnectSprite);
	}
	
	
	private void createGameSprite(String pString){
		Log.d("Logd", pString);
	}
	
//##############################################################################################################
	private void initMessagePool() {
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_GAME_FOUND, GameFoundServerMessage.class);
	}
//##############################################################################################################
	
	
	//clientorgo
	private void Refresh(){
		this.initServerDiscovery();
	}
	
	private void initServerDiscovery() {
		try {
			this.mSocketServerDiscoveryClient = new SocketServerDiscoveryClient<DefaultDiscoveryData>(WifiUtils.getBroadcastIPAddressRaw(Game.getInstance().getApplicationContext()), DISCOVERY_PORT, LOCAL_PORT, DefaultDiscoveryData.class, new ISocketServerDiscoveryClientListener<DefaultDiscoveryData>() {
				@Override
				public void onDiscovery(final SocketServerDiscoveryClient<DefaultDiscoveryData> pSocketServerDiscoveryClient, final DefaultDiscoveryData pDiscoveryData) {
					try {
						final String ipAddressAsString = IPUtils.ipAddressToString(pDiscoveryData.getServerIP());
						Log.d("Logd","DiscoveryClient: Server discovered at: " + ipAddressAsString + ":" + pDiscoveryData.getServerPort());
						MatchScene.this.initClient(ipAddressAsString, pDiscoveryData.getServerPort());
					} catch (final UnknownHostException e) {
						Log.d("Logd","DiscoveryClient: IPException: " + e);
					}
				}

				@Override
				public void onTimeout(final SocketServerDiscoveryClient<DefaultDiscoveryData> pSocketServerDiscoveryClient, final SocketTimeoutException pSocketTimeoutException) {
					Debug.e(pSocketTimeoutException);
					Log.d("Logd","DiscoveryClient: Timeout: " + pSocketTimeoutException);
				}

				@Override
				public void onException(final SocketServerDiscoveryClient<DefaultDiscoveryData> pSocketServerDiscoveryClient, final Throwable pThrowable) {
					Debug.e(pThrowable);
					Log.d("Logd","DiscoveryClient: Exception: " + pThrowable);
				}
			});

			this.mSocketServerDiscoveryClient.discoverAsync();
		} catch (final Throwable t) {
			Debug.e(t);
		}
	}
	
	
	private void initClient(final String pIPAddress, final int pPort) {
		try {
			 this.mServerConnector = new SocketConnectionServerConnector(new SocketConnection(new Socket(pIPAddress, pPort)), new ExampleServerConnectorListener());
			 
			 this.mServerConnector.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_CLOSE, ConnectionCloseServerMessage.class, new IServerMessageHandler<SocketConnection>() {
			 	 @Override
				 public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					 Game.getInstance().finish();
				 }
			 });
			/*
			 this.mServerConnector.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED, AddFaceServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				 @Override
				 public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					 final AddFaceServerMessage addFaceServerMessage = (AddFaceServerMessage)pServerMessage;
					 
				 }
			 });
			*/
			 this.mServerConnector.registerServerMessage(FLAG_MESSAGE_SERVER_GAME_FOUND, GameFoundServerMessage.class, new IServerMessageHandler<SocketConnection>() {
			 	 @Override
				 public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage)throws IOException {
					 final GameFoundServerMessage gameFoundServerMessage = (GameFoundServerMessage)pServerMessage;
					 MatchScene.this.createGameSprite(gameFoundServerMessage.mName);
				 }
			 });


			this.mServerConnector.getConnection().start();
		} catch (final Throwable t) {
			Debug.e(t);
		}
	}
	
	
	//levanto serverin
	private void newGame(){
		this.mSocketServer = new SocketServer<SocketConnectionClientConnector>(SERVER_PORT, new ExampleClientConnectorListener(), new ExampleServerStateListener()) {
			@Override
			protected SocketConnectionClientConnector newClientConnector(final SocketConnection pSocketConnection) throws IOException {
				return new SocketConnectionClientConnector(pSocketConnection);
			}
		};

		this.mSocketServer.start();
		
		try {
			final byte[] wifiIPv4Address = WifiUtils.getWifiIPv4AddressRaw(Game.getInstance().getApplicationContext());
			this.mSocketServerDiscoveryServer = new SocketServerDiscoveryServer<DefaultDiscoveryData>(DISCOVERY_PORT, new ExampleSocketServerDiscoveryServerListener()) {
				@Override
				protected DefaultDiscoveryData onCreateDiscoveryResponse() {
					return new DefaultDiscoveryData(wifiIPv4Address, SERVER_PORT);
				}
			};
			this.mSocketServerDiscoveryServer.start();
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
	public static class GameFoundServerMessage extends ServerMessage {
		private String mName;
		
		public GameFoundServerMessage() {

		}

		public GameFoundServerMessage(final String pName) {
			this.mName = pName;
		}

		public void set(final String pName) {
			this.mName = pName;
		}

		@Override
		public short getFlag() {
			return FLAG_MESSAGE_SERVER_GAME_FOUND;
		}

		@Override
		protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
			this.mName = pDataInputStream.readLine();
		}

		@Override
		protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
			pDataOutputStream.writeChars(this.mName);
		}
	}
	
	private class ExampleServerStateListener implements ISocketServerListener<SocketConnectionClientConnector> {
		@Override
		public void onStarted(final SocketServer<SocketConnectionClientConnector> pSocketServer) {
			try {
				Log.d("Logd","SERVER: Started. IP:" + WifiUtils.getWifiIPv4Address(Game.getInstance().getApplicationContext()));
			} catch (UnknownHostException e) {
				Log.d("Logd","Error retrieving IP of your Server: " + e);
			}
		}

		@Override
		public void onTerminated(final SocketServer<SocketConnectionClientConnector> pSocketServer) {
			Log.d("Logd","SERVER: Terminated.");
		}

		@Override
		public void onException(final SocketServer<SocketConnectionClientConnector> pSocketServer, final Throwable pThrowable) {
			Debug.e(pThrowable);
			Log.d("Logd","SERVER: Exception: " + pThrowable);
		}
	}

	private class ExampleClientConnectorListener implements ISocketConnectionClientConnectorListener {
		@Override
		public void onStarted(final ClientConnector<SocketConnection> pConnector) {
			Log.d("Logd","SERVER: Client connected: " + pConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		}

		@Override
		public void onTerminated(final ClientConnector<SocketConnection> pConnector) {
			Log.d("Logd","SERVER: Client disconnected: " + pConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		}
	}
	
	public class ExampleSocketServerDiscoveryServerListener implements ISocketServerDiscoveryServerListener<DefaultDiscoveryData> {
		@Override
		public void onStarted(final SocketServerDiscoveryServer<DefaultDiscoveryData> pSocketServerDiscoveryServer) {
			Log.d("Logd","DiscoveryServer: Started.");
		}

		@Override
		public void onTerminated(final SocketServerDiscoveryServer<DefaultDiscoveryData> pSocketServerDiscoveryServer) {
			Log.d("Logd","DiscoveryServer: Terminated.");
		}

		@Override
		public void onException(final SocketServerDiscoveryServer<DefaultDiscoveryData> pSocketServerDiscoveryServer, final Throwable pThrowable) {
			Debug.e(pThrowable);
			Log.d("Logd","DiscoveryServer: Exception: " + pThrowable);
		}

		@Override
		public void onDiscovered(final SocketServerDiscoveryServer<DefaultDiscoveryData> pSocketServerDiscoveryServer, final InetAddress pInetAddress, final int pPort) {
			Log.d("Logd","DiscoveryServer: Discovered by: " + pInetAddress.getHostAddress() + ":" + pPort);
		}
	}

	private class ExampleServerConnectorListener implements ISocketConnectionServerConnectorListener {
		@Override
		public void onStarted(final ServerConnector<SocketConnection> pConnector) {
			Log.d("Logd","Client: Connected to server.");
		}

		@Override
		public void onTerminated(final ServerConnector<SocketConnection> pConnector) {
			Log.d("Logd","Client: Disconnected from Server...");
			Game.getInstance().finish();
		}
	}
	
	public class ConnectionCloseServerMessage extends ServerMessage{

		public ConnectionCloseServerMessage() {

		}

		@Override
		public short getFlag() {
			return FLAG_MESSAGE_SERVER_CONNECTION_CLOSE;
		}

		@Override
		protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
			/* Nothing to read. */
		}

		@Override
		protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
			/* Nothing to write. */
		}
	}
}
