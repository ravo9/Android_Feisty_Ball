package ozog.development.feistyball.functionality;

import java.util.Timer;
import android.os.Handler;

import ozog.development.feistyball.windows.MainGame;

public class Time {

    static Timer timer;
    static Handler handler;
    static Handler movementHandler;

    // In centiseconds.
    static public int gameTime;
    static public int levelTime;

    static {
        handler = new Handler();
        movementHandler = new Handler();
    }

    public static void updateTime() {

        levelTime++;
        Layout.levelTimer.setText("Level: " + displayTime(levelTime));

        if (MainGame.gameMode == "fullGame"){

            gameTime++;
            Layout.gameTimer.setText("Game: " + displayTime(gameTime));
        } else if (MainGame.gameMode == "singleLevel") {}
    }

    public static void resetTimers() {

        ///timer.cancel();
        handler.removeCallbacksAndMessages(null);
        movementHandler.removeCallbacksAndMessages(null);

        // Are the following 4 lines necessary?
        handler = null;
        handler = new Handler();

        movementHandler = null;
        movementHandler = new Handler();

        timer.cancel();
        timer = null;
    }

    public static String displayTime(int time) {

        int minutes = time/6000;
        int seconds = (time - 6000 * minutes)/100;
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
}
