package com.quest.polygons;

import java.util.Vector;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.shader.ShaderProgram;

public abstract class PolygonShape extends Shape implements IAreaShape {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	protected float mWidth;
	protected float mHeight;
	protected Vector<float[]> mVertices;
	// ===========================================================
	// Constructors
	// ===========================================================

	public PolygonShape(final Vector<float[]> pVertices, final ShaderProgram pShaderProgram) {
	//	super(pVertices.get(0)[0], pVertices.get(0)[1], pShaderProgram);
		super(0,0, pShaderProgram);
		float widthmin = 0;
		float widthmax = 0;
		float heightmin = 0;
		float heightmax = 0;
		mVertices = pVertices;
		for(int i = pVertices.size()-1;i>=0;i--){
				if(pVertices.get(i)[0]<widthmin)widthmin = pVertices.get(i)[0];
				if(pVertices.get(i)[0]>widthmax)widthmax = pVertices.get(i)[0];
				if(pVertices.get(i)[1]<heightmin)heightmin = pVertices.get(i)[1];
				if(pVertices.get(i)[1]>heightmax)heightmax = pVertices.get(i)[1];
		}
		
		this.mWidth = widthmax-widthmin;
		this.mHeight = heightmax-heightmin;
		this.resetRotationCenter();
		this.resetScaleCenter();
		this.resetSkewCenter();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Vector<float[]> getVertices(){
		return this.mVertices;
	}
	
	@Override
	public float getWidth() {
		return this.mWidth;
	}

	@Override
	public float getHeight() {
		return this.mHeight;
	}

	@Override
	public void setWidth(final float pWidth) {
		this.mWidth = pWidth;
		this.onUpdateVertices();
	}

	@Override
	public void setHeight(final float pHeight) {
		this.mHeight = pHeight;
		this.onUpdateVertices();
	}

	@Override
	public void setSize(final float pWidth, final float pHeight) {
		this.mWidth = pWidth;
		this.mHeight = pHeight;
		this.onUpdateVertices();
	}

	@Override
	public float getWidthScaled() {
		return this.getWidth() * this.mScaleX;
	}

	@Override
	public float getHeightScaled() {
		return this.getHeight() * this.mScaleY;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void reset() {
		super.reset();

		this.resetRotationCenter();
		this.resetSkewCenter();
		this.resetScaleCenter();
	}

	@Override
	public float[] getSceneCenterCoordinates() {
		return this.convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f);
	}

	public float[] getSceneCenterCoordinates(final float[] pReuse) {
		return this.convertLocalToSceneCoordinates(this.mWidth * 0.5f, this.mHeight * 0.5f, pReuse);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void resetRotationCenter() {
		this.mRotationCenterX = this.mWidth * 0.5f;
		this.mRotationCenterY = this.mHeight * 0.5f;
	}

	public void resetScaleCenter() {
		this.mScaleCenterX = this.mWidth * 0.5f;
		this.mScaleCenterY = this.mHeight * 0.5f;
	}

	public void resetSkewCenter() {
		this.mSkewCenterX = this.mWidth * 0.5f;
		this.mSkewCenterY = this.mHeight * 0.5f;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}