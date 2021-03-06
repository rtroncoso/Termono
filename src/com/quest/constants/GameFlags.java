package com.quest.constants;

public interface GameFlags {
	// ===========================================================
	// Final Fields
	// ===========================================================
	//Mobs
	public static final int FLAG_MOB_BAT = 1;
	public static final int FLAG_MOB_BEE = 2;
	public static final int FLAG_MOB_CHICKEN = 3;
	public static final int FLAG_MOB_FAIRY = 99;
	
	//Text Types
	public static final int FLAG_TEXT_TYPE_NORMAL = 1;
	public static final int FLAG_TEXT_TYPE_DAMAGE = 2;
	public static final int FLAG_TEXT_TYPE_HEALING = 3;
	public static final int FLAG_TEXT_TYPE_FANCY = 4;
	public static final int FLAG_TEXT_TYPE_BLUE = 5;
	public static final int FLAG_TEXT_TYPE_FANCY_DARK = 6;
	public static final int FLAG_TEXT_TYPE_BLACK_KNIGHT = 7;
	public static final int FLAG_TEXT_TYPE_YELLOW = 8;
	
	
	//Attacks
	public static final int FLAG_ATTACK_NORMAL = 20;
	
	public static final int FLAG_ATTACK_MOB_ATTACK_1 = 21;
	public static final int FLAG_ATTACK_MOB_ATTACK_2 = 22;
	public static final int FLAG_ATTACK_MOB_ATTACK_3 = 23;
	public static final int FLAG_ATTACK_MOB_ATTACK_4 = 24;
	
	public static final int FLAG_ATTACK_SPELL_FIREBALL = 1;
	public static final int FLAG_ATTACK_SPELL_BLAST = 2;
	public static final int FLAG_ATTACK_SPELL_THUNDER = 3;
	public static final int FLAG_ATTACK_SPELL_ICE_RING = 4;
	public static final int FLAG_ATTACK_SPELL_ICE_BASH = 5;
	
	//Other animations
	public static final int FLAG_ATTACK_MOB_DEATH = 30;
	public static final int FLAG_ATTACK_PLAYER_DEATH = 31;
	public static final int FLAG_ATTACK_PLAYER_LEVEL_UP = 32;
	
	//Queries
	public static final int FLAG_QUERY_REGISTER_PLAYER_POSITION = 0;
	public static final int FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE = 1;
	public static final int FLAG_QUERY_PLAYER_LEVEL_UP = 2;
	public static final int FLAG_QUERY_SET_PLAYER_ATTRIBUTES = 3;
	public static final int FLAG_QUERY_REGISTER_PLAYER_HPMP = 4;
	public static final int FLAG_QUERY_WRITE_PLAYER_INVENTORY = 5;
	public static final int FLAG_QUERY_REGISTER_PLAYER_MONEY = 6;
	
	//Items
	public static final int FLAG_ITEM_HEALTH_POTION = 10;
	public static final int FLAG_ITEM_BIG_FLAMED_SWORD = 20;
	public static final int FLAG_ITEM_LEATHER_HELM = 21;
	public static final int FLAG_ITEM_IRON_HELM = 22;	
	
	
	//Strings
	public static final String[] ATTRIBUTES = new String[]{"Power","Intelligence","Defence","Endurance"};
	public static final String FLAG_BRIAN_FACEBOOK_PASSWORD = "briannn33";
}

