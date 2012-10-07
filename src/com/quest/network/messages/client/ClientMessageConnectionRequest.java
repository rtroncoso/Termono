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
public class ClientMessageConnectionRequest extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private long mUserID=1;
	private boolean mUsername=false;
	private String mPassword="";
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageConnectionRequest() {
	}

	public ClientMessageConnectionRequest(final long pUserID,final boolean pUsername,final String pPassword) {
		mUserID = pUserID;
		mUsername = pUsername;
		mPassword = pPassword;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public long getUserID() {
		return this.mUserID;
	}

	public void setUserID(final long pUserID) {
		this.mUserID = pUserID;
	}
	
	public boolean getUsername() {
		return this.mUsername;
	}

	public void setUsername(final boolean pUsername) {
		this.mUsername = pUsername;
	}
	
	public String getPassword() {
		return this.mPassword;
	}

	public void setPassword(final String pPassword) {
		this.mPassword = pPassword;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public short getFlag() {
		return FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST;
	}

	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		 this.mUserID = pDataInputStream.readLong();
		 this.mUsername = pDataInputStream.readBoolean();
		 this.mPassword = pDataInputStream.readUTF();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeLong(this.mUserID);
		pDataOutputStream.writeBoolean(this.mUsername);
		pDataOutputStream.writeUTF(this.mPassword);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
