package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

import com.quest.constants.ClientMessageFlags;



public class ClientMessageMobRequest extends QuestClientMessage implements ClientMessageFlags {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private int MOB_FLAG;
	private int corner1X,corner1Y;
	private int corner2X,corner2Y;
	private int Map;
	private int Amount;
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageMobRequest() {
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

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}


	/**
	 * @return the corner1X
	 */
	public int getCorner1X() {
		return corner1X;
	}

	/**
	 * @return the corner1Y
	 */
	public int getCorner1Y() {
		return corner1Y;
	}

	/**
	 * @param corner1y the corner1Y to set
	 */
	public void setCorner1(int corner1x,int corner1y) {
		corner1X = corner1x;
		corner1Y = corner1y;
	}

	/**
	 * @return the corner2X
	 */
	public int getCorner2X() {
		return corner2X;
	}

	/**
	 * @return the corner2Y
	 */
	public int getCorner2Y() {
		return corner2Y;
	}

	/**
	 * @param corner2y the corner2Y to set
	 */
	public void setCorner2Y(int corner2x,int corner2y) {
		corner2X = corner2x;
		corner2Y = corner2y;
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
		return FLAG_MESSAGE_CLIENT_REQUEST_MOBS;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)throws IOException {
		this.MOB_FLAG = pDataInputStream.readInt();
		this.corner1X = pDataInputStream.readInt();
		this.corner1Y = pDataInputStream.readInt();
		this.corner2X = pDataInputStream.readInt();
		this.corner2Y = pDataInputStream.readInt();
		this.Map = pDataInputStream.readInt();
		this.Amount = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)throws IOException {
		pDataOutputStream.writeInt(this.MOB_FLAG);
		pDataOutputStream.writeInt(this.corner1X);
		pDataOutputStream.writeInt(this.corner1Y);
		pDataOutputStream.writeInt(this.corner2X);
		pDataOutputStream.writeInt(this.corner2Y);
		pDataOutputStream.writeInt(this.Map);
		pDataOutputStream.writeInt(this.Amount);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}