package ozog.development.feistyball;

import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Level{

    private static int screenWidth;
    private static int screenHeight;
    private static int horizontalBrickWidth;
    private static int horizontalBrickHeight;
    private static int interBrickSpace;
    public static int currentLevel;
    public static int lastLevelNumber;
    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Propeller> propellers;
    public static ArrayList<DestructionBall> destructionBalls;
    public static ArrayList<GameButton> gameButtons;

    static {
        screenWidth = Layout.screenWidth;
        screenHeight = Layout.screenHeight;
        horizontalBrickWidth = (int) (screenWidth * 0.2);
        horizontalBrickHeight = (int) (horizontalBrickWidth * 0.25);
        interBrickSpace = (int) (horizontalBrickWidth * 0.05);
        currentLevel = 0;

        lastLevelNumber = 7;

        obstacles = new ArrayList<>();
        propellers = new ArrayList<>();
        destructionBalls = new ArrayList<>();
        gameButtons = new ArrayList<>();
    }

    public static void loadMainMenu() {
        MainGame.game.closeGame();
    }

    public static void closeMainMenu() {


    }

    public static void loadInterLevelMenu() {

        MainGame.windowLevelComplited.setVisibility(View.VISIBLE);
        MainGame.windowLevelComplited.animate().translationX(0).setDuration(2000);
    }

    public static void hideInterLevelMenu() {

        MainGame.windowLevelComplited.animate().translationX(-screenWidth).setDuration(2000);
        MainGame.windowLevelComplited.setVisibility(View.INVISIBLE);
        MainGame.windowLevelComplited.animate().translationX(screenWidth);
    }

    public static void loadNextLevel() {

        // Hide the black holes and bonuses by default
        Layout.blackHoleA.setX(-500);
        Layout.blackHoleB.setX(-500);
        Layout.bonus01.setX(-550);

        // Choose proper level to load
        switch (currentLevel) {
            case 0:
                Time.gameTime = 0;
                setLevel7(MainGame.game);
                //setLevel1(MainGame.game);
                break;
            case 1:
                setLevel2(MainGame.game);
                break;
            case 2:
                setLevel3(MainGame.game);
                break;
            case 3:
                setLevel4(MainGame.game);
                break;
            case 4:
                setLevel5(MainGame.game);
                break;
            case 5:
                setLevel6(MainGame.game);
                break;
            case 6:
                setLevel7(MainGame.game);
                break;
            case 7:
                loadMainMenu();
                break;
        }

        Time.levelTime = 0;
        currentLevel++;

        hideInterLevelMenu();

        Time.timer = new Timer();

        // Movement
        Time.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Time.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Time.updateTime();
                        Mechanics.update();
                        Messages.update();
                    }
                });
            }
        }, 0, 10);
    }

    public static void restartLevel() {
        currentLevel--;
        loadNextLevel();
    }

    public static void levelComplited(View v) {
        levelComplited();
    }

    public static void levelComplited() {

        // Visible elements fades out
        int animationTime = 2000;
        Layout.ball.animate().alpha(0.0f).setDuration(animationTime);
        Layout.destination.animate().alpha(0.0f).setDuration(animationTime);
        Layout.blackHoleA.animate().alpha(0.0f).setDuration(animationTime);
        Layout.blackHoleB.animate().alpha(0.0f).setDuration(animationTime);
        Layout.bonus01.animate().alpha(0.0f).setDuration(animationTime);

        for (Propeller p : propellers) {
            p.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        for (Obstacle o : obstacles) {
            if (o.getImage() != null)
                o.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        for (GameButton g : gameButtons) {
            g.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        for (DestructionBall d : destructionBalls) {
            d.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        loadInterLevelMenu();

        resetGameElements();

        Layout.finalLevelTime.setText("Level Time: " + Time.displayTime(Time.levelTime));
        Layout.finalTotalTime.setText("Total Time: " + Time.displayTime(Time.gameTime));

        // Update best score if it has been broken
        if (BestScores.isRecordBroken())
            BestScores.updateRecord();
    }

    public static void resetGameElements() {

        Time.timer.cancel();

        // Clear level elements.
        propellers.clear();
        obstacles.clear();
        destructionBalls.clear();
        gameButtons.clear();
        Layout.ball.animate().cancel();
        Layout.destination.animate().cancel();
        Layout.blackHoleA.setX(-500);
        Layout.blackHoleB.setX(-500);
        Layout.bonus01.setX(-550);
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
        Obstacle.addBrick(game, (int) (screenWidth * 0.2 + 20), (int) (screenHeight * 0.26), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.4 + 40), (int) (screenHeight * 0.26), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.8 - 10), (int) (screenHeight * 0.26), 0);

        Obstacle.addBrick(game, (int) (screenWidth * 0.2 - 70), (int) (screenHeight * 0.40), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.4 - 50), (int) (screenHeight * 0.40), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.6 - 30), (int) (screenHeight * 0.40), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.8 - 10), (int) (screenHeight * 0.40), 0);

        Propeller.addPropeller(game, (int) (screenWidth * 0.10), (int) (screenHeight * 0.1));
        Propeller.addPropeller(game, (int) (screenWidth * 0.70), (int) (screenHeight * 0.50));
        Propeller.addPropeller(game, (int) (screenWidth * 0.20), (int) (screenHeight * 0.90));
    }

    public static void setLevel2(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.14);
        float ballPositionY = (float) (screenHeight * 0.56);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.1);
        float destinationPositionY = (float) (screenHeight * 0.52);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, (int) (screenWidth * 0.5), (int) (screenHeight * 0.3), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.7 + 20), (int) (screenHeight * 0.3), 0);

        Obstacle.addBrick(game, (int) (-interBrickSpace), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48), 1);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 20 + horizontalBrickWidth), 1);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2 + 20), (int) (screenHeight * 0.48 + 40 + horizontalBrickWidth * 2), 1);

        Obstacle.addBrick(game, (int) (screenWidth * 0.8), (int) (screenHeight * 0.77), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.74), (int) (screenHeight * 0.77), 1);

        Propeller.addPropeller(game, (int) (screenWidth * 0.60), (int) (screenHeight * 0.02));
        Propeller.addPropeller(game, (int) (screenWidth * 0.08), (int) (screenHeight * 0.25));
        Propeller.addPropeller(game, (int) (screenWidth * 0.85), (int) (screenHeight * 0.85));
    }

    public static void setLevel3(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.65);
        // 200 is the ball width/ height
        float ballPositionY = (float) (screenHeight - 200);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.75);
        float destinationPositionY = (float) (screenHeight * 0.5);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, (int) (-interBrickSpace * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace + horizontalBrickWidth), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace + horizontalBrickWidth * 3), (int) (screenHeight * 0.25), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2 + horizontalBrickWidth * 4), (int) (screenHeight * 0.25), 0);

        Obstacle.addBrick(game, (int) (-interBrickSpace + horizontalBrickWidth * 1 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace + horizontalBrickWidth * 3 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2 + horizontalBrickWidth * 4 + 75), (int) (screenHeight * 0.45), 0);

        Obstacle.addBrick(game, (int) (screenWidth * 0.70), (int) (screenHeight * 0.45 + horizontalBrickHeight + interBrickSpace), 1);
        Obstacle.addBrick(game, (int) (screenWidth * 0.70), (int) (screenHeight * 0.45 + horizontalBrickHeight + horizontalBrickWidth + interBrickSpace * 2), 1);
        Obstacle.addBrick(game, (int) (screenWidth * 0.70), (int) (screenHeight * 0.45 + horizontalBrickHeight + + horizontalBrickWidth * 2 + interBrickSpace * 3), 1);

        Obstacle.addBrick(game, (int) (0.86* horizontalBrickWidth ), (int) (screenHeight * 0.7), 1);
        Obstacle.addBrick(game, (int) (0.86 * horizontalBrickWidth ), (int) (screenHeight * 0.7 + horizontalBrickWidth + interBrickSpace), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 1.86 - horizontalBrickHeight - 1), (int) (screenHeight * 0.7 + horizontalBrickWidth + horizontalBrickHeight + interBrickSpace * 2), 1);

        Propeller.addPropeller(game, (int) (screenWidth * 0.75), (int) (screenHeight * 0.1));
        Propeller.addPropeller(game, (int) (screenWidth * 0.75), (int) (screenHeight * 0.34));

        Layout.blackHoleA.setX((float) (screenWidth * 0.1));
        Layout.blackHoleA.setY((float) (screenHeight * 0.1));

        Layout.blackHoleB.setX((float) (screenWidth * 0.13));
        Layout.blackHoleB.setY((float) (screenHeight * 0.87));
    }

    public static void setLevel4(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.45);
        float ballPositionY = (float) (screenHeight * 0.15);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.05);
        float destinationPositionY = (float) (screenHeight * 0.04);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addBrick(game, (int) ( -interBrickSpace * 1.5 +horizontalBrickWidth * 1), (int) (screenHeight * 0.315), 0);
        Obstacle.addBrick(game, (int) ( -interBrickSpace * 0.5 + horizontalBrickWidth * 2 + interBrickSpace), (int) (screenHeight * 0.315), 0);
        Obstacle.addBrick(game, (int) ( interBrickSpace * 0.5 + horizontalBrickWidth * 3 + interBrickSpace * 2), (int) (screenHeight * 0.315), 0);

        Obstacle.addBrick(game, (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 1.5 + horizontalBrickWidth), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 0.5 + horizontalBrickWidth * 2), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 0.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.48), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 1.5 + horizontalBrickWidth * 4), (int) (screenHeight * 0.48), 0);

        Obstacle.addBrick(game, (int) (screenWidth * 0.25), (int) (screenHeight * 0.48 + horizontalBrickHeight + interBrickSpace), 1);
        Obstacle.addBrick(game, (int) (screenWidth * 0.7), (int) (screenHeight * 0.48 - horizontalBrickWidth - interBrickSpace), 1);


        Obstacle.addBrick(game, (int) (screenWidth * 0.67 - horizontalBrickWidth - 2 * interBrickSpace - horizontalBrickHeight), (int) (screenHeight * 0.88 - interBrickSpace - horizontalBrickWidth), 1);
        Obstacle.addBrick(game, (int) (screenWidth * 0.67 - horizontalBrickWidth - interBrickSpace), (int) (screenHeight * 0.86 - horizontalBrickWidth), 0);
        Obstacle.addBrick(game, (int) (screenWidth * 0.67), (int) (screenHeight * 0.88), 1);
        Obstacle.addBrick(game, (int) (screenWidth * 0.67), (int) (screenHeight * 0.88 - interBrickSpace - horizontalBrickWidth), 1);

        Propeller.addPropeller(game, (int) (screenWidth * 0.81), (int) (screenHeight * 0.37));
        Propeller.addPropeller(game, (int) (screenWidth * 0.55), (int) (screenHeight * 0.37));
        Propeller.addPropeller(game, (int) (screenWidth * 0.8), (int) (screenHeight * 0.9));

        Layout.blackHoleA.setX((float) (screenWidth * 0.78));
        Layout.blackHoleA.setY((float) (screenHeight * 0.04));

        Layout.blackHoleB.setX((float) (screenWidth * 0.48));
        Layout.blackHoleB.setY((float) (screenHeight * 0.78));

        Layout.bonus01.setX((float) (screenWidth * 0.05));
        Layout.bonus01.setY((float) (screenHeight * 0.54));
    }

    public static void setLevel5(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.15);
        float ballPositionY = (float) (screenHeight - 200);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.035);
        float destinationPositionY = (float) (screenHeight * 0.83);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller(game, (int) (screenWidth * 0.1), (int) (screenHeight * 0.06));
        Propeller.addPropeller(game, (int) (screenWidth * 0.78), (int) (screenHeight * 0.06));

        Obstacle.addBrick(game, (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 1.5 + horizontalBrickWidth), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 0.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 1.5 + horizontalBrickWidth * 4), (int) (screenHeight * 0.2), 0);

        Layout.blackHoleA.setX((float) (screenWidth * 0.07));
        Layout.blackHoleA.setY((float) (screenHeight * 0.26));

        Obstacle.addBrick(game, (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 1.5 + horizontalBrickWidth), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 1.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2.5 + horizontalBrickWidth * 4), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 3.5 + horizontalBrickWidth * 5), (int) (screenHeight * 0.4), 0);

        Layout.blackHoleB.setX((float) (screenWidth * 0.93 - 240));
        Layout.blackHoleB.setY((float) (screenHeight * 0.46));

        Obstacle.addBrick(game, (int) (-interBrickSpace * 0.3 + horizontalBrickWidth), (int) (screenHeight * 0.64), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2), (int) (screenHeight * 0.64), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 0.6 + horizontalBrickWidth * 3), (int) (screenHeight * 0.64), 0);

        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 3 - screenWidth * 0.11), (int) (screenHeight * 0.81), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace + horizontalBrickWidth * 4 - screenWidth * 0.11), (int) (screenHeight * 0.81), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2 + horizontalBrickWidth * 5 - screenWidth * 0.11), (int) (screenHeight * 0.81), 0);

        Layout.bonus01.setX((float) (screenWidth * 0.82));
        Layout.bonus01.setY((float) (screenHeight * 0.87));
    }

    public static void setLevel6(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.15);
        float ballPositionY = (float) (screenHeight * 0.17);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.035);
        float destinationPositionY = (float) (screenHeight * 0.09);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        DestructionBall.addDestructionBall(game, (int) (screenWidth * 0.4), 0, (float)0.5);
        DestructionBall.addDestructionBall(game, (int) (screenWidth * 0.6), 0, (float)(1));

        GameButton.addGameButton(game, (int) (screenWidth * 0.16), 0);
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(1));

        Obstacle.addBrick(game, (int) (-interBrickSpace * 4), (int) (screenHeight * 0.3), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 3 + horizontalBrickWidth), (int) (screenHeight * 0.3), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 2 + horizontalBrickWidth * 2), (int) (screenHeight * 0.3), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace + horizontalBrickWidth * 3), (int) (screenHeight * 0.3), 0);

        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth * 4 - interBrickSpace * 4), (int) (screenHeight * 0.5), 0);
        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth * 3 - interBrickSpace * 3), (int) (screenHeight * 0.5), 0);
        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth * 2 - interBrickSpace * 2), (int) (screenHeight * 0.5), 0);
        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth - interBrickSpace), (int) (screenHeight * 0.5), 0);

        Layout.bonus01.setVisibility(View.VISIBLE);
        Layout.bonus01.setX((float) (screenWidth * 0.83));
        Layout.bonus01.setY((float) (screenHeight * 0.55));

        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth * 4 - interBrickSpace * 4), (int) (screenHeight * 0.65), 0);
        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth * 3 - interBrickSpace * 3), (int) (screenHeight * 0.65), 0);
        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth * 2 - interBrickSpace * 2), (int) (screenHeight * 0.65), 0);
        Obstacle.addBrick(game, (int) (screenWidth - horizontalBrickWidth - interBrickSpace), (int) (screenHeight * 0.65), 0);

        Obstacle.addBrick(game, (int) (-interBrickSpace * 4), (int) (screenHeight * 0.8), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 3 + horizontalBrickWidth), (int) (screenHeight * 0.8), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 2 + horizontalBrickWidth * 2), (int) (screenHeight * 0.8), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace + horizontalBrickWidth * 3), (int) (screenHeight * 0.8), 0);

        Propeller.addPropeller(game, (int) (screenWidth * 0.07), (int) (screenHeight * 0.88));
    }

    public static void setLevel7(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.8);
        float ballPositionY = (float) (screenHeight * 0.07);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.7);
        float destinationPositionY = (float) (screenHeight * 0.04);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Layout.blackHoleA.setX((float) (screenWidth * 0.35));
        Layout.blackHoleA.setY((float) (screenHeight * 0.06));

        Obstacle.addBrick(game, (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (-interBrickSpace * 1.5 + horizontalBrickWidth), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (horizontalBrickWidth * 2), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 1.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2.5 + horizontalBrickWidth * 4), (int) (screenHeight * 0.2), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 3.5 + horizontalBrickWidth * 5), (int) (screenHeight * 0.2), 0);

        Layout.blackHoleB.setX((float) (screenWidth * 0.07));
        Layout.blackHoleB.setY((float) (screenHeight * 0.26));

        Obstacle.addBrick(game, (int) (interBrickSpace * 1), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2 + horizontalBrickWidth), (int) (screenHeight * 0.4), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 3 + horizontalBrickWidth * 2), (int) (screenHeight * 0.4), 0);

        Propeller.addPropeller(game, (int) (screenWidth * 0.1), (int) (screenHeight * 0.5));
        Propeller.addPropeller(game, (int) (screenWidth * 0.1), (int) (screenHeight * 0.8));

        DestructionBall.addDestructionBall(game, (int) (screenWidth * 0.2), (int) (screenHeight * 0.61), (float)(1));
        Level.destructionBalls.get(0).rotate180();
        DestructionBall.addDestructionBall(game, (int) (screenWidth * 0.2), (int) (screenHeight * 0.81), (float)(1));
        Level.destructionBalls.get(1).rotate180();
        DestructionBall.addDestructionBall(game, (int) (screenWidth * 0.2), (int) (screenHeight * 1.01), (float)(1));
        Level.destructionBalls.get(2).rotate180();

        DestructionBall.setSpeed(8);

        Obstacle.addBrick(game, (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.7), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace + horizontalBrickWidth * 2), (int) (screenHeight * 0.7), 0);
        Obstacle.addBrick(game, (int) (interBrickSpace * 2.5 + horizontalBrickWidth * 3), (int) (screenHeight * 0.7), 0);

        GameButton.addGameButton(game, (int) (screenWidth * 0.1), 0);
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(1));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(2));
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
