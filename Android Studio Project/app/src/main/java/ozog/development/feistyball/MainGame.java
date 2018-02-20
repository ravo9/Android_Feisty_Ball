package ozog.development.feistyball;

import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame extends AppCompatActivity implements SensorEventListener {

    ImageView ball;
    Button btnCenter;

    Timer timer;
    Handler handler;

    int screenWidth;
    int screenHeight;

    float[] mAccelerometerData = new float[3];
    float[] mMagnetometerData = new float[3];

    int sensorType;

    SensorManager sensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    SensorEventListener sensorListener;

    float[] rotationMatrix;
    float[] orientationValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        ball = findViewById(R.id.ball);
        btnCenter = findViewById(R.id.btnCenter);

        ball.setX( (float)screenWidth/ 2.0f - 50 );
        ball.setY( (float)screenHeight/ 2.0f - 50 );

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorAccelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);
        rotationMatrix = new float[9];
        orientationValues = new float[3];

        handler = new Handler();

        Database.createDatabase();
        DatabaseHandler x = new DatabaseHandler(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorAccelerometer != null) {
            sensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            sensorManager.registerListener(this, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    public void centerBall( View v ) {
        ball.setX( (float)screenWidth/ 2.0f - 50 );
        ball.setY( (float)screenHeight/ 2.0f - 50 );
    }

    public void newGame(View v) {

        timer = new Timer();

        // Movement
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run () {
                        update();
                    }
                });
            }
        }, 0, 10);

    }

    public void update() {
        ball.setX( ball.getX() + orientationValues[2] * 10 );
        ball.setY( ball.getY() - orientationValues[1] * 10 );
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        sensorType = sensorEvent.sensor.getType();

        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerometerData = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetometerData = sensorEvent.values.clone();
                break;
            default:
                return;
        }

        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,
                null, mAccelerometerData, mMagnetometerData);

        if (rotationOK) {
            SensorManager.getOrientation(rotationMatrix, orientationValues);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
