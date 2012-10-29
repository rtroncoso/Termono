package com.quest.scenes;

import java.util.Vector;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.quest.constants.GameFlags;
import com.quest.game.Game;
import com.quest.polygons.Polygon;

public class TestScene extends Scene implements GameFlags,IOnSceneTouchListener{
	// ===========================================================
	// Constants
	// ===========================================================
	private int VERTICES_AMOUNT = 20;
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mTestTextureAtlas;
	private ITextureRegion mButton1TextureRegion;
	private ITextureRegion mButton2TextureRegion;
	private ITextureRegion mBackgroundTextureRegion;
	private ITextureRegion mPointT;
	private Sprite mPoint1;
	private Sprite mPoint2;
	private Sprite mPoint3;
	private Sprite mButton1Sprite;
	private Sprite mButton2Sprite;
	private Sprite mBackgroundSprite;
	private int choice;
	private Polygon mPoly;
	private Vector<float[]> mVertices;
	// ===========================================================
	// Constructors
	// ===========================================================
	public TestScene(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/MatchScene/Main/");
		this.mTestTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 2056,2056, TextureOptions.BILINEAR);		
		this.mPointT = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTestTextureAtlas, Game.getInstance().getApplicationContext(), "Point.png", 0, 0);
		this.mButton1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTestTextureAtlas, Game.getInstance().getApplicationContext(), "new.png", 0, 9);
		this.mButton2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTestTextureAtlas, Game.getInstance().getApplicationContext(), "Ok.png", 65, 9);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mTestTextureAtlas, Game.getInstance().getApplicationContext(), "scroll.png", 0, 56);
		this.mTestTextureAtlas.load();
		
		
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		this.attachChild(mBackgroundSprite);
		
		this.mButton1Sprite = new Sprite(20, 20,this.mButton1TextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
						//showUsernameInput();
						mPoly.addVertice(new float[]{300,300});
						break;
					}
				}
				return true;
			}				
		};
		
		this.mButton2Sprite = new Sprite(this.mBackgroundSprite.getWidth()-100, 20,this.mButton2TextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
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
						
						break;
					}
				}
				return true;
			}				
		};
		mButton1Sprite.getTextureRegion().setTextureWidth(30);
		this.attachChild(mButton1Sprite);
		
		this.attachChild(mButton2Sprite);
		this.registerTouchArea(mButton1Sprite);
		this.registerTouchArea(mButton2Sprite);
		mVertices = new Vector<float[]>();
		mVertices.add(new float[]{100,100});
		mVertices.add(new float[]{300,100});
		mVertices.add(new float[]{200,300});
		mPoly = new Polygon(mVertices, Game.getInstance().getVertexBufferObjectManager(),VERTICES_AMOUNT);
		this.attachChild(mPoly);
		
		this.mPoint1 = new Sprite(mVertices.get(0)[0]+mVertices.get(0)[0]-4.5f, mVertices.get(0)[1]+mVertices.get(0)[1]-4.5f, this.mPointT, Game.getInstance().getVertexBufferObjectManager());
		mPoint1.setColor(0.1f, 0.9f, 0.1f);
		this.attachChild(mPoint1);
		this.mPoint2 = new Sprite(mVertices.get(1)[0]+mVertices.get(0)[0]-4.5f, mVertices.get(1)[1]+mVertices.get(0)[1]-4.5f, this.mPointT, Game.getInstance().getVertexBufferObjectManager());
		this.attachChild(mPoint2);
		this.mPoint3 = new Sprite(mVertices.get(2)[0]+mVertices.get(0)[0]-4.5f, mVertices.get(2)[1]+mVertices.get(0)[1]-4.5f, this.mPointT, Game.getInstance().getVertexBufferObjectManager());
		this.attachChild(mPoint3);
		
		
		this.attachChild(Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_NORMAL, 5, 5, "Touch the screen!", "vert"));
    	this.setTouchAreaBindingOnActionDownEnabled(true);
    	this.setOnSceneTouchListener(this);
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
	public void showUsernameInput() {
		Game.getInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(Game.getInstance());

				alert.setTitle("Enter instruction number");
				alert.setMessage("");
				final EditText editText = new EditText(Game.getInstance());
				editText.setTextSize(15f);
				editText.setText("");
				editText.setGravity(Gravity.CENTER_HORIZONTAL);
				alert.setView(editText);
				alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {//*** que checkee que no deje en blanco
						executeinst(Integer.parseInt(editText.getText().toString()));
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
	
	public void executeinst(int pInstruction){
		switch (pInstruction) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			
			break;
		case 5:
			
			break;
		case 6:
			
			break;
		case 7:
			
			break;
		case 8:
			
			break;
		case 9:
			
			break;
			
		default:
			break;
		}
		
	}

	public void ans(String pText){
		Game.getTextHelper().ChangeText(pText, "answertext", 0, this.mBackgroundSprite.getHeight()-50);
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			mPoint1.setPosition(pSceneTouchEvent.getX()-(mPoint1.getWidth()/2), pSceneTouchEvent.getY()-(mPoint1.getHeight()/2));
			break;
		case TouchEvent.ACTION_MOVE:
			mPoint1.setPosition(pSceneTouchEvent.getX()-(mPoint1.getWidth()/2), pSceneTouchEvent.getY()-(mPoint1.getHeight()/2));
			break;
		case TouchEvent.ACTION_UP:
			Game.getTextHelper().ChangeText("Vertices left: "+(VERTICES_AMOUNT-mVertices.size()-1)+" X: "+pSceneTouchEvent.getX()+" Y: "+pSceneTouchEvent.getY(),"vert", 5, 5);
			mPoly.addVertice(new float[]{pSceneTouchEvent.getX()-mVertices.get(0)[0],pSceneTouchEvent.getY()-mVertices.get(0)[1]});
			mPoly.setColor(Game.getRandomFloat(), Game.getRandomFloat(), Game.getRandomFloat());
			mPoint1.setPosition(mVertices.get(mVertices.size()-3)[0]+mVertices.get(0)[0]-(mPoint1.getWidth()/2), mVertices.get(mVertices.size()-3)[1]+mVertices.get(0)[1]-(mPoint1.getHeight()/2));
			mPoint2.setPosition(mVertices.get(mVertices.size()-2)[0]+mVertices.get(0)[0]-(mPoint2.getWidth()/2), mVertices.get(mVertices.size()-2)[1]+mVertices.get(0)[1]-(mPoint1.getHeight()/2));
			mPoint3.setPosition(mVertices.get(mVertices.size()-1)[0]+mVertices.get(0)[0]-(mPoint2.getWidth()/2), mVertices.get(mVertices.size()-1)[1]+mVertices.get(0)[1]-(mPoint1.getHeight()/2));
			break;
		}
	return true;
	}
}
