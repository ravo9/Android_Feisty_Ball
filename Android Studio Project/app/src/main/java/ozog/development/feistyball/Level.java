package ozog.development.feistyball;


import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import java.util.Timer;
import java.util.TimerTask;

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

    public static void loadNextLevel(View v) {

        if (MainGame.currentLevel == 0) {
            MainGame.gameTime = 0;
            Layout.btnNewGame.setVisibility(View.INVISIBLE);
            Layout.btnScores.setVisibility(View.INVISIBLE);
            Level.setLevel4(MainGame.game);
        }
        else if (MainGame.currentLevel == 1) {
            Level.setLevel2(MainGame.game);
        }

        MainGame.levelTime = 0;
        MainGame.currentLevel++;

        MainGame.windowLevelComplited.animate().translationX(-screenWidth).setDuration(2000);
        MainGame.windowLevelComplited.setVisibility(View.INVISIBLE);
        MainGame.windowLevelComplited.animate().translationX(screenWidth);

        MainGame.timer = new Timer();

        // Movement
        MainGame.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainGame.handler.post(new Runnable() {
                    @Override
                    public void run () {
                        MainGame.update();
                        MainGame.updateTime();
                    }
                });
            }
        }, 0, 10);

    }

    public static void levelComplited(View v)
    {

        MainGame.timer.cancel();
        int animationTime = 2000;
        Layout.ball.animate().alpha(0.0f).setDuration(animationTime);
        Layout.destination.animate().alpha(0.0f).setDuration(animationTime);

        for (Propeller p: MainGame.propellers) {
            p.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        for (Obstacle o: MainGame.obstacles) {
            if (o.getImage() != null)
                o.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        MainGame.windowLevelComplited.setVisibility(View.VISIBLE);
        MainGame.windowLevelComplited.animate().translationX(0).setDuration(2000);

        Layout.finalLevelTime.setText("Level Time: " + MainGame.displayTime(MainGame.levelTime));
        Layout.finalTotalTime.setText("Total Time: " + MainGame.displayTime(MainGame.gameTime));

        // Clear level elements.
        MainGame.propellers.clear();
        MainGame.obstacles.clear();
        Layout.ball.animate().cancel();
        Layout.destination.animate().cancel();

    }

    public static void setLevel1(MainGame game) {

        float ballPositionX = (screenWidth / 2) - 100;
        float ballPositionY = (float) (screenHeight * 0.7);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (screenWidth / 2 + 50);
        float destinationPositionY = (float) (screenHeight * 0.055);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, 10, (int) (screenHeight * 0.26), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.2 + 20), (int) (screenHeight * 0.26), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.4 + 40), (int) (screenHeight * 0.26), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.26), 0);

        Obstacle.addBrick(game, (int)(screenWidth * 0.2 - 70), (int) (screenHeight * 0.40), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.4 - 50), (int) (screenHeight * 0.40), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.6 - 30), (int) (screenHeight * 0.40), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.40), 0);

        Propeller.addPropeller(game, (int)(screenWidth * 0.10), (int) (screenHeight * 0.1));
        Propeller.addPropeller(game, (int)(screenWidth * 0.70), (int) (screenHeight * 0.50));
        Propeller.addPropeller(game, (int)(screenWidth * 0.20), (int) (screenHeight * 0.90));

    }

    public static void setLevel2(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.75);
        float ballPositionY = (float) (screenHeight * 0.15);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.1);
        float destinationPositionY = (float) (screenHeight * 0.82);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, (int)(screenWidth * 0.5), (int) (screenHeight * 0.3), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.7 + 20), (int) (screenHeight * 0.3), 0);

        Obstacle.addBrick(game, (int)(horizontalBrickWidth), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48), 1);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 20 + horizontalBrickWidth), 1);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 40 + horizontalBrickWidth * 2), 1);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 60 + horizontalBrickWidth * 3), 1);

        Obstacle.addBrick(game, (int)(screenWidth * 0.8), (int) (screenHeight * 0.77), 0);
        Obstacle.addBrick(game, (int)(screenWidth * 0.74), (int) (screenHeight * 0.77), 1);

        Propeller.addPropeller(game, (int)(screenWidth * 0.60), (int) (screenHeight * 0.02));
        Propeller.addPropeller(game, (int)(screenWidth * 0.08), (int) (screenHeight * 0.25));
        Propeller.addPropeller(game, (int)(screenWidth * 0.85), (int) (screenHeight * 0.85));

    }

    public static void setLevel3(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.8);
        float ballPositionY = (float) (screenHeight * 0.8);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.75);
        float destinationPositionY = (float) (screenHeight * 0.5);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, (int)(-interBrickSpace * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int)(-interBrickSpace + horizontalBrickWidth), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int)(interBrickSpace + horizontalBrickWidth * 3), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int)(interBrickSpace * 2 + horizontalBrickWidth * 4), (int) (screenHeight * 0.25), 0);

        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addBrick(game, (int)(interBrickSpace + horizontalBrickWidth * 3 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addBrick(game, (int)(interBrickSpace * 2 + horizontalBrickWidth * 4 + 75), (int) (screenHeight * 0.45), 0);

        Obstacle.addBrick(game, (int)(horizontalBrickWidth + 35), (int) (screenHeight * 0.7), 1);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth + 35), (int) (screenHeight * 0.7 + horizontalBrickWidth + interBrickSpace), 0);
        Obstacle.addBrick(game, (int)(horizontalBrickWidth * 2 - horizontalBrickHeight + 34), (int) (screenHeight * 0.7 + horizontalBrickWidth + horizontalBrickHeight + interBrickSpace * 2), 1);

        Propeller.addPropeller(game, (int)(screenWidth * 0.75), (int) (screenHeight * 0.1));
        Propeller.addPropeller(game, (int)(screenWidth * 0.75), (int) (screenHeight * 0.34));

        Layout.blackHoleA.setVisibility(View.VISIBLE);
        Layout.blackHoleA.setX((float)(screenWidth * 0.1));
        Layout.blackHoleA.setY((float)(screenHeight * 0.1));

        Layout.blackHoleB.setVisibility(View.VISIBLE);
        Layout.blackHoleB.setX((float)(screenWidth * 0.19));
        Layout.blackHoleB.setY((float)(screenHeight * 0.88));
    }

    public static void setLevel4(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.45);
        float ballPositionY = (float) (screenHeight * 0.35);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.05);
        float destinationPositionY = (float) (screenHeight * 0.04);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, (int)(-interBrickSpace * 2.5), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int)(-interBrickSpace * 1.5 + horizontalBrickWidth), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int)( -interBrickSpace * 0.5 + horizontalBrickWidth * 2), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int)(interBrickSpace * 0.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int)(interBrickSpace * 1.5 + horizontalBrickWidth * 4), (int) (screenHeight * 0.48), 0);

        Obstacle.addBrick(game, (int)(screenWidth * 0.25), (int) (screenHeight * 0.48 + horizontalBrickHeight + interBrickSpace), 1);
        Obstacle.addBrick(game, (int)(screenWidth * 0.7), (int) (screenHeight * 0.48 - horizontalBrickWidth - interBrickSpace), 1);

        Obstacle.addBrick(game, (int)(screenWidth * 0.67), (int) (screenHeight * 0.88), 1);
        Obstacle.addBrick(game, (int)(screenWidth * 0.67), (int) (screenHeight * 0.88 - interBrickSpace - horizontalBrickWidth), 1);

        Propeller.addPropeller(game, (int)(screenWidth * 0.8), (int) (screenHeight * 0.36));
        Propeller.addPropeller(game, (int)(screenWidth * 0.12), (int) (screenHeight * 0.36));
        Propeller.addPropeller(game, (int)(screenWidth * 0.8), (int) (screenHeight * 0.9));

        Layout.blackHoleA.setVisibility(View.VISIBLE);
        Layout.blackHoleA.setX((float)(screenWidth * 0.78));
        Layout.blackHoleA.setY((float)(screenHeight * 0.04));

        Layout.blackHoleB.setVisibility(View.VISIBLE);
        Layout.blackHoleB.setX((float)(screenWidth * 0.43));
        Layout.blackHoleB.setY((float)(screenHeight * 0.88));

        Layout.bonus01.setVisibility(View.VISIBLE);
        Layout.bonus01.setX((float)(screenWidth * 0.05));
        Layout.bonus01.setY((float)(screenHeight * 0.54));
    }



    public static void setBallPosition(float x, float y) {
        Layout.ball.setX(x);
        Layout.ball.setY(y);
    }

    public static void setDestinationPosition(float x, float y) {
        Layout.destination.setImageDrawable(Drawables.destinationImageGrey);
        Layout.destination.setX(x);
        Layout.destination.setY(y);
    }
}
