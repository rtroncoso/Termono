package com.quest.constants;

public interface ServerMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================
	
	/* Server --> Client */
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG = 100;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE = 101;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_REFUSE = 102;
	public static final short FLAG_MESSAGE_SERVER_CREATE_PLAYER = 103;
	public static final short FLAG_MESSAGE_SERVER_EXISTING_PLAYER = 104;
	public static final short FLAG_MESSAGE_SERVER_SEND_PLAYER = 105;
	public static final short FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION = 120;
	
}
