package com.quest.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.shared.IDiscoveryData;
import org.andengine.util.StreamUtils;

import android.R.bool;
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
		private String mMessage;
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

		public MatchesDiscoveryData(final byte[] pServerIP, final int pServerPort, String pMessage) {
			this.mServerIP = pServerIP;
			this.mServerPort = pServerPort;
			this.mMessage = pMessage;
		}

		private final String CleanMessage(){
			this.mMessage = this.mMessage.trim();
			String clean = ""; 
			for(int i=0;i<this.mMessage.length();i+=2){
				clean=clean+this.mMessage.substring(i,i+1);
			}
			return clean;
		}
		
		private final void SplitMessage(String pMessage){
			String[] temp = pMessage.split(";");
			this.mUserID = temp[1];
			this.mUsername = temp[2];
			this.mMatchName = temp[3];
			if(temp[4]=="true"){//no iguala ?
				this.mHasPassword = true;
			}else{
				this.mHasPassword = false;
			}
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
			this.mMessage = pDataInputStream.readLine();
			this.SplitMessage(this.CleanMessage());
		}

		@Override
		public void write(final DataOutputStream pDataOutputStream) throws IOException {
			pDataOutputStream.writeByte((byte)this.mServerIP.length);
			pDataOutputStream.write(this.mServerIP);
			pDataOutputStream.writeShort((short)this.mServerPort);
			pDataOutputStream.writeChars(mMessage);
		}

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}