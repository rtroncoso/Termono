package com.quest.network;

import java.io.IOException;
import java.net.Socket;

import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.game.Game;
import com.quest.network.messages.server.ConnectionCloseServerMessage;
import com.quest.network.messages.server.ConnectionEstablishedServerMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ConnectionRejectedProtocolMissmatchServerMessage;
import com.quest.network.messages.server.ServerMessageFlags;
import com.quest.util.constants.MessageConstants;

public class QClient extends ServerConnector<SocketConnection> implements ServerMessageFlags {
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
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_CLOSE, ConnectionCloseServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					Log.d("Logd","(QClient)CLIENT: Connection terminated.");
					Game.getInstance().finish();				
				}
			});
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED, ConnectionEstablishedServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					Log.d("Logd","(QClient)CLIENT: Connection established.");
				}
			});
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH, ConnectionRejectedProtocolMissmatchServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ConnectionRejectedProtocolMissmatchServerMessage connectionRejectedProtocolMissmatchServerMessage = (ConnectionRejectedProtocolMissmatchServerMessage)pServerMessage;
					if(connectionRejectedProtocolMissmatchServerMessage.getProtocolVersion() > MessageConstants.PROTOCOL_VERSION) {
						//						Toast.makeText(context, text, duration).show();
					} else if(connectionRejectedProtocolMissmatchServerMessage.getProtocolVersion() < MessageConstants.PROTOCOL_VERSION) {
						//						Toast.makeText(context, text, duration).show();
					}
					Log.d("Logd","(QClient)CLIENT: Connection rejected.");
					Game.getInstance().finish();				
				}
			});
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG, ConnectionPongServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ConnectionPongServerMessage connectionPongServerMessage = (ConnectionPongServerMessage) pServerMessage;
					final long roundtripMilliseconds = System.currentTimeMillis() - connectionPongServerMessage.getTimestamp();
					Log.d("Logd","Ping: " + roundtripMilliseconds / 2 + "ms");
				}
			});
	/*
			this.registerServerMessage(FLAG_MESSAGE_SERVER_SET_PADDLEID, SetPaddleIDServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final SetPaddleIDServerMessage setPaddleIDServerMessage = (SetPaddleIDServerMessage) pServerMessage;
					PongGameActivity.this.setPaddleID(setPaddleIDServerMessage.mPaddleID);
				}
			});

			this.registerServerMessage(FLAG_MESSAGE_SERVER_UPDATE_SCORE, UpdateScoreServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final UpdateScoreServerMessage updateScoreServerMessage = (UpdateScoreServerMessage) pServerMessage;
					PongGameActivity.this.updateScore(updateScoreServerMessage.mPaddleID, updateScoreServerMessage.mScore);
				}
			});

			this.registerServerMessage(FLAG_MESSAGE_SERVER_UPDATE_BALL, UpdateBallServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final UpdateBallServerMessage updateBallServerMessage = (UpdateBallServerMessage) pServerMessage;
					PongGameActivity.this.updateBall(updateBallServerMessage.mX, updateBallServerMessage.mY);
				}
			});
			
			this.registerServerMessage(FLAG_MESSAGE_SERVER_UPDATE_PADDLE, UpdatePaddleServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final UpdatePaddleServerMessage updatePaddleServerMessage = (UpdatePaddleServerMessage) pServerMessage;
					PongGameActivity.this.updatePaddle(updatePaddleServerMessage.mPaddleID, updatePaddleServerMessage.mX, updatePaddleServerMessage.mY);
				}
			});
	*/
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