package com.quest.network;

import java.io.IOException;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.client.IClientMessage;
import org.andengine.extension.multiplayer.protocol.server.IClientMessageHandler;
import org.andengine.extension.multiplayer.protocol.server.SocketServer;
import org.andengine.extension.multiplayer.protocol.server.SocketServer.ISocketServerListener.DefaultSocketServerListener;
import org.andengine.extension.multiplayer.protocol.server.connector.ClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector.ISocketConnectionClientConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.data.PlayerData;
import com.quest.game.Game;
import com.quest.helpers.PlayerHelper;
import com.quest.network.messages.client.ClientMessageFlags;
import com.quest.network.messages.client.ClientMessageConnectionRequest;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ServerMessageConnectionAcknowledge;
import com.quest.network.messages.server.ServerMessageConnectionRefuse;
import com.quest.network.messages.server.ServerMessageFlags;
import com.quest.util.constants.IGameConstants;


public class QServer extends SocketServer<SocketConnectionClientConnector> implements IUpdateHandler, ClientMessageFlags, ServerMessageFlags, IGameConstants {
	
	
// ===========================================================
// Constants
// ===========================================================
	
// ===========================================================
// Fields
// ===========================================================
	private final MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();
	private PlayerHelper mPlayerList;
	
// ===========================================================
// Constructors
// ===========================================================
	public QServer(final ISocketConnectionClientConnectorListener pSocketConnectionClientConnectorListener) {
		super(SERVER_PORT, pSocketConnectionClientConnectorListener, new DefaultSocketServerListener<SocketConnectionClientConnector>());
		Log.d("Quest!","Server started");
		this.mPlayerList = new PlayerHelper();
		this.initMessagePool();
	}
	
	private void initMessagePool() {
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG, ConnectionPongServerMessage.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE, ServerMessageConnectionAcknowledge.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_REFUSE, ServerMessageConnectionRefuse.class);
		//this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION, UpdateEntityPositionServerMessage.class);		
	}

// ===========================================================
// Methods for/from SuperClass/Interfaces
// ===========================================================
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	protected SocketConnectionClientConnector newClientConnector(final SocketConnection pSocketConnection) throws IOException {
		final SocketConnectionClientConnector clientConnector = new SocketConnectionClientConnector(pSocketConnection);
		final PlayerData playerData = new PlayerData();
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST, ClientMessageConnectionRequest.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageConnectionRequest clientMessageConnectionRequest = (ClientMessageConnectionRequest) pClientMessage;
				if(clientMessageConnectionRequest.getPassword().equals(Game.getDataHandler().getMatchPassword(clientMessageConnectionRequest.getMatchName()))){//Si la pass esta bien
					final ServerMessageConnectionAcknowledge serverMessageConnectionAcknowledge = (ServerMessageConnectionAcknowledge) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE);
					serverMessageConnectionAcknowledge.setMatchName(clientMessageConnectionRequest.getMatchName());
					serverMessageConnectionAcknowledge.setMatchID(Game.getDataHandler().getMatchID(clientMessageConnectionRequest.getMatchName()));					
					try {
						pClientConnector.sendServerMessage(serverMessageConnectionAcknowledge);
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(serverMessageConnectionAcknowledge);
					
					if(Game.getDataHandler().CheckAndAddProfile(clientMessageConnectionRequest.getUserID(), getName())){//Agrega el perfil, checkeo si existe
						playerData.setProfileID(Game.getDataHandler().getProfileID(clientMessageConnectionRequest.getUserID()));//agrego el ProfileID a playerData 
						
						final int[] IDArray = Game.getDataHandler().getPlayerIDifExists(playerData.getProfileID(), clientMessageConnectionRequest.getMatchName());
						if(IDArray.length>0){	
							
						}else{//No tiene chara pido que se haga uno
							
						}
					}else{//No conocia el perfil, no tiene chara pido que haga uno
						playerData.setProfileID(Game.getDataHandler().getProfileID(clientMessageConnectionRequest.getUserID()));//agrego el ProfileID a playerData
						
					}
					
				}else{
					final ServerMessageConnectionRefuse serverMessageConnectionRefuse = (ServerMessageConnectionRefuse) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_REFUSE);
					serverMessageConnectionRefuse.setMatchName(clientMessageConnectionRequest.getMatchName());
					serverMessageConnectionRefuse.setReason(true);
					try {
						pClientConnector.sendServerMessage(serverMessageConnectionRefuse);
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(serverMessageConnectionRefuse);
				}
			}
		});
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_CONNECTION_PING, ConnectionPingClientMessage.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ConnectionPingClientMessage connectionPingClientMessage = (ConnectionPingClientMessage) pClientMessage;
				final long roundtripMilliseconds = System.currentTimeMillis() - connectionPingClientMessage.getTimestamp();
				Log.d("Quest!","Server Pong: " + roundtripMilliseconds + "ms");
				final ConnectionPongServerMessage connectionPongServerMessage = (ConnectionPongServerMessage) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG);
				connectionPongServerMessage.setTimestamp(connectionPingClientMessage.getTimestamp());
				try {
					pClientConnector.sendServerMessage(connectionPongServerMessage);
				} catch (IOException e) {
					Debug.e(e);
				}
				QServer.this.mMessagePool.recycleMessage(connectionPongServerMessage);
			}
		});
		/*
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_MOVE_PLAYER, ClientMessageMovePlayer.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				// TODO: Forwardear el mensaje de que se movio a todos los players
			}
		});*/
		
		return clientConnector;
	}
// ===========================================================
// Getter & Setter
// ===========================================================
	
// ===========================================================
// Methods
// ===========================================================


// ===========================================================
// Inner and Anonymous Classes
// ===========================================================


}