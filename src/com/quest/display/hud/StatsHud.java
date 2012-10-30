package com.quest.display.hud;

import java.util.Vector;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.constants.GameFlags;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.polygons.Polygon;
import com.quest.timers.Timer;

public class StatsHud extends HUD implements GameFlags{

	// ===========================================================
	// Constants
	// ===========================================================
	private int X1_OFFSET = 95;
	private int X2_OFFSET = 311;
	private int Y1_OFFSET = 25;
	private int Y2_OFFSET = 37;
	private float TOTAL_SIZE = 232;
	private float RECTANGLE_SIZE = 217;
	private float TIP_SIZE = 15;
	private float RECTANGLE_RATIO = (RECTANGLE_SIZE * 100) / TOTAL_SIZE;
	private float TIP_RATIO = (TIP_SIZE * 100) / TOTAL_SIZE;
	// ===========================================================
	// Fields
	// ===========================================================
	private HUD mHud;
	private Player mOwnPlayer;
	private BitmapTextureAtlas mTextureAtlas;
	private ITextureRegion mSwordTextureRegion;
	private Sprite mSwordSprite;
	private Polygon mHPpolygon;
	private Polygon mMPpolygon;
	private Vector<float[]> mVertices;
	private Text mHPtext;
	private Text mMPtext;
	private Text mLeveltext;
	private Timer mRefreshTimer;
	private int oldExp=-1;
	private Entity statsEntity;
	// ===========================================================
	// Constructors
	// ===========================================================
	public StatsHud(HUD pHud,Player pOwnPlayer) {
		this.mHud = pHud;
		this.mOwnPlayer = pOwnPlayer;
		if(statsEntity==null)statsEntity = new Entity(5,5);
		mVertices = new Vector<float[]>();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/HUD/");
		this.mTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512, 128, TextureOptions.BILINEAR);		
		this.mSwordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTextureAtlas, Game.getInstance().getApplicationContext(), "ShortSword.png", 0, 0);
		this.mTextureAtlas.load();
		
		this.mSwordSprite = new Sprite(0, 0, this.mSwordTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		
		this.mLeveltext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_HEALING, mSwordSprite.getX()+5, mSwordSprite.getY()+5, "L: ", "StatsHud;Level");
		this.mHPtext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_DAMAGE, mSwordSprite.getX()+100, mSwordSprite.getY()+5, "HP: ", "StatsHud;HP");
		this.mMPtext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_BLUE, mSwordSprite.getX()+100, mSwordSprite.getY()+mSwordSprite.getHeight()-10, "MP: ", "StatsHud;MP");
		
		statsEntity.attachChild(mSwordSprite);
		statsEntity.attachChild(this.mLeveltext);
		statsEntity.attachChild(this.mHPtext);
		statsEntity.attachChild(this.mMPtext);
		
		
		mVertices.add(new float[]{X1_OFFSET+((RECTANGLE_RATIO*pOwnPlayer.getCurrHP())/100)+((TIP_RATIO*pOwnPlayer.getCurrHP())/100),Y1_OFFSET});//Top left
		mVertices.add(new float[]{X1_OFFSET,Y1_OFFSET});//Top left
		mVertices.add(new float[]{((RECTANGLE_RATIO*pOwnPlayer.getModHP())/100)+X1_OFFSET,Y1_OFFSET});
		mVertices.add(new float[]{X1_OFFSET,Y2_OFFSET});//Bottom left
		mVertices.add(new float[]{((RECTANGLE_RATIO*pOwnPlayer.getModHP())/100)+X1_OFFSET,Y2_OFFSET});
		this.mHPpolygon = new Polygon(mVertices, Game.getInstance().getVertexBufferObjectManager(), 5,false);
		this.statsEntity.attachChild(mHPpolygon);
		
		this.mRefreshTimer = new Timer(0.2f, new ITimerCallback() 
			{	
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					updateHUD();
				}
			});
		Game.getTimerHelper().addTimer(mRefreshTimer, mOwnPlayer.getUserID()+"HUD");
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Entity getStatsEntity(){
		return this.statsEntity;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	private void updateHUD(){
		this.mHPtext.setText("HP: "+mOwnPlayer.getCurrHP()+"/"+mOwnPlayer.getModHP());
		this.mMPtext.setText("MP: "+mOwnPlayer.getCurrMana()+"/"+mOwnPlayer.getModMana());
		
		if(oldExp != mOwnPlayer.getExperience()){
			this.mLeveltext.setText("L: "+mOwnPlayer.getLevel());
			//update polygons
		}
		oldExp = mOwnPlayer.getExperience();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
