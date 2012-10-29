package com.quest.scenes;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class LoadingScene extends Scene implements GameFlags{


	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mWalkingMageTextureAtlas;
	private ITiledTextureRegion mWalkingMageTextureRegion;
	private AnimatedSprite mWalkingMage;
	private Text mLoadingText;
	private Text mCurrentTaskText;
	// ===========================================================
	// Constructors
	// ===========================================================
	public LoadingScene() {
		mLoadingText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_FANCY, 0, 0, "LOADING", "LoadingScene");
		mLoadingText.setScale(2f);
		this.attachChild(mLoadingText);


		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/LoadingScene/");
		this.mWalkingMageTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 128, 64, TextureOptions.BILINEAR);
		this.mWalkingMageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mWalkingMageTextureAtlas, 
		Game.getInstance().getApplicationContext(), "WalkingMage.png", 0, 0, 4, 1);
		
		
		this.mWalkingMageTextureAtlas.load();
		
		this.mWalkingMage= new AnimatedSprite(Game.getSceneManager().getDisplay().getCameraWidth()+40,(-1*(this.mWalkingMageTextureRegion.getHeight() / 2)), 
				this.mWalkingMageTextureRegion, Game.getInstance().getEngine().getVertexBufferObjectManager());
		this.mWalkingMage.setCullingEnabled(true);
		this.mWalkingMage.setScale(2);

		this.attachChild(this.mWalkingMage);
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Text getCurrentTaskText() {
		return mCurrentTaskText;
	}


	public void setCurrentTaskText(Text mCurrentTaskText) {
		this.mCurrentTaskText = mCurrentTaskText;
	}


	public void loadingAnimation(boolean pAnimate) {
		if(pAnimate){
			this.mWalkingMage.animate(50,true,new IAnimationListener() {
				@Override
				public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
						int pInitialLoopCount) {
					mWalkingMage.setFlippedHorizontal(false);
					mWalkingMage.registerEntityModifier(new MoveModifier(4f, (Game.getSceneManager().getDisplay().getCameraWidth()/2)+40, (-1*(Game.getSceneManager().getDisplay().getCameraWidth()/2))-40,mWalkingMage.getY(), mWalkingMage.getY()));				
				}

				@Override
				public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
						int pOldFrameIndex, int pNewFrameIndex) {
					// TODO Auto-generated method stub
					if(mWalkingMage.isFlippedHorizontal()){
						mLoadingText.setPosition(mWalkingMage.getX()-(mLoadingText.getWidth()*1.5f), mWalkingMage.getY()+10);
					}else{
						mLoadingText.setPosition(mWalkingMage.getX()+mWalkingMage.getWidthScaled()+35, mWalkingMage.getY()+10);
					}
				}

				@Override
				public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
						int pRemainingLoopCount, int pInitialLoopCount) {
					if(mWalkingMage.getX()==(-1*(Game.getSceneManager().getDisplay().getCameraWidth()/2))-40){
						mWalkingMage.setFlippedHorizontal(true);
						mWalkingMage.registerEntityModifier(new MoveModifier(0.7f, (-1*(Game.getSceneManager().getDisplay().getCameraWidth()/2))-40, (Game.getSceneManager().getDisplay().getCameraWidth()/2)+40,mWalkingMage.getY(), mWalkingMage.getY()));
					}else if(mWalkingMage.getX()==(Game.getSceneManager().getDisplay().getCameraWidth()/2)+40){
						mWalkingMage.setFlippedHorizontal(false);
						mWalkingMage.registerEntityModifier(new MoveModifier(0.7f, (Game.getSceneManager().getDisplay().getCameraWidth()/2)+40, (-1*(Game.getSceneManager().getDisplay().getCameraWidth()/2))-40,mWalkingMage.getY(), mWalkingMage.getY()));
					}
				}

				@Override
				public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}else{
			this.mWalkingMage.stopAnimation();
		}
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	public void changeCurrentTaskText(String pCurrentTask){
		this.mCurrentTaskText.setText(pCurrentTask);
		this.mCurrentTaskText.setX((Game.getSceneManager().getDisplay().getDisplayWidth()/2)-(mCurrentTaskText.getWidth()/2));
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
