package android.peter.geometry.utility;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.peter.control.ViewController;
import android.peter.geometry.mesh.primitives.Scene;

public class OpenGLRenderer implements Renderer {

	static {
		System.loadLibrary("native_allocator");
	}

	private native String invokeNativeFunction();

	private native Object freeNativeBuffer();

	private native String allocNativeBuffer();

	ViewController controller = null; // UIController, if applicable
	private Scene scene;
	int width;
	int height;

	public OpenGLRenderer(Scene scene) {
		this.scene = scene;
		String hello = invokeNativeFunction();
		System.out.println("THIS IS A NATIVE CALL: " + hello);
	}

	public void setController(ViewController g) {
		controller = g;

	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		// need to get width & height
		controller.getProjectionMatrix(gl, width, height);

		if (scene.lightGroup.isShining()) {
			gl.glEnable(GL10.GL_LIGHTING);
		} else {
			gl.glDisable(GL10.GL_LIGHTING);
		}

		if (controller != null) {
			controller.getOrientation(gl);
		}

		if (scene != null)
			scene.drawMeshes(gl);

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 85f, (float) width / (float) height, 0.1f,
				100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		if (scene.lightGroup.isShining())
			scene.enableLights(gl);

		width = controller.getWidth();
		height = controller.getHeight();

		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

}