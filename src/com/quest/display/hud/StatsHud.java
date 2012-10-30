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

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.polygons.Polygon;
import com.quest.timers.Timer;

public class StatsHud extends HUD implements GameFlags{

	// ===========================================================
	// Constants
	// ===========================================================
	private float RECTANGLE_HEIGHT = 12;
	private float X_OFFSET = 94;
	private float Y1_HP_OFFSET = 25;
	private float Y2_HP_OFFSET = Y1_HP_OFFSET + RECTANGLE_HEIGHT;
	private float Y1_MP_OFFSET = Y2_HP_OFFSET + 6;
	private float Y2_MP_OFFSET = Y1_MP_OFFSET + RECTANGLE_HEIGHT;
	private float TOTAL_SIZE = 232;
	private float RECTANGLE_SIZE = 217;
	private float TIP_SIZE = TOTAL_SIZE - RECTANGLE_SIZE;
	private float SWORD_RATIO = (RECTANGLE_SIZE * 100) / TOTAL_SIZE;
	
	private float Hip = (((TIP_SIZE)*(TIP_SIZE)) + ((RECTANGLE_HEIGHT)*(RECTANGLE_HEIGHT)));
	private double Angle = Math.asin(((RECTANGLE_HEIGHT)/Math.sqrt(Hip)));
	private float Pend = (float) Math.tan(Angle);
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
	private Vector<float[]> mHPVertices;
	private Vector<float[]> mMPVertices;
	private Text mHPtext;
	private Text mMPtext;
	private Text mLeveltext;
	private Timer mRefreshTimer;
	private int oldExp=-1;
	private float oldHP=-1;
	private float oldMP=-1;
	private Entity statsEntity;
	
	private float life_ratio;
	private float mana_ratio;
	private float currHP_pos;
	private float currMP_pos;
	private float currHP;
	private float currMP;
	private float totHP;
	private float totMP;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public StatsHud(HUD pHud,Player pOwnPlayer) {
		this.mHud = pHud;
		this.mOwnPlayer = pOwnPlayer;
		if(statsEntity==null)statsEntity = new Entity(5,5);
		mHPVertices = new Vector<float[]>();
		mMPVertices = new Vector<float[]>();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/HUD/");
		this.mTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512, 128, TextureOptions.BILINEAR);		
		this.mSwordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTextureAtlas, Game.getInstance().getApplicationContext(), "ShortSword.png", 0, 0);
		this.mTextureAtlas.load();
		
		this.mSwordSprite = new Sprite(0, 0, this.mSwordTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		
		this.mLeveltext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_HEALING, mSwordSprite.getX()+5, mSwordSprite.getY()+5, "L: ", "StatsHud;Level");
		this.mHPtext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_DAMAGE, mSwordSprite.getX()+100, mSwordSprite.getY()+5, "HP: ", "StatsHud;HP");
		this.mMPtext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_BLUE, mSwordSprite.getX()+100, mSwordSprite.getY()+mSwordSprite.getHeight()-15, "MP: ", "StatsHud;MP");
		
		statsEntity.attachChild(mSwordSprite);
		statsEntity.attachChild(this.mLeveltext);
		statsEntity.attachChild(this.mHPtext);
		statsEntity.attachChild(this.mMPtext);
		
		totHP = mOwnPlayer.getModHP();
		currHP = mOwnPlayer.getCurrHP();
		life_ratio = (totHP * SWORD_RATIO) / 100;
		currHP_pos = (currHP / (float)(totHP)) * TOTAL_SIZE; 
		
		mHPVertices.add(new float[]{X_OFFSET+currHP_pos,Y2_HP_OFFSET}); //inicial
		mHPVertices.add(new float[]{X_OFFSET,Y2_HP_OFFSET});// abajo izq
		mHPVertices.add(new float[]{X_OFFSET,Y1_HP_OFFSET});// arriba izq
		mHPVertices.add(new float[]{X_OFFSET+(((currHP-((currHP-life_ratio)*((int)(currHP/life_ratio))))/(float)(totHP))*TOTAL_SIZE),Y1_HP_OFFSET});// arriba der		
		mHPVertices.add(new float[]{X_OFFSET+currHP_pos,((((currHP_pos-RECTANGLE_SIZE)*Pend))*((int)(currHP/life_ratio)))+Y1_HP_OFFSET});// arriba lineal
		this.mHPpolygon = new Polygon(mHPVertices, Game.getInstance().getVertexBufferObjectManager(), 5,false);		
		this.mHPpolygon.setColor(1f, 0.1f, 0.1f);
		this.statsEntity.attachChild(mHPpolygon);
		
		totMP = mOwnPlayer.getModMana();
		currMP = mOwnPlayer.getCurrMana();
		mana_ratio = (totMP * SWORD_RATIO) / 100;
		currMP_pos = (currMP / (float)(totMP)) * TOTAL_SIZE; 
		
		mMPVertices.add(new float[]{X_OFFSET+currMP_pos,Y1_MP_OFFSET}); //inicial
		mMPVertices.add(new float[]{X_OFFSET,Y1_MP_OFFSET});// abajo izq
		mMPVertices.add(new float[]{X_OFFSET,Y2_MP_OFFSET});// arriba izq
		mMPVertices.add(new float[]{X_OFFSET+(((currMP-((currMP-mana_ratio)*((int)(currMP/mana_ratio))))/(float)(totMP))*TOTAL_SIZE),Y2_MP_OFFSET});// arriba der		
		mMPVertices.add(new float[]{X_OFFSET+currMP_pos,((((currMP_pos-RECTANGLE_SIZE)*(-Pend)))*((int)(currMP/mana_ratio)))+Y2_MP_OFFSET});// arriba lineal
		this.mMPpolygon = new Polygon(mMPVertices, Game.getInstance().getVertexBufferObjectManager(), 5,false);
		this.mMPpolygon.setColor(0.1f, 0.1f, 1f);
		this.statsEntity.attachChild(mMPpolygon);
		
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
		if(oldExp != mOwnPlayer.getExperience()){ 
			this.mLeveltext.setText("L: "+mOwnPlayer.getLevel());
			totHP = mOwnPlayer.getModHP();//solo getteo la vida si hubo cambio de exp(level)
			totMP = mOwnPlayer.getModMana();
			//update exp polygons
		}
		currHP = mOwnPlayer.getCurrHP();
		currMP = mOwnPlayer.getCurrMana();
		
		if(oldHP != currHP || oldMP != currMP){
			this.mHPtext.setText("HP: "+currHP+"/"+totHP);
			this.mMPtext.setText("MP: "+currMP+"/"+totMP);
			this.mHPpolygon.UpdateVertices(updatevida(currHP, totHP));
			this.mMPpolygon.UpdateVertices(updatemana(currMP, totMP));
		}
		
		oldExp = mOwnPlayer.getExperience();
		oldHP = currHP;
		oldMP = currMP;
	}
	
	public Vector<float[]> updatevida(float currHP,float totHP){
		life_ratio = (totHP * SWORD_RATIO) / 100;
		currHP_pos = (currHP / totHP) * TOTAL_SIZE;
		mHPVertices.setElementAt((new float[]{X_OFFSET+currHP_pos,Y2_HP_OFFSET}),0); //inicial
		mHPVertices.setElementAt(new float[]{X_OFFSET+(((currHP-((currHP-life_ratio)*((int)(currHP/life_ratio))))/(float)(totHP))*TOTAL_SIZE),Y1_HP_OFFSET},3);// arriba der		
		mHPVertices.setElementAt(new float[]{X_OFFSET+currHP_pos,((((currHP_pos-RECTANGLE_SIZE)*Pend))*((int)(currHP/life_ratio)))+Y1_HP_OFFSET},4);// arriba lineal
		
		return mHPVertices;
	}
	
	public Vector<float[]> updatemana(float currMP,float totMP){
		mana_ratio = (totMP * SWORD_RATIO) / 100;
		currMP_pos = (currMP / totMP) * TOTAL_SIZE;
		mMPVertices.setElementAt((new float[]{X_OFFSET+currMP_pos,Y1_MP_OFFSET}),0); //inicial
		mMPVertices.setElementAt(new float[]{X_OFFSET+(((currMP-((currMP-mana_ratio)*((int)(currMP/mana_ratio))))/(float)(totMP))*TOTAL_SIZE),Y2_MP_OFFSET},3);// arriba der		
		mMPVertices.setElementAt(new float[]{X_OFFSET+currMP_pos,((((currMP_pos-RECTANGLE_SIZE)*(-Pend)))*((int)(currMP/mana_ratio)))+Y2_MP_OFFSET},4);// arriba lineal		
		return mMPVertices;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
