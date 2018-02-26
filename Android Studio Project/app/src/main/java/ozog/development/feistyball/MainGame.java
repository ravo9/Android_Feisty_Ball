package ozog.development.feistyball;

import android.content.Context;
import android.content.res.Resources;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame extends AppCompatActivity implements SensorEventListener {

    public RelativeLayout rl;
    public Context c;

    public static ImageView ball;
    public static ImageView destination;
    public static ImageView blackHoleA;
    public static ImageView blackHoleB;
    public static ImageView bonus01;

    public Button btnNewGame;
    public Button btnRestartLevel;
    public Button btnNextLevel;
    public Button btnScores;

    public TextView gameTimer;
    public TextView levelTimer;
    public TextView finalLevelTime;
    public TextView finalTotalTime;

    public FrameLayout windowLevelComplited;

    static Drawable brickImageHorizontal;
    static Drawable brickImageVertical;
    static Drawable propellerImage;
    static Drawable destinationImage;
    static Drawable destinationImageGrey;
    static Drawable blackHoleImage;
    static Drawable bonus6SecondImage;

    Timer timer;
    Handler handler;

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
    SensorEventListener sensorListener;

    float[] rotationMatrix;
    float[] orientationValues;

    // In centiseconds.
    static int gameTime;
    static int levelTime;

    static ArrayList<Obstacle> obstacles;
    static public ArrayList<Propeller> propellers;
    static ArrayList<BlackHole> blackHoles;

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
        blackHoleA = findViewById(R.id.blackHoleA);
        blackHoleB = findViewById(R.id.blackHoleB);
        bonus01 = findViewById(R.id.bonus01);

        btnNewGame = findViewById(R.id.btnNewGame);
        btnRestartLevel = findViewById(R.id.btnRestartLevel);
        btnNextLevel = findViewById(R.id.btnNextLevel);
        btnScores = findViewById(R.id.btnScores);

        gameTimer = findViewById(R.id.gameTimer);
        levelTimer = findViewById(R.id.levelTimer);
        finalLevelTime = findViewById(R.id.finalLevelTime);
        finalTotalTime = findViewById(R.id.finalTotalTime);

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

        // Images upload.
        try {
            InputStream stream = getAssets().open("brick_h.png");
            brickImageHorizontal = Drawable.createFromStream(stream, null);
            stream = getAssets().open("brick_v.png");
            brickImageVertical = Drawable.createFromStream(stream, null);
            stream = getAssets().open("propeller.png");
            propellerImage = Drawable.createFromStream(stream, null);
            stream = getAssets().open("destination02.png");
            destinationImage = Drawable.createFromStream(stream, null);
            stream = getAssets().open("destination02grey.png");
            destinationImageGrey = Drawable.createFromStream(stream, null);
            stream = getAssets().open("bonus_6_sec.png");
            bonus6SecondImage = Drawable.createFromStream(stream, null);
            stream = getAssets().open("black_hole.png");
            blackHoleImage = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        obstacles = new ArrayList<>();
        propellers = new ArrayList<>();
        blackHoles = new ArrayList<>();

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

    public void nextLevel(View v) {

        if (currentLevel == 0) {
            gameTime = 0;
            btnNewGame.setVisibility(View.INVISIBLE);
            btnScores.setVisibility(View.INVISIBLE);
            Level.setLevel4(this);
        }
        else if (currentLevel == 1) {
            Level.setLevel2(this);
        }

        levelTime = 0;
        currentLevel++;

        windowLevelComplited.animate().translationX(-screenWidth).setDuration(2000);
        windowLevelComplited.setVisibility(View.INVISIBLE);
        windowLevelComplited.animate().translationX(screenWidth);

        timer = new Timer();

        // Movement
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run () {
                        update();
                        updateTime();
                    }
                });
            }
        }, 0, 10);

    }

    public void restartLevel(View v) {
        currentLevel--;
        nextLevel(v);
    }

    public void updateTime() {

        levelTime++;
        gameTime++;

        levelTimer.setText("Level Time: " + displayTime(levelTime));
        gameTimer.setText("Game Time: " + displayTime(gameTime));
    }

    public String displayTime(int time) {
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




    public void levelComplited(View v) {

        timer.cancel();
        int animationTime = 2000;
        ball.animate().alpha(0.0f).setDuration(animationTime);
        destination.animate().alpha(0.0f).setDuration(animationTime);

        for (Propeller p: propellers) {
            p.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        for (Obstacle o: obstacles) {
            if (o.getImage() != null)
                o.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        windowLevelComplited.setVisibility(View.VISIBLE);
        windowLevelComplited.animate().translationX(0).setDuration(2000);

        finalLevelTime.setText("Level Time: " + displayTime(levelTime));
        finalTotalTime.setText("Total Time: " + displayTime(gameTime));

        // Clear level elements.
        propellers.clear();
        obstacles.clear();
        ball.animate().cancel();
        destination.animate().cancel();

    }

    public void update() {

        float currentX = ball.getX();
        float currentY = ball.getY();

        float newX = ball.getX() + orientationValues[2] * 10;
        float newY = ball.getY() - orientationValues[1] * 10;

        // Check if there is any obstacle (e.g. brick)
        if (isObstacleArea(newX, newY) == false) {
            ball.setX( newX );
            ball.setY( newY );
        } else if (isObstacleArea(newX, currentY) == false) {
            ball.setX( newX );
        } else if (isObstacleArea(currentX, newY) == false) {
            ball.setY(newY);
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
            if (ballCenterPointX > blackHoleA.getX() + 120 - 50 && ballCenterPointX < blackHoleA.getX() + 120 + 50) {
                if (ballCenterPointY > blackHoleA.getY() + 120 - 50 && ballCenterPointY < blackHoleA.getY() + 120 + 50) {
                    ball.setX(blackHoleB.getX() + 30);
                    ball.setY(blackHoleB.getY() + 30);
                    // Freeze black hole per 3 seconds
                    blackHoleFreezer = 300;
                }
            }

            if (ballCenterPointX > blackHoleB.getX() + 120 - 50 && ballCenterPointX < blackHoleB.getX() + 120 + 50) {
                if (ballCenterPointY > blackHoleB.getY() + 120 - 50 && ballCenterPointY < blackHoleB.getY() + 120 + 50) {
                    ball.setX(blackHoleA.getX() + 30);
                    ball.setY(blackHoleA.getY() + 30);
                    // Freeze black hole per 3 seconds
                    blackHoleFreezer = 300;
                    //ball.animate().translationX(ball.getX() + 100).setDuration(400);
                }
            }
        }
        else
            blackHoleFreezer--;


        // Check if there is a bonus
        if (ballCenterPointX > bonus01.getX() + 100 - 50 && ballCenterPointX < bonus01.getX() + 100 + 50) {
            if (ballCenterPointY > bonus01.getY() + 100 - 50 && ballCenterPointY < bonus01.getY() + 100 + 50) {
                gameTime -= 60;
                levelTime -= 60;
                Toast.makeText(getApplicationContext(), "-6 seconds!", Toast.LENGTH_SHORT).show();
                bonus01.setVisibility(View.INVISIBLE);
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
            destination.setImageDrawable(destinationImage);
            destination.setRotation(destination.getRotation() + (float)0.5);
            // Check if the destination has been achieved.
            if (ballCenterPointX > destination.getX() +  175 - 50 && ballCenterPointX < destination.getX() + 175 + 50) {
                if (ballCenterPointY > destination.getY() + 175 - 50 && ballCenterPointY < destination.getY() + 175 +50) {
                    levelComplited(rl);
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
            if (collides(x + 100, y + 100, o))
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

    public void addWalls() {
        obstacles.add(new Obstacle(0, -1, screenWidth, 1, null));
        obstacles.add(new Obstacle(screenWidth - 1, 0, 1, screenHeight, null));
        obstacles.add(new Obstacle(0, screenHeight, screenWidth, 1, null));
        obstacles.add(new Obstacle(-1, 0, 1, screenHeight, null));
    }

    public void addBrick(int x, int y, int orientation) {

        ImageView brick = new ImageView(this);

        int brickWidth = 0;
        int brickHeight = 0;

        if (orientation == 0) {
            brick.setImageDrawable(brickImageHorizontal);
            brickWidth = (int)(screenWidth * 0.2);
            brickHeight = (int)(brickWidth * 0.25);
        }
        else if (orientation == 1) {
            brick.setImageDrawable(brickImageVertical);
            brickHeight = (int)(screenWidth * 0.2);
            brickWidth = (int)(brickHeight * 0.25);
        }

        brick.setMinimumHeight(brickHeight);
        brick.setMinimumWidth(brickWidth);

        rl.addView(brick);

        brick.setX(x);
        brick.setY(y);

        obstacles.add(new Obstacle(x, y, brickWidth, brickHeight, brick));
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
