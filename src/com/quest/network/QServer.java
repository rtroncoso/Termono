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

import com.quest.network.messages.client.ClientMessageFlags;
import com.quest.network.messages.client.ConnectionCloseClientMessage;
import com.quest.network.messages.client.ConnectionEstablishClientMessage;
import com.quest.network.messages.client.ConnectionPingClientMessage;
import com.quest.network.messages.server.ConnectionCloseServerMessage;
import com.quest.network.messages.server.ConnectionEstablishedServerMessage;
import com.quest.network.messages.server.ConnectionPongServerMessage;
import com.quest.network.messages.server.ConnectionRejectedProtocolMissmatchServerMessage;
import com.quest.network.messages.server.ServerMessageFlags;
import com.quest.network.messages.server.SetPaddleIDServerMessage;
import com.quest.util.constants.MessageConstants;


public class QServer extends SocketServer<SocketConnectionClientConnector> implements IUpdateHandler, ClientMessageFlags, ServerMessageFlags {
	
	
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
	public QServer(final ISocketConnectionClientConnectorListener pSocketConnectionClientConnectorListener) {
		super(SERVER_PORT, pSocketConnectionClientConnectorListener, new DefaultSocketServerListener<SocketConnectionClientConnector>());
		Log.d("Logd","Server started");
		this.initMessagePool();
	}
	
	private void initMessagePool() {
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED, ConnectionEstablishedServerMessage.class);
		//this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_TEST, TestServerMessage.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG, ConnectionPongServerMessage.class);
		this.mMessagePool.registerMessage(FLAG_MESSAGE_SERVER_CONNECTION_CLOSE, ConnectionCloseServerMessage.class);
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

		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE, ConnectionCloseClientMessage.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				pClientConnector.terminate();
				Log.d("Logd","Terminated");
			}
		});

		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH, ConnectionEstablishClientMessage.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ConnectionEstablishClientMessage connectionEstablishClientMessage = (ConnectionEstablishClientMessage) pClientMessage;
				if(connectionEstablishClientMessage.getProtocolVersion() == MessageConstants.PROTOCOL_VERSION) {
					final ConnectionEstablishedServerMessage connectionEstablishedServerMessage = (ConnectionEstablishedServerMessage) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_ESTABLISHED);
					try {
						pClientConnector.sendServerMessage(connectionEstablishedServerMessage);
						Log.d("Logd","Established sent");
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(connectionEstablishedServerMessage);
				} else {
					final ConnectionRejectedProtocolMissmatchServerMessage connectionRejectedProtocolMissmatchServerMessage = (ConnectionRejectedProtocolMissmatchServerMessage) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_REJECTED_PROTOCOL_MISSMATCH);
					connectionRejectedProtocolMissmatchServerMessage.setProtocolVersion(MessageConstants.PROTOCOL_VERSION);
					try {
						pClientConnector.sendServerMessage(connectionRejectedProtocolMissmatchServerMessage);
						Log.d("Logd","Rejected sent");
					} catch (IOException e) {
						Debug.e(e);
					}
					QServer.this.mMessagePool.recycleMessage(connectionRejectedProtocolMissmatchServerMessage);
				}
			}
		});

		clientConnector.registerClientMessage(FLAG_MESSAGE_CLIENT_CONNECTION_PING, ConnectionPingClientMessage.class, new IClientMessageHandler<SocketConnection>() {
			@Override
			public void onHandleMessage(final ClientConnector<SocketConnection> pClientConnector, final IClientMessage pClientMessage) throws IOException {
				final ConnectionPongServerMessage connectionPongServerMessage = (ConnectionPongServerMessage) QServer.this.mMessagePool.obtainMessage(FLAG_MESSAGE_SERVER_CONNECTION_PONG);
				try {
					pClientConnector.sendServerMessage(connectionPongServerMessage);
					Log.d("Logd","Pong");
				} catch (IOException e) {
					Debug.e(e);
				}
				QServer.this.mMessagePool.recycleMessage(connectionPongServerMessage);
			}
		});

		clientConnector.sendServerMessage(new SetPaddleIDServerMessage(this.mClientConnectors.size())); // TODO should not be size(), as it only works properly for first two connections!
		
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