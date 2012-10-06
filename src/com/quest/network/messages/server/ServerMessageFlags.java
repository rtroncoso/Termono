package com.quest.network.messages.server;

public interface ServerMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================
	
	/* Server --> Client */
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG = 0;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PUNG = 2;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION = 20;
	
}
