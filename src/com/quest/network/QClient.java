package com.quest.network;

import java.io.IOException;
import java.net.Socket;

import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;

import android.R.bool;
import android.util.Log;

import com.quest.data.MatchData;
import com.quest.game.Game;
import com.quest.network.messages.client.ClientMessageConnectionRequest;
import com.quest.network.messages.client.ClientMessageFlags;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ServerMessageConnectionAcknowledge;
import com.quest.network.messages.server.ServerMessageConnectionRefuse;
import com.quest.network.messages.server.ServerMessageFlags;
import com.quest.objects.BooleanMessage;
import com.quest.scenes.MatchScene;

public class QClient extends ServerConnector<SocketConnection> implements ClientMessageFlags, ServerMessageFlags {
	// ===========================================================
	// Constants
	// ===========================================================
	static int SERVER_PORT = 4444;
	// ===========================================================
	// Fields
	// ===========================================================
	private final MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();
	
	// ===========================================================
	// Constructors
	// ===========================================================
		private void initMessagePool() {
			this.mMessagePool.registerMessage(FLAG_MESSAGE_CLIENT_CONNECTION_PING, ConnectionPingClientMessage.class);
			this.mMessagePool.registerMessage(FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST, ClientMessageConnectionRequest.class);
			}
	
		public QClient(final String pServerIP, final ISocketConnectionServerConnectorListener pSocketConnectionServerConnectorListener) throws IOException {
			super(new SocketConnection(new Socket(pServerIP, SERVER_PORT)), pSocketConnectionServerConnectorListener);
			
			
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE, ServerMessageConnectionAcknowledge.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageConnectionAcknowledge serverMessageConnectionAcknowledge= (ServerMessageConnectionAcknowledge) pServerMessage;
					Game.getMatchData().setMatchID(serverMessageConnectionAcknowledge.getMatchID());
					Game.getSceneManager().getMatchScene().ClearTouchAreas();
					Game.getSceneManager().getMatchScene().LoadMatchEntity(3);
				}
			});
	
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_REFUSE, ServerMessageConnectionRefuse.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageConnectionRefuse serverMessageConnectionRefuse = (ServerMessageConnectionRefuse) pServerMessage;
					if(serverMessageConnectionRefuse.getReason()){
						new BooleanMessage(serverMessageConnectionRefuse.getMatchName(), "Wrong password provided.","Ok", Game.getInstance()){};
					}else{
						new BooleanMessage(serverMessageConnectionRefuse.getMatchName(), "You have been kicked.","Ok", Game.getInstance()){
							@Override
							public void onOK() {
								//hacer que no pueda entrar
								super.onOK();
							}						
						};
					}
				}
			});
			
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG, ConnectionPongServerMessage.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ConnectionPongServerMessage connectionPongServerMessage = (ConnectionPongServerMessage) pServerMessage;
					final long roundtripMilliseconds = (System.currentTimeMillis() - connectionPongServerMessage.getTimestamp())/2;
					Log.d("Quest!","CLIENT Ping: " + roundtripMilliseconds + "ms");					
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
			
		this.initMessagePool();
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
		public void sendPingMessage(){			
			final ConnectionPingClientMessage connectionPingClientMessage = (ConnectionPingClientMessage) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_CONNECTION_PING);
			connectionPingClientMessage.setTimestamp(System.currentTimeMillis());
			try {
				sendClientMessage(connectionPingClientMessage);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(connectionPingClientMessage);
		}
		

		public void sendConnectionRequestMessage(final String pUserID,final String pUsername,final String pPassword,final String pMatchName){			
			final ClientMessageConnectionRequest clientMessageConnectionRequest = (ClientMessageConnectionRequest) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST);
			clientMessageConnectionRequest.setUserID(pUserID);
			clientMessageConnectionRequest.setUsername(pUsername);
			clientMessageConnectionRequest.setPassword(pPassword);
			clientMessageConnectionRequest.setMatchName(pMatchName);
			try {
				sendClientMessage(clientMessageConnectionRequest);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(clientMessageConnectionRequest);
		}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}