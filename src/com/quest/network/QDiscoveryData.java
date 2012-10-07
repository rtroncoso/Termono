package com.quest.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.shared.IDiscoveryData;
import org.andengine.util.StreamUtils;

import android.R.bool;
import android.R.integer;
import android.util.Log;

public interface QDiscoveryData extends IDiscoveryData{
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void read(final DataInputStream pDataInputStream) throws IOException;
	public void write(final DataOutputStream pDataOutputStream) throws IOException;

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public class DiscoveryDataFactory {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		// ===========================================================
		// Constructors
		// ===========================================================

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		public static byte[] write(final IDiscoveryData pDiscoveryData) throws IOException {
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

			try {
				pDiscoveryData.write(dataOutputStream);
				return byteArrayOutputStream.toByteArray();
			} finally {
				StreamUtils.close(dataOutputStream);
			}
		}

		public static void read(final byte[] pData, final IDiscoveryData pDiscoveryData) throws IOException {
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pData);
			final DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

			try {
				pDiscoveryData.read(dataInputStream);
			} finally {
				StreamUtils.close(dataInputStream);
			}
		}

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}

	public class MatchesDiscoveryData implements IDiscoveryData {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		private byte[] mServerIP;
		private int mServerPort;
		private String mUserID;
		private String mUsername;
		private String mMatchName;
		private boolean mHasPassword;
		// ===========================================================
		// Constructors
		// ===========================================================

		@Deprecated
		public MatchesDiscoveryData() {

		}

		public MatchesDiscoveryData(final byte[] pServerIP, final int pServerPort,final String pUserID,final String pUsername,final String pMatchName,final boolean pHasPassword) {
			this.mServerIP = pServerIP;
			this.mServerPort = pServerPort;
			this.mUserID = pUserID;
			this.mUsername = pUsername;
			this.mMatchName = pMatchName;
			this.mHasPassword = pHasPassword;
		}

		
			
		
		// ===========================================================
		// Getter & Setter
		// ===========================================================

		public final byte[] getServerIP() {
			return this.mServerIP;
		}

		public final int getServerPort() {
			return this.mServerPort;
		}

		public final String getUserID() {
			return mUserID;
		}

		public final String getUsername() {
			return mUsername;
		}
		
		public final String getMatchName() {
			return mMatchName;
		}
		
		public final boolean hasPassword() {
			return mHasPassword;
		}

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		@Override
		public void read(final DataInputStream pDataInputStream) throws IOException {
			final byte serverIPLength = pDataInputStream.readByte();
			this.mServerIP = new byte[serverIPLength];
			for(int i = 0; i < this.mServerIP.length; i++) {
				this.mServerIP[i] = pDataInputStream.readByte();
			}
			this.mServerPort = pDataInputStream.readShort();
			this.mUserID = pDataInputStream.readUTF();
			this.mUsername = pDataInputStream.readUTF();
			this.mMatchName = pDataInputStream.readUTF();
			this.mHasPassword = pDataInputStream.readBoolean();
			
		}

		@Override
		public void write(final DataOutputStream pDataOutputStream) throws IOException {
			pDataOutputStream.writeByte((byte)this.mServerIP.length);
			pDataOutputStream.write(this.mServerIP);
			pDataOutputStream.writeShort((short)this.mServerPort);
			pDataOutputStream.writeUTF(this.mUserID);
			pDataOutputStream.writeUTF(this.mUsername);
			pDataOutputStream.writeUTF(this.mMatchName);
			pDataOutputStream.writeBoolean(this.mHasPassword);
		}

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}