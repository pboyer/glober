package android.peter.control;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.SharedPreferences;
import android.peter.geometry.utility.Matrix4;
import android.peter.geometry.utility.OpenGLRenderer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchController extends ViewController implements OnTouchListener {

	Context context;

	/* Rotation values */
	private float xrot; // X Rotation
	private float yrot; // Y Rotation

	/* Rotation speed values */
	private float xspeed; // X Rotation Speed ( NEW )
	private float yspeed; // Y Rotation Speed ( NEW )
	private float oldX;
	private float oldY;
	private final float TOUCH_SCALE = 0.002f;

	public TouchController(Context context, OpenGLRenderer r) {
		super(context);

		this.setRenderer(r);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
	}

	@Override
	public void getOrientation(GL10 gl) {

		// these are no fuckin good
		// get pitch and roll, use these to create a rotation matrix
		// load this matrix

		Matrix4 gyroIncMat = new Matrix4();

		gyroIncMat.setFromEulerAngles(xrot, yrot, 0);

		gl.glLoadMatrixf(gyroIncMat.val, 0);

		//
		// gl.glRotatef(yrot, 0.0f, 0.0f, 1.0f); // X
		// gl.glRotatef(xrot, 0.0f, 1.0f, 0.0f); // Y

		xrot += xspeed;
		yrot += yspeed;
	}

	@Override
	public void setListening(boolean b) {

	}

	// @Override
	// public boolean onKeyUp(int keyCode, KeyEvent event) {
	// //
	// if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
	// yspeed -= 0.1f;
	//
	// } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
	// yspeed += 0.1f;
	//
	// } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
	// xspeed -= 0.1f;
	//
	// } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
	// xspeed += 0.1f;
	//
	// } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
	// filter += 1;
	// if (filter > 2) {
	// filter = 0;
	// }
	// }
	//
	// // We handled the event
	// return true;
	// }

	@Override
	public void setZoom(float z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getProjectionMatrix(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSettings(SharedPreferences sharedPreferences) {
		// TODO Auto-generated method stub

	}

	public boolean onTouch(View v, MotionEvent event) {
		//
		System.out.println("Touch controller touch");
		float x = event.getX();
		float y = event.getY();

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// Calculate the change
			float dx = x - oldX;
			float dy = y - oldY;

			xrot += dy * TOUCH_SCALE;
			yrot += dx * TOUCH_SCALE;

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			// user has picked up hand
		}

		// Remember the values
		oldX = x;
		oldY = y;

		// We handled the event
		return true;
	}

}
