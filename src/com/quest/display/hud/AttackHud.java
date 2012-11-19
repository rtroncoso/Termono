package com.quest.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.timers.Timer;
import com.quest.util.constants.IGameConstants;

public class AttackHud extends HUD implements GameFlags,IGameConstants{


	// ===========================================================
	// Constants
	// ===========================================================
	private int OFFSET = 15;
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mAttackTextureAtlas;
	private ITextureRegion mAttackTextureRegion;
	private Sprite mAttackSprite;
	private boolean cooling = false;
	private Player mOwner;
	// ===========================================================
	// Constructors
	// ===========================================================
	public AttackHud() {

		// Init local Variables
		mOwner = Game.getPlayerHelper().getOwnPlayer();
		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		// Create Texture objects
		this.mAttackTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		this.mAttackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAttackTextureAtlas, Game.getInstance().getApplicationContext(), "AttackHud.png", 0, 0);

		// Load Texture into memory and on the screen
		Game.getInstance().getTextureManager().loadTexture(this.mAttackTextureAtlas);
		
		setAttackSprite(new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth()-mAttackTextureRegion.getWidth()-OFFSET, Game.getSceneManager().getDisplay().getCameraHeight()-mAttackTextureRegion.getHeight()-OFFSET, mAttackTextureRegion, Game.getInstance().getVertexBufferObjectManager()){
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					this.mGrabbed = true;
					break;					
				case TouchEvent.ACTION_UP:
					if(this.mGrabbed == true){
						mOwner.onDisplayAttackingAction();
						Log.d("Quest!","Cooling: "+cooling);
						if(cooling == false){
							Mob tmpMob = Game.getMobHelper().getMobInDirection(mOwner.getFacingDirection(), mOwner.getTMXTileAt().getTileColumn(), mOwner.getTMXTileAt().getTileRow(), mOwner.getCurrentMap());
							if(tmpMob!=null){
								//si me laggea el ataque grafico cambiar por esto
								//mOwner.onAttackAction(tmpMob, FLAG_ATTACK_NORMAL);
								//lo unico que cambia es que solo se va a animar si ataca a un mob
								Game.getSceneManager().getGameScene().changeMobHUD(tmpMob);
								Game.getBattleHelper().startAttack(mOwner, FLAG_ATTACK_NORMAL, tmpMob);
								startCooldown(1);
							}
						}
					}
					mGrabbed = false;
					break;
				}
				return true;
			}
		});
		
	}
	


    
	// ===========================================================
	// Methods
	// ===========================================================
	public void startCooldown(float time){
			cooling = true;
			Game.getTimerHelper().addTimer(new Timer(time, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					cooling = false;
					Game.getTimerHelper().deleteTimer(mOwner.getUserID()+";Cooldown");
				}
			}), mOwner.getUserID()+";Cooldown");
	}



	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================


	public Sprite getAttackSprite() {
		return mAttackSprite;
	}




	public void setAttackSprite(Sprite mAttackSprite) {
		this.mAttackSprite = mAttackSprite;
	}
	
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
}