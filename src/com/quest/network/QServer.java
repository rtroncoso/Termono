package com.quest.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import org.andengine.extension.tmx.TMXTile;
import org.andengine.util.debug.Debug;

import android.util.Log;

import com.quest.constants.ClientMessageFlags;
import com.quest.constants.ServerMessageFlags;
import com.quest.data.MatchData;
import com.quest.data.ProfileData;
import com.quest.entities.Mob;
import com.quest.entities.Player;
import com.quest.entities.objects.Attack;
import com.quest.game.Game;
import com.quest.network.messages.client.ClientMessageAreaAttack;
import com.quest.network.messages.client.ClientMessageAttackMessage;
import com.quest.network.messages.client.ClientMessageChangeMap;
import com.quest.network.messages.client.ClientMessageConnectionRequest;
import com.quest.network.messages.client.ClientMessageMobRequest;
import com.quest.network.messages.client.ClientMessageMovePlayer;
import com.quest.network.messages.client.ClientMessagePlayerCreate;
import com.quest.network.messages.client.ClientMessageSelectedPlayer;
import com.quest.network.messages.client.ClientMessageSendCollideTiles;
import com.quest.network.messages.client.ClientMessageSetPlayerAttributes;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.QuestServerMessage;
import com.quest.network.messages.server.ServerMessageConnectionAcknowledge;
import com.quest.network.messages.server.ServerMessageConnectionRefuse;
import com.quest.network.messages.server.ServerMessageCreatePlayer;
import com.quest.network.messages.server.ServerMessageDisplayAreaAttack;
import com.quest.network.messages.server.ServerMessageExistingMob;
import com.quest.network.messages.server.ServerMessageExistingPlayer;
import com.quest.network.messages.server.ServerMessageFixedAttackData;
import com.quest.network.messages.server.ServerMessageMapChanged;
import com.quest.network.messages.server.ServerMessageMatchStarted;
import com.quest.network.messages.server.ServerMessageMobDied;
import com.quest.network.messages.server.ServerMessageMoveMob;
import com.quest.network.messages.server.ServerMessagePlayerLevelUP;
import com.quest.network.messages.server.ServerMessageSendPlayer;
import com.quest.network.messages.server.ServerMessageSetPlayerAttributes;
import com.quest.network.messages.server.ServerMessageSpawnMob;
import com.quest.network.messages.server.ServerMessageUpdateEntityPosition;
import com.quest.util.constants.IGameConstants;
import com.quest.util.constants.IMeasureConstants;


public class QServer extends SocketServer<SocketConnectionClientConnector> implements IUpdateHandler, ClientMessageFlags, ServerMessageFlags, IGameConstants {
	
	
// ===========================================================
// Constants
// ===========================================================
	
// ===========================================================
// Fields
// ===========================================================
	private final MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();
	
// ===========================================================
// Constructors
// ===========================================================
	public QServer(final ISocketConnectionClientConnectorListener pSocketConnectionClientConnectorListener) {
		super(SERVER_PORT, pSocketConnectionClientConnectorListener, new DefaultSocketServerListener<SocketConnectionClientConnector>());
		Log.d("Quest!","Server started");
		this.initMessagePool();
	}
	
	private void initMessagePool() {
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG, ConnectionPongServerMessage.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ACKNOWLEDGE, ServerMessageConnectionAcknowledge.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_REFUSE, ServerMessageConnectionRefuse.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CREATE_PLAYER, ServerMessageCreatePlayer.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_EXISTING_PLAYER, ServerMessageExistingPlayer.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_SEND_PLAYER, ServerMessageSendPlayer.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_MATCH_STARTED, ServerMessageMatchStarted.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION, ServerMessageUpdateEntityPosition.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_PLAYER_CHANGED_MAP, ServerMessageMapChanged.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_FIXED_ATTACK_DATA, ServerMessageFixedAttackData.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_MOB_DIED, ServerMessageMobDied.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_SPAWN_MOB, ServerMessageSpawnMob.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_EXISTING_MOB, ServerMessageExistingMob.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_MOVE_MOB, ServerMessageMoveMob.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_DISPLAY_AREA_ATTACK, ServerMessageDisplayAreaAttack.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_PLAYER_LEVELUP, ServerMessagePlayerLevelUP.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_SET_PLAYER_ATTRIBUTES, ServerMessageSetPlayerAttributes.class);
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
						
						final int[] IDArray = Game.getDataHandler().getPlayerIDifExists(connectedClientProfileData.getProfileID(), clientMessageConnectionRequest.getMatchName());
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
					for(int i = 0;i<Game.getPlayerHelper().getEntities().size();i++){
						final ServerMessageSendPlayer serverMessageSendPlayerown = (ServerMessageSendPlayer) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_SEND_PLAYER);
						serverMessageSendPlayerown.LoadPlayer(Game.getPlayerHelper().getPlayerbyIndex(i), Game.getDataHandler().getInventoryItems(Game.getPlayerHelper().getPlayerbyIndex(i).getPlayerID()), Game.getDataHandler().getInventoryAmounts(Game.getPlayerHelper().getPlayerbyIndex(i).getPlayerID()), Game.getDataHandler().getInventoryEquipStatus(Game.getPlayerHelper().getPlayerbyIndex(i).getPlayerID()));
						try {
							pClientConnector.sendServerMessage(serverMessageSendPlayerown);
						} catch (IOException e) {
							Debug.e(e);
						}
					QServer.this.mMessagePool.recycleMessage(serverMessageSendPlayerown);
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
				int playerid = Game.getDataHandler().AddNewPlayer(connectedClientMatchData.getMatchID(),connectedClientProfileData.getProfileID(), clientMessagePlayerCreate.getPlayerClass(),clientMessagePlayerCreate.getPlayerHeadID());
				Game.getDataHandler().setPlayerAttributes(clientMessagePlayerCreate.getAttributes(), playerid);
				Game.getDataHandler().setPlayerLevel(1, playerid);
				Game.getDataHandler().setPlayerCurrentHPMP(playerid, (clientMessagePlayerCreate.getAttributes()[3]*10), (clientMessagePlayerCreate.getAttributes()[1]*10));
				Game.getDataHandler().setPlayerExperience(playerid, 0);
				Game.getDataHandler().setPlayerMoney(playerid, 0);
				Game.getDataHandler().setPlayerCurrentMap(6, playerid);
				Game.getDataHandler().setPlayerPosition(15+Game.getPlayerHelper().getEntities().size(), 8, playerid);
				//Game.getPlayerHelper().addPlayer(new Player(playerid, Game.getDataHandler().getPlayerClass(playerid)),connectedClientProfileData.getUserID());//*** poner el userID donde sea que corresponda
				Game.getPlayerHelper().addPlayer(new Player(playerid, Game.getDataHandler().getPlayerClass(playerid),clientMessagePlayerCreate.getUserID()));
				
				final ServerMessageSendPlayer serverMessageSendPlayer = (ServerMessageSendPlayer) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_SEND_PLAYER);
				serverMessageSendPlayer.LoadPlayer(Game.getPlayerHelper().getPlayerbyPlayerID(playerid), Game.getDataHandler().getInventoryItems(playerid), Game.getDataHandler().getInventoryAmounts(playerid), Game.getDataHandler().getInventoryEquipStatus(playerid));
				
				sendBroadcast(serverMessageSendPlayer);
				
				if(Game.getMatchData().isStarted()){
					final ServerMessageMatchStarted serverMessageMatchStarted = (ServerMessageMatchStarted) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_MATCH_STARTED);
					try {
						pClientConnector.sendServerMessage(serverMessageMatchStarted);
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(serverMessageMatchStarted);
				}
				
			}
		});
		
		//El cliente creo un eligio player.
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_SELECTED_PLAYER, ClientMessageSelectedPlayer.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageSelectedPlayer clientMessageSelectedPlayer = (ClientMessageSelectedPlayer) pClientMessage;
				Game.getPlayerHelper().addPlayer(new Player(clientMessageSelectedPlayer.getPlayerID(), Game.getDataHandler().getPlayerClass(clientMessageSelectedPlayer.getPlayerID()),connectedClientProfileData.getUserID()));
			
				final ServerMessageSendPlayer serverMessageSendPlayer = (ServerMessageSendPlayer) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_SEND_PLAYER);
				serverMessageSendPlayer.LoadPlayer(Game.getPlayerHelper().getPlayerbyPlayerID(clientMessageSelectedPlayer.getPlayerID()), Game.getDataHandler().getInventoryItems(clientMessageSelectedPlayer.getPlayerID()), Game.getDataHandler().getInventoryAmounts(clientMessageSelectedPlayer.getPlayerID()), Game.getDataHandler().getInventoryEquipStatus(clientMessageSelectedPlayer.getPlayerID()));
				sendBroadcast(serverMessageSendPlayer);
				
				if(Game.getMatchData().isStarted()){
					final ServerMessageMatchStarted serverMessageMatchStarted = (ServerMessageMatchStarted) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_MATCH_STARTED);
					try {
						pClientConnector.sendServerMessage(serverMessageMatchStarted);
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(serverMessageMatchStarted);
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
		
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_MOVE_PLAYER, ClientMessageMovePlayer.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageMovePlayer clientMessageMovePlayer = (ClientMessageMovePlayer) pClientMessage;
				//Mover el player con esos datos
				//se anima por mas que no este en el map y no este attacheado? 
				Game.getPlayerHelper().getPlayer(clientMessageMovePlayer.getPlayerKey()).moveToTile(Game.getMapManager().getTMXTileAt(clientMessageMovePlayer.getX() * IMeasureConstants.TILE_SIZE, clientMessageMovePlayer.getY() * IMeasureConstants.TILE_SIZE));
				sendUpdateEntityPositionMessage(clientMessageMovePlayer.getPlayerKey(), clientMessageMovePlayer.getX(), clientMessageMovePlayer.getY());
			}
		});
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_PLAYER_CHANGED_MAP, ClientMessageChangeMap.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageChangeMap clientMessageChangeMap = (ClientMessageChangeMap) pClientMessage;
				
				sendMessagePlayerChangedMap(clientMessageChangeMap.getPlayerKey(), clientMessageChangeMap.getMapID(),clientMessageChangeMap.getX(),clientMessageChangeMap.getY());
				
				Player tmpPlayer = (Player)Game.getPlayerHelper().getPlayer(clientMessageChangeMap.getPlayerKey());
				//Si estaba solo en el mapa reciclo los mobs
				if(Game.getPlayerHelper().isAloneInMap(tmpPlayer)){
					ArrayList<Mob> mobsinmap = Game.getMobHelper().getMobsInMap(tmpPlayer.getCurrentMap());
					for(int i = mobsinmap.size()-1;i>=0;i--){
						Game.getSceneManager().getGameScene().detachChild(mobsinmap.get(i));
						Game.getSceneManager().getGameScene().unregisterTouchArea(mobsinmap.get(i).getBodySprite());
					}
				}
				
				if(tmpPlayer.getCurrentMap()==Game.getPlayerHelper().getOwnPlayer().getCurrentMap()){//si el mapa viejo era el mio
					Game.getSceneManager().getGameScene().detachChild(tmpPlayer.getCurrentMap());
					Game.getSceneManager().getGameScene().unregisterTouchArea(tmpPlayer.getBodySprite());//checkear si funciona ***
				}else{//si era otro
					if(clientMessageChangeMap.getMapID()==Game.getPlayerHelper().getOwnPlayer().getCurrentMap()){//si el mapa nuevo es el mio
						tmpPlayer.setTileAt(clientMessageChangeMap.getX(), clientMessageChangeMap.getY());
						Game.getSceneManager().getGameScene().attachChild(tmpPlayer);
						Game.getSceneManager().getGameScene().registerTouchArea(tmpPlayer.getBodySprite());
					}
				}
				tmpPlayer.setCurrentMap(clientMessageChangeMap.getMapID());
				
				if(!Game.getPlayerHelper().isAloneInMap(tmpPlayer)){//Si no esta solo en el map le tengo que mandar los mobs que existen en ese mapa
					ArrayList<Mob> mobsinmap = Game.getMobHelper().getMobsInMap(tmpPlayer.getCurrentMap());
					for(int i = mobsinmap.size()-1;i>=0;i--){
						final ServerMessageExistingMob serverMessageExistingMob = (ServerMessageExistingMob) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_EXISTING_MOB);
						serverMessageExistingMob.LoadMob(mobsinmap.get(i));
						try {
							pClientConnector.sendServerMessage(serverMessageExistingMob);
						} catch (IOException e) {
							Debug.e(e);
						}
						QServer.this.mMessagePool.recycleMessage(serverMessageExistingMob);						
					}
				}
			}
		});
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_REQUEST_MOBS, ClientMessageMobRequest.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageMobRequest clientMessageMobRequest = (ClientMessageMobRequest) pClientMessage;
				for(int i = 0;i<clientMessageMobRequest.getAmount();i++){
					Game.getSceneManager().getGameScene().CreateMob_Server(clientMessageMobRequest.getMOB_FLAG(), Game.getRandomInt(clientMessageMobRequest.getCorner1X(), clientMessageMobRequest.getCorner2X()), Game.getRandomInt(clientMessageMobRequest.getCorner1Y(), clientMessageMobRequest.getCorner2Y()), clientMessageMobRequest.getMap());
				}
			}
		});
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_ATTACK_MESSAGE, ClientMessageAttackMessage.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageAttackMessage clientMessageAttackMessage = (ClientMessageAttackMessage) pClientMessage;
				if(Game.getMobHelper().MobExists(clientMessageAttackMessage.getAttackedMobID())){//Si el mob existe/no lo mataron
					Game.getBattleHelper().startAttack(Game.getPlayerHelper().getPlayer(connectedClientProfileData.getUserID()), clientMessageAttackMessage.getAttackID(), Game.getMobHelper().getMob(clientMessageAttackMessage.getAttackedMobID()));
				}
			}
		});
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_AREA_ATTACK_MESSAGE, ClientMessageAreaAttack.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageAreaAttack clientMessageAreaAttack = (ClientMessageAreaAttack) pClientMessage;
				Attack tmpAtt = Game.getAttacksHelper().addNewAttack(clientMessageAreaAttack.getAttack_Flag());
				TMXTile tmpTile = Game.getMapManager().getTMXTileAt(clientMessageAreaAttack.getTileX(), clientMessageAreaAttack.getTileY());
				
				sendMessageDisplayAreaAttack(clientMessageAreaAttack.getAttack_Flag(), tmpTile.getTileX()+16, tmpTile.getTileY()+16, clientMessageAreaAttack.getMap(),connectedClientProfileData.getUserID());
				
				tmpAtt.setAnimationAtCenter(tmpTile.getTileX()+16,tmpTile.getTileY()+16);
				
				ArrayList<Mob> tmpMobsinArea = Game.getMobHelper().getMobsInArea(clientMessageAreaAttack.getTileX(), clientMessageAreaAttack.getTileY(), (int)(tmpAtt.getEffect()[1]));
				Game.getPlayerHelper().getPlayer(connectedClientProfileData.getUserID()).decreaseMP(Game.getAttacksHelper().getAttackManaCost(clientMessageAreaAttack.getAttack_Flag()));
				for(int i = tmpMobsinArea.size()-1;i>=0;i--){
					Game.getBattleHelper().manageAttack(Game.getPlayerHelper().getPlayer(connectedClientProfileData.getUserID()), tmpAtt.getAttackFlag(), tmpMobsinArea.get(i));	
				}
				
				if(clientMessageAreaAttack.getMap() == Game.getPlayerHelper().getOwnPlayer().getCurrentMap())
				Game.getSceneManager().getGameScene().getAttackLayer().add(tmpAtt);
				
			}
		});
		
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_SET_PLAYER_ATTRIBUTES, ClientMessageSetPlayerAttributes.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageSetPlayerAttributes clientMessageSetPlayerAttributes = (ClientMessageSetPlayerAttributes) pClientMessage;
				sendMessageSetPlayerAttributes(clientMessageSetPlayerAttributes.getPlayerID(), clientMessageSetPlayerAttributes.getAttributes(), clientMessageSetPlayerAttributes.getUnassigned());
				Game.getPlayerHelper().getPlayerbyPlayerID(clientMessageSetPlayerAttributes.getPlayerID()).setAttributes(clientMessageSetPlayerAttributes.getAttributes());
				Game.getPlayerHelper().getPlayerbyPlayerID(clientMessageSetPlayerAttributes.getPlayerID()).setUnassignedPoints(clientMessageSetPlayerAttributes.getUnassigned());
				Game.getQueryQueuer().addSetPlayerAttributesQuery(clientMessageSetPlayerAttributes.getPlayerID(), clientMessageSetPlayerAttributes.getAttributes(), clientMessageSetPlayerAttributes.getUnassigned());
			}
		});
		
		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_SEND_COLLIDE_TILES, ClientMessageSendCollideTiles.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ClientMessageSendCollideTiles clientMessageSendCollideTiles = (ClientMessageSendCollideTiles) pClientMessage;
				if(Game.getMapManager().getMapCollideTiles(clientMessageSendCollideTiles.getMapID())==null){
					ArrayList<int[]> tileCords = clientMessageSendCollideTiles.getTileList();
					ArrayList<TMXTile> tileList = new ArrayList<TMXTile>();
					for(int i = 0;i<tileCords.size();i++)tileList.add(new TMXTile(0, tileCords.get(i)[0], tileCords.get(i)[1], IMeasureConstants.TILE_SIZE, IMeasureConstants.TILE_SIZE, null));
					Game.getMapManager().addCollides(clientMessageSendCollideTiles.getMapID(),tileList);	
				}
			}
		});
		
		return clientConnector;
	}
// ===========================================================
// Getter & Setter
// ===========================================================

// ===========================================================
// Methods
// ===========================================================
public void sendBroadcast(QuestServerMessage pServerMessage){
	//int tmpHash = pServerMessage.hashCode();
	try {
		//pServerMessage.setMsgHash(tmpHash); Funciones de doble envio/checkeo de mensaje
		//pServerMessage.setMsgInstance(1);
		this.sendBroadcastServerMessage(pServerMessage);
		//wait(5);
		//pServerMessage.setMsgInstance(2);
		//this.sendBroadcastServerMessage(pServerMessage);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.mMessagePool.recycleMessage(pServerMessage);
}

public void sendMatchStartedMessage(){
	final ServerMessageMatchStarted serverMessageMatchStarted = (ServerMessageMatchStarted) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_MATCH_STARTED);
	sendBroadcast(serverMessageMatchStarted);
}

public void sendUpdateEntityPositionMessage(String pPlayerKey, int pX, int pY){			
	final ServerMessageUpdateEntityPosition serverMessageUpdateEntityPosition = (ServerMessageUpdateEntityPosition) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_UPDATE_ENTITY_POSITION);
	serverMessageUpdateEntityPosition.set(pPlayerKey, pX, pY);
	sendBroadcast(serverMessageUpdateEntityPosition);				
}

public void sendFixedAttackData(int pMobEntityUserData,int pAttackID,int pDamage,String pPlayerEntityUserData,boolean ismonsterAttacking){
	final ServerMessageFixedAttackData serverMessageFixedAttackData = (ServerMessageFixedAttackData) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_FIXED_ATTACK_DATA);
	serverMessageFixedAttackData.setMobEntityUserData(pMobEntityUserData);
	serverMessageFixedAttackData.setPlayerEntityUserData(pPlayerEntityUserData);
	serverMessageFixedAttackData.setDamage(pDamage);
	serverMessageFixedAttackData.setAttackID(pAttackID);
	serverMessageFixedAttackData.setMonsterAttacking(ismonsterAttacking);
	sendBroadcast(serverMessageFixedAttackData);
}

public void sendMobDiedMessage(int pMobEntityUserData,float pExperience,int pMoney,int pDroppedItem,int pDroppedAmount,String pPlayerKey){
	final ServerMessageMobDied serverMessageMobDied = (ServerMessageMobDied) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_MOB_DIED);
	serverMessageMobDied.setMobEntityUserData(pMobEntityUserData);
	serverMessageMobDied.setExperience(pExperience);
	serverMessageMobDied.setMoney(pMoney);
	serverMessageMobDied.setDroppedItem(pDroppedItem);
	serverMessageMobDied.setDroppedAmount(pDroppedAmount);
	serverMessageMobDied.setPlayerKey(pPlayerKey);
	sendBroadcast(serverMessageMobDied);
}

public void sendSpawnMobMessage(int MOB_FLAG,int MobID,int tileX,int tileY,int map){
	final ServerMessageSpawnMob serverMessageSpawnMob = (ServerMessageSpawnMob) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_SPAWN_MOB);
	serverMessageSpawnMob.setMOB_FLAG(MOB_FLAG);
	serverMessageSpawnMob.setMobID(MobID);
	serverMessageSpawnMob.setTileX(tileX);
	serverMessageSpawnMob.setTileY(tileY);
	serverMessageSpawnMob.setMap(map);
	sendBroadcast(serverMessageSpawnMob);
}
public void sendMessageMoveMob(int pMobKey, int pX, int pY){
	final ServerMessageMoveMob serverMessageMoveMob = (ServerMessageMoveMob) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_MOVE_MOB);
	serverMessageMoveMob.set(pMobKey, pX, pY);
	sendBroadcast(serverMessageMoveMob);
}

public void sendMessageDisplayAreaAttack(int pAttack_Flag, int pX,int pY,int pMap,String userID){
	final ServerMessageDisplayAreaAttack serverMessageDisplayAreaAttack = (ServerMessageDisplayAreaAttack) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_DISPLAY_AREA_ATTACK);
	serverMessageDisplayAreaAttack.setUserID(userID);
	serverMessageDisplayAreaAttack.setAttack_Flag(pAttack_Flag);
	serverMessageDisplayAreaAttack.setTileX(pX);
	serverMessageDisplayAreaAttack.setTileY(pY);
	serverMessageDisplayAreaAttack.setMap(pMap);
	sendBroadcast(serverMessageDisplayAreaAttack);
}

public void sendMessagePlayerChangedMap(String pPlayerKey, int pMapID, int pX, int pY){
	final ServerMessageMapChanged serverMessageMapChanged = (ServerMessageMapChanged) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_PLAYER_CHANGED_MAP);
	serverMessageMapChanged.setPlayerKey(pPlayerKey);
	serverMessageMapChanged.setMapID(pMapID);
	serverMessageMapChanged.setPos(pX, pY);
	sendBroadcast(serverMessageMapChanged);
}

public void sendMessagePlayerLevelUP(String pPlayerKey, int pLevel, int pUnassignedPoints){
	final ServerMessagePlayerLevelUP serverMessagePlayerLevelUP = (ServerMessagePlayerLevelUP) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_PLAYER_LEVELUP);
	serverMessagePlayerLevelUP.set(pPlayerKey, pLevel, pUnassignedPoints);
	sendBroadcast(serverMessagePlayerLevelUP);
}

public void sendMessageSetPlayerAttributes(int pPlayerID, int[] pAttributes, int pUnassigned){
	final ServerMessageSetPlayerAttributes serverMessageSetPlayerAttributes = (ServerMessageSetPlayerAttributes) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_SET_PLAYER_ATTRIBUTES);
	serverMessageSetPlayerAttributes.setPlayerID(pPlayerID);
	serverMessageSetPlayerAttributes.setAttributes(pAttributes);
	serverMessageSetPlayerAttributes.setUnassigned(pUnassigned);
	sendBroadcast(serverMessageSetPlayerAttributes);
}
// ===========================================================
// Inner and Anonymous Classes
// ===========================================================


}
