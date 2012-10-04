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
 * @since 12:23:37 - 21.05.2011
 */
public class ConnectionPingClientMessage extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private long mTimestamp;
	private String mUser = "sinmod";
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ConnectionPingClientMessage() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public long getTimestamp() {
		return this.mTimestamp;
	}

	public void setTimestamp(final long pTimestamp) {
		this.mTimestamp = pTimestamp;
	}

	public String getUser(){
		return this.mUser;
	}
	
	public void setUser(final String user){
		this.mUser = user;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_CONNECTION_PING;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		this.mTimestamp = pDataInputStream.readLong();
		this.mUser = pDataInputStream.readLine();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeLong(this.mTimestamp);
		pDataOutputStream.writeChars(this.mUser);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
