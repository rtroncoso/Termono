package com.quest.objects;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.quest.display.hud.StatsHud;

public class CommentCode {/*

	###############################
	Mover sprite con touch
	##############################	
	this.setPosition(pSceneTouchEvent.getX() - this.getWidth(), pSceneTouchEvent.getY() - this.getHeight());
	
	
	#########################################
	set texto
	##########################################
	private StatsHud mStatsHud;
	
	this.mStatsHud = new StatsHud(this.mGame);
	this.mStatsHud.getTermono().setText("a");
	this.attachChild(this.mStatsHud.getTermono());
	mStatsHud.getTermono().setText(String.valueOf(this.getX())+ "  " + String.valueOf(this.getY()));


   #############################
   ejemplo
   #############################
   	this.mItemsSprite = new Sprite(80, 200,this.mItemsTextureRegion,this.mGame.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					mStatsHud.getTermono().setText(String.valueOf(this.getX())+ "  " + String.valueOf(this.getY()));
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
				break;
			case TouchEvent.ACTION_UP:
				break;
			}
			return true;
			}					
		};

	
	
*/}
