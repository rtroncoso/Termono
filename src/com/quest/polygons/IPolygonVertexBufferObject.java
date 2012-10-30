package com.quest.polygons;

import org.andengine.opengl.vbo.IVertexBufferObject;


public interface IPolygonVertexBufferObject  extends IVertexBufferObject {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void onUpdateColor(final Polygon pPolygon);
	public void onUpdateVertices(final Polygon pPolygon);
}
