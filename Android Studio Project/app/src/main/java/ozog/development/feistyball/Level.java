package ozog.development.feistyball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;


public class Level {

    private static float[] ballPosition;
    private static float[] destinationPosition;

    private static float screenWidth;
    private static float screenHeight;


    static {

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        ballPosition = new float[2];
        destinationPosition = new float[2];

    }

    public static void setLevel1() {

        ballPosition[0] = (screenWidth / 2) - 100;
        ballPosition[1] = (float) (screenHeight * 0.7);
        setBallPosition(ballPosition[0], ballPosition[1]);

        destinationPosition[0] = (screenWidth / 2) - 110;
        destinationPosition[1] = (float) (screenHeight * 0.1);
        setDestinationPosition(destinationPosition[0], destinationPosition[1]);

        addBrick(300, 300);
        // Doesn't work with the second brick.
        //addBrick(300, 500);

    }

    public static void setBallPosition(float x, float y) {
        MainGame.ball.setX(x);
        MainGame.ball.setY(y);
    }

    public static void setDestinationPosition(float x, float y) {
        MainGame.destination.setX(x);
        MainGame.destination.setY(y);
    }

    public static void addBrick(float x, float y) {
        ImageView brick1 = MainGame.brick;
        MainGame.rl.addView(brick1);

        brick1.setX(x);
        brick1.setY(y);
    }
}
