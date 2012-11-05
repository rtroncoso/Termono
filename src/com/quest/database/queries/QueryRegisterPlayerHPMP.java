package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QueryRegisterPlayerHPMP extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	private int HP,MP;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QueryRegisterPlayerHPMP() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_REGISTER_PLAYER_HPMP;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getDataHandler().setPlayerCurrentHPMP(mPlayerID, HP, MP);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void set(int pPlayerID,int hp,int mp){
		this.setPlayerID(pPlayerID);
		this.setHP(hp);
		this.setMP(mp);
	}
	
	/**
	 * @return the mPlayerID
	 */
	public int getPlayerID() {
		return mPlayerID;
	}

	/**
	 * @param mPlayerID the mPlayerID to set
	 */
	public void setPlayerID(int pPlayerID) {
		this.mPlayerID = pPlayerID;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hp) {
		this.HP = hp;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int mp) {
		this.MP = mp;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}