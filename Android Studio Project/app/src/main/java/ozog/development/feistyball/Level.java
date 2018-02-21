package ozog.development.feistyball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Level {

    private static float[] ballPosition;
    private static float[] destinationPosition;

    private static int screenWidth;
    private static int screenHeight;


    static {

        screenWidth = MainGame.screenWidth;
        screenHeight = MainGame.screenHeight;

        ballPosition = new float[2];
        destinationPosition = new float[2];

    }

    public static void setLevel1(MainGame game) {

        ballPosition[0] = (screenWidth / 2) - 100;
        ballPosition[1] = (float) (screenHeight * 0.7);
        setBallPosition(ballPosition[0], ballPosition[1]);

        destinationPosition[0] = (screenWidth / 2) - 110;
        destinationPosition[1] = (float) (screenHeight * 0.1);
        setDestinationPosition(destinationPosition[0], destinationPosition[1]);

        game.addBrick(10, (int) (screenHeight * 0.26));
        game.addBrick((int)(screenWidth * 0.2 + 20), (int) (screenHeight * 0.26));
        game.addBrick((int)(screenWidth * 0.4 + 40), (int) (screenHeight * 0.26));
        game.addBrick((int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.26));

        game.addBrick((int)(screenWidth * 0.2 - 70), (int) (screenHeight * 0.40));
        game.addBrick((int)(screenWidth * 0.4 - 50), (int) (screenHeight * 0.40));
        game.addBrick((int)(screenWidth * 0.6 - 30), (int) (screenHeight * 0.40));
        game.addBrick((int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.40));


        game.addPropeller((int)(screenWidth * 0.10), (int) (screenHeight * 0.1));
        game.addPropeller((int)(screenWidth * 0.70), (int) (screenHeight * 0.50));
        game.addPropeller((int)(screenWidth * 0.20), (int) (screenHeight * 0.90));

    }

    public static void setBallPosition(float x, float y) {
        MainGame.ball.setX(x);
        MainGame.ball.setY(y);
    }

    public static void setDestinationPosition(float x, float y) {
        MainGame.destination.setX(x);
        MainGame.destination.setY(y);
    }


}
