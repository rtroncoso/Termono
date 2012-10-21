package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.constants.ServerMessageFlags;

public class ServerMessageSpawnMob extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private int MOB_FLAG;
	private int tileX,tileY;
	private int Map;
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ServerMessageSpawnMob() {
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getMOB_FLAG() {
		return MOB_FLAG;
	}

	public void setMOB_FLAG(int pMOB_FLAG) {
		MOB_FLAG = pMOB_FLAG;
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
		return FLAG_MESSAGE_SERVER_SPAWN_MOB;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)throws IOException {
		this.MOB_FLAG = pDataInputStream.readInt();
		this.tileX = pDataInputStream.readInt();
		this.tileY = pDataInputStream.readInt();
		this.Map = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)throws IOException {
		pDataOutputStream.writeInt(this.MOB_FLAG);
		pDataOutputStream.writeInt(this.tileX);
		pDataOutputStream.writeInt(this.tileY);
		pDataOutputStream.writeInt(this.Map);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}