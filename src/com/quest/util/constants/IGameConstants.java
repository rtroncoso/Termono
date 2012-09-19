package com.quest.util.constants;


/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 19:49:20 - 28.02.2011
 */
public interface IGameConstants {
	// ===========================================================
	// Final Fields
	// ===========================================================

	public static final int GAME_FPS = 60;

	public static final int GAME_WIDTH = 720;
	public static final int GAME_WIDTH_HALF = GAME_WIDTH / 2;
	public static final int GAME_HEIGHT = 480;
	public static final int GAME_HEIGHT_HALF = GAME_HEIGHT / 2;

	public static final int PADDLE_WIDTH = 20;
	public static final int PADDLE_WIDTH_HALF = PADDLE_WIDTH / 2;
	public static final int PADDLE_HEIGHT = 80;
	public static final int PADDLE_HEIGHT_HALF = PADDLE_HEIGHT / 2;

	public static final int BALL_WIDTH = 10;
	public static final int BALL_HEIGHT = 10;
	public static final int BALL_RADIUS = BALL_WIDTH / 2;

	public static final int SCORE_PADDING = 5;

	public static final int SERVER_PORT = 4444;

	public static final byte DIRECTION_DEFAULT = 0;
	public static final byte DIRECTION_EAST = 1;
	public static final byte DIRECTION_WEST = 2;
	public static final byte DIRECTION_SOUTH = 3;
	public static final byte DIRECTION_NORTH = 4;
	
	// ===========================================================
	// Methods
	// ===========================================================
}