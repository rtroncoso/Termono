package com.quest.network.messages.server;

public interface ServerMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================
	
	/* Server --> Client */
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_CLOSE = 1;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED = FLAG_MESSAGE_SERVER_CONNECTION_CLOSE + 1;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH = FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED + 1;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG = 0;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION = FLAG_MESSAGE_SERVER_CONNECTION_PONG + 1;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_PADDLE = FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION + 1;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED1 = 10;
}
