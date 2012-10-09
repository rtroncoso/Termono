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

import com.quest.data.MatchData;
import com.quest.data.PlayerData;
import com.quest.data.ProfileData;
import com.quest.game.Game;
import com.quest.helpers.PlayerHelper;
import com.quest.network.messages.client.ClientMessageConnectionRequest;
import com.quest.network.messages.client.ClientMessageFlags;
import com.quest.network.messages.client.ClientMessagePlayerCreate;
import com.quest.network.messages.client.ClientMessageSelectedPlayer;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ServerMessageConnectionAcknowledge;
import com.quest.network.messages.server.ServerMessageConnectionRefuse;
import com.quest.network.messages.server.ServerMessageCreatePlayer;
import com.quest.network.messages.server.ServerMessageExistingPlayer;
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
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CREATE_PLAYER, ServerMessageCreatePlayer.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_EXISTING_PLAYER, ServerMessageExistingPlayer.class);
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
		final PlayerData connectedClientPlayerData = new PlayerData();
		final MatchData	 connectedClientMatchData = new MatchData();
		final ProfileData connectedClientProfileData = new ProfileData();
		
		//Un cliente pidio conectarse al match
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_CONNECTION_REQUEST, ClientMessageConnectionRequest.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageConnectionRequest clientMessageConnectionRequest = (ClientMessageConnectionRequest) pClientMessage;
				if(clientMessageConnectionRequest.getPassword().equals(Game.getDataHandler().getMatchPassword(clientMessageConnectionRequest.getMatchName()))){//Si la pass esta bien
					//creo msg de acknowledge
					final ServerMessageConnectionAcknowledge serverMessageConnectionAcknowledge = (ServerMessageConnectionAcknowledge) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE);
					//lleno los data con lo del mensaje
					connectedClientProfileData.setUserID(clientMessageConnectionRequest.getUserID());
					connectedClientProfileData.setUsername(clientMessageConnectionRequest.getUsername());
					connectedClientMatchData.setMatchName(clientMessageConnectionRequest.getMatchName());
					connectedClientMatchData.setMatchID(Game.getDataHandler().getMatchID(clientMessageConnectionRequest.getMatchName()));
					//completo el msg de acknowledge
					serverMessageConnectionAcknowledge.setMatchName(connectedClientMatchData.getMatchName());
					serverMessageConnectionAcknowledge.setMatchID(connectedClientMatchData.getMatchID());					
					//mando el mensaje
					try {
						pClientConnector.sendServerMessage(serverMessageConnectionAcknowledge);
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(serverMessageConnectionAcknowledge);
					
					//checkeo si tiene conosco el profile y lo agrego si no lo tengo
					if(Game.getDataHandler().CheckAndAddProfile(connectedClientProfileData.getUserID(),connectedClientProfileData.getUsername())){//Agrega el perfil, checkeo si existe
						//agrego el ProfileID a profileData
						connectedClientProfileData.setProfileID(Game.getDataHandler().getProfileID(connectedClientProfileData.getUserID())); 
						//levanto un array con las IDs de los personajes del cliente
						final int[] IDArray = Game.getDataHandler().getPlayerIDifExists(connectedClientPlayerData.getProfileID(), clientMessageConnectionRequest.getMatchName());
						if(IDArray.length>0){	
							//mando mensajes con charas
							for(int i=0;i<IDArray.length;i++){
								final ServerMessageExistingPlayer serverMessageExistingPlayer = (ServerMessageExistingPlayer) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_EXISTING_PLAYER);
								serverMessageExistingPlayer.setCharacterID(IDArray[i]);
								serverMessageExistingPlayer.setLevel(Game.getDataHandler().getPlayerLevel(IDArray[i]));
								serverMessageExistingPlayer.setPlayerClass(Game.getDataHandler().getPlayerClass(IDArray[i]));
								try {
									pClientConnector.sendServerMessage(serverMessageExistingPlayer);
								} catch (IOException e) {
									Debug.e(e);
								}
								QServer.this.mMessagePool.recycleMessage(serverMessageExistingPlayer);
							}
						}else{
							//creo el MatchProfile la id del cliente con la id de la partida a la que se conecto
							Game.getDataHandler().AddNewMatchProfile(connectedClientProfileData.getProfileID(), connectedClientMatchData.getMatchID(),false);
							//No tiene chara pido que se haga uno
							final ServerMessageCreatePlayer serverMessageCreatePlayer = (ServerMessageCreatePlayer) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CREATE_PLAYER);
							try {
								pClientConnector.sendServerMessage(serverMessageCreatePlayer);
							} catch (IOException e) {
								Debug.e(e);
							}
							QServer.this.mMessagePool.recycleMessage(serverMessageCreatePlayer);
						}
					}else{//No conocia el perfil(ya se agrega cuando se checkea),osea que no tiene chara pido que haga uno
						//agrego el ProfileID a profileData
						connectedClientProfileData.setProfileID(Game.getDataHandler().getProfileID(connectedClientProfileData.getUserID()));//agrego el ProfileID a playerData
						//creo el MatchProfile la id del cliente con la id de la partida a la que se conecto
						Game.getDataHandler().AddNewMatchProfile(connectedClientProfileData.getProfileID(), connectedClientMatchData.getMatchID(),false);
						final ServerMessageCreatePlayer serverMessageCreatePlayer = (ServerMessageCreatePlayer) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CREATE_PLAYER);
						try {
							pClientConnector.sendServerMessage(serverMessageCreatePlayer);
						} catch (IOException e) {
							Debug.e(e);
						}
						QServer.this.mMessagePool.recycleMessage(serverMessageCreatePlayer);
					}
					
				}else{//la contraseña esta mal
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
		
		
		//El cliente creo un nuevo player.
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_PLAYER_CREATE, ClientMessagePlayerCreate.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessagePlayerCreate clientMessagePlayerCreate = (ClientMessagePlayerCreate) pClientMessage;
				//creo el personaje que me mando
				connectedClientPlayerData.setPlayerClass(clientMessagePlayerCreate.getPlayerClass());
				connectedClientPlayerData.setAttributes(clientMessagePlayerCreate.getAttributes());

				int playerid = Game.getDataHandler().AddNewPlayer(connectedClientMatchData.getMatchID(), connectedClientPlayerData.getPlayerClass());
				connectedClientPlayerData.setPlayerID(playerid);
				Game.getDataHandler().setPlayerAttributes(connectedClientPlayerData.getAttributes(), connectedClientPlayerData.getPlayerID());
				Game.getDataHandler().setPlayerLevel(1, connectedClientPlayerData.getPlayerID());
				
				//Devolverle el playerID/PlayerDATA?
				
			}
		});
		
		//El cliente creo un eligio player.
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_SELECTED_PLAYER, ClientMessageSelectedPlayer.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageSelectedPlayer clientMessageSelectedPlayer = (ClientMessageSelectedPlayer) pClientMessage;
				//cargo en data el personaje que me mando
				connectedClientPlayerData.setPlayerID(clientMessageSelectedPlayer.getPlayerID());
				connectedClientPlayerData.setPlayerClass(Game.getDataHandler().getPlayerClass(connectedClientPlayerData.getPlayerID()));
				connectedClientPlayerData.setLevel(Game.getDataHandler().getPlayerLevel(connectedClientPlayerData.getPlayerID())); 
				connectedClientPlayerData.setAttributes(Game.getDataHandler().getPlayerAttributes(connectedClientPlayerData.getPlayerID()));
				//***completar tambien los modifiers y lo que falta
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