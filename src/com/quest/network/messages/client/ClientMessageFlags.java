package com.quest.network.messages.client;

public interface ClientMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================

	/* Client --> Server */
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH = 1;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE = FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH + 1;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_PING = 0;
	public static final short FLAG_MESSAGE_CLIENT_MOVE_PLAYER = FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE + 1;
	public static final short FLAG_MESSAGE_CLIENT_CONNECT_TO_MATCH = 10;

}
