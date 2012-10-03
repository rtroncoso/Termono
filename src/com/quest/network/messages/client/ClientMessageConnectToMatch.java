package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

public class ClientMessageConnectToMatch  extends ClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	public String mUserID;
	public String mUsername;
	public String mPassword;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ClientMessageConnectToMatch() {

	}

	public ClientMessageConnectToMatch(final String pUserID,final String pUsername,final String pPassword) {
		this.mUserID = pUserID;
		this.mUsername = pUsername;
		this.mPassword = pPassword;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================	

	public String getUserID(){
		return this.mUserID;
	}
	
	public void setUserID(final String pUserID) {
		this.mUserID = pUserID;
	}

	public String getUsername(){
		return this.mUsername;
	}
	
	public void setUsername(final String pUsername) {
		this.mUsername = pUsername;
	}

	public String getPassword(){
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
		return FLAG_MESSAGE_CLIENT_CONNECT_TO_MATCH;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
		this.mUserID = pDataInputStream.readLine();
		this.mUsername = pDataInputStream.readLine();
		this.mPassword = pDataInputStream.readLine();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeChars(this.mUserID);
		pDataOutputStream.writeChars(this.mUsername);
		pDataOutputStream.writeChars(this.mPassword);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
