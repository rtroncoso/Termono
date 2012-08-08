package com.quest.util.constants;

public interface INetworkConstants {

	/* Server --> Client */
	public static final short FLAG_MESSAGE_SERVER_SET_PADDLEID = 1;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_SCORE = FLAG_MESSAGE_SERVER_SET_PADDLEID + 1;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_BALL = FLAG_MESSAGE_SERVER_UPDATE_SCORE + 1;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_PADDLE = FLAG_MESSAGE_SERVER_UPDATE_BALL + 1;

	/* Client --> Server */
	public static final short FLAG_MESSAGE_CLIENT_MOVE_PADDLE = 1;
	
	
	public static final int SERVER_PORT = 4444;
}
