package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 12:23:37 - 21.05.2011
 */
public class ClientMessageConnectionRequest extends ClientBaseMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private long mUserID=1;
	//private String mUsername="nop";
//	private String mPassword="nop";
	
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageConnectionRequest() {
	}

	public ClientMessageConnectionRequest(final long pUserID){//,final String pUsername){//,final String pPassword) {
		mUserID = pUserID;
	//	mUsername = pUsername;
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
	/*
	public String getUsername() {
		return cleanMessage(this.mUsername);
	}

	public void setUsername(final String pUsername) {
		this.mUsername = pUsername;
	}*/
	/*
	public String getPassword() {
		return this.mPassword;
	}

	public void setPassword(final String pPassword) {
		this.mPassword = pPassword;
	}*/
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
		// this.mUsername = pDataInputStream.readLine();
		// this.mPassword = pDataInputStream.readLine();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeLong(this.mUserID);
	//	pDataOutputStream.writeChars(this.mUsername);
	//	pDataOutputStream.writeChars(this.mPassword);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
