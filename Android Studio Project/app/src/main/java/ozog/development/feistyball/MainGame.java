package ozog.development.feistyball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainGame extends AppCompatActivity implements SensorEventListener {

    public static RelativeLayout rl;
    public static Context c;
    public static MainGame game;
    public static FrameLayout windowLevelComplited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        rl = findViewById(R.id.RelativeLayout1);
        c = getApplicationContext();
        game = this;
        windowLevelComplited = findViewById(R.id.windowLevelComplited);

        // Upload images into drawables.
        Drawables.uploadImages(getApplicationContext());

        // Upload connections to the layout elements
        Layout.loadLayoutElementsConnections(this);

        windowLevelComplited.animate().translationX(Layout.screenWidth);
    }

    // Sensors functionality that hasn't been moved to Sensors class
    @Override
    protected void onStart() {
        super.onStart();
        if (Sensors.mSensorAccelerometer != null) {
            Sensors.sensorManager.registerListener(this, Sensors.mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (Sensors.mSensorMagnetometer != null) {
            Sensors.sensorManager.registerListener(this, Sensors.mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Sensors.sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensors.onSensorChanged(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void loadNextLevel(View v) {
        Level.loadNextLevel(v);
    }

    public void restartLevel(View v) { Level.restartLevel(v); }

}
