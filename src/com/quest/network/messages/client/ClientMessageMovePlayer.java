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
 * @since 12:00:36 - 21.05.2011
 */
public class ClientMessageMovePlayer extends QuestClientMessage implements ClientMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private String mPlayerKey;
	private int mX,mY;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	@Deprecated
	public ClientMessageMovePlayer() {		
	}
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setPlayerKey(String mPlayerKey) {
		this.mPlayerKey = mPlayerKey;
	}


	/**
	 * @return the mX
	 */
	public int getX() {
		return mX;
	}

	/**
	 * @param mX the mX to set
	 */
	public void setX(int mX) {
		this.mX = mX;
	}

	/**
	 * @return the mY
	 */
	public int getY() {
		return mY;
	}

	/**
	 * @param mY the mY to set
	 */
	public void setY(int mY) {
		this.mY = mY;
	}

	public String getPlayerKey() {
		return mPlayerKey;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	@Override
	protected void onReadTransmissionData(final DataInputStream pDataInputStream) throws IOException {
		this.mPlayerKey = pDataInputStream.readUTF();
		this.mX = pDataInputStream.readInt();
		this.mY = pDataInputStream.readInt();
	}

	@Override
	protected void onWriteTransmissionData(final DataOutputStream pDataOutputStream) throws IOException {
		pDataOutputStream.writeUTF(this.mPlayerKey);
		pDataOutputStream.writeInt(this.mX);
		pDataOutputStream.writeInt(this.mY);
	}

	@Override
	public short getFlag() {		
		return FLAG_MESSAGE_CLIENT_MOVE_PLAYER;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
