package android.peter.geometry.mesh.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class Mesh {

	// Translate params.
	public float x = 0;
	public float y = 0;
	public float z = 0;

	// Rotate params.
	public float rx = 0;
	public float ry = 0;
	public float rz = 0;

	// Our vertex buffer.
	private FloatBuffer verticesBuffer = null;

	// Our index buffer.
	private ShortBuffer indicesBuffer = null;

	// Our normals buffer.
	private FloatBuffer normalsBuffer = null;

	// Our texture coordinates buffer.
	private FloatBuffer textureBuffer = null;

	// The number of indices.
	private int numOfIndices = -1;

	// Flat Color
	private final float[] rgba = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };

	private int textureId = -1;

	private boolean shouldLoadTexture;

	Bitmap bitmap;

	// Smooth Colors
	private FloatBuffer colorBuffer = null;

	public void setColor(float r, float g, float b, float a) {
		rgba[0] = r;
		rgba[1] = g;
		rgba[2] = b;
		rgba[3] = a;
	}

	public void setColors(float[] colors) {
		// float has 4 bytes.
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}

	public void setVertices(float[] vertices) {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		verticesBuffer = vbb.asFloatBuffer();
		verticesBuffer.put(vertices);
		verticesBuffer.position(0);
	}

	public void setIndices(short[] indices) {
		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indicesBuffer = ibb.asShortBuffer();
		indicesBuffer.put(indices);
		indicesBuffer.position(0);
		numOfIndices = indices.length;
	}

	public void setNormals(float[] normals) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(normals.length * 4);
		ibb.order(ByteOrder.nativeOrder());
		normalsBuffer = ibb.asFloatBuffer();
		normalsBuffer.put(normals);
		normalsBuffer.position(0);
	}

	public void setTextureCoordinates(float[] texture) {
		if (texture != null) {
			ByteBuffer byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
			byteBuf.order(ByteOrder.nativeOrder());
			textureBuffer = byteBuf.asFloatBuffer();
			textureBuffer.put(texture);
			textureBuffer.position(0);
		}
	}

	public void loadBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		shouldLoadTexture = true;
	}

	public void draw(GL10 gl) {
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW);
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK);
		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
		// Set flat color
		gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
		// Smooth color
		if (colorBuffer != null) {
			// Enable the color array buffer to be used during rendering.
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			// Point out the where the color buffer is.
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		}
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		if (shouldLoadTexture) {
			this.loadGLTexture(gl);
			shouldLoadTexture = false;
		}

		if (normalsBuffer != null) {
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, normalsBuffer);
		}

		if (textureId != -1 && textureBuffer != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		}

		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
		gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices,
				GL10.GL_UNSIGNED_SHORT, indicesBuffer);
		gl.glPopMatrix();

		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		if (colorBuffer != null) {
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}

		if (normalsBuffer != null) {
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}

		if (textureId != -1 && textureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}

		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
	}

	/**
	 * Loads the texture.
	 * 
	 * @param gl
	 */
	protected void loadGLTexture(GL10 gl) {
		// Generate one texture pointer...
		int[] textures = new int[1];
		textureId = textures[0];
		gl.glGenTextures(1, textures, 0);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	}
}
