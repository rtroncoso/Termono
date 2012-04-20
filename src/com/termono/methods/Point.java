package com.termono.methods;

public class Point {

	private float mX;
	private float mY;

	public Point(float X, float Y) {
		this.mX = X;
		this.mY = Y;
	}

	public float getX() {
		return this.mX;
	}

	public float getY() {
		return this.mY;
	}

	public void setX(float X) {
		this.mX = X;
	}

	public void setY(float Y) {
		this.mY = Y;
	}
}
