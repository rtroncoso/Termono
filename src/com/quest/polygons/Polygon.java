package com.quest.polygons;

import java.util.Vector;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.HighPerformanceRectangleVertexBufferObject;
import org.andengine.entity.shape.IShape;
import org.andengine.opengl.shader.PositionColorShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttribute;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;


import android.opengl.GLES20;

public class Polygon extends PolygonShape {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int VERTEX_INDEX_X = 0;
	public static final int VERTEX_INDEX_Y = Polygon.VERTEX_INDEX_X + 1;
	public static final int COLOR_INDEX = Polygon.VERTEX_INDEX_Y + 1;

	public static final int VERTEX_SIZE = 2 + 1;
//	public static final int VERTICES_PER_POLYGON = 4;
//	public static final int RECTANGLE_SIZE = Polygon.VERTEX_SIZE * Polygon.VERTICES_PER_POLYGON;

	public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(2)
		.add(ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, GLES20.GL_FLOAT, false)
		.add(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, GLES20.GL_UNSIGNED_BYTE, true)
		.build();

	// ===========================================================
	// Fields
	// ===========================================================

	protected final IPolygonVertexBufferObject mPolygonVertexBufferObject;
	private int VerticesCount = 5;
	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Uses a default {@link HighPerformanceRectangleVertexBufferObject} in {@link DrawType#STATIC} with the {@link VertexBufferObjectAttribute}s: {@link Rectangle#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
	 */
	public Polygon(final Vector<float[]> pVertices, final VertexBufferObjectManager pVertexBufferObjectManager,int MaxVertices) {
		this(pVertices, pVertexBufferObjectManager, DrawType.STATIC,MaxVertices);
	}

	/**
	 * Uses a default {@link HighPerformanceRectangleVertexBufferObject} with the {@link VertexBufferObjectAttribute}s: {@link Rectangle#VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT}.
	 */
	public Polygon(final Vector<float[]> pVertices, final VertexBufferObjectManager pVertexBufferObjectManager, final DrawType pDrawType,int MaxVertices) {
		this(pVertices, new HighPerformancePolygonVertexBufferObject(pVertexBufferObjectManager, Polygon.VERTEX_SIZE*MaxVertices, pDrawType, true, Polygon.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
	}
/*
	public Polygon(final float pX, final float pY, final float pWidth, final float pHeight, final IPolygonVertexBufferObject pPolygonVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, PositionColorShaderProgram.getInstance());

		this.mPolygonVertexBufferObject = pPolygonVertexBufferObject;

		this.onUpdateVertices();
		this.onUpdateColor();

		this.setBlendingEnabled(true);
	}
*/
	
	public Polygon(final Vector<float[]> pVertices, final IPolygonVertexBufferObject pPolygonVertexBufferObject) {
		super(pVertices, PositionColorShaderProgram.getInstance());
		this.VerticesCount = pVertices.size();
		this.mPolygonVertexBufferObject = pPolygonVertexBufferObject;

		this.onUpdateVertices();
		this.onUpdateColor();

		this.setBlendingEnabled(true);
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public IPolygonVertexBufferObject getVertexBufferObject() {
		return this.mPolygonVertexBufferObject;
	}

	@Override
	protected void preDraw(final GLState pGLState, final Camera pCamera) {
		super.preDraw(pGLState, pCamera);

		this.mPolygonVertexBufferObject.bind(pGLState, this.mShaderProgram);
	}

	@Override
	protected void draw(final GLState pGLState, final Camera pCamera) {
		this.mPolygonVertexBufferObject.draw(GLES20.GL_TRIANGLE_STRIP, VerticesCount);
	}

	@Override
	protected void postDraw(final GLState pGLState, final Camera pCamera) {
		this.mPolygonVertexBufferObject.unbind(pGLState, this.mShaderProgram);

		super.postDraw(pGLState, pCamera);
	}

	@Override
	protected void onUpdateColor() {
		this.mPolygonVertexBufferObject.onUpdateColor(this);
	}

	@Override
	protected void onUpdateVertices() {
		this.mPolygonVertexBufferObject.onUpdateVertices(this);
	}

	@Override
	public boolean collidesWith(IShape pOtherShape) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(float pX, float pY) {
		// TODO Auto-generated method stub
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void addVertice(float[] vertice){
		this.mVertices.add(vertice);
		this.VerticesCount++;
		this.onUpdateVertices();
		this.onUpdateColor();
	}
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}