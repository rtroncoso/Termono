package com.quest.network.messages.client;
public interface ClientMessageFlags {
	// ===========================================================
	// Final Fields
	// ===========================================================

	/* Connection Flags. */
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE = Short.MIN_VALUE;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH = FLAG_MESSAGE_CLIENT_CONNECTION_CLOSE + 1;
	public static final short FLAG_MESSAGE_CLIENT_CONNECTION_PING = FLAG_MESSAGE_CLIENT_CONNECTION_ESTABLISH + 1;

	// ===========================================================
	// Methods
	// ===========================================================
}
