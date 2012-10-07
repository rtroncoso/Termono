package com.quest.network.messages.server;

public interface ServerMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================
	
	/* Server --> Client */
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG = 100;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE = 101;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_REFUSE = 102;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION = 120;
	
}
