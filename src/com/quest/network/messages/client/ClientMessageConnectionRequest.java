package com.quest.network.messages.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.client.ClientMessage;

import com.quest.constants.ClientMessageFlags;

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

	private String mUserID;
	private String mUsername;
	private String mPassword;
	private String mMatchName;
	// ===========================================================
	// Constructors
	// ===========================================================

	@Deprecated
	public ClientMessageConnectionRequest() {
	}

	public ClientMessageConnectionRequest(final String pUserID,final String pUsername,final String pPassword,final String pMatchName) {
		mUserID = pUserID;
		mUsername = pUsername;
		mPassword = pPassword;
		mMatchName = pMatchName;
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	public String getUserID() {
		return this.mUserID;
	}

	public void setUserID(final String pUserID) {
		this.mUserID = pUserID;
	}
	
	public String getUsername() {
		return this.mUsername;
	}

	public void setUsername(final String pUsername) {
		this.mUsername = pUsername;
	}
	
	public String getPassword() {
		return this.mPassword;
	}

	public void setPassword(final String pPassword) {
		this.mPassword = pPassword;
	}
	
	public String getMatchName(){
		return this.mMatchName;
	}
	
	public void setMatchName(final String pMatchName){
		this.mMatchName = pMatchName;
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
		 this.mUserID = pDataInputStream.readUTF();
		 this.mUsername = pDataInputStream.readUTF();
		 this.mPassword = pDataInputStream.readUTF();
		 this.mMatchName = pDataInputStream.readUTF();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mUserID);
		pDataOutputStream.writeUTF(this.mUsername);
		pDataOutputStream.writeUTF(this.mPassword);
		pDataOutputStream.writeUTF(this.mMatchName);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
