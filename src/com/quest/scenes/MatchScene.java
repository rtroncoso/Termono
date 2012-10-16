package com.quest.scenes;

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

import com.quest.data.MatchData;
import com.quest.data.ProfileData;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.network.QClient;
import com.quest.network.QDiscoveryData.MatchesDiscoveryData;
import com.quest.network.QServer;
import com.quest.objects.BooleanMessage;
import com.quest.objects.CharacterObject;
import com.quest.objects.InputText;
import com.quest.objects.MatchObject;
public class MatchScene extends Scene {

	// ===========================================================
	// Constants
	// ===========================================================
	private static final int SERVER_PORT = 4444;
	private static final int DISCOVERY_PORT = 4445;
	protected static final boolean AVD_DEBUGGING = false;//CAMBIAR A FALSE PARA EL CELU***
	final byte[] wifiIPv4Address = WifiUtils.getWifiIPv4AddressRaw(Game.getInstance());
	
	// ===========================================================
	// Fields
	// ===========================================================
	//acordarme de hacer que no se pueda tocar una partida tapada por las bars

	
	//Scene
	private Entity mCurrentEntity;
		private BitmapTextureAtlas mSceneTextureAtlas;
		private ITextureRegion mBackgroundTextureRegion;
		private ITextureRegion mUpperBarTextureRegion;
		private ITextureRegion mLowerBarTextureRegion;
		private Sprite mBackgroundSprite;
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
		private ITextureRegion mJoinedTextureRegion;
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
	
	//Load Match	
	private Entity mLoadMatchEntity;
	private BitmapTextureAtlas mNewMatchTextureAtlas;
	private Sprite mLoadMatchTopLeftSprite;
	private Sprite mLoadMatchTopRightSprite;
	private Sprite mLoadMatchBottomLeftSprite;
	private Sprite mLoadMatchBottomRightSprite;
	private TiledTextureRegion mTiledTextureRegion;
	private String mMatchName;
	private String mMatchPassword;
	private InputText mMatchNameInput;
	private InputText mMatchPasswordInput;
	private ITextureRegion mPreviousTextureRegion;
	private ITextureRegion mNextTextureRegion;
	private int Step;
	private ITextureRegion mOrcTextureRegion;
	private ITextureRegion mPaladinTextureRegion;
	private ITextureRegion mMageTextureRegion;
	private ITextureRegion mArcherTextureRegion;
	private Sprite mOrcSprite;
	private Sprite mPaladinSprite;
	private Sprite mMageSprite;
	private Sprite mArcherSprite;
	private int mChoices[];
	private Sprite previousSprite;
	private Sprite nextSprite;
	private Sprite okSprite;
	private ITextureRegion mAttributeBackgroundTextureRegion;
	private ITextureRegion mAttributeMinusTextureRegion;
	private ITextureRegion mAttributePlusTextureRegion;
	private Sprite mAttributeBackgroundSprite1;
	private Sprite mAttributeBackgroundSprite2;
	private Sprite mAttributeBackgroundSprite3;
	private Sprite mAttributeBackgroundSprite4;
	private Sprite mAttributeMinusSprite1;
	private Sprite mAttributeMinusSprite2;
	private Sprite mAttributeMinusSprite3;
	private Sprite mAttributeMinusSprite4;
	private Sprite mAttributePlusSprite1;
	private Sprite mAttributePlusSprite2;
	private Sprite mAttributePlusSprite3;	
	private Sprite mAttributePlusSprite4;
	
	private int mSelectedCharacterID=0;	
	private ArrayList<CharacterObject> mCharacterList;
	private Entity mCharactersEntity;
	
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
		this.mMatchesEntity = new Entity(0,0);
		this.mOwnMatchesEntity = new Entity(0,0);
		this.mLoadMatchEntity = new Entity(0,0);
		this.mLobbyEntity = new Entity(0,0);
		this.mDirectEntity = new Entity(0,0);
		this.mDiscoveredMatchEntity = new Entity(125,61);
		this.mLoadedOwnMatchesEntity = new Entity(125,61);
		this.mCharactersEntity = new Entity((Game.getSceneManager().getDisplay().getCameraWidth()/2)-30,(Game.getSceneManager().getDisplay().getCameraHeight()/2));
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/MatchScene/Main/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 2036,2036, TextureOptions.BILINEAR);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Background.png", 0, 0);
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
		this.mAttributeBackgroundTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "AttributesBackground.png", 795, 1230);
		this.mAttributeMinusTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "AttributesMinus.png", 965, 1230);
		this.mAttributePlusTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "AttributesPlus.png", 1030, 1230);
		this.mJoinedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "joined.png", 1095, 1230);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "ButtonSprite.png", 0, 0, 1, 1);
		this.mSceneTextureAtlas.load();	
		
		
		
		//Background
		this.mBackgroundSprite = new Sprite(0, 0, mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mBackgroundSprite);
		
		this.mUpperBarSprite = new Sprite(0, 0, mUpperBarTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.attachChild(this.mUpperBarSprite);
		
		this.mLowerBarSprite = new Sprite(0,this.mBackgroundSprite.getHeight()- 66,mLowerBarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
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
		
		this.mNewGameSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mNewGameTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
						MatchScene.this.SwitchEntity(LoadMatchEntity(1));
					}
					break;
				}
			return true;	
			}
		};
		
		this.mRefreshSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mRefreshTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
		
		this.mDirectConnectSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,	this.mBackgroundSprite.getHeight()-45-10, this.mDirectConnectTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		
		this.mLowbarSprite = new Sprite(115,this.mBackgroundSprite.getHeight()- 41,mLowbarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		
		this.mNewGameSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mNewGameTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
						MatchScene.this.SwitchEntity(LoadMatchEntity(1));
					}
					break;
				}
			return true;	
			}
		};
	
		//Delete match
		this.mCancelSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mCancelTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
		
		
		this.mOkSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,	this.mBackgroundSprite.getHeight()-45-10, this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
								Game.setMatchData(new MatchData(Game.getDataHandler().getMatchID(MatchScene.this.mSelectedMatchName), MatchScene.this.mSelectedMatchName));
								MatchScene.this.mSelectedCharacterID = 0;
								SwitchEntity(LoadMatchEntity(2));
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
		
		this.mLowbarSprite = new Sprite(115,this.mBackgroundSprite.getHeight()- 41,mLowbarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
						Game.getServer().terminate();//***Poner devuelta
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
			this.mOkSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
							//Game.getSceneManager().setTestScene();
						}
						break;
					}
				return true;	
				}
			};
			this.mLobbyEntity.attachChild(this.mOkSprite);
			this.registerTouchArea(this.mOkSprite);
			
			//Kick player
			this.mCancelSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mCancelTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
						return new MatchesDiscoveryData(wifiIPv4Address, SERVER_PORT,pUserID,Game.getDataHandler().getUsername(1),pMatchName,Game.getDataHandler().hasPassword(Game.getDataHandler().getMatchID(pMatchName, pUserID)));
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
	
	
	//Action 1 = new match / 2 = Load Match / 3 = Join Match
	public Entity LoadMatchEntity(final int pAction){
		this.mLoadMatchEntity.detachChildren();
		Step = 0;
		this.mCharacterList = new ArrayList<CharacterObject>();
		this.mCharactersEntity.detachChildren();//cambiar a dispose o algo
		this.mSelectedCharacterID = 0;
		this.mCharactersEntity.setX(61);
		
		switch (pAction) {
		case 1://Creando un nuevo match

			this.mLoadMatchTopLeftSprite = new Sprite(16,12,this.mBackTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
			this.mLoadMatchEntity.attachChild(this.mLoadMatchTopLeftSprite);
			this.registerTouchArea(mLoadMatchTopLeftSprite);
			
			this.mLoadMatchTopRightSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						if(MatchScene.this.mLoadMatchTopRightSprite.isVisible()){
							mGrabbed = true;				
						}
						break;
					case TouchEvent.ACTION_UP:
						if(MatchScene.this.mLoadMatchTopRightSprite.isVisible()){
							if(mGrabbed) {
								mGrabbed = false;
								MatchScene.this.clearTouchAreas();
								//Creando un nuevo character en partida propia nueva
								HasMatches = true;
								int matchid = Game.getDataHandler().AddNewMatch(1,MatchScene.this.mMatchNameInput.getText(),MatchScene.this.mMatchPasswordInput.getText(),false);
								Game.setMatchData(new MatchData(matchid, MatchScene.this.mMatchNameInput.getText()));
								int playerid = Game.getDataHandler().AddNewPlayer(matchid,1, mChoices[0],mChoices[1]);//*** headID
								Game.getDataHandler().setPlayerAttributes(mChoices[2],mChoices[3],mChoices[4],mChoices[5], playerid);
								Game.getDataHandler().setPlayerLevel(1, playerid);
								Game.getDataHandler().setPlayerCurrentHPMP(playerid, (mChoices[5]*10), (mChoices[3]*10));
								Game.getPlayerHelper().addPlayer(new Player(playerid, Game.getDataHandler().getPlayerClass(playerid)),Game.getDataHandler().getUserID(1));
								
								if(AVD_DEBUGGING){//sacar despues
									SwitchEntity(LoadLobbyEntity(false, Game.getMatchData().getMatchName(),"00:00:00:00:00:00"));
								}else{
									SwitchEntity(LoadLobbyEntity(false, Game.getMatchData().getMatchName(),Game.getUserID()));
								}
							}
						}
						break;
					}
				return true;	
				}
			};
			this.mLoadMatchEntity.attachChild(this.mLoadMatchTopRightSprite);
			this.registerTouchArea(this.mLoadMatchTopRightSprite);

			
			//Previous
			this.mLoadMatchBottomLeftSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mPreviousTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			      boolean mGrabbed = false;
			      @Override
			      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			        switch(pSceneTouchEvent.getAction()) {
			        case TouchEvent.ACTION_DOWN:
			          if(MatchScene.this.mLoadMatchBottomLeftSprite.isVisible()){
			            mGrabbed = true; 
			          }
			          break;
			        case TouchEvent.ACTION_UP:
			          if(MatchScene.this.mLoadMatchBottomLeftSprite.isVisible()){
			            if(mGrabbed) {
			              mGrabbed = false;
			              Step-=1;
			              MatchScene.this.MatchCreate(false,false);
			            }
			          }
			          break;
			        }
			        return true;  
			      }
			    };
		    this.mLoadMatchEntity.attachChild(this.mLoadMatchBottomLeftSprite);
		    this.registerTouchArea(this.mLoadMatchBottomLeftSprite);


	        //Next
	        this.mLoadMatchBottomRightSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,  this.mBackgroundSprite.getHeight()-45-10, this.mNextTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
		          boolean mGrabbed = false;
		          @Override
		          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		            switch(pSceneTouchEvent.getAction()) {
		            case TouchEvent.ACTION_DOWN:
		              if(MatchScene.this.mLoadMatchBottomRightSprite.isVisible()){
		                mGrabbed = true;        
		              }
		              break;
		            case TouchEvent.ACTION_UP:
		              if(MatchScene.this.mLoadMatchBottomRightSprite.isVisible()){
		                if(mGrabbed) {
		                  mGrabbed = false;
		                  if(Step==0){
							if(MatchScene.this.mMatchNameInput.getText().equals(null)||MatchScene.this.mMatchNameInput.getText().equals("")){
								MatchScene.this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(250, 350, "Please enter a valid name for the match", "MatchScene;Alert"));
							}else{
								if(!HasMatches || !Game.getDataHandler().MatchExists(MatchScene.this.mMatchNameInput.getText(),1)){
									Game.getTextHelper().ClearText("MatchScene;Alert");
									Game.getTextHelper().ClearText("MatchScene;Alert2");
									Step+=1;
									MatchScene.this.MatchCreate(false,true);
								}else{
									MatchScene.this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(20, 370, "You already have a match with that name, please choose another one.", "MatchScene;Alert2"));
								}
							}
		                  }else{
		                  Step+=1;
		                  MatchScene.this.MatchCreate(false,true);
		                  }
		                }
		              }
		              break;
		            }
		            return true;  
		          }
		        };
	        this.mLoadMatchEntity.attachChild(this.mLoadMatchBottomRightSprite);
	        this.registerTouchArea(this.mLoadMatchBottomRightSprite);

			this.MatchCreate(true,true);
			
			break;

		case 2://Loadeando un match
			

			//Back
			this.mLoadMatchTopLeftSprite = new Sprite(16,12,this.mBackTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
							ShowLowerBar(true);
							MatchScene.this.SwitchEntity(LoadOwnMatchesEntity());
						}
						break;
					}
				return true;	
				}
			};
			this.mLoadMatchEntity.attachChild(this.mLoadMatchTopLeftSprite);
			this.registerTouchArea(mLoadMatchTopLeftSprite);
			
			//Create new character
			this.mLoadMatchTopRightSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mNewGameTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						if(MatchScene.this.mLoadMatchTopRightSprite.isVisible()){
							mGrabbed = true;				
						}
						break;
					case TouchEvent.ACTION_UP:
							if(mGrabbed) {
								MatchScene.this.mLoadMatchEntity.detachChild(MatchScene.this.mCharactersEntity);
								MatchScene.this.CharacterCreation(true, 0, 2,true);
								mGrabbed = false;
							}
						break;
					}
				return true;	
				}
			};
			this.mLoadMatchEntity.attachChild(this.mLoadMatchTopRightSprite);
			this.registerTouchArea(this.mLoadMatchTopRightSprite);
			
			//Delete Character
			this.mLoadMatchBottomLeftSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mCancelTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
			              if(MatchScene.this.mSelectedCharacterID!=0){
			            	  //Borrar chara
			              }
			            }
			          break;
			        }
			        return true;  
			      }
			    };
		    this.mLoadMatchEntity.attachChild(this.mLoadMatchBottomLeftSprite);
		    this.registerTouchArea(this.mLoadMatchBottomLeftSprite);


	        //Choose Character
	        this.mLoadMatchBottomRightSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,  this.mBackgroundSprite.getHeight()-45-10, this.mOkTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
		                  //Eligiendo un character de una partida propia
		                  if(MatchScene.this.mSelectedCharacterID!=0){
		                	  int pIndex = 0;
		                	  for(int i = 0;i<mCharacterList.size();i++){
		                		  if(mCharacterList.get(i).getCharacterID() == mSelectedCharacterID){
		                			  pIndex = i;   
		                		  }
		                	  }
		                	 Game.getPlayerHelper().addPlayer(new Player(mSelectedCharacterID, Game.getDataHandler().getPlayerClass(mSelectedCharacterID)),Game.getDataHandler().getUserID(1));
			            	 MatchScene.this.clearTouchAreas();
			            	 if(AVD_DEBUGGING){
			            		 MatchScene.this.SwitchEntity(LoadLobbyEntity(false, Game.getMatchData().getMatchName(), "00:00:00:00:00:00"));
			            	 }else{
			            		 MatchScene.this.SwitchEntity(LoadLobbyEntity(false, Game.getMatchData().getMatchName(), Game.getUserID()));
		            		 }
			              }
		                }
		              break;
		            }
		            return true;  
		          }
		        };
	        this.mLoadMatchEntity.attachChild(this.mLoadMatchBottomRightSprite);
	        this.registerTouchArea(this.mLoadMatchBottomRightSprite);

			//Load characters
			this.mLoadMatchEntity.attachChild(LoadOwnLocalCharacters());
			
			
			break;
			
		case 3://Conectandome a un match

			//Back
			this.mLoadMatchTopLeftSprite = new Sprite(16,12,this.mBackTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
							//mandar mensaje de desconexion
							Game.getClient().terminate();
							MatchScene.this.clearTouchAreas();
							ShowLowerBar(true);
							MatchScene.this.SwitchEntity(LoadMatchesEntity());
						}
						break;
					}
				return true;	
				}
			};
			this.mLoadMatchEntity.attachChild(this.mLoadMatchTopLeftSprite);
			this.registerTouchArea(mLoadMatchTopLeftSprite);
			
			//Create new character
			this.mLoadMatchTopRightSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mNewGameTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						if(MatchScene.this.mLoadMatchTopRightSprite.isVisible()){
							mGrabbed = true;				
						}
						break;
					case TouchEvent.ACTION_UP:
							if(mGrabbed) {
								MatchScene.this.mLoadMatchEntity.detachChild(mCharactersEntity);
								MatchScene.this.CharacterCreation(true, 0, 3,true);
								mGrabbed = false;
							}
						break;
					}
				return true;	
				}
			};
			this.mLoadMatchEntity.attachChild(this.mLoadMatchTopRightSprite);
			this.registerTouchArea(this.mLoadMatchTopRightSprite);
			
			
			//Delete Character
			this.mLoadMatchBottomLeftSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mCancelTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
			              if(MatchScene.this.mSelectedCharacterID!=0){
			            	  //Mandar mensaje de borrar chara
			              }
			            }
			          break;
			        }
			        return true;  
			      }
			  };
		    this.mLoadMatchEntity.attachChild(this.mLoadMatchBottomLeftSprite);
		    this.registerTouchArea(this.mLoadMatchBottomLeftSprite);


	        //Choose Character
	        this.mLoadMatchBottomRightSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,  this.mBackgroundSprite.getHeight()-45-10, this.mOkTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
		                  //Eligiendo un chara en partida ajena
		                  if(MatchScene.this.mSelectedCharacterID!=0){
			            	  //Mandar mensaje de con el char elegido chara
		                	  Game.getClient().sendSelectedPlayer(mSelectedCharacterID);
				            	 if(AVD_DEBUGGING){
				            		 MatchScene.this.SwitchEntity(LoadLobbyEntity(true,null,null));
				            	 }else{
				            		 MatchScene.this.SwitchEntity(LoadLobbyEntity(true,null,null));
			            		 }
			              }
		                }
		              break;
		            }
		            return true;  
		          }
		        };
	        this.mLoadMatchEntity.attachChild(this.mLoadMatchBottomRightSprite);
	        this.registerTouchArea(this.mLoadMatchBottomRightSprite);

	        this.mCharactersEntity.setX(61);
	        this.mCharactersEntity.attachChild(Game.getTextHelper().NewText(500, 500, "----------------------------------------------------------------------------------------------", "MatchScene;RetrievingCharacters"));
	        Game.getTextHelper().ChangeText("Retrieving characters, please wait.", "MatchScene;RetrievingCharacters", 0, 0);
	        this.mLoadMatchEntity.attachChild(mCharactersEntity);
			
			break;
		}
		
		
		
		return this.mLoadMatchEntity;
	}
	
	private Entity LoadOwnLocalCharacters(){
		int[] IDArray = Game.getDataHandler().getPlayerIDifExists(1, Game.getMatchData().getMatchName());
		if(IDArray.length>0){	
			for(int i = 0;i<IDArray.length;i++){
				MatchScene.this.mCharacterList.add(new CharacterObject(LoadCharacterTextureRegion(Game.getDataHandler().getPlayerClass(IDArray[i])),this.mCharacterList.size()*64, 0, MatchScene.this, this.mCharactersEntity, IDArray[i], Game.getDataHandler().getPlayerLevel(IDArray[i]),Game.getDataHandler().getPlayerAttributes(IDArray[i]),Game.getDataHandler().getPlayerClass(IDArray[i]), "MatchScene;"+String.valueOf(MatchScene.this.mCharacterList.size())));
			}
		}else{//No tiene chara pido que se haga uno
			this.mCharactersEntity.attachChild(Game.getTextHelper().NewText(0, 0, "You have no characters in this match, please create one.", "MatchScene;NoCharasAlert"));
		}
		return this.mCharactersEntity;
	}
	
	public void LoadOwnRemoteCharacters(int pCharacterID, int pLevel, int pClass){//***Ajustar para que pida la textura/loquesea correspondiente
		if(mCharactersEntity.getChildCount()==1){Game.getTextHelper().ClearText("MatchScene;RetrievingCharacters");}
		Log.d("Quest!","id " + pCharacterID+" level "+pLevel+" class "+pClass);
		this.mCharacterList.add(new CharacterObject(LoadCharacterTextureRegion(pClass), this.mCharacterList.size()*64, 0, MatchScene.this, this.mCharactersEntity, pCharacterID, pLevel,"MatchScene;"+String.valueOf(MatchScene.this.mCharacterList.size())));
	}

	public void msgCreateRemoteCharacter(){
		if(mCharactersEntity.getChildCount()==1)Game.getTextHelper().ChangeText("You have no characters in this match, please create one.", "MatchScene;RetrievingCharacters",0,0);
	}
	
 	private ITextureRegion LoadCharacterTextureRegion(int pPlayerClass){//Pasar a una clase bien hecha
		switch (pPlayerClass){//Game.getDataHandler().getPlayerClass(pPlayerID)) {
		case 1:
			return this.mPaladinTextureRegion; 
		case 2:
			return this.mMageTextureRegion;
		case 3:
			return this.mOrcTextureRegion;
		default:
			return this.mArcherTextureRegion;
		}
	}
	
		private void MatchCreate(boolean pStart,boolean nextstep){
		    if(pStart){
		      this.mLoadMatchTopRightSprite.setVisible(false);
				this.mChoices = new int[7];
				this.mChoices[0] = 0;
				this.mChoices[1] = 1;//***
				this.mChoices[2] = 1;
				this.mChoices[3] = 1;
				this.mChoices[4] = 1;
				this.mChoices[5] = 1;
				this.mChoices[6] = 10;
		      this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText"));
		      this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText1"));
		      this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText2"));
		      this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText3"));
		      this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText4"));
		      Game.getTextHelper().ClearText("MatchScene;StepText3");
		      Game.getTextHelper().ClearText("MatchScene;StepText4");
		      Game.getTextHelper().ClearText("MatchScene;StepText1");
		      Game.getTextHelper().ClearText("MatchScene;StepText2");
		    }
		    this.clearTouchAreas();
		    RegisterOldLoadMatchTouchAreas();
		    switch (Step) {
		   		    case 0:
		   		      this.mLoadMatchBottomLeftSprite.setVisible(false);		
		   		      this.mLoadMatchBottomRightSprite.setVisible(true);
		   		      if(nextstep){
		   				this.mMatchNameInput = Game.getTextHelper().NewInputText(250, 100, "Match Name", "Choose a name for the match.", this.mTiledTextureRegion, 0, 0, false);
		   				this.mLoadMatchEntity.attachChild(mMatchNameInput);
		   				this.mMatchPasswordInput = Game.getTextHelper().NewInputText(290, 150, "Match Password", "Choose a password for the match.", this.mTiledTextureRegion, 0, 0, true);
		   				this.mLoadMatchEntity.attachChild(mMatchPasswordInput);
		   		      }
		   		      	
		   		      Game.getTextHelper().ChangeText("Follow the instructions to create a new match", "MatchScene;StepText", 100, 50);	
		   		      Game.getTextHelper().ChangeText("Match name:", "MatchScene;StepText1",100, 100);
		   		      Game.getTextHelper().ChangeText("Match password:", "MatchScene;StepText2",100, 150);
		   		      this.registerTouchArea(mMatchNameInput);
		   		      this.registerTouchArea(mMatchPasswordInput);
		   				
		   			  if(!nextstep){
		   				this.mMatchNameInput.setVisible(true);
		   				this.mMatchPasswordInput.setVisible(true);
		   			  }
		   		      break; 
		   		      
		   		    case 1:
		   		      this.mLoadMatchBottomLeftSprite.setVisible(true);
		   	    	  if(nextstep){
		   	    		  this.mMatchNameInput.setVisible(false);
		   	    		  this.mMatchPasswordInput.setVisible(false);	 		   					
		   	    	  }
		   	    	  
		   	    	  Game.getTextHelper().ChangeText("Choose the map for the match", "MatchScene;StepText", 100, 50);
		   	    	  Game.getTextHelper().ClearText("MatchScene;StepText1");
		   	    	  Game.getTextHelper().ClearText("MatchScene;StepText2");
		   	    	  this.mLoadMatchBottomRightSprite.setVisible(true);
		   	    	  if(!nextstep){
		   	    		
		   	    		  this.mLoadMatchEntity.detachChild(mPaladinSprite);
		   	    		  this.mLoadMatchEntity.detachChild(mMageSprite);
		   	    		  this.mLoadMatchEntity.detachChild(mOrcSprite);
		   	    		  this.mLoadMatchEntity.detachChild(mArcherSprite);
		   	    		  Game.getTextHelper().ClearText("MatchScene;StepText1");
		   	    		  Game.getTextHelper().ClearText("MatchScene;StepText2");
		   	    		  Game.getTextHelper().ClearText("MatchScene;StepText3");
		   	    		  Game.getTextHelper().ClearText("MatchScene;StepText4");
		   	    	  }
		   		      break;
		   		    case 2: 
		   	      	  CharacterCreation(true, Step, 1, nextstep);
		   		      break;
		   		    case 3:
		   		      CharacterCreation(true, Step, 1, nextstep);
		   		      break;
		   		    }
		   			 	  
		    
		  }
		  
		private void CharacterCreation(boolean pStart, int pStep, final int pAction, boolean pNextStep){
		    if(pStart){		    	
		    	this.clearTouchAreas();
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
				                  if(pAction==1){
				                	  mLoadMatchBottomRightSprite.setVisible(true);
				                  }else{
				                	  nextSprite.setVisible(true);
				                  }
				                  mChoices[0] = 1;
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
					                  if(pAction==1){
					                	  mLoadMatchBottomRightSprite.setVisible(true);
					                  }else{
					                	  nextSprite.setVisible(true);
					                  }
					                  mChoices[0] = 2;
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
					                  if(pAction==1){
					                	  mLoadMatchBottomRightSprite.setVisible(true);
					                  }else{
					                	  nextSprite.setVisible(true);
					                  }
					                  mChoices[0] = 3;
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
						                  if(pAction==1){
						                	  mLoadMatchBottomRightSprite.setVisible(true);
						                  }else{
						                	  nextSprite.setVisible(true);
						                  }
						                  mChoices[0] = 4;
						                }
						              break;
						            }
						            return true;  
						          }
						        };
		    	this.mAttributeBackgroundSprite1 = new Sprite(24, 105, this.mAttributeBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		    	this.mAttributeBackgroundSprite2 = new Sprite(218, 105, this.mAttributeBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		    	this.mAttributeBackgroundSprite3 = new Sprite(412, 105, this.mAttributeBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		    	this.mAttributeBackgroundSprite4 = new Sprite(606, 105, this.mAttributeBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
				this.mAttributeMinusSprite1 = new Sprite(this.mAttributeBackgroundSprite1.getX()+(this.mAttributeBackgroundSprite1.getWidth()/2)-32,this.mAttributeBackgroundSprite1.getY()+180, this.mAttributeMinusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				      boolean mGrabbed = false;
				      @Override
				      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				        switch(pSceneTouchEvent.getAction()) {
				        case TouchEvent.ACTION_DOWN:
				          if(mAttributeMinusSprite1.isVisible()){
				            mGrabbed = true; 
				          }
				          break;
				        case TouchEvent.ACTION_UP:
					          if(mAttributeMinusSprite1.isVisible()){
						            if(mGrabbed) {
						              mGrabbed = false;
						              if(changePoints(false, 1)){
							        	  if(pAction == 1){
							        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
							        	  }else{
							        		  MatchScene.this.okSprite.setVisible(true);
							        	  }
							          }else{
							        	  if(pAction == 1){
							        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
							        	  }else{
							        		  MatchScene.this.okSprite.setVisible(false);
							        	  }
							          }
						            }
						          }
				          break;
				        }
				        return true;  
				      }
			    };
			    this.mAttributeMinusSprite2 = new Sprite(this.mAttributeBackgroundSprite2.getX()+(this.mAttributeBackgroundSprite2.getWidth()/2)-32,this.mAttributeBackgroundSprite2.getY()+180, this.mAttributeMinusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				      boolean mGrabbed = false;
				      @Override
				      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				        switch(pSceneTouchEvent.getAction()) {
				        case TouchEvent.ACTION_DOWN:
				          if(mAttributeMinusSprite2.isVisible()){
				            mGrabbed = true; 
				          }
				          break;
				        case TouchEvent.ACTION_UP:
					          if(mAttributeMinusSprite2.isVisible()){
						            if(mGrabbed) {
						              mGrabbed = false;
						              if(changePoints(false, 2)){
							        	  if(pAction == 1){
							        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
							        	  }else{
							        		  MatchScene.this.okSprite.setVisible(true);
							        	  }
							          }else{
							        	  if(pAction == 1){
							        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
							        	  }else{
							        		  MatchScene.this.okSprite.setVisible(false);
							        	  }
							          }
						            }
						          }
				          break;
				        }
				        return true;  
				      }
				    };  
			    this.mAttributeMinusSprite3 = new Sprite(this.mAttributeBackgroundSprite3.getX()+(this.mAttributeBackgroundSprite3.getWidth()/2)-32,this.mAttributeBackgroundSprite3.getY()+180, this.mAttributeMinusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
					      boolean mGrabbed = false;
					      @Override
					      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					        switch(pSceneTouchEvent.getAction()) {
					        case TouchEvent.ACTION_DOWN:
					          if(mAttributeMinusSprite3.isVisible()){
					            mGrabbed = true; 
					          }
					          break;
					        case TouchEvent.ACTION_UP:
						          if(mAttributeMinusSprite3.isVisible()){
							            if(mGrabbed) {
							              mGrabbed = false;
							              if(changePoints(false, 3)){
								        	  if(pAction == 1){
								        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
								        	  }else{
								        		  MatchScene.this.okSprite.setVisible(true);
								        	  }
								          }else{
								        	  if(pAction == 1){
								        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
								        	  }else{
								        		  MatchScene.this.okSprite.setVisible(false);
								        	  }
								          }
							            }
							          }
					          break;
					        }
					        return true;  
					      }
					    };
			    this.mAttributeMinusSprite4 = new Sprite(this.mAttributeBackgroundSprite4.getX()+(this.mAttributeBackgroundSprite4.getWidth()/2)-32,this.mAttributeBackgroundSprite4.getY()+180, this.mAttributeMinusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
						      boolean mGrabbed = false;
						      @Override
						      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						        switch(pSceneTouchEvent.getAction()) {
						        case TouchEvent.ACTION_DOWN:
						          if(mAttributeMinusSprite4.isVisible()){
						            mGrabbed = true; 
						          }
						          break;
						        case TouchEvent.ACTION_UP:
						          if(mAttributeMinusSprite4.isVisible()){
						            if(mGrabbed) {
						              mGrabbed = false;
						              if(changePoints(false, 4)){
							        	  if(pAction == 1){
							        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
							        	  }else{
							        		  MatchScene.this.okSprite.setVisible(true);
							        	  }
							          }else{
							        	  if(pAction == 1){
							        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
							        	  }else{
							        		  MatchScene.this.okSprite.setVisible(false);
							        	  }
							          }
						            }
						          }
						          break;
						        }
						        return true;  
						      }
						    };
				this.mAttributePlusSprite1 = new Sprite(this.mAttributeBackgroundSprite1.getX()+(this.mAttributeBackgroundSprite1.getWidth()/2)-32,this.mAttributeBackgroundSprite1.getY()+90, this.mAttributePlusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				      boolean mGrabbed = false;
				      @Override
				      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				        switch(pSceneTouchEvent.getAction()) {
				        case TouchEvent.ACTION_DOWN:
				          if(mAttributePlusSprite1.isVisible()){
				            mGrabbed = true; 
				          }
				          break;
				        case TouchEvent.ACTION_UP:
				          if(mAttributePlusSprite1.isVisible()){
				            if(mGrabbed) {
				              mGrabbed = false;
				              if(changePoints(true, 1)){
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(true);
					        	  }
					          }else{
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(false);
					        	  }
					          }
				            }
				          }
				          break;
				        }
				        return true;  
				      }
			    };
				this.mAttributePlusSprite2 = new Sprite(this.mAttributeBackgroundSprite2.getX()+(this.mAttributeBackgroundSprite2.getWidth()/2)-32,this.mAttributeBackgroundSprite2.getY()+90, this.mAttributePlusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				      boolean mGrabbed = false;
				      @Override
				      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				        switch(pSceneTouchEvent.getAction()) {
				        case TouchEvent.ACTION_DOWN:
				          if(mAttributePlusSprite2.isVisible()){
				            mGrabbed = true; 
				          }
				          break;
				        case TouchEvent.ACTION_UP:
				          if(mAttributePlusSprite2.isVisible()){
				            if(mGrabbed) {
				              mGrabbed = false;
				              if(changePoints(true, 2)){
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(true);
					        	  }
					          }else{
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(false);
					        	  }
					          }
				            }
				          }
				          break;
				        }
				        return true;  
				      }
			    };
				this.mAttributePlusSprite3 = new Sprite(this.mAttributeBackgroundSprite3.getX()+(this.mAttributeBackgroundSprite3.getWidth()/2)-32,this.mAttributeBackgroundSprite3.getY()+90, this.mAttributePlusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				      boolean mGrabbed = false;
				      @Override
				      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				        switch(pSceneTouchEvent.getAction()) {
				        case TouchEvent.ACTION_DOWN:
				          if(mAttributePlusSprite3.isVisible()){
				            mGrabbed = true; 
				          }
				          break;
				        case TouchEvent.ACTION_UP:
				          if(mAttributePlusSprite3.isVisible()){
				            if(mGrabbed) {
				              mGrabbed = false;
				              if(changePoints(true, 3)){
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(true);
					        	  }
					          }else{
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(false);
					        	  }
					          }
				            }
				          }
				          break;
				        }
				        return true;  
				      }
			    };
				this.mAttributePlusSprite4 = new Sprite(this.mAttributeBackgroundSprite4.getX()+(this.mAttributeBackgroundSprite4.getWidth()/2)-32,this.mAttributeBackgroundSprite4.getY()+90, this.mAttributePlusTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				      boolean mGrabbed = false;
				      @Override
				      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				        switch(pSceneTouchEvent.getAction()) {
				        case TouchEvent.ACTION_DOWN:
				          if(mAttributePlusSprite4.isVisible()){
				            mGrabbed = true; 
				          }
				          break;
				        case TouchEvent.ACTION_UP:
				          if(mAttributePlusSprite4.isVisible()){
				            if(mGrabbed) {
				              mGrabbed = false;
				              if(changePoints(true, 4)){
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(true);
					        	  }
					          }else{
					        	  if(pAction == 1){
					        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(false);								        		  
					        	  }else{
					        		  MatchScene.this.okSprite.setVisible(false);
					        	  }
					          }
				            }
				          }
				          break;
				        }
				        return true;  
				      }
			    };		    
			    switch (pAction) {//cambiar los botones y sus funciones
					case 1:
						//Nada
						break;
					default:
						Step=0;
						this.mChoices = new int[7];
						this.mChoices[0] = 0;
						this.mChoices[1] = 0;
						this.mChoices[2] = 1;
						this.mChoices[3] = 1;
						this.mChoices[4] = 1;
						this.mChoices[5] = 1;
						this.mChoices[6] = 10;
						this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText"));
						this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText1"));
						this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText2"));
						this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText3"));
						this.mLoadMatchEntity.attachChild(Game.getTextHelper().NewText(100, 150, "---------------------------------------------------------------------------------------", "MatchScene;StepText4"));
						Game.getTextHelper().ClearText("MatchScene;StepText3");
						Game.getTextHelper().ClearText("MatchScene;StepText4");
						Game.getTextHelper().ClearText("MatchScene;StepText1");
						Game.getTextHelper().ClearText("MatchScene;StepText2");
						this.mLoadMatchEntity.detachChild(this.mLoadMatchBottomLeftSprite);
						this.mLoadMatchEntity.detachChild(this.mLoadMatchBottomRightSprite);
						this.mLoadMatchEntity.detachChild(this.mLoadMatchTopRightSprite);
						this.previousSprite = new Sprite(16, this.mBackgroundSprite.getHeight()-10-45, this.mPreviousTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
						      boolean mGrabbed = false;
						      @Override
						      public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						        switch(pSceneTouchEvent.getAction()) {
						        case TouchEvent.ACTION_DOWN:
						          if(previousSprite.isVisible()){
						            mGrabbed = true; 
						          }
						          break;
						        case TouchEvent.ACTION_UP:
						          if(previousSprite.isVisible()){
						            if(mGrabbed) {
						              mGrabbed = false;
						              Step-=1;
						              CharacterCreation(false, Step, 2, false);
						            }
						          }
						          break;
						        }
						        return true;  
						      }
						    };
					    this.mLoadMatchEntity.attachChild(previousSprite);
						this.nextSprite  = new Sprite(this.mBackgroundSprite.getWidth()-12-63,  this.mBackgroundSprite.getHeight()-45-10, this.mNextTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
						          boolean mGrabbed = false;
						          @Override
						          public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						            switch(pSceneTouchEvent.getAction()) {
						            case TouchEvent.ACTION_DOWN:
						              if(nextSprite.isVisible()){
						                mGrabbed = true;        
						              }
						              break;
						            case TouchEvent.ACTION_UP:
						              if(nextSprite.isVisible()){
						                if(mGrabbed) {
						                  mGrabbed = false;
						                  Step+=1;
						                  CharacterCreation(false, Step, 2, true);
						                }
						              }
						              break;
						            }
						            return true;  
						          }
						        };
				        this.mLoadMatchEntity.attachChild(nextSprite);
				        this.okSprite = new Sprite(this.mBackgroundSprite.getWidth()-12-63,12,this.mOkTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
								boolean mGrabbed = false;
								@Override
								public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
									switch(pSceneTouchEvent.getAction()) {
									case TouchEvent.ACTION_DOWN:
										if(okSprite.isVisible()){
											mGrabbed = true;				
										}
										break;
									case TouchEvent.ACTION_UP:
										if(okSprite.isVisible()){
											if(mGrabbed) {
												mGrabbed = false;
												if(pAction==2){
													//Creando un chara en una partida loadeada
													mGrabbed = false;
													MatchScene.this.clearTouchAreas();
													
													int playerid = Game.getDataHandler().AddNewPlayer(Game.getMatchData().getMatchID(),1, mChoices[0], mChoices[1]);//*** headID
													Game.getDataHandler().setPlayerAttributes(mChoices[2],mChoices[3],mChoices[4],mChoices[5], playerid);
													Game.getDataHandler().setPlayerLevel(1, playerid);//***
													Game.getDataHandler().setPlayerCurrentHPMP(playerid, (mChoices[5]*10), (mChoices[3]*10));
													Game.getPlayerHelper().addPlayer(new Player(playerid, Game.getDataHandler().getPlayerClass(playerid)),Game.getDataHandler().getUserID(1));
													if(AVD_DEBUGGING){//sacar despues
														SwitchEntity(LoadLobbyEntity(false, Game.getMatchData().getMatchName(),"00:00:00:00:00:00"));
													}else{
														SwitchEntity(LoadLobbyEntity(false, Game.getMatchData().getMatchName(),Game.getUserID()));
													}
												}else{
													//Creando chara en partida ajena
													MatchScene.this.clearTouchAreas();
													Game.getClient().sendPlayerCreate(mChoices,Game.getDataHandler().getUserID(1));//*** sacar profile data y match data
													if(!Game.getDataHandler().checkifJoined(Game.getProfileData().getUserID(), Game.getMatchData().getMatchName())){
													Game.getDataHandler().AddNewMatch(Game.getDataHandler().getProfileID(Game.getProfileData().getUserID()), Game.getMatchData().getMatchName(), Game.getMatchData().getPassword(),true);
													}
													if(AVD_DEBUGGING){//sacar despues
														SwitchEntity(LoadLobbyEntity(true, null,null));
													}else{
														SwitchEntity(LoadLobbyEntity(true, null,null));
													}
												}
											}
										}
										break;
									}
								return true;	
								}
							};
						this.mLoadMatchEntity.attachChild(okSprite);
						break;
				}
		    }
		    this.clearTouchAreas();
  
	    	switch (pAction) {//modificar para uqe no haga falta el switch
				case 1:
					RegisterOldLoadMatchTouchAreas();
					switch(pStep){
					case 2:
						if(pNextStep){
				   	    	  this.mLoadMatchBottomRightSprite.setVisible(false);
						}
						Game.getTextHelper().ChangeText("Choose your character.", "MatchScene;StepText",200, 200);
						Game.getTextHelper().ChangeText("Paladin", "MatchScene;StepText1",100, 300);
			   	    	Game.getTextHelper().ChangeText("Mage", "MatchScene;StepText2",250, 300);
			   	    	Game.getTextHelper().ChangeText("Orc", "MatchScene;StepText3",335, 300);
			   	    	Game.getTextHelper().ChangeText("Archer", "MatchScene;StepText4",455, 300);
			   			  		    
			   	    	if(mChoices[0]==0){
			   	    		this.mPaladinSprite.setAlpha(1f);
		   					this.mMageSprite.setAlpha(1f);
		   					this.mOrcSprite.setAlpha(1f);
		   					this.mArcherSprite.setAlpha(1f);
			   	    	}else{
			   	    		this.mPaladinSprite.setAlpha(0.5f);
		   					this.mMageSprite.setAlpha(0.5f);
		   					this.mOrcSprite.setAlpha(0.5f);
		   					this.mArcherSprite.setAlpha(0.5f);			   					
		   					switch(mChoices[0]){
		   					case 1:this.mPaladinSprite.setAlpha(1f);break; 
		   					case 2:this.mMageSprite.setAlpha(1f);break;
		   					case 3:this.mOrcSprite.setAlpha(1f);break;
		   					case 4:this.mArcherSprite.setAlpha(1f);break;
		   					}
		   					this.mLoadMatchBottomRightSprite.setVisible(true);
			   	    	}
			   	    	mLoadMatchEntity.attachChild(mPaladinSprite);
		   				mLoadMatchEntity.attachChild(mMageSprite);
		   				mLoadMatchEntity.attachChild(mOrcSprite);
		   				mLoadMatchEntity.attachChild(mArcherSprite);
		   				this.registerTouchArea(mPaladinSprite);
		   				this.registerTouchArea(mMageSprite);
		   				this.registerTouchArea(mOrcSprite);
			   			this.registerTouchArea(mArcherSprite);
						
						if(!pNextStep){
							this.mLoadMatchTopRightSprite.setVisible(false);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite1);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite2);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite3);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite4);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite1);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite2);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite3);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite4);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite1);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite2);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite3);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite4);	
						}
						break;
					case 3:
						if(pNextStep){
							this.mLoadMatchBottomRightSprite.setVisible(false);
			   				this.mLoadMatchEntity.detachChild(mPaladinSprite);
			   			  	this.mLoadMatchEntity.detachChild(mMageSprite);
			   				this.mLoadMatchEntity.detachChild(mOrcSprite);
			   				this.mLoadMatchEntity.detachChild(mArcherSprite);
						}
						
						if(mChoices[6]==0){
		        		  MatchScene.this.mLoadMatchTopRightSprite.setVisible(true);
		        		  mAttributePlusSprite1.setVisible(false);
		        		  mAttributePlusSprite2.setVisible(false);
		        		  mAttributePlusSprite3.setVisible(false);
		        		  mAttributePlusSprite4.setVisible(false);
						}
						if(mChoices[2]==1)mAttributeMinusSprite1.setVisible(false);						
						if(mChoices[3]==1)mAttributeMinusSprite2.setVisible(false);
						if(mChoices[4]==1)mAttributeMinusSprite3.setVisible(false);
						if(mChoices[5]==1)mAttributeMinusSprite4.setVisible(false);
						
						Game.getTextHelper().ChangeText("Distribute your attribute points.\n               Points left: "+String.valueOf(mChoices[6]), "MatchScene;StepText",150, 30);
						Game.getTextHelper().ChangeText("Power: "+String.valueOf(mChoices[2]), "MatchScene;StepText1",mAttributeBackgroundSprite1.getX()+30, mAttributeBackgroundSprite1.getY()-20);
						Game.getTextHelper().ChangeText("Intelligence: "+String.valueOf(mChoices[3]), "MatchScene;StepText2",mAttributeBackgroundSprite2.getX()+10, mAttributeBackgroundSprite2.getY()-20);
						Game.getTextHelper().ChangeText("Defense: "+String.valueOf(mChoices[4]), "MatchScene;StepText3",mAttributeBackgroundSprite3.getX()+30, mAttributeBackgroundSprite3.getY()-20);
						Game.getTextHelper().ChangeText("Endurance: "+String.valueOf(mChoices[5]), "MatchScene;StepText4",mAttributeBackgroundSprite4.getX()+20, mAttributeBackgroundSprite4.getY()-20);						
						
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite1);
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite2);
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite3);
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite4);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite1);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite2);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite3);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite4);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite1);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite2);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite3);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite4);			   	    	
			   	    	this.registerTouchArea(mAttributeBackgroundSprite1);
			   	    	this.registerTouchArea(mAttributeBackgroundSprite2);
			   	    	this.registerTouchArea(mAttributeBackgroundSprite3);
			   	    	this.registerTouchArea(mAttributeBackgroundSprite4);
			   	    	this.registerTouchArea(mAttributeMinusSprite1);
			   	    	this.registerTouchArea(mAttributeMinusSprite2);
			   	    	this.registerTouchArea(mAttributeMinusSprite3);
			   	    	this.registerTouchArea(mAttributeMinusSprite4);
			   	    	this.registerTouchArea(mAttributePlusSprite1);
			   	    	this.registerTouchArea(mAttributePlusSprite2);
			   	    	this.registerTouchArea(mAttributePlusSprite3);
			   	    	this.registerTouchArea(mAttributePlusSprite4);

						break;
					}
					break;
				default:
					RegisterNewLoadMatchTouchAreas();
					switch(pStep){
					case 0:
						this.previousSprite.setVisible(false);
						this.nextSprite.setVisible(false);
						this.okSprite.setVisible(false);
						Game.getTextHelper().ChangeText("Choose your character.", "MatchScene;StepText",200, 200);
						Game.getTextHelper().ChangeText("Paladin", "MatchScene;StepText1",100, 300);
			   	    	Game.getTextHelper().ChangeText("Mage", "MatchScene;StepText2",250, 300);
			   	    	Game.getTextHelper().ChangeText("Orc", "MatchScene;StepText3",335, 300);
			   	    	Game.getTextHelper().ChangeText("Archer", "MatchScene;StepText4",455, 300);
			   			  		    
			   	    	if(mChoices[0]==0){
			   	    		this.mPaladinSprite.setAlpha(1f);
		   					this.mMageSprite.setAlpha(1f);
		   					this.mOrcSprite.setAlpha(1f);
		   					this.mArcherSprite.setAlpha(1f);
			   	    	}else{
			   	    		this.mPaladinSprite.setAlpha(0.5f);
		   					this.mMageSprite.setAlpha(0.5f);
		   					this.mOrcSprite.setAlpha(0.5f);
		   					this.mArcherSprite.setAlpha(0.5f);			   					
		   					switch(mChoices[0]){
		   					case 1:this.mPaladinSprite.setAlpha(1f);break; 
		   					case 2:this.mMageSprite.setAlpha(1f);break;
		   					case 3:this.mOrcSprite.setAlpha(1f);break;
		   					case 4:this.mArcherSprite.setAlpha(1f);break;
		   					}
		   					this.nextSprite.setVisible(true);
			   	    	}
			   	    	mLoadMatchEntity.attachChild(mPaladinSprite);
		   				mLoadMatchEntity.attachChild(mMageSprite);
		   				mLoadMatchEntity.attachChild(mOrcSprite);
		   				mLoadMatchEntity.attachChild(mArcherSprite);
		   				this.registerTouchArea(mPaladinSprite);
		   				this.registerTouchArea(mMageSprite);
		   				this.registerTouchArea(mOrcSprite);
			   			this.registerTouchArea(mArcherSprite);
						
						if(!pNextStep){
							this.okSprite.setVisible(false);
							this.previousSprite.setVisible(false);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite1);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite2);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite3);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeBackgroundSprite4);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite1);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite2);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite3);
				   	    	this.mLoadMatchEntity.detachChild(mAttributeMinusSprite4);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite1);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite2);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite3);
				   	    	this.mLoadMatchEntity.detachChild(mAttributePlusSprite4);	
						}
						break;
					case 1:
						if(pNextStep){
							this.nextSprite.setVisible(false);
							this.previousSprite.setVisible(true);
			   				this.mLoadMatchEntity.detachChild(mPaladinSprite);
			   			  	this.mLoadMatchEntity.detachChild(mMageSprite);
			   				this.mLoadMatchEntity.detachChild(mOrcSprite);
			   				this.mLoadMatchEntity.detachChild(mArcherSprite);
						}
						
						if(mChoices[6]==0){
		        		  MatchScene.this.okSprite.setVisible(true);
		        		  mAttributePlusSprite1.setVisible(false);
		        		  mAttributePlusSprite2.setVisible(false);
		        		  mAttributePlusSprite3.setVisible(false);
		        		  mAttributePlusSprite4.setVisible(false);
						}
						if(mChoices[2]==1)mAttributeMinusSprite1.setVisible(false);						
						if(mChoices[3]==1)mAttributeMinusSprite2.setVisible(false);
						if(mChoices[4]==1)mAttributeMinusSprite3.setVisible(false);
						if(mChoices[5]==1)mAttributeMinusSprite4.setVisible(false);
						
						Game.getTextHelper().ChangeText("Distribute your attribute points.\n               Points left: "+String.valueOf(mChoices[6]), "MatchScene;StepText",150, 50);
						Game.getTextHelper().ChangeText("Power: "+String.valueOf(mChoices[2]), "MatchScene;StepText1",mAttributeBackgroundSprite1.getX()+30, mAttributeBackgroundSprite1.getY()-20);
						Game.getTextHelper().ChangeText("Intelligence: "+String.valueOf(mChoices[3]), "MatchScene;StepText2",mAttributeBackgroundSprite2.getX()+10, mAttributeBackgroundSprite2.getY()-20);
						Game.getTextHelper().ChangeText("Defense: "+String.valueOf(mChoices[4]), "MatchScene;StepText3",mAttributeBackgroundSprite3.getX()+30, mAttributeBackgroundSprite3.getY()-20);
						Game.getTextHelper().ChangeText("Endurance: "+String.valueOf(mChoices[5]), "MatchScene;StepText4",mAttributeBackgroundSprite4.getX()+20, mAttributeBackgroundSprite4.getY()-20);						
						
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite1);
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite2);
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite3);
			   	    	mLoadMatchEntity.attachChild(mAttributeBackgroundSprite4);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite1);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite2);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite3);
			   	    	mLoadMatchEntity.attachChild(mAttributeMinusSprite4);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite1);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite2);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite3);
			   	    	mLoadMatchEntity.attachChild(mAttributePlusSprite4);			   	    	
			   	    	this.registerTouchArea(mAttributeBackgroundSprite1);
			   	    	this.registerTouchArea(mAttributeBackgroundSprite2);
			   	    	this.registerTouchArea(mAttributeBackgroundSprite3);
			   	    	this.registerTouchArea(mAttributeBackgroundSprite4);
			   	    	this.registerTouchArea(mAttributeMinusSprite1);
			   	    	this.registerTouchArea(mAttributeMinusSprite2);
			   	    	this.registerTouchArea(mAttributeMinusSprite3);
			   	    	this.registerTouchArea(mAttributeMinusSprite4);
			   	    	this.registerTouchArea(mAttributePlusSprite1);
			   	    	this.registerTouchArea(mAttributePlusSprite2);
			   	    	this.registerTouchArea(mAttributePlusSprite3);
			   	    	this.registerTouchArea(mAttributePlusSprite4);

						break;
					}
					break;						

	    	}

	    }

		
		
	private boolean changePoints(boolean pAdding,int pSelection){
		if(pAdding){
			switch(pSelection){
			case 1:
				mChoices[2]+=1;
				mChoices[6]-=1;
				this.mAttributeMinusSprite1.setVisible(true);
				Game.getTextHelper().ChangeText("Power: "+String.valueOf(mChoices[2]), "MatchScene;StepText1",mAttributeBackgroundSprite1.getX()+30, mAttributeBackgroundSprite1.getY()-20);
				break;
			case 2:
				mChoices[3]+=1;
				mChoices[6]-=1;
				this.mAttributeMinusSprite2.setVisible(true);
				Game.getTextHelper().ChangeText("Intelligence: "+String.valueOf(mChoices[3]), "MatchScene;StepText2",mAttributeBackgroundSprite2.getX()+10, mAttributeBackgroundSprite2.getY()-20);
				break;
			case 3:
				mChoices[4]+=1;
				mChoices[6]-=1;
				this.mAttributeMinusSprite3.setVisible(true);
				Game.getTextHelper().ChangeText("Defense: "+String.valueOf(mChoices[4]), "MatchScene;StepText3",mAttributeBackgroundSprite3.getX()+30, mAttributeBackgroundSprite3.getY()-20);
				break;
			case 4:
				mChoices[5]+=1;
				mChoices[6]-=1;
				this.mAttributeMinusSprite4.setVisible(true);
				Game.getTextHelper().ChangeText("Endurance: "+String.valueOf(mChoices[5]), "MatchScene;StepText4",mAttributeBackgroundSprite4.getX()+20, mAttributeBackgroundSprite4.getY()-20);
				break;
			}
		}else{
			switch(pSelection){
			case 1:
				mChoices[2]-=1;
				mChoices[6]+=1;
				if(mChoices[2]==1)this.mAttributeMinusSprite1.setVisible(false);
				Game.getTextHelper().ChangeText("Power: "+String.valueOf(mChoices[2]), "MatchScene;StepText1",mAttributeBackgroundSprite1.getX()+30, mAttributeBackgroundSprite1.getY()-20);
				break;
			case 2:
				mChoices[3]-=1;
				mChoices[6]+=1;
				if(mChoices[3]==1)this.mAttributeMinusSprite2.setVisible(false);
				Game.getTextHelper().ChangeText("Intelligence: "+String.valueOf(mChoices[3]), "MatchScene;StepText2",mAttributeBackgroundSprite2.getX()+10, mAttributeBackgroundSprite2.getY()-20);
				break;
			case 3:
				mChoices[4]-=1;
				mChoices[6]+=1;
				if(mChoices[4]==1)this.mAttributeMinusSprite3.setVisible(false);
				Game.getTextHelper().ChangeText("Defense: "+String.valueOf(mChoices[4]), "MatchScene;StepText3",mAttributeBackgroundSprite3.getX()+30, mAttributeBackgroundSprite3.getY()-20);
				break;
			case 4:
				mChoices[5]-=1;
				mChoices[6]+=1;
				if(mChoices[5]==1)this.mAttributeMinusSprite4.setVisible(false);
				Game.getTextHelper().ChangeText("Endurance: "+String.valueOf(mChoices[5]), "MatchScene;StepText4",mAttributeBackgroundSprite4.getX()+20, mAttributeBackgroundSprite4.getY()-20);
				break;
			}
		
		}
		Game.getTextHelper().ChangeText("Distribute your attribute points.\n               Points left: "+String.valueOf(mChoices[6]), "MatchScene;StepText",150, 30);
		if(mChoices[6]==0){
			this.mAttributePlusSprite1.setVisible(false);
			this.mAttributePlusSprite2.setVisible(false);
			this.mAttributePlusSprite3.setVisible(false);
			this.mAttributePlusSprite4.setVisible(false);
			return true;
		}else{
			this.mAttributePlusSprite1.setVisible(true);
			this.mAttributePlusSprite2.setVisible(true);
			this.mAttributePlusSprite3.setVisible(true);
			this.mAttributePlusSprite4.setVisible(true);
			return false;
		}
		
	}
	
		
	private void RegisterOldLoadMatchTouchAreas(){
		this.registerTouchArea(this.mLoadMatchTopLeftSprite);
		this.registerTouchArea(this.mLoadMatchTopRightSprite);
		this.registerTouchArea(this.mLoadMatchBottomLeftSprite);
		this.registerTouchArea(this.mLoadMatchBottomRightSprite);
	}
		
	private void RegisterNewLoadMatchTouchAreas(){
		this.registerTouchArea(this.previousSprite);
		this.registerTouchArea(this.nextSprite);
		this.registerTouchArea(this.okSprite);
		this.registerTouchArea(this.mLoadMatchTopLeftSprite);
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
	
	public void SwitchEntity(Entity pEntity){
		this.detachChild(this.mCurrentEntity);
		this.mCurrentEntity = pEntity;//algun dispose para borrar lo viejo?
		this.attachChild(this.mCurrentEntity);
	}
	
	public void RequestConnection(String pIP,String pPassword,String pMatchName,String pUserID){
		initClient(pIP);
		Game.setProfileData(new ProfileData(pUserID,Game.getDataHandler().getUsername(pUserID)));
		Game.setMatchData(new MatchData(pMatchName,pPassword));//****Cambiar a client
		if(AVD_DEBUGGING){
			Game.getClient().sendConnectionRequestMessage("11:11:11:11:11:11","Username2",pPassword,pMatchName);
		}else{
			Game.getClient().sendConnectionRequestMessage(Game.getUserID(),Game.getDataHandler().getUsername(1),pPassword,pMatchName);
		}
		Game.getClient().sendPingMessage();

	} 
	
	public void ClearTouchAreas(){
		MatchScene.this.clearTouchAreas();
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
						if(Game.getTextHelper().getText("MatchScene;OwnIP").getText().equals(ipAddressAsString)){//lo pongo separado porque con || no funcatring pUserID,boolean pHasPassword,float pTextX,float pTextY, String pKey) 
							conts=true;
						}//comentado para el AVD, sacar coment para el celu ***
						if(conts==false){
							Game.getDataHandler().CheckAndAddProfile(pDiscoveryData.getUserID(),pDiscoveryData.getUsername());
							if(MatchScene.this.mCurrentEntity == MatchScene.this.mMatchesEntity){//lo pongo separado porque con || no funcatring pUserID,boolean pHasPassword,float pTextX,float pTextY, String pKey)
							MatchScene.this.mMatchList.add(new MatchObject(MatchScene.this.mMatchBackgroundTextureRegion,0, MatchScene.this.mMatchList.size()*163, MatchScene.this, ipAddressAsString, MatchScene.this.mDiscoveredMatchEntity,true,pDiscoveryData.getMatchName(),pDiscoveryData.getUserID(),pDiscoveryData.hasPassword(),"MatchScene;"+String.valueOf(MatchScene.this.mMatchList.size())));
							}
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

	public ITextureRegion getJoinedTexture(){
		return this.mJoinedTextureRegion;
	}
	
	public void setSelectedMatch(String pSelectedMatchName){
		this.mSelectedMatchName = pSelectedMatchName;
		Log.d("Quest!","Selected Match: "+this.mSelectedMatchName);
		for(int i = 0;i<this.mOwnMatchesList.size();i++){
			if(this.mSelectedMatchName.equals(this.mOwnMatchesList.get(i).getMatchName())){
				this.mOwnMatchesList.get(i).changeAlpha(0.85f);
			}else{
				this.mOwnMatchesList.get(i).changeAlpha(0.5f);
			}
		}
	}
	
	public void setSelectedCharacter(int pSelectedCharacterID){
		this.mSelectedCharacterID = pSelectedCharacterID;
		Log.d("Quest!","Selected Character: "+String.valueOf(this.mSelectedCharacterID));
		for(int i = 0;i<this.mCharacterList.size();i++){
			if(this.mSelectedCharacterID == this.mCharacterList.get(i).getCharacterID()){
				this.mCharacterList.get(i).changeAlpha(0.85f);
			}else{
				this.mCharacterList.get(i).changeAlpha(0.5f);
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
					public void onClick(DialogInterface dialog, int whichButton) {//*** que checkee que no deje en blanco
						if(editText.getText().toString().equals(null)||editText.getText().toString().equals("")||editText.getText().toString().equals(" ")||editText.getText().toString().equals("***Player***")){
							showUsernameInput();
						}else{
							Game.getDataHandler().setUsername(1,editText.getText().toString());
						}
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
