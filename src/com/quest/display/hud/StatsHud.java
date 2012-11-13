package com.quest.display.hud;

import java.util.Vector;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.constants.GameFlags;
import com.quest.entities.BaseEntity;
import com.quest.game.Game;
import com.quest.polygons.Polygon;
import com.quest.timers.Timer;

public class StatsHud extends HUD implements GameFlags{

	// ===========================================================
	// Constants
	// ===========================================================
	private float RECTANGLE_HEIGHT = 12;
	private float TOTAL_SIZE = 232;
	private float RECTANGLE_SIZE = 217;
	private float TIP_SIZE = TOTAL_SIZE - RECTANGLE_SIZE;
	private float X1_OFFSET = 94;
	private float X2_OFFSET = 40;
	private float Y1_HP_OFFSET = 25;
	private float Y2_HP_OFFSET = Y1_HP_OFFSET + RECTANGLE_HEIGHT;
	private float Y1_MP_OFFSET = Y2_HP_OFFSET + 6;
	private float Y2_MP_OFFSET = Y1_MP_OFFSET + RECTANGLE_HEIGHT;
	private float SWORD_RATIO = (RECTANGLE_SIZE * 100) / TOTAL_SIZE;
	
	private float EXP_RECTANGLE_X1_OFFSET = 15;
	private float EXP_RECTANGLE_X2_OFFSET = 70-EXP_RECTANGLE_X1_OFFSET;
	private float EXP_RECTANGLE_X3_OFFSET = 263;
	private float EXP_RECTANGLE_Y1_OFFSET = 35;
	private float EXP_RECTANGLE_Y2_OFFSET = 44-EXP_RECTANGLE_Y1_OFFSET;
	
	private float NAME_X_OFFSET = 65;
	private float NAME_Y_OFFSET = 55;
	
	private float Hip = (((TIP_SIZE)*(TIP_SIZE)) + ((RECTANGLE_HEIGHT)*(RECTANGLE_HEIGHT)));
	private double Angle = Math.asin(((RECTANGLE_HEIGHT)/Math.sqrt(Hip)));
	private float Pend = (float) Math.tan(Angle);
	// ===========================================================
	// Fields
	// ===========================================================
	private HUD mHud;
	private BaseEntity mBaseEntity;
	private BitmapTextureAtlas mTextureAtlas;
	private ITextureRegion mSwordTextureRegion;
	private Sprite mSwordSprite;
	private Polygon mHPpolygon;
	private Polygon mMPpolygon;
	private Rectangle mExpRectangle;
	private Vector<float[]> mHPVertices;
	private Vector<float[]> mMPVertices;
	private Text mHPtext;
	private Text mMPtext;
	private Text mLeveltext;
	private Text mNameText;
	private Timer mRefreshTimer;
	private float oldExp=-1;
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
	
	float Offset = 0;
	float Offset2 = 0;
	// ===========================================================
	// Constructors
	// ===========================================================
	public StatsHud(HUD pHud,BaseEntity pEntity) {
		this.mHud = pHud;
		this.mBaseEntity = pEntity;
		if(statsEntity==null)statsEntity = new Entity(5,5);
		mHPVertices = new Vector<float[]>();
		mMPVertices = new Vector<float[]>();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/HUD/");
		this.mTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512, 128, TextureOptions.BILINEAR);		
		this.mSwordTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTextureAtlas, Game.getInstance().getApplicationContext(), "ShortSword.png", 0, 0);
		this.mTextureAtlas.load();
		
		this.mSwordSprite = new Sprite(0, 0, this.mSwordTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		float offtext = 100;
		float lvloffset = 0;
		if(!mBaseEntity.getUserData().equals(Game.getUserID())){
			Offset = mSwordSprite.getWidth() + X2_OFFSET;
			Offset2 = Offset + TOTAL_SIZE - X1_OFFSET + 10;//*** revisar el ultimo 10
			lvloffset = mSwordSprite.getWidth()-NAME_X_OFFSET;
			offtext = lvloffset-NAME_X_OFFSET*1.5f;
			mNameText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_NORMAL, Offset+mSwordSprite.getWidth()-NAME_X_OFFSET, mSwordSprite.getY()+NAME_Y_OFFSET, pEntity.getName(), "StatsHud;Name");
			this.statsEntity.attachChild(mNameText);
		}
		this.mSwordSprite.setX(0+Offset);
		
		
		this.mLeveltext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_YELLOW, mSwordSprite.getX()+5+lvloffset, mSwordSprite.getY()+5, "L: ", "StatsHud;Level");
		this.mLeveltext.setScale(2.5f);
		this.mHPtext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_DAMAGE, mSwordSprite.getX()+offtext, mSwordSprite.getY()+5, "HP: ", "StatsHud;HP");
		this.mMPtext = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_BLUE, mSwordSprite.getX()+offtext, mSwordSprite.getY()+mSwordSprite.getHeight()-15, "MP: ", "StatsHud;MP");
		
		this.mExpRectangle = new Rectangle(mSwordSprite.getX()+EXP_RECTANGLE_X1_OFFSET, EXP_RECTANGLE_Y1_OFFSET, EXP_RECTANGLE_X2_OFFSET, EXP_RECTANGLE_Y2_OFFSET, Game.getInstance().getVertexBufferObjectManager());
		this.mExpRectangle.setColor(0, 0.8f, 0);
		statsEntity.attachChild(mExpRectangle);
		
		statsEntity.attachChild(mSwordSprite);
		statsEntity.attachChild(this.mLeveltext);
		statsEntity.attachChild(this.mHPtext);
		statsEntity.attachChild(this.mMPtext);
		
		totHP = mBaseEntity.getModHP();
		currHP = mBaseEntity.getCurrHP();
		life_ratio = (totHP * SWORD_RATIO) / 100;
		currHP_pos = (currHP / (float)(totHP)) * TOTAL_SIZE; 
		
		mHPVertices.add(new float[]{X1_OFFSET+currHP_pos,Y2_HP_OFFSET}); //inicial
		mHPVertices.add(new float[]{X1_OFFSET+Offset2,Y2_HP_OFFSET});// abajo izq
		mHPVertices.add(new float[]{X1_OFFSET+Offset2,Y1_HP_OFFSET});// arriba izq
		mHPVertices.add(new float[]{X1_OFFSET+(((currHP-((currHP-life_ratio)*((int)(currHP/life_ratio))))/(float)(totHP))*TOTAL_SIZE),Y1_HP_OFFSET});// arriba der		
		mHPVertices.add(new float[]{X1_OFFSET+currHP_pos,((((currHP_pos-RECTANGLE_SIZE)*Pend))*((int)(currHP/life_ratio)))+Y1_HP_OFFSET});// arriba lineal
		this.mHPpolygon = new Polygon(mHPVertices, Game.getInstance().getVertexBufferObjectManager(), 5,false);		
		this.mHPpolygon.setColor(1f, 0.1f, 0.1f);
		this.statsEntity.attachChild(mHPpolygon);
		
		totMP = mBaseEntity.getModMana();if(totMP==0)mMPtext.setVisible(false);
		currMP = mBaseEntity.getCurrMana();
		mana_ratio = (totMP * SWORD_RATIO) / 100;
		currMP_pos = (currMP / (float)(totMP)) * TOTAL_SIZE; 
		
		mMPVertices.add(new float[]{X1_OFFSET+currMP_pos,Y1_MP_OFFSET}); //inicial
		mMPVertices.add(new float[]{X1_OFFSET+Offset2,Y1_MP_OFFSET});// abajo izq
		mMPVertices.add(new float[]{X1_OFFSET+Offset2,Y2_MP_OFFSET});// arriba izq
		mMPVertices.add(new float[]{X1_OFFSET+(((currMP-((currMP-mana_ratio)*((int)(currMP/mana_ratio))))/(float)(totMP))*TOTAL_SIZE),Y2_MP_OFFSET});// arriba der		
		mMPVertices.add(new float[]{X1_OFFSET+currMP_pos,((((currMP_pos-RECTANGLE_SIZE)*(-Pend)))*((int)(currMP/mana_ratio)))+Y2_MP_OFFSET});// arriba lineal
		this.mMPpolygon = new Polygon(mMPVertices, Game.getInstance().getVertexBufferObjectManager(), 5,false);
		this.mMPpolygon.setColor(0.1f, 0.1f, 1f);
		this.statsEntity.attachChild(mMPpolygon);
		

		if((mBaseEntity).getUserData().equals(Game.getUserID())){
			this.RegisterOwnPlayerRefreshTimer(mBaseEntity);
		}else{
			this.mSwordSprite.setFlippedHorizontal(true);
			this.mExpRectangle.setX(mSwordSprite.getX()+EXP_RECTANGLE_X3_OFFSET);
			this.RegisterOtherRefreshTimer(mBaseEntity);
		}		
			
		
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Entity getStatsEntity(){
		return this.statsEntity;
	}

	public BaseEntity getBaseEntity(){
		return this.mBaseEntity;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	private void updateOwnHUD(){
		if(oldExp != mBaseEntity.getExperience()){ 
			this.mLeveltext.setText("L: "+mBaseEntity.getLevel());
			this.mExpRectangle.setWidth((Game.getLevelHelper().getPlayerExptoNextLevel(mBaseEntity.getExperience())/ Game.getLevelHelper().getExptoNextLevel(mBaseEntity.getLevel())) * EXP_RECTANGLE_X2_OFFSET);
		}
		totHP = mBaseEntity.getModHP();
		totMP = mBaseEntity.getModMana();
		currHP = mBaseEntity.getCurrHP();
		currMP = mBaseEntity.getCurrMana();
		
		if(oldHP != currHP || oldMP != currMP){
			this.mHPtext.setText("HP: "+(int)currHP+"/"+(int)totHP);
			this.mMPtext.setText("MP: "+(int)currMP+"/"+(int)totMP);
			this.mHPpolygon.UpdateVertices(UpdateOwnHP(currHP, totHP));
			this.mMPpolygon.UpdateVertices(UpdateOwnMP(currMP, totMP));
		}
		
		oldExp = mBaseEntity.getExperience();
		oldHP = currHP;
		oldMP = currMP;
	}
	
	public Vector<float[]> UpdateOwnHP(float currHP,float totHP){
		life_ratio = (totHP * SWORD_RATIO) / 100;
		currHP_pos = (currHP / totHP) * TOTAL_SIZE;
		mHPVertices.setElementAt((new float[]{X1_OFFSET+currHP_pos,Y2_HP_OFFSET}),0); //inicial
		mHPVertices.setElementAt(new float[]{X1_OFFSET+(((currHP-((currHP-life_ratio)*((int)(currHP/life_ratio))))/(float)(totHP))*TOTAL_SIZE),Y1_HP_OFFSET},3);// arriba der		
		mHPVertices.setElementAt(new float[]{X1_OFFSET+currHP_pos,((((currHP_pos-RECTANGLE_SIZE)*Pend))*((int)(currHP/life_ratio)))+Y1_HP_OFFSET},4);// arriba lineal
		
		return mHPVertices;
	}
	
	public Vector<float[]> UpdateOwnMP(float currMP,float totMP){
		mana_ratio = (totMP * SWORD_RATIO) / 100;
		currMP_pos = (currMP / totMP) * TOTAL_SIZE;
		mMPVertices.setElementAt((new float[]{X1_OFFSET+currMP_pos,Y1_MP_OFFSET}),0); //inicial
		mMPVertices.setElementAt(new float[]{X1_OFFSET+(((currMP-((currMP-mana_ratio)*((int)(currMP/mana_ratio))))/(float)(totMP))*TOTAL_SIZE),Y2_MP_OFFSET},3);// arriba der		
		mMPVertices.setElementAt(new float[]{X1_OFFSET+currMP_pos,((((currMP_pos-RECTANGLE_SIZE)*(-Pend)))*((int)(currMP/mana_ratio)))+Y2_MP_OFFSET},4);// arriba lineal		
		return mMPVertices;
	}
	
	private void updateOtherHUD(){
		currHP = mBaseEntity.getCurrHP();
		currMP = mBaseEntity.getCurrMana();
		
		if(oldHP != currHP || oldMP != currMP){
			this.mHPtext.setText("HP: "+(int)currHP+"/"+(int)totHP);
			this.mMPtext.setText("MP: "+(int)currMP+"/"+(int)totMP);
			this.mLeveltext.setText("L: "+mBaseEntity.getLevel());
			this.mHPpolygon.UpdateVertices(UpdateOtherHP(currHP, totHP));
			this.mMPpolygon.UpdateVertices(UpdateOtherMP(currMP, totMP));
		}
		
		
		oldHP = currHP;
		oldMP = currMP;
	}
	
	public Vector<float[]> UpdateOtherHP(float currHP,float totHP){
		life_ratio = (totHP * SWORD_RATIO) / 100;
		currHP_pos = (currHP / totHP) * TOTAL_SIZE;
		mHPVertices.setElementAt((new float[]{X1_OFFSET+Offset2-currHP_pos,Y2_HP_OFFSET}),0); //inicial
		mHPVertices.setElementAt(new float[]{X1_OFFSET+Offset2-(((currHP-((currHP-life_ratio)*((int)(currHP/life_ratio))))/(float)(totHP))*TOTAL_SIZE),Y1_HP_OFFSET},3);// arriba der		
		mHPVertices.setElementAt(new float[]{X1_OFFSET+Offset2-currHP_pos,((((currHP_pos-RECTANGLE_SIZE)*Pend))*((int)(currHP/life_ratio)))+Y1_HP_OFFSET},4);// arriba lineal
		
		return mHPVertices;
	}
	
	public Vector<float[]> UpdateOtherMP(float currMP,float totMP){
		mana_ratio = (totMP * SWORD_RATIO) / 100;
		currMP_pos = (currMP / totMP) * TOTAL_SIZE;
		mMPVertices.setElementAt(new float[]{X1_OFFSET+Offset2-currMP_pos,Y1_MP_OFFSET},0); //inicial
		mMPVertices.setElementAt(new float[]{X1_OFFSET+Offset2-(((currMP-((currMP-mana_ratio)*((int)(currMP/mana_ratio))))/(float)(totMP))*TOTAL_SIZE),Y2_MP_OFFSET},3);// arriba der		
		mMPVertices.setElementAt(new float[]{X1_OFFSET+Offset2-currMP_pos,((((currMP_pos-RECTANGLE_SIZE)*(-Pend)))*((int)(currMP/mana_ratio)))+Y2_MP_OFFSET},4);// arriba lineal		
		return mMPVertices;
	}
	
	public void ChangeEntity(BaseEntity pNewEntity){
		if(mBaseEntity!=null)Game.getTimerHelper().deleteTimer(mBaseEntity.getUserData()+"HUD");
		this.mBaseEntity = pNewEntity;

		statsEntity.setVisible(true);
		totHP = mBaseEntity.getModHP();
		currHP = mBaseEntity.getCurrHP();
		life_ratio = (totHP * SWORD_RATIO) / 100;
		currHP_pos = (currHP / (float)(totHP)) * TOTAL_SIZE; 
		totMP = mBaseEntity.getModMana();
		currMP = mBaseEntity.getCurrMana();
		mana_ratio = (totMP * SWORD_RATIO) / 100;
		currMP_pos = (currMP / (float)(totMP)) * TOTAL_SIZE;
		mNameText.setText(pNewEntity.getName());
		if(totMP==0){
			mMPtext.setVisible(false);
		}else{
			mMPtext.setVisible(true);
		}
		this.RegisterOtherRefreshTimer(mBaseEntity);
	}
	
	public void dettachHUD(Integer pMobID){
		if(mBaseEntity != null && (Integer)(mBaseEntity.getUserData())==pMobID){
			Game.getTimerHelper().deleteTimer(mBaseEntity.getUserData()+"HUD");
			mBaseEntity = null;
			statsEntity.setVisible(false);
		}
	}

	public void dettachHUD(String pUserID){
		if(mBaseEntity != null && mBaseEntity.getUserData().toString().equals(pUserID)){
			Game.getTimerHelper().deleteTimer(mBaseEntity.getUserData()+"HUD");
			mBaseEntity = null;
			statsEntity.setVisible(false);
		}
	}
	
	public void RegisterOwnPlayerRefreshTimer(final BaseEntity pEntity){
		this.mRefreshTimer = new Timer(0.1f, new ITimerCallback() 
		{	
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				updateOwnHUD();
			}
		});
		Game.getTimerHelper().addTimer(mRefreshTimer, pEntity.getUserData()+"HUD");
	}
	
	public void RegisterOtherRefreshTimer(final BaseEntity pEntity){
		this.mRefreshTimer = new Timer(0.1f, new ITimerCallback() 
		{	
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				updateOtherHUD();
			}
		});
		Game.getTimerHelper().addTimer(mRefreshTimer, pEntity.getUserData()+"HUD");
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
