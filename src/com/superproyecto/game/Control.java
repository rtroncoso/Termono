package com.superproyecto.game;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import com.superproyecto.player.Player;


public class Control {

	/*
	 * PRIVATE FIELDS
	 */
	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private DigitalOnScreenControl mDigitalOnScreenControl;
	private Game mGame;
	private Player mPlayer;
	
	public Control(Game pGame, Player pPlayer) {
		
		// Set internal Fields
		this.mGame = pGame;
		this.mPlayer = pPlayer;

		// Load controls texture into memory
		this.mOnScreenControlTexture = new BitmapTextureAtlas(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this.mGame.getApplicationContext(), "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this.mGame.getApplicationContext(), "onscreen_control_knob.png", 128, 0);
		this.mGame.getTextureManager().loadTextures(this.mOnScreenControlTexture);

		this.mDigitalOnScreenControl = new DigitalOnScreenControl(0, this.mGame.getDisplay().getCameraHeight() - this.mOnScreenControlBaseTextureRegion.getHeight(),
				this.mGame.getDisplay().getCamera(),
				this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f,
				new IOnScreenControlListener() {
			
					@Override
					public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX, float pValueY) {
						
						// Controls it's attached Entity
						Control.this.mPlayer.onControlChange(pBaseOnScreenControl, pValueX, pValueY);
					}
				});

		this.mDigitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mDigitalOnScreenControl.getControlBase().setAlpha(0.3f);
		this.mDigitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		this.mDigitalOnScreenControl.getControlBase().setScale(1.0f);
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.0f);
		this.mDigitalOnScreenControl.refreshControlKnobPosition();

	}
	
	public DigitalOnScreenControl getDigitalOnScreenControl() {
		return this.mDigitalOnScreenControl;
	}
}
