package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

import com.quest.util.constants.IGameConstants;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 19:48:32 - 28.02.2011
 */
public class UpdateEntityPositionServerMessage extends ServerMessage implements IGameConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mEntityID;
	private int mEntityDirection;

	// ===========================================================
	// Constructors
	// ===========================================================

	public UpdateEntityPositionServerMessage() {
		this.mEntityID = 0;
		this.mEntityDirection = 0;
	}

	public UpdateEntityPositionServerMessage(int pEntityID, int pEntityDirection) {
		this.mEntityID = pEntityID;
		this.mEntityDirection = pEntityDirection;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void set(int pEntityID, int pEntityDirection) {
		this.mEntityID = pEntityID;
		this.mEntityDirection = pEntityDirection;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mEntityID = pDataInputStream.readInt();
		this.mEntityDirection = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeInt(this.mEntityID);
		pDataOutputStream.writeInt(this.mEntityDirection);
	}

	@Override
	public short getFlag() {
		// TODO Auto-generated method stub
		return 0;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}