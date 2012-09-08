package android.peter.control;

import android.content.Context;
import android.content.SharedPreferences;

public class GyroPreferences {

	private static SharedPreferences sharedPreferences;
	public static float accelerometerFilter = 1.0f;
	public static float compassFilter = 1.0f;
	public static float gyroscopeFilter = 0.038f;
	public static boolean useGyroscope;
	public static Context context;

	public GyroPreferences(SharedPreferences appSharedPrefs, Context context) {
		this.sharedPreferences = appSharedPrefs;
		this.context = context;
	}

	public static void update() {

	}
}
