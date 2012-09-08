package android.peter.control;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLU;
import android.peter.geometry.entry.R;
import android.peter.geometry.utility.Matrix4;
import android.peter.geometry.utility.OpenGLRenderer;

public class GyroController extends ViewController {

	// gyroscope values
	private static final float NS2S = 1.0f / 1000000000.0f;
	private float timestamp;
	private boolean useGyro;
	private boolean gyroStarted;
	public float kFilteringFactor = 1.0f;
	public float kFilteringFactorComp = 1.0f;
	private float gyroscopeFilter;

	public float[] viewingPosition;

	private boolean compAccelReady = false;
	private boolean compassValuesSensed = false;
	private boolean accelValuesSensed = false;
	private final float[] accValues;
	private final float[] compValues;
	// private final float[] compAccMat = new float[9];

	private final Matrix4 compAccMat;
	// private final Quaternion compAccQuat;
	// private final Quaternion peterQuat;
	private Matrix4 peterMat;

	Context context;
	OpenGLRenderer renderer;
	SensorManager sm;
	float zoom = 1.0f;
	boolean zoomChanged = false;
	final float lensLength = 60.0f;
	float rx;
	float ry;
	float rz;

	public GyroController(Context context, OpenGLRenderer r) {
		super(context);

		viewingPosition = new float[] { 0, 0, 0 };
		compAccMat = new Matrix4();
		// compAccQuat = new Quaternion(0, 0, 0, 0);
		// peterQuat = new Quaternion(0, 0, 0, 0);
		peterMat = new Matrix4();
		accValues = new float[3];
		compValues = new float[3];

		this.context = context;
		this.renderer = r;

		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

		this.setRenderer(r);
		this.requestFocus();
		this.setFocusableInTouchMode(true);

	}

	@Override
	public void setZoom(float val) {
		if (val * lensLength > 7.5f && val * lensLength < 100f) {
			zoom = val;
			zoomChanged = true;
		}
	}

	public void useGyro(boolean b) {
		useGyro = b;
		if (b) {
			kFilteringFactor = 1.0f;
			kFilteringFactorComp = 1.0f;
		} else {
			kFilteringFactor = 0.03f;
			kFilteringFactorComp = 0.03f;
		}

		setListening(true);
	}

	@Override
	public void getOrientation(GL10 gl) {

		if (useGyro)
			gl.glLoadMatrixf(peterMat.val, 0);
		else
			gl.glLoadMatrixf(compAccMat.val, 0);
		gl.glTranslatef(-viewingPosition[0], -viewingPosition[1],
				-viewingPosition[2]);

	}

	@Override
	public void getProjectionMatrix(GL10 gl, int width, int height) {
		if (zoomChanged) {
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl, lensLength * zoom, (float) width
					/ (float) height, 0.1f, 10000.0f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			zoomChanged = false;
		}
	}

	@Override
	public void setListening(boolean b) {
		if (b) {
			// TODO: must check for the whether these sensors exist or not
			// see AppPreferences.java
			if (useGyro) {
				sm.registerListener(sel,
						sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
						SensorManager.SENSOR_DELAY_FASTEST);
			}
			sm.registerListener(sel,
					sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					SensorManager.SENSOR_DELAY_FASTEST);
			sm.registerListener(sel,
					sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
					SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			gyroStarted = false;
			sm.unregisterListener(sel);
		}
	}

	private final SensorEventListener sel = new SensorEventListener() {
		public void onSensorChanged(SensorEvent event) {

			switch (event.sensor.getType()) {

			case (Sensor.TYPE_GYROSCOPE):
				// System.out.println("gyro");
				float dT = (event.timestamp - timestamp) * NS2S;
				// int frequency = 30;

				if (compAccelReady) {

					// initializes the matrix
					if (!gyroStarted) {
						peterMat.set(compAccMat);
						gyroStarted = true;
					}
					Matrix4 gyroIncMat = new Matrix4();

					gyroIncMat.setFromEulerAngles(event.values[0] * dT,
							event.values[1] * dT, event.values[2] * dT);

					peterMat = peterMat.mul(gyroIncMat);

					peterMat.lerp(compAccMat, gyroscopeFilter);

					compAccelReady = false;
				}

				break;

			case (Sensor.TYPE_ACCELEROMETER):
				// System.out.println("accel");
				accelValuesSensed = true;

				accValues[0] = (float) ((event.values[0] * kFilteringFactor) + (accValues[0] * (1.0 - kFilteringFactor)));
				accValues[1] = (float) ((event.values[1] * kFilteringFactor) + (accValues[1] * (1.0 - kFilteringFactor)));
				accValues[2] = (float) ((event.values[2] * kFilteringFactor) + (accValues[2] * (1.0 - kFilteringFactor)));

				if (compassValuesSensed) {
					compAccelReady = true;
				}

				break;

			case (Sensor.TYPE_MAGNETIC_FIELD):
				// System.out.println("comp");
				compassValuesSensed = true;

				compValues[0] = (float) ((event.values[0] * kFilteringFactorComp) + (compValues[0] * (1.0 - kFilteringFactorComp)));
				compValues[1] = (float) ((event.values[1] * kFilteringFactorComp) + (compValues[1] * (1.0 - kFilteringFactorComp)));
				compValues[2] = (float) ((event.values[2] * kFilteringFactorComp) + (compValues[2] * (1.0 - kFilteringFactorComp)));

				if (accelValuesSensed) {
					compAccelReady = true;
				}

				break;

			}

			timestamp = event.timestamp;

			if (compAccelReady) {

				SensorManager.getRotationMatrix(compAccMat.val, null,
						accValues, compValues);
				compAccelReady = true;
				compassValuesSensed = false;
				accelValuesSensed = false;

			}

		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// no need to do anything else here
		}
	};

	@Override
	public void updateSettings(SharedPreferences sharedPreferences) {

		boolean useGyroscope = sharedPreferences.getBoolean(
				context.getString(R.string.use_gyroscope), true);
		int compassSmoothing = sharedPreferences.getInt(
				context.getString(R.string.compass_smoothing), 40);
		int accelerometerSmoothing = sharedPreferences.getInt(
				context.getString(R.string.accelerometer_smoothing), 40);
		int gyroscopeSmoothing = sharedPreferences.getInt(
				context.getString(R.string.gyroscope_smoothing), 38);

		useGyro = useGyroscope;
		kFilteringFactor = 0.005f + (100 - accelerometerSmoothing) * 0.001f;
		kFilteringFactorComp = 0.005f + (100 - compassSmoothing) * 0.001f;
		gyroscopeFilter = (100 - gyroscopeSmoothing) * 0.001f;

	}

}
