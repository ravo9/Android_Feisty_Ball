package ozog.development.feistyball;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Timer;

public class MainGame extends AppCompatActivity implements SensorEventListener {

    public static RelativeLayout rl;
    public static Context c;
    public static MainGame game;

    public static FrameLayout windowLevelComplited;

    static Timer timer;
    static Handler handler;

    public static int blackHoleFreezer;
    public static int currentLevel;

    public static int screenWidth;
    public static int screenHeight;

    float[] mAccelerometerData = new float[3];
    float[] mMagnetometerData = new float[3];

    int sensorType;
    SensorManager sensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;

    float[] rotationMatrix;
    static float[] orientationValues;

    // In centiseconds.
    static int gameTime;
    static int levelTime;

    static ArrayList<Obstacle> obstacles;
    static public ArrayList<Propeller> propellers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        rl = findViewById(R.id.RelativeLayout1);
        c = getApplicationContext();
        game = this;

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        blackHoleFreezer = 0;

        windowLevelComplited = findViewById(R.id.windowLevelComplited);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorAccelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);
        rotationMatrix = new float[9];
        orientationValues = new float[3];

        handler = new Handler();

        // Upload images into drawables.
        Drawables.uploadImages(getApplicationContext());

        // Upload connections to the layout elements
        Layout.loadLayoutElementsConnections(this);

        obstacles = new ArrayList<>();
        propellers = new ArrayList<>();

        currentLevel = 0;

        windowLevelComplited.animate().translationX(screenWidth);

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

    public void loadNextLevel(View v) {
        Level.loadNextLevel(v);
    }

    public void restartLevel(View v) {
        currentLevel--;
        Level.loadNextLevel(v);
    }

    public static void updateTime() {

        levelTime++;
        gameTime++;

        Layout.levelTimer.setText("Level Time: " + displayTime(levelTime));
        Layout.gameTimer.setText("Game Time: " + displayTime(gameTime));
    }

    public static String displayTime(int time) {
        int minutes = (int)time/6000;
        int seconds = (int)(time - 6000 * minutes)/100;
        int centiseconds = time - 6000 * minutes - 100 * seconds;

        String min = Integer.toString(minutes);
        if ( minutes < 10 )
            min = "0" + min;

        String sec = Integer.toString(seconds);
        if ( seconds < 10 )
            sec = "0" + sec;

        String centsec = Integer.toString(centiseconds);
        if ( centiseconds < 10 )
            centsec = "0" + centsec;

        return (min + ":" + sec + ":" + centsec);
    }

    public static void update() {

        float currentX = Layout.ball.getX();
        float currentY = Layout.ball.getY();

        float newX = Layout.ball.getX() + orientationValues[2] * 10;
        float newY = Layout.ball.getY() - orientationValues[1] * 10;

        // Check if there is any obstacle (e.g. brick)
        if (isObstacleArea(newX, newY) == false) {
            Layout.ball.setX( newX );
            Layout.ball.setY( newY );
        } else if (isObstacleArea(newX, currentY) == false) {
            Layout.ball.setX( newX );
        } else if (isObstacleArea(currentX, newY) == false) {
            Layout.ball.setY(newY);
        }

        // Check if there is a propeller
        float ballCenterPointX = currentX + 90;
        float ballCenterPointY = currentY + 90;

        for (Propeller p: propellers) {
            if (ballCenterPointX > p.getCenterX() - 50 && ballCenterPointX < p.getCenterX() + 50) {
                if (ballCenterPointY > p.getCenterY() - 50 && ballCenterPointY < p.getCenterY() + 50) {
                    if (!p.switchedOn())
                        p.switchOn();
                }
            }
        }

        // Check if there is a black hole
        if (blackHoleFreezer == 0) {
            if (ballCenterPointX > Layout.blackHoleA.getX() + 120 - 50 && ballCenterPointX < Layout.blackHoleA.getX() + 120 + 50) {
                if (ballCenterPointY > Layout.blackHoleA.getY() + 120 - 50 && ballCenterPointY < Layout.blackHoleA.getY() + 120 + 50) {
                    Layout.ball.setX(Layout.blackHoleB.getX() + 30);
                    Layout.ball.setY(Layout.blackHoleB.getY() + 30);
                    // Freeze black hole per 3 seconds
                    blackHoleFreezer = 300;
                }
            }

            if (ballCenterPointX > Layout.blackHoleB.getX() + 120 - 50 && ballCenterPointX < Layout.blackHoleB.getX() + 120 + 50) {
                if (ballCenterPointY > Layout.blackHoleB.getY() + 120 - 50 && ballCenterPointY < Layout.blackHoleB.getY() + 120 + 50) {
                    Layout.ball.setX(Layout.blackHoleA.getX() + 30);
                    Layout.ball.setY(Layout.blackHoleA.getY() + 30);
                    // Freeze black hole per 3 seconds
                    blackHoleFreezer = 300;
                    //ball.animate().translationX(ball.getX() + 100).setDuration(400);
                }
            }
        }
        else
            blackHoleFreezer--;

        // Check if there is a bonus
        if (ballCenterPointX > Layout.bonus01.getX() + 100 - 50 && ballCenterPointX < Layout.bonus01.getX() + 100 + 50) {
            if (ballCenterPointY > Layout.bonus01.getY() + 100 - 50 && ballCenterPointY < Layout.bonus01.getY() + 100 + 50) {
                gameTime -= 60;
                levelTime -= 60;
                Toast.makeText(c, "-6 seconds!", Toast.LENGTH_SHORT).show();
                Layout.bonus01.setVisibility(View.INVISIBLE);
            }
        }

        // Update propellers rotation
        for (Propeller p: propellers) {
            if (p.switchedOn()) {
                float currentPosition = p.getImage().getRotation();
                p.getImage().setRotation(currentPosition + 1);
            }
        }

        // Check if all propellers are switched on and the destination can be unlocked
        boolean allSwitched = true;
        for (Propeller p: propellers) {
            if (!p.switchedOn()) {
                allSwitched = false;
            }
        }
        if (allSwitched == true) {
            Layout.destination.setImageDrawable(Drawables.destinationImage);
            Layout.destination.setRotation(Layout.destination.getRotation() + (float)0.5);
            // Check if the destination has been achieved.
            if (ballCenterPointX > Layout.destination.getX() +  175 - 50 && ballCenterPointX < Layout.destination.getX() + 175 + 50) {
                if (ballCenterPointY > Layout.destination.getY() + 175 - 50 && ballCenterPointY < Layout.destination.getY() + 175 +50) {
                    Level.levelComplited(rl);
                }
            }
        }
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

    public static boolean isObstacleArea(float x, float y) {

        // x and y are left top corner of the ball image

        for (Obstacle o: obstacles) {
            if (collides(x + 100, y + 100, o))
                return true;
        }

        return false;

    }

    // 'The coding daddy' function
    private static boolean collides(float ballX, float ballY, Obstacle o) {

        float closestX = clamp(ballX, o.getOriginX(), o.getOriginX() + o.getWidth());
        float closestY = clamp(ballY, o.getOriginY(), o.getHeight() + o.getOriginY());

        float distanceX = ballX - closestX;
        float distanceY = ballY - closestY;

        // 100 is a ball radius
        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(100, 2);
    }

    // 'The coding daddy' function
    public static float clamp(float value, float min, float max) {
        float x = value;
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }

}
