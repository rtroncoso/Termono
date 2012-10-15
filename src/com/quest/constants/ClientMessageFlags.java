package com.quest.constants;

public interface ClientMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================

	/* Client --> Server */
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_PING = 200;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST = 201;
	public static final short FLAG_MESSAGE_CLIENT_PLAYER_CREATE = 202;
	public static final short FLAG_MESSAGE_CLIENT_SELECTED_PLAYER = 203;
	public static final short FLAG_MESSAGE_CLIENT_MOVE_PLAYER = 210;

}
