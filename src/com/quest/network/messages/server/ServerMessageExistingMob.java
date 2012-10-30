package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ServerMessageFlags;
import com.quest.entities.Mob;

public class ServerMessageExistingMob extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private int MOB_FLAG;
	private int tileX,tileY;
	private int Map;
	private int MobID;
	private int currHP,currMP;
	private byte Facing_Direction;
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ServerMessageExistingMob() {
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void LoadMob(final Mob pMob){
		this.setMOB_FLAG(pMob.getMobFlag());
		this.setTileX(pMob.getTMXTileAt().getTileColumn());
		this.setTileY(pMob.getTMXTileAt().getTileRow());
		this.setMap(pMob.getCurrentMap());
		this.setMobID(Integer.parseInt(pMob.getUserData().toString()));
		this.setCurrHP((int)pMob.getCurrHP());
		this.setCurrMP((int)pMob.getCurrMana());
		this.setFacing_Direction(pMob.getFacingDirection());
	}
	
	public int getMOB_FLAG() {
		return MOB_FLAG;
	}

	public void setMOB_FLAG(int pMOB_FLAG) {
		MOB_FLAG = pMOB_FLAG;
	}

	public int getMobID() {
		return MobID;
	}

	public void setMobID(int mobID) {
		MobID = mobID;
	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	/**
	 * @return the currHP
	 */
	public int getCurrHP() {
		return currHP;
	}

	/**
	 * @param f the currHP to set
	 */
	public void setCurrHP(int f) {
		this.currHP = f;
	}

	/**
	 * @return the currMP
	 */
	public int getCurrMP() {
		return currMP;
	}

	/**
	 * @param f the currMP to set
	 */
	public void setCurrMP(int f) {
		this.currMP = f;
	}

	/**
	 * @return the facing_Direction
	 */
	public byte getFacing_Direction() {
		return Facing_Direction;
	}

	/**
	 * @param facing_Direction the facing_Direction to set
	 */
	public void setFacing_Direction(byte facing_Direction) {
		Facing_Direction = facing_Direction;
	}

	public int getMap() {
		return Map;
	}

	public void setMap(int map) {
		Map = map;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_SERVER_EXISTING_MOB;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)throws IOException {
		this.MOB_FLAG = pDataInputStream.readInt();
		this.MobID = pDataInputStream.readInt();
		this.tileX = pDataInputStream.readInt();
		this.tileY = pDataInputStream.readInt();
		this.Map = pDataInputStream.readInt();
		this.currHP = pDataInputStream.readInt();
		this.currMP = pDataInputStream.readInt();
		this.Facing_Direction = pDataInputStream.readByte();
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)throws IOException {
		pDataOutputStream.writeInt(this.MOB_FLAG);
		pDataOutputStream.writeInt(this.MobID);
		pDataOutputStream.writeInt(this.tileX);
		pDataOutputStream.writeInt(this.tileY);
		pDataOutputStream.writeInt(this.Map);
		pDataOutputStream.writeInt(this.currHP);
		pDataOutputStream.writeInt(this.currMP);
		pDataOutputStream.writeByte(this.Facing_Direction);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}