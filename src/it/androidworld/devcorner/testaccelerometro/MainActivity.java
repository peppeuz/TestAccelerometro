package it.androidworld.devcorner.testaccelerometro;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	private boolean inizializzato;
	private SensorManager sensorManager;
	private Sensor accelerometro;
	private final float rumore = (float) 1.0;
	TextView asseX;
	TextView asseY;
	TextView asseZ;
	private float ultimaX, ultimaY, ultimaZ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inizializzato = false;
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometro = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometro,
				SensorManager.SENSOR_DELAY_NORMAL);
		asseX = (TextView) findViewById(R.id.asseX);
		asseY = (TextView) findViewById(R.id.asseY);
		asseZ = (TextView) findViewById(R.id.asseZ);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!inizializzato) {
			ultimaX = x;
			ultimaY = y;
			ultimaZ = z;
			asseX.setText("Asse X: 0.0");
			asseY.setText("Asse Y: 0.0");
			asseZ.setText("Asse Z: 0.0");
			inizializzato = true;
		} else {
			float deltaX = Math.abs(ultimaX - x);
			float deltaY = Math.abs(ultimaY - y);
			float deltaZ = Math.abs(ultimaZ - z);
			if (deltaX < rumore)
				deltaX = (float) 0.0;
			if (deltaY < rumore)
				deltaY = (float) 0.0;
			if (deltaZ < rumore)
				deltaZ = (float) 0.0;
			ultimaX = x;
			ultimaY = y;
			ultimaZ = z;
			asseX.setText("Asse X: " + Float.toString(deltaX));
			asseY.setText("Asse Y: " + Float.toString(deltaY));
			asseZ.setText("Asse Z: " + Float.toString(deltaZ));

		}
	}
}
