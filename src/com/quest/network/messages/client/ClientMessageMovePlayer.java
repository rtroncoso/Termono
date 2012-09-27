package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;


/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 12:00:36 - 21.05.2011
 */
public class ClientMessageMovePlayer extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private byte mPlayerDirection;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ClientMessageMovePlayer() {
		this.mPlayerDirection = 0;
		
	}
	
	public ClientMessageMovePlayer(byte pPlayerDirection) {
		this.mPlayerDirection = pPlayerDirection;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setPlayerDirection(byte mPlayerDirection) {
		this.mPlayerDirection = mPlayerDirection;
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		this.mPlayerDirection = pDataInputStream.readByte();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeByte(this.mPlayerDirection);
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
