package android.peter.geometry.mesh.primitives;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Square {
	private float[] vertices = {
	      -1.0f,  1.0f, 0.0f,  // 0, Top Left
	      -1.0f, -1.0f, 0.0f,  // 1, Bottom Left
	       1.0f, -1.0f, 0.0f,  // 2, Bottom Right
	       1.0f,  1.0f, 0.0f,  // 3, Top Right	
	};
	
	private short[] indices = { 0, 1, 2, 0, 2, 3};
	
	private FloatBuffer vertexBuffer;
	private ShortBuffer indexBuffer;
	
	public Square() {

		//store the vertices as a buffer
		//the allocateDirect factory method is called below.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		//vbb is a view buffer.  it is indexed by the content type.
		vertexBuffer = vbb.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		//store the faces in a byte buffer
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		indexBuffer = ibb.asShortBuffer();
		indexBuffer.put(indices);
		indexBuffer.position(0);
	}

	
	public void draw(GL10 gl) {
		
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glCullFace(GL10.GL_BACK);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_SHORT, indexBuffer);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisable(GL10.GL_CULL_FACE);
	}
}
