package com.quest.scenes;

import java.io.IOException;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.server.connector.ClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector.ISocketConnectionClientConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.game.Game;
import com.quest.network.QClient;
import com.quest.network.QServer;
import com.quest.network.messages.client.ConnectionPingClientMessage;

public class MatchScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	
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
	
	private QServer mServer;
	private QClient mClient;
	// ===========================================================
	// Constructors
	// ===========================================================
	public MatchScene(){
		this.mScrollEntity = new Entity(0,0);
		
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
						try {
							final ConnectionPingClientMessage connectionPingClientMessage = new ConnectionPingClientMessage(); // TODO Pooling
							connectionPingClientMessage.setTimestamp(System.currentTimeMillis());
							MatchScene.this.mClient.sendClientMessage(connectionPingClientMessage);
							Log.d("Logd","Ping");
						} catch (final IOException e) {
							Debug.e(e);
						}
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
						MatchScene.this.initServer();
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
					case TouchEvent.ACTION_UP:
						if(mGrabbed) {
							mGrabbed = false;
							MatchScene.this.initClient();
						}
						break;
					}
				return true;	
				}
		};
		this.attachChild(this.mDirectConnectSprite);
		this.registerTouchArea(this.mDirectConnectSprite);
	}
	
	
	
	private void initServer() {
		this.mServer = new QServer(new ExampleClientConnectorListener());

		this.mServer.start();

		MatchScene.this.registerUpdateHandler(this.mServer);
	}
	
	
	private void initClient() {
		try {
			this.mClient = new QClient("192.168.1.4", new ExampleServerConnectorListener());
			this.mClient.getConnection().start();
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
}
