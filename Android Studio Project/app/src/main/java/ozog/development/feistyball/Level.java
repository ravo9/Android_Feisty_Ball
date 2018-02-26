package ozog.development.feistyball;


import android.view.View;
import android.view.animation.Animation;

public class Level {

    private static int screenWidth;
    private static int screenHeight;
    private static int horizontalBrickWidth;
    private static int horizontalBrickHeight;
    private static int interBrickSpace;


    static {

        screenWidth = MainGame.screenWidth;
        screenHeight = MainGame.screenHeight;
        horizontalBrickWidth = (int)(screenWidth * 0.2);
        horizontalBrickHeight = (int)(horizontalBrickWidth * 0.25);
        interBrickSpace = (int)(horizontalBrickWidth * 0.1);
    }

    public static void setLevel1(MainGame game) {

        float ballPositionX = (screenWidth / 2) - 100;
        float ballPositionY = (float) (screenHeight * 0.7);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (screenWidth / 2 + 50);
        float destinationPositionY = (float) (screenHeight * 0.055);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

        game.addBrick(10, (int) (screenHeight * 0.26), 0);
        game.addBrick((int)(screenWidth * 0.2 + 20), (int) (screenHeight * 0.26), 0);
        game.addBrick((int)(screenWidth * 0.4 + 40), (int) (screenHeight * 0.26), 0);
        game.addBrick((int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.26), 0);

        game.addBrick((int)(screenWidth * 0.2 - 70), (int) (screenHeight * 0.40), 0);
        game.addBrick((int)(screenWidth * 0.4 - 50), (int) (screenHeight * 0.40), 0);
        game.addBrick((int)(screenWidth * 0.6 - 30), (int) (screenHeight * 0.40), 0);
        game.addBrick((int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.40), 0);

        game.addPropeller((int)(screenWidth * 0.10), (int) (screenHeight * 0.1));
        game.addPropeller((int)(screenWidth * 0.70), (int) (screenHeight * 0.50));
        game.addPropeller((int)(screenWidth * 0.20), (int) (screenHeight * 0.90));

    }

    public static void setLevel2(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.75);
        float ballPositionY = (float) (screenHeight * 0.15);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.1);
        float destinationPositionY = (float) (screenHeight * 0.82);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

        game.addBrick((int)(screenWidth * 0.5), (int) (screenHeight * 0.3), 0);
        game.addBrick((int)(screenWidth * 0.7 + 20), (int) (screenHeight * 0.3), 0);

        game.addBrick((int)(horizontalBrickWidth), (int) (screenHeight * 0.48), 0);
        game.addBrick((int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48), 1);
        game.addBrick((int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 20 + horizontalBrickWidth), 1);
        game.addBrick((int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 40 + horizontalBrickWidth * 2), 1);
        game.addBrick((int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 60 + horizontalBrickWidth * 3), 1);

        game.addBrick((int)(screenWidth * 0.8), (int) (screenHeight * 0.77), 0);
        game.addBrick((int)(screenWidth * 0.74), (int) (screenHeight * 0.77), 1);

        game.addPropeller((int)(screenWidth * 0.60), (int) (screenHeight * 0.02));
        game.addPropeller((int)(screenWidth * 0.08), (int) (screenHeight * 0.25));
        game.addPropeller((int)(screenWidth * 0.85), (int) (screenHeight * 0.85));

    }

    public static void setLevel3(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.8);
        float ballPositionY = (float) (screenHeight * 0.8);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.75);
        float destinationPositionY = (float) (screenHeight * 0.5);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

        game.addBrick((int)(-interBrickSpace * 2), (int) (screenHeight * 0.25), 0);
        game.addBrick((int)(-interBrickSpace + horizontalBrickWidth), (int) (screenHeight * 0.25), 0);
        game.addBrick((int)(horizontalBrickWidth * 2), (int) (screenHeight * 0.25), 0);
        game.addBrick((int)(interBrickSpace + horizontalBrickWidth * 3), (int) (screenHeight * 0.25), 0);
        game.addBrick((int)(interBrickSpace * 2 + horizontalBrickWidth * 4), (int) (screenHeight * 0.25), 0);

        game.addBrick((int)(horizontalBrickWidth * 2 + 75), (int) (screenHeight * 0.45), 0);
        game.addBrick((int)(interBrickSpace + horizontalBrickWidth * 3 + 75), (int) (screenHeight * 0.45), 0);
        game.addBrick((int)(interBrickSpace * 2 + horizontalBrickWidth * 4 + 75), (int) (screenHeight * 0.45), 0);

        game.addBrick((int)(horizontalBrickWidth + 35), (int) (screenHeight * 0.7), 1);
        game.addBrick((int)(horizontalBrickWidth + 35), (int) (screenHeight * 0.7 + horizontalBrickWidth + interBrickSpace), 0);
        game.addBrick((int)(horizontalBrickWidth * 2 - horizontalBrickHeight + 34), (int) (screenHeight * 0.7 + horizontalBrickWidth + horizontalBrickHeight + interBrickSpace * 2), 1);

        game.addPropeller((int)(screenWidth * 0.75), (int) (screenHeight * 0.1));
        game.addPropeller((int)(screenWidth * 0.75), (int) (screenHeight * 0.34));

        game.blackHoleA.setVisibility(View.VISIBLE);
        game.blackHoleA.setX((float)(screenWidth * 0.1));
        game.blackHoleA.setY((float)(screenHeight * 0.1));

        game.blackHoleB.setVisibility(View.VISIBLE);
        game.blackHoleB.setX((float)(screenWidth * 0.19));
        game.blackHoleB.setY((float)(screenHeight * 0.88));
    }

    public static void setLevel4(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.45);
        float ballPositionY = (float) (screenHeight * 0.35);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.05);
        float destinationPositionY = (float) (screenHeight * 0.04);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

        game.addBrick((int)(-interBrickSpace * 2.5), (int) (screenHeight * 0.48), 0);
        game.addBrick((int)(-interBrickSpace * 1.5 + horizontalBrickWidth), (int) (screenHeight * 0.48), 0);
        game.addBrick((int)( -interBrickSpace * 0.5 + horizontalBrickWidth * 2), (int) (screenHeight * 0.48), 0);
        game.addBrick((int)(interBrickSpace * 0.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.48), 0);
        game.addBrick((int)(interBrickSpace * 1.5 + horizontalBrickWidth * 4), (int) (screenHeight * 0.48), 0);

        game.addBrick((int)(screenWidth * 0.25), (int) (screenHeight * 0.48 + horizontalBrickHeight + interBrickSpace), 1);
        game.addBrick((int)(screenWidth * 0.7), (int) (screenHeight * 0.48 - horizontalBrickWidth - interBrickSpace), 1);

        game.addBrick((int)(screenWidth * 0.67), (int) (screenHeight * 0.88), 1);
        game.addBrick((int)(screenWidth * 0.67), (int) (screenHeight * 0.88 - interBrickSpace - horizontalBrickWidth), 1);

        game.addPropeller((int)(screenWidth * 0.8), (int) (screenHeight * 0.36));
        game.addPropeller((int)(screenWidth * 0.12), (int) (screenHeight * 0.36));
        game.addPropeller((int)(screenWidth * 0.8), (int) (screenHeight * 0.9));

        game.blackHoleA.setVisibility(View.VISIBLE);
        game.blackHoleA.setX((float)(screenWidth * 0.78));
        game.blackHoleA.setY((float)(screenHeight * 0.04));

        game.blackHoleB.setVisibility(View.VISIBLE);
        game.blackHoleB.setX((float)(screenWidth * 0.43));
        game.blackHoleB.setY((float)(screenHeight * 0.88));

        game.bonus01.setVisibility(View.VISIBLE);
        game.bonus01.setX((float)(screenWidth * 0.05));
        game.bonus01.setY((float)(screenHeight * 0.54));
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
