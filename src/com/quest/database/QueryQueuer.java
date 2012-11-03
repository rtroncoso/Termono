package com.quest.database;

import java.util.Queue;

import org.andengine.extension.multiplayer.protocol.adt.message.IMessage;
import org.andengine.extension.multiplayer.protocol.util.MessagePool;
import org.andengine.util.adt.queue.IQueue;

import com.quest.constants.GameFlags;
import com.quest.database.queries.IQuery;
import com.quest.database.queries.Query;
import com.quest.database.queries.QueryRegisterPlayerPosition;
import com.quest.pools.QueryPool;

public class QueryQueuer implements GameFlags{

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private final QueryPool<IQuery> mQueryPool = new QueryPool<IQuery>();
	private Queue<Query> mQueue;
	// ===========================================================
	// Constructors
	// ===========================================================
	public QueryQueuer(){
		this.initQueryPool();
		
	}
	
	private void initQueryPool(){
		this.mQueryPool.registerQuery(FLAG_QUERY_REGISTER_PLAYER_POSITION, QueryRegisterPlayerPosition.class);
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
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
