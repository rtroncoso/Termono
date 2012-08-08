package com.quest.network;

import java.io.IOException;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.server.SocketServer;
import org.andengine.extension.multiplayer.protocol.server.SocketServer.ISocketServerListener.DefaultSocketServerListener;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector;
import org.andengine.extension.multiplayer.protocol.server.connector.SocketConnectionClientConnector.ISocketConnectionClientConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.quest.network.messages.client.ClientMessageFlags;
import com.quest.network.messages.server.ServerMessageFlags;
import com.quest.util.constants.INetworkConstants;

public class Server extends SocketServer<SocketConnectionClientConnector> implements IUpdateHandler, INetworkConstants, ContactListener, ClientMessageFlags, ServerMessageFlags {

	// ===========================================================
	// Constants
	// ===========================================================
	private final MessagePool<IMessage> mMessagePool = new MessagePool<IMessage>();
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private SocketServer<SocketConnectionClientConnector> mSocketServer;
	private ServerConnector<SocketConnection> mServerConnector;

	
	// ===========================================================
	// Constructors
	// ===========================================================

	public Server(final ISocketConnectionClientConnectorListener pSocketConnectionClientConnectorListener) {
		super(SERVER_PORT, pSocketConnectionClientConnectorListener, new DefaultSocketServerListener<SocketConnectionClientConnector>());

		this.initMessagePool();
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================

	private void initMessagePool() {
		
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected SocketConnectionClientConnector newClientConnector(
			SocketConnection pSocketConnection) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================


}
