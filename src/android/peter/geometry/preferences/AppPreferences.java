package android.peter.geometry.preferences;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.peter.geometry.entry.R;
import android.preference.PreferenceActivity;

public class AppPreferences extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {
	SensorManager sm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

		List<Sensor> accelList = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
		List<Sensor> compassList = sm.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);

		addPreferencesFromResource(R.xml.app_preferences);

		if (accelList.size() != 0 && compassList.size() != 0) {

			addPreferencesFromResource(R.xml.acc_comp_preferences);

			List<Sensor> gyroscopeList = sm
					.getSensorList(Sensor.TYPE_GYROSCOPE);

			if (gyroscopeList.size() != 0) {
				addPreferencesFromResource(R.xml.gyro_preferences);
			}
		}

		// TODO: default settings don't fuckin work
		// TODO: implement touch controller so it does not suck

		// TODO: get a better icon

		// TODO: better precision globe as user setting

	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
