package com.quest.database.queries;


public interface IQuery {
		// ===========================================================
		// Final Fields
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		public short getFlag();
		public void executeQuery();
		public void clearQuery();
	
}
