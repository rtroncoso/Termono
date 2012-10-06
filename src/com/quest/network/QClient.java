package com.quest.network;

import java.io.IOException;
import java.net.Socket;

import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;

import android.util.Log;

import com.quest.game.Game;
import com.quest.network.messages.client.ClientMessageFlags;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ConnectionPungServerMessage;
import com.quest.network.messages.server.ServerMessageFlags;

public class QClient extends ServerConnector<SocketConnection> implements ClientMessageFlags, ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================
	static int SERVER_PORT = 4444;
	// ===========================================================
	// Fields
	// ===========================================================
	
	// ===========================================================
	// Constructors
	// ===========================================================

		public QClient(final String pServerIP, final ISocketConnectionServerConnectorListener pSocketConnectionServerConnectorListener) throws IOException {
			super(new SocketConnection(new Socket(pServerIP, SERVER_PORT)), pSocketConnectionServerConnectorListener);
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PUNG, ConnectionPungServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ConnectionPungServerMessage connectionPungServerMessage = (ConnectionPungServerMessage) pServerMessage;
					final long roundtripMilliseconds = System.currentTimeMillis() - connectionPungServerMessage.getTimestamp();
					Log.d("Quest!","CLIENT Pung: " + roundtripMilliseconds / 2 + "ms");
					Game.getSceneManager().getCurrScene().attachChild(Game.getTextHelper().NewText(200, 440, "CLIENT Pung: " + roundtripMilliseconds / 2 + "ms", "Client"));
				}
			});
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG, ConnectionPongServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ConnectionPongServerMessage connectionPongServerMessage = (ConnectionPongServerMessage) pServerMessage;
					final long roundtripMilliseconds = System.currentTimeMillis() - connectionPongServerMessage.getTimestamp();
					Log.d("Quest!","CLIENT Ping: " + roundtripMilliseconds / 2 + "ms");
					Game.getSceneManager().getCurrScene().attachChild(Game.getTextHelper().NewText(200, 10, "CLIENT Ping: " + roundtripMilliseconds / 2 + "ms", "Client"));
				}
			});
			/*
			this.registerServerMessage(FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION, UpdateEntityPositionServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final UpdateEntityPositionServerMessage updateEntityPosition = (UpdateEntityPositionServerMessage) pServerMessage;
					// mandarle nueva posicion a todos
					// final long roundtripMilliseconds = System.currentTimeMillis() - connectionPongServerMessage.getTimestamp();
					//Log.d("Quest!","Ping: " + roundtripMilliseconds / 2 + "ms"); 
				}
			});*/
		}
		
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}