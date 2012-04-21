/**
 * 
 */
package com.termono.player;

import java.util.Random;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.util.modifier.ease.EaseLinear;

import com.termono.game.Game;
import com.termono.objects.Entity;

/**
 * @author raccoon
 *
 */
//public class Enemy extends Entity implements IOnScreenControlListener{
public class Enemy extends Entity{
	
	// ===========================================================
	// Constants
	// ===========================================================
	private final float SPEED_MODIFIER = 5.0f;
	private final float TILE_WIDTH = 32;
	private final float TILE_HEIGHT = 32;

	// ===========================================================
	// Fields
	// ===========================================================
	private int Vida;
	private PathModifier mPathModifier;
	private Path mPath;
	private Random rand;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Enemy(Game pEngine) {
		super(pEngine);	
		// TODO Auto-generated constructor stub
	}


	// ===========================================================
	// Methods
	// ===========================================================
public void moveToTile(final float pToTileX, final float pToTileY, final float pSpeed) {
		
		this.mPath = new Path(2).to(this.mAnimatedSprite.getX(), this.mAnimatedSprite.getY()).to(pToTileX, pToTileY);
		
		this.mPathModifier = new PathModifier(pSpeed / SPEED_MODIFIER, this.mPath, new IPathModifierListener() {

			@Override
			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				Enemy.this.isWalking = true;
				
				long frameDuration = (long) ((pSpeed / SPEED_MODIFIER) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };
				
				// RIGHT
				if(Enemy.this.mAnimatedSprite.getX() - pToTileX < 0)
					Enemy.this.mAnimatedSprite.animate(frameDurations, 8, 11, false);
				// LEFT
				if(Enemy.this.mAnimatedSprite.getX() - pToTileX > 0)
					Enemy.this.mAnimatedSprite.animate(frameDurations, 4, 7, false);
				// DOWN
				if(Enemy.this.mAnimatedSprite.getY() - pToTileY < 0)
					Enemy.this.mAnimatedSprite.animate(frameDurations, 0, 3, false);
				// UP
				if(Enemy.this.mAnimatedSprite.getY() - pToTileY > 0)
					Enemy.this.mAnimatedSprite.animate(frameDurations, 12, 15, false);
				
			}

			@Override
			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPathWaypointFinished(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPathFinished(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				
				Enemy.this.isWalking = false;
				//Enemy.this.mAnimatedSprite.stopAnimation();
				
			}	
		}, EaseLinear.getInstance());
		
		this.mAnimatedSprite.registerEntityModifier(this.mPathModifier);
	}
	
	
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
/*	
	public void RandomPath(int pValue) {
		if(pValue != 0.0f) {
			if(!this.isWalking) {
				// Gets the new Tile
				float moveToXTile = this.mAnimatedSprite.getX() + (TILE_WIDTH * pValueX);
				float moveToYTile = this.mAnimatedSprite.getY() + (TILE_HEIGHT * pValueY);
				
				// Moves to it
				this.moveToTile(moveToXTile, moveToYTile, 1.0f);
			}
		}
	}
	
	*/	
		
		public int GetRandom(int min, int max)
		{
		rand = new Random();	
		int RandomNum = rand.nextInt(max - min + 1) + min;
		return RandomNum;
		}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
