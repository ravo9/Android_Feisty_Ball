package ozog.development.feistyball;

import java.util.Timer;
import android.os.Handler;

public class Time {

    static Timer timer;
    static Handler handler;

    // In centiseconds.
    static int gameTime;
    static int levelTime;

    static {
        handler = new Handler();
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
}
