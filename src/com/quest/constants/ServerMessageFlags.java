package com.quest.constants;

public interface ServerMessageFlags {

	// ===========================================================
	// Final Fields
	// ===========================================================
	
	/* Server --> Client */
	//Connection - Match
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_PONG = 100;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE = 101;
	public static final short FLAG_MESSAGE_SERVER_CONNECTION_REFUSE = 102;
	public static final short FLAG_MESSAGE_SERVER_CREATE_PLAYER = 103;
	public static final short FLAG_MESSAGE_SERVER_EXISTING_PLAYER = 104;
	public static final short FLAG_MESSAGE_SERVER_SEND_PLAYER = 105;
	public static final short FLAG_MESSAGE_SERVER_MATCH_STARTED = 106;
	
	//Movement
	public static final short FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION = 120;
	public static final short FLAG_MESSAGE_SERVER_PLAYER_CHANGED_MAP = 121;
	
	//Mob
	public static final short FLAG_MESSAGE_SERVER_MOB_DIED = 131;
	public static final short FLAG_MESSAGE_SERVER_SPAWN_MOB = 132;
	public static final short FLAG_MESSAGE_SERVER_MOVE_MOB = 133;
	public static final short FLAG_MESSAGE_SERVER_EXISTING_MOB = 134;
	
	//Battle
	public static final short FLAG_MESSAGE_SERVER_FIXED_ATTACK_DATA = 140;
	public static final short FLAG_MESSAGE_SERVER_DISPLAY_AREA_ATTACK = 141;
	
	//Player
	public static final short FLAG_MESSAGE_SERVER_PLAYER_LEVELUP = 150;
	public static final short FLAG_MESSAGE_SERVER_SET_PLAYER_ATTRIBUTES = 151;
	
}

