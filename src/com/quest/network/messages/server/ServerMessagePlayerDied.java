package com.quest.network.messages.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.quest.constants.ServerMessageFlags;

public class ServerMessagePlayerDied  extends QuestServerMessage implements ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private int mX;
	private int mY;
	private String mPlayerKey;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public ServerMessagePlayerDied() {
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getPlayerKey() {
		return mPlayerKey;
	}

	public void setPlayerKey(String mPlayerKey) {
		this.mPlayerKey = mPlayerKey;
	}

	public void setCoords(int[] coords){
		this.setX(coords[0]);
		this.setY(coords[1]);
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
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream) throws IOException {
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
		return FLAG_MESSAGE_SERVER_PLAYER_DIED;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}