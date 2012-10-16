package com.quest.network;

import java.io.IOException;
import java.net.Socket;

import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.adt.message.server.IServerMessage;
import org.andengine.extension.multiplayer.protocol.client.IServerMessageHandler;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;

import android.util.Log;

import com.quest.constants.ClientMessageFlags;
import com.quest.constants.ServerMessageFlags;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.network.messages.client.ClientMessageAttackMessage;
import com.quest.network.messages.client.ClientMessageConnectionRequest;
import com.quest.network.messages.client.ClientMessageMovePlayer;
import com.quest.network.messages.client.ClientMessagePlayerCreate;
import com.quest.network.messages.client.ClientMessageSelectedPlayer;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ServerMessageConnectionAcknowledge;
import com.quest.network.messages.server.ServerMessageConnectionRefuse;
import com.quest.network.messages.server.ServerMessageCreatePlayer;
import com.quest.network.messages.server.ServerMessageExistingPlayer;
import com.quest.network.messages.server.ServerMessageFixedAttackData;
import com.quest.network.messages.server.ServerMessageMobDied;
import com.quest.network.messages.server.ServerMessageSendPlayer;
import com.quest.network.messages.server.ServerMessageUpdateEntityPosition;
import com.quest.objects.BooleanMessage;

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
			this.mMessagePool.registerMessage(FLAG_MESSAGE_CLIENT_PLAYER_CREATE, ClientMessagePlayerCreate.class);
			this.mMessagePool.registerMessage(FLAG_MESSAGE_CLIENT_SELECTED_PLAYER, ClientMessageSelectedPlayer.class);
			this.mMessagePool.registerMessage(FLAG_MESSAGE_CLIENT_MOVE_PLAYER, ClientMessageMovePlayer.class);
			this.mMessagePool.registerMessage(FLAG_MESSAGE_CLIENT_ATTACK_MESSAGE, ClientMessageAttackMessage.class);
			}
	
		public QClient(final String pServerIP, final ISocketConnectionServerConnectorListener pSocketConnectionServerConnectorListener) throws IOException {
			super(new SocketConnection(new Socket(pServerIP, SERVER_PORT)), pSocketConnectionServerConnectorListener);
			
			
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE, ServerMessageConnectionAcknowledge.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageConnectionAcknowledge serverMessageConnectionAcknowledge= (ServerMessageConnectionAcknowledge) pServerMessage;
					Log.d("Quest!","Acknowledged");
					Game.getMatchData().setMatchID(serverMessageConnectionAcknowledge.getMatchID());
					Game.getSceneManager().getMatchScene().ClearTouchAreas();
					Game.getSceneManager().getMatchScene().SwitchEntity(Game.getSceneManager().getMatchScene().LoadMatchEntity(3));
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
			
			//mando que cree un chara
			this.registerServerMessage(FLAG_MESSAGE_SERVER_CREATE_PLAYER, ServerMessageCreatePlayer.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					//No tiene player en la partida, le pido que se haga uno
					Game.getSceneManager().getMatchScene().msgCreateRemoteCharacter();
				}
			});
			//Llego un chara que tengo
			this.registerServerMessage(FLAG_MESSAGE_SERVER_EXISTING_PLAYER, ServerMessageExistingPlayer.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageExistingPlayer serverMessageExistingPlayer = (ServerMessageExistingPlayer) pServerMessage;
					//Tiene player en la partida, lo agrego a la lista
					Game.getSceneManager().getMatchScene().LoadOwnRemoteCharacters(serverMessageExistingPlayer.getCharacterID(), serverMessageExistingPlayer.getLevel(), serverMessageExistingPlayer.getPlayerClass());
				}
			});
			
			//Recibi un new player!
			this.registerServerMessage(FLAG_MESSAGE_SERVER_SEND_PLAYER, ServerMessageSendPlayer.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageSendPlayer serverMessageSendPlayer = (ServerMessageSendPlayer) pServerMessage;
					Game.getPlayerHelper().addPlayer(new Player(serverMessageSendPlayer.getUserID(), serverMessageSendPlayer.getPlayerID(), serverMessageSendPlayer.getPlayerClass(), serverMessageSendPlayer.getLevel(), serverMessageSendPlayer.getAttributes(), serverMessageSendPlayer.getCurrHPMP(), serverMessageSendPlayer.getHeadID(), serverMessageSendPlayer.getItemID(), serverMessageSendPlayer.getAmounts(), serverMessageSendPlayer.getIsEquipped()),serverMessageSendPlayer.getUserID());
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

			this.registerServerMessage(FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION, ServerMessageUpdateEntityPosition.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageUpdateEntityPosition serverMessageUpdateEntityPosition = (ServerMessageUpdateEntityPosition) pServerMessage;
					//mover los players con esos datos
					Game.getPlayerHelper().getPlayer(serverMessageUpdateEntityPosition.getPlayerKey()).moveInDirection(serverMessageUpdateEntityPosition.getPlayerDirection());
				}
			});
		
			this.registerServerMessage(FLAG_MESSAGE_SERVER_FIXED_ATTACK_DATA, ServerMessageFixedAttackData.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageFixedAttackData serverMessageFixedAttackData = (ServerMessageFixedAttackData) pServerMessage;
					//simular el ataque con esos datos
					if(serverMessageFixedAttackData.isMonsterAttacking()){
						Game.getBattleHelper().displayAttack(Game.getMobHelper().getMob(serverMessageFixedAttackData.getMobID()), serverMessageFixedAttackData.getAttackID(), serverMessageFixedAttackData.getDamage(), Game.getPlayerHelper().getPlayer(serverMessageFixedAttackData.getPlayerKey()));
					}else{
						Game.getBattleHelper().displayAttack(Game.getPlayerHelper().getPlayer(serverMessageFixedAttackData.getPlayerKey()), serverMessageFixedAttackData.getAttackID(), serverMessageFixedAttackData.getDamage(), Game.getMobHelper().getMob(serverMessageFixedAttackData.getMobID()));
					}
				}
			});
			
			this.registerServerMessage(FLAG_MESSAGE_SERVER_MOB_DIED, ServerMessageMobDied.class, new IServerMessageHandler<SocketConnection>() {
				@Override
				public void onHandleMessage(final ServerConnector<SocketConnection> pServerConnector, final IServerMessage pServerMessage) throws IOException {
					final ServerMessageMobDied serverMessageMobDied = (ServerMessageMobDied) pServerMessage;
					//simular la muerte
					
					Game.getBattleHelper().killMob(Game.getMobHelper().getMob(serverMessageMobDied.getMobEntityUserData()), serverMessageMobDied.getDroppedItem(),serverMessageMobDied.getDroppedAmount(), serverMessageMobDied.getExperience(), serverMessageMobDied.getMoney(), (Player)(Game.getPlayerHelper().getPlayer(serverMessageMobDied.getPlayerKey())));
					
				}
			});
			
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
		
		public void sendPlayerCreate(int[] pChoices,String pUserID){			
			final ClientMessagePlayerCreate clientMessagePlayerCreate= (ClientMessagePlayerCreate) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_PLAYER_CREATE);
			clientMessagePlayerCreate.setChoices(pChoices);
			clientMessagePlayerCreate.setUserID(pUserID);
			try {
				sendClientMessage(clientMessagePlayerCreate);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(clientMessagePlayerCreate);
		}	
		
		public void sendSelectedPlayer(int pPlayerID){			
			final ClientMessageSelectedPlayer clientMessageSelectedPlayer= (ClientMessageSelectedPlayer) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_SELECTED_PLAYER);
			clientMessageSelectedPlayer.setPlayerID(pPlayerID);
			try {
				sendClientMessage(clientMessageSelectedPlayer);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(clientMessageSelectedPlayer);
		}	
		/*
		public void sendUnequipMessage(int pItemKey){
			final ClientMessageSelectedPlayer clientMessageSelectedPlayer= (ClientMessageSelectedPlayer) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_SELECTED_PLAYER);
			clientMessageSelectedPlayer.setPlayerID(pPlayerID);
			try {
				sendClientMessage(clientMessageSelectedPlayer);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(clientMessageSelectedPlayer);
		}*/
		
		public void sendMovePlayerMessage(String pPlayerKey, byte pPlayerDirection){			
			final ClientMessageMovePlayer clientMessageMovePlayer = (ClientMessageMovePlayer) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_MOVE_PLAYER);
			clientMessageMovePlayer.setPlayerKey(pPlayerKey);
			clientMessageMovePlayer.setPlayerDirection(pPlayerDirection);
			try {
				sendClientMessage(clientMessageMovePlayer);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(clientMessageMovePlayer);
		}
		
		public void sendAttackMessage(int pAttackedMobID, int pAttackID){			
			final ClientMessageAttackMessage clientMessageAttackMessage = (ClientMessageAttackMessage) QClient.this.mMessagePool.obtainMessage(FLAG_MESSAGE_CLIENT_ATTACK_MESSAGE);
			clientMessageAttackMessage.setAttackedMobID(pAttackedMobID);
			clientMessageAttackMessage.setAttackID(pAttackID);
			try {
				sendClientMessage(clientMessageAttackMessage);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			QClient.this.mMessagePool.recycleMessage(clientMessageAttackMessage);
		}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}