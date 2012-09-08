package android.peter.geometry.entry;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Debug;
import android.peter.control.GyroController;
import android.peter.control.PinchZoomController;
import android.peter.control.TouchController;
import android.peter.control.ViewController;
import android.peter.geometry.mesh.primitives.Globe;
import android.peter.geometry.mesh.primitives.Scene;
import android.peter.geometry.preferences.AppPreferences;
import android.peter.geometry.utility.OpenGLRenderer;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class SampleApp extends Activity {

	ViewController controller;
	OpenGLRenderer renderer;
	SharedPreferences sharedPreferences;
	Globe globe;
	PinchZoomController zoomController;

	// static {
	// System.loadLibrary("native-allocator");
	// }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		Scene scene = new Scene();
		renderer = new OpenGLRenderer(scene);
		controller = new TouchController(this, renderer);
		setContentView(controller);
		controller.setListening(true);
		renderer.setController(controller);

		zoomController = new PinchZoomController();
		controller.setOnTouchListener((TouchController) controller);

		sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getApplication());

		float radius = 50f;
		globe = new Globe(radius, 24, 12);
		globe.z = -radius;

		Bitmap b1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.maphi);
		globe.loadBitmap(b1);
		scene.meshGroup.add(globe);

		long allocatedBytes = Debug.getNativeHeapAllocatedSize();
		System.out.println("HEAP ALLOCATED: " + allocatedBytes);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		long allocatedBytes;
		switch (item.getItemId()) {
		case R.id.preferences:
			startActivity(new Intent(this, AppPreferences.class));
		case R.id.open_image:
			return true;
		case R.id.bridge_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b1 = BitmapFactory.decodeResource(getResources(),
					R.drawable.bridge);
			globe.loadBitmap(b1);
			return true;
		case R.id.globe_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b2 = BitmapFactory.decodeResource(getResources(),
					R.drawable.maphi);
			globe.loadBitmap(b2);
			return true;
		case R.id.mill_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b3 = BitmapFactory.decodeResource(getResources(),
					R.drawable.mill);
			globe.loadBitmap(b3);
			return true;
		case R.id.highway_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b4 = BitmapFactory.decodeResource(getResources(),
					R.drawable.equirectangular);
			globe.loadBitmap(b4);
			return true;
		case R.id.escher_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b5 = BitmapFactory.decodeResource(getResources(),
					R.drawable.escher);
			globe.loadBitmap(b5);
			return true;
		case R.id.ruins_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b6 = BitmapFactory.decodeResource(getResources(),
					R.drawable.ruins);
			globe.loadBitmap(b6);
			return true;
		case R.id.lusanne_image:
			allocatedBytes = Debug.getNativeHeapAllocatedSize();
			System.out.println("HEAP ALLOCATED: " + allocatedBytes);
			Bitmap b7 = BitmapFactory.decodeResource(getResources(),
					R.drawable.lusanne);
			globe.loadBitmap(b7);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		int zoomFactor = sharedPreferences.getInt(
				getString(R.string.zoom_factor), 40);
		zoomController.setZoomFactor(zoomFactor);

		// THIS CAN BE A PREFERENCE
		boolean useFullscreen = sharedPreferences.getBoolean(
				getString(R.string.use_fullscreen), false);

		if (useFullscreen) {
			WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			this.getWindow().setAttributes(attrs);
		} else {
			// go non-full screen
			WindowManager.LayoutParams attrs = this.getWindow().getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			this.getWindow().setAttributes(attrs);
		}

		boolean useOrientationSensors = sharedPreferences.getBoolean(
				getString(R.string.use_orientation_sensors), false);

		// TODO: this is kinda stupid looking

		if (!useOrientationSensors) {
			if (!(controller instanceof TouchController)) {
				System.out.println("Setting touch controller to control");
				controller = new TouchController(this, renderer);
				renderer.setController(controller);
				controller.setOnTouchListener((TouchController) controller);
			}
		} else {
			if (!(controller instanceof GyroController)) {
				System.out.println("Setting gyro controller to control");
				controller = new GyroController(this, renderer);
				renderer.setController(controller);
				controller.setOnTouchListener(zoomController);
			}
		}
		controller.updateSettings(sharedPreferences);
		controller.setListening(true);

	}

	@Override
	public void onStop() {
		super.onStop();
		controller.setListening(false);
	}
}