package com.quest.polygons;

import org.andengine.opengl.vbo.IVertexBufferObject;


public interface IPolygonVertexBufferObject  extends IVertexBufferObject {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void onUpdateColor(final Polygon pRectangle);
	public void onUpdateVertices(final Polygon pRectangle);
}
