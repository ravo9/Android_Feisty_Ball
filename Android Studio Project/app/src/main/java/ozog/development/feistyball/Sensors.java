package ozog.development.feistyball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class Sensors {

    static public float[] mAccelerometerData;
    static public float[] mMagnetometerData;

    static public int sensorType;
    static public SensorManager sensorManager;
    static public Sensor mSensorAccelerometer;
    static public Sensor mSensorMagnetometer;

    static float[] rotationMatrix;
    static float[] orientationValues;

    static {
        mAccelerometerData = new float[3];
        mMagnetometerData = new float[3];

        sensorManager = (SensorManager)MainGame.c.getSystemService(Context.SENSOR_SERVICE);
        mSensorAccelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);

        rotationMatrix = new float[9];
        orientationValues = new float[3];
    }

    public static void onSensorChanged(SensorEvent sensorEvent) {

        Sensors.sensorType = sensorEvent.sensor.getType();

        switch (Sensors.sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                Sensors.mAccelerometerData = sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                Sensors.mMagnetometerData = sensorEvent.values.clone();
                break;
            default:
                return;
        }

        boolean rotationOK = SensorManager.getRotationMatrix(Sensors.rotationMatrix,
                null, Sensors.mAccelerometerData, Sensors.mMagnetometerData);

        if (rotationOK) {
            SensorManager.getOrientation(Sensors.rotationMatrix, Sensors.orientationValues);
        }
    }
}
