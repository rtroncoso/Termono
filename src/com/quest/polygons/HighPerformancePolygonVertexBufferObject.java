package com.quest.polygons;

import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.HighPerformanceVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;


public class HighPerformancePolygonVertexBufferObject extends HighPerformanceVertexBufferObject implements IPolygonVertexBufferObject {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public HighPerformancePolygonVertexBufferObject(final VertexBufferObjectManager pVertexBufferObjectManager, final int pCapacity, final DrawType pDrawType, final boolean pAutoDispose, final VertexBufferObjectAttributes pVertexBufferObjectAttributes) {
		super(pVertexBufferObjectManager, pCapacity, pDrawType, pAutoDispose, pVertexBufferObjectAttributes);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdateColor(final Polygon pPolygon) {
		final float[] bufferData = this.mBufferData;

		final float packedColor = pPolygon.getColor().getABGRPackedFloat();
		for(int i = 0;i<pPolygon.getVertices().size();i++)
		{
			bufferData[i * Polygon.VERTEX_SIZE + Polygon.COLOR_INDEX] = packedColor;
		}
		
		this.setDirtyOnHardware();
	}

	@Override
	public void onUpdateVertices(final Polygon pPolygon) {
		final float[] bufferData = this.mBufferData;
		for(int i = 0;i<pPolygon.getVertices().size();i++)
		{
			bufferData[i * Polygon.VERTEX_SIZE + Polygon.VERTEX_INDEX_X] = pPolygon.getVertices().get(i)[0];
			bufferData[i * Polygon.VERTEX_SIZE + Polygon.VERTEX_INDEX_Y] = pPolygon.getVertices().get(i)[1];
		}
		
		this.setDirtyOnHardware();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}