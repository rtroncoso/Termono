package com.quest.constants;

public interface ClientMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================

	/* Client --> Server */
	//Match - connection flags
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_PING = 200;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST = 201;
	public static final short FLAG_MESSAGE_CLIENT_PLAYER_CREATE = 202;
	public static final short FLAG_MESSAGE_CLIENT_SELECTED_PLAYER = 203;
	
	//Movement flags
	public static final short FLAG_MESSAGE_CLIENT_MOVE_PLAYER = 210;
	public static final short FLAG_MESSAGE_CLIENT_PLAYER_CHANGED_MAP= 211;

	//Battle flags
	public static final short FLAG_MESSAGE_CLIENT_ATTACK_MESSAGE = 220;
	public static final short FLAG_MESSAGE_CLIENT_AREA_ATTACK_MESSAGE = 221;
}
