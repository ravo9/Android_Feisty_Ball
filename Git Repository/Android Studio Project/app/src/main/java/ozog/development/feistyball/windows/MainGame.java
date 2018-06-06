package ozog.development.feistyball.windows;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.functionality.Level;
import ozog.development.feistyball.R;
import ozog.development.feistyball.functionality.Sensors;
import ozog.development.feistyball.functionality.Time;

public class MainGame extends AppCompatActivity implements SensorEventListener {

    public static RelativeLayout rl;
    public static MainGame game;
    public static FrameLayout windowLevelComplited;
    // Game mode may take values:'singleLevel', 'fullGame'.
    public static String gameMode;
    public static int singleLevelNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        rl = findViewById(R.id.RelativeLayout1);

        game = this;
        windowLevelComplited = findViewById(R.id.windowLevelComplited);

        // Upload images into drawables.
        Drawables.uploadImages(getApplicationContext());

        // Upload connections to the layout elements
        Layout.loadLayoutElementsConnections(this);

        // Keep the screen active (do not fade)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        windowLevelComplited.animate().translationX(Layout.screenWidth);

        if (gameMode == "fullGame"){

            Level.currentLevel = 0;
            Level.loadNextLevel();
        } else if (gameMode == "singleLevel") {

            Level.currentLevel = singleLevelNumber - 1;
            Level.loadNextLevel();
        }
    }

    // Sensors functionality that hasn't been moved to Sensors class
    @Override
    protected void onStart() {
        super.onStart();
        if (Sensors.mSensorAccelerometer != null) {
            Sensors.sensorManager.registerListener(this, Sensors.mSensorAccelerometer,
                    //SensorManager.SENSOR_DELAY_NORMAL);
                    SensorManager.SENSOR_DELAY_GAME);
        }
        if (Sensors.mSensorMagnetometer != null) {
            Sensors.sensorManager.registerListener(this, Sensors.mSensorMagnetometer,
                    //SensorManager.SENSOR_DELAY_NORMAL);
                    SensorManager.SENSOR_DELAY_GAME);
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
    public void onAccuracyChanged(Sensor sensor, int i) {}

    @Override
    public void onBackPressed() {
        Time.resetTimers();
        Level.resetGameElements();
        closeGame();
    }

    public void loadNextLevel(View v) { Level.loadNextLevel(); }

    public void restartLevel(View v) { Level.restartLevel(); }

    public void closeGame(View v) { closeGame(); }

    public void closeGame() {

        gameMode = "";
        singleLevelNumber = -1;

        if (isFeedbackDisplayAllowed()) {
            displayFeedbackScreen();
        }
        else {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        }
    }

    public void displayFeedbackScreen() {

        if (isFeedbackDisplayAllowed()) {

            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

            TextView myMsg = new TextView(this);
            myMsg.setText("Your opinion matters! \n\n\n" +
                    "Would you like to rate my game?\n\n\n" +
                    "Thank you for your time!");
            myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
            myMsg.setTextSize(16);
            myMsg.setPadding(35, 90, 35, 90);
            builder2.setView(myMsg);

            builder2.setNeutralButton("Rate now", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    String url = "market://details?id=ozog.development.feistyball";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                    blockFeedbackDisplay();

                    // Now close the game
                    finish();
                }
            });

            builder2.setPositiveButton("Maybe later", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    leaveGameActivity();
                }
            });

            builder2.setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    blockFeedbackDisplay();
                    leaveGameActivity();
                }
            });

            AlertDialog dialog2 = builder2.create();
            dialog2.show();
        }
    }

    public void leaveGameActivity() {
        Intent intent = new Intent(MainMenu.game, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public static boolean isFeedbackDisplayAllowed() {
        MainMenu.displayFeedbackStatus = MainMenu.game.getSharedPreferences("displayFeedbackStatus", Context.MODE_PRIVATE);
        return (!MainMenu.displayFeedbackStatus.contains("status_blocked"));
    }

    public static void blockFeedbackDisplay() {
        MainMenu.displayFeedbackStatus = MainMenu.game.getSharedPreferences("displayFeedbackStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MainMenu.displayFeedbackStatus.edit();
        editor.putString("status_blocked", "status_blocked");
        editor.apply();
    }
}

