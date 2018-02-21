package ozog.development.feistyball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame extends AppCompatActivity implements SensorEventListener {

    public static RelativeLayout rl;
    public static Context c;

    static ImageView ball;
    static ImageView destination;

    static Button btnCenter;
    static Button btnNewGame;

    static Drawable brickImage;
    static Drawable propellerImage;

    Timer timer;
    Handler handler;

    public static int screenWidth;
    public static int screenHeight;

    float[] mAccelerometerData = new float[3];
    float[] mMagnetometerData = new float[3];

    int sensorType;

    SensorManager sensorManager;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    SensorEventListener sensorListener;

    float[] rotationMatrix;
    float[] orientationValues;

    static ArrayList<Obstacle> obstacles;
    static public ArrayList<Propeller> propellers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        rl = findViewById(R.id.RelativeLayout1);
        c = getApplicationContext();

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        ball = findViewById(R.id.ball);
        destination = findViewById(R.id.destination);

        btnCenter = findViewById(R.id.btnCenter);
        btnNewGame = findViewById(R.id.btnNewGame);



        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorAccelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);
        rotationMatrix = new float[9];
        orientationValues = new float[3];

        handler = new Handler();

        try {
            InputStream stream = getAssets().open("brick.png");
            brickImage = Drawable.createFromStream(stream, null);
            stream = getAssets().open("propeller.png");
            propellerImage = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        obstacles = new ArrayList<>();
        propellers = new ArrayList<>();

        ball.setMinimumWidth(180);
        ball.setMinimumHeight(180);
        ball.setMaxWidth(180);
        ball.setMaxHeight(180);


        // Animation code taken from StackOverflow
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        destination.setAnimation(rotate);
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

        btnNewGame.setVisibility(View.INVISIBLE);

        Level.setLevel1(this);

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

        float currentX = ball.getX();
        float currentY = ball.getY();

        float newX = ball.getX() + orientationValues[2] * 10;
        float newY = ball.getY() - orientationValues[1] * 10;

        // Check if is there any obstacle (e.g. brick)
        if (isObstacleArea(newX, newY) == false) {
            ball.setX( newX );
            ball.setY( newY );
        } else if (isObstacleArea(newX, currentY) == false) {
            ball.setX( newX );
        } else if (isObstacleArea(currentX, newY) == false) {
            ball.setY(newY);
        }

        // Check if is there a propeller

        float ballCenterPointX = currentX + 45;
        float ballCenterPointY = currentY + 45;

        for (Propeller p: propellers) {
            if (ballCenterPointX > p.getCenterX() - 30 && ballCenterPointX < p.getCenterX() + 30) {
                if (ballCenterPointY > p.getCenterY() - 30 && ballCenterPointY < p.getCenterY() + 30) {
                    if (p.switchedOn() == false)
                        p.switchOn();
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



    public boolean isObstacleArea(float x, float y) {

        // x and y are left top corner of the ball image

        for (Obstacle o: obstacles) {
            if (collides(x + 90, y + 90, o))
                return true;
        }

        return false;

    }

    // 'The coding daddy' function
    private boolean collides(float ballX, float ballY, Obstacle o) {

        float closestX = clamp(ballX, o.getOriginX(), o.getOriginX() + o.getWidth());
        float closestY = clamp(ballY, o.getOriginY(), o.getHeight() + o.getOriginY());

        float distanceX = ballX - closestX;
        float distanceY = ballY - closestY;

        // 100 is a ball radius
        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(90, 2);
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

    public void addBrick(int x, int y) {

        ImageView brick = new ImageView(this);
        brick.setImageDrawable(brickImage);

        // Change to relative
        int brickWidth = (int)(screenWidth * 0.2);
        int brickHeight = (int)(brickWidth * 0.25);

        brick.setMinimumHeight(brickHeight);
        brick.setMinimumWidth(brickWidth);

        rl.addView(brick);

        brick.setX(x);
        brick.setY(y);

        obstacles.add(new Obstacle(x, y, brickWidth, brickHeight));
    }

    public void addPropeller(int x, int y) {

        ImageView propeller = new ImageView(this);

        propeller.setImageDrawable(propellerImage);

        int propellerLength = (int)(screenWidth * 0.12);

        propeller.setMinimumHeight(propellerLength);
        propeller.setMinimumWidth(propellerLength);

        propeller.setX(x);
        propeller.setY(y);

        rl.addView(propeller);


        propellers.add(new Propeller(x, y, propellerLength, propeller));
    }
}
