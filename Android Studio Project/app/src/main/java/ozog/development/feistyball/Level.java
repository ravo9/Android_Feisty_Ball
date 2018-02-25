package ozog.development.feistyball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
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

    private static int screenWidth;
    private static int screenHeight;


    static {

        screenWidth = MainGame.screenWidth;
        screenHeight = MainGame.screenHeight;
    }

    public static void setLevel1(MainGame game) {

        game.currentLevel = 1;

        float ballPositionX = (screenWidth / 2) - 100;
        float ballPositionY = (float) (screenHeight * 0.7);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (screenWidth / 2 + 50);
        float destinationPositionY = (float) (screenHeight * 0.055);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

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
        MainGame.destination.setImageDrawable(MainGame.destinationImageGrey);
        MainGame.destination.setX(x);
        MainGame.destination.setY(y);
    }
}
