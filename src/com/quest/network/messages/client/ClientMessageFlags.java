package com.quest.network.messages.client;

public interface ClientMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================

	/* Client --> Server */
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_PING = 1;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST = 3;
	public static final short FLAG_MESSAGE_CLIENT_MOVE_PLAYER = 210;

}
