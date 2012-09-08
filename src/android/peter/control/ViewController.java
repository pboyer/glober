package android.peter.control;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;

public abstract class ViewController extends GLSurfaceView {

	public ViewController(Context context) {
		super(context);
	}

	public abstract void updateSettings(SharedPreferences sharedPreferences);

	public abstract void getOrientation(GL10 gl);

	public abstract void setListening(boolean b);

	public abstract void setZoom(float z);

	public abstract void getProjectionMatrix(GL10 gl, int width, int height);
}
