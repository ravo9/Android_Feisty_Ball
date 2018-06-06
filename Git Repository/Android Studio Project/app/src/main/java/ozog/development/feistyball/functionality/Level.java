package ozog.development.feistyball.functionality;

import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ozog.development.feistyball.game.elements.BlackHole;
import ozog.development.feistyball.game.elements.Bonus;
import ozog.development.feistyball.game.elements.DestructionBall;
import ozog.development.feistyball.game.elements.GameButton;
import ozog.development.feistyball.game.elements.Obstacle;
import ozog.development.feistyball.game.elements.Propeller;
import ozog.development.feistyball.game.elements.Ball;
import ozog.development.feistyball.game.elements.Destination;
import ozog.development.feistyball.windows.BestScores;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Level{

    private static final int screenWidth;
    private static final int screenHeight;
    // Height of a horizontally lying brick.
    private static final int brickShort;
    // Width of a horizontally lying brick.
    private static final int brickLong;

    // A length of a brick (longer) plus a space between bricks.
    private static final int brickUnitW;
    private static final int brickUnitH;

    public static int currentLevel;
    public static int lastLevelNumber;

    public static ArrayList<Obstacle> obstacles;
    public static ArrayList<Propeller> propellers;
    public static ArrayList<DestructionBall> destructionBalls;
    public static ArrayList<GameButton> gameButtons;


    static {
        screenWidth = Layout.screenWidth;
        screenHeight = Layout.screenHeight;

        brickLong = (int) (screenWidth * 0.2);
        brickShort = (int) (brickLong * 0.25);

        brickUnitW = (int)(Obstacle.longerBrickLength * 1.045);
        brickUnitH = (int)(Obstacle.shorterBrickLenght + Obstacle.longerBrickLength * 0.045);

        currentLevel = 0;
        lastLevelNumber = 10;

        obstacles = new ArrayList<>();
        propellers = new ArrayList<>();
        destructionBalls = new ArrayList<>();
        gameButtons = new ArrayList<>();
    }

    public static void loadInterLevelMenu() {

        // In case of playing the last level in single-level mode.
        if (currentLevel == lastLevelNumber)
            Layout.btnNextLevel.setEnabled(false);

        Layout.finalLevelTime.setText("Level Time: " + Time.displayTime(Time.levelTime));

        if (MainGame.gameMode == "fullGame"){

            Layout.finalTotalTime.setText("Total Time: " + Time.displayTime(Time.gameTime));

            if (currentLevel == lastLevelNumber)
                loadEndingMenu();

        } else if (MainGame.gameMode == "singleLevel") {

            int currentLevelRecord = BestScores.getRecord(currentLevel);
            if (currentLevelRecord == -1)
                Layout.finalTotalTime.setText("Level Record: 00:00:00");
            else
                Layout.finalTotalTime.setText("Level Record: " + Time.displayTime(currentLevelRecord));
        }

        MainGame.windowLevelComplited.setVisibility(View.VISIBLE);
        MainGame.windowLevelComplited.animate().translationX(0).setDuration(2000);
    }

    public static void hideInterLevelMenu() {

        MainGame.windowLevelComplited.animate().translationX(-screenWidth).setDuration(2000);
        MainGame.windowLevelComplited.setVisibility(View.INVISIBLE);
        MainGame.windowLevelComplited.animate().translationX(screenWidth);
    }

    public static void loadEndingMenu() {

        Layout.levelCompletedTitle.setText("Game completed!");
        Layout.btnNextLevel.setText("Restart game");
        Layout.btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { wholeGameRestart(); }
        });
    }

    public static void wholeGameRestart() {

        currentLevel = 0;
        loadNextLevel();

        // Reset ending menu settings (make the level completed menu again)
        Layout.levelCompletedTitle.setText("Level completed!");
        Layout.btnNextLevel.setText("Next level");
        Layout.btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { loadNextLevel(); }
        });
    }

    public static void loadNextLevel() {

        // Remove previous level elements
        int unnecessaryElementsAmount = (MainGame.rl.getChildCount() - 3);
        MainGame.rl.removeViews(3, unnecessaryElementsAmount);

        // Prepare the destination
        Destination.addDestination();

        // Prepare the ball
        Ball.addBall();

        // Choose proper level to load
        switch (currentLevel) {
            case 0:
                Time.gameTime = 0;
                setLevel1();
                break;
            case 1:
                setLevel2();
                break;
            case 2:
                setLevel3();
                break;
            case 3:
                setLevel4();
                break;
            case 4:
                setLevel5();
                break;
            case 5:
                setLevel6();
                break;
            case 6:
                setLevel7();
                break;
            case 7:
                setLevel8();
                break;
            case 8:
                setLevel9();
                break;
            case 9:
                setLevel10();
                break;
        }

        if (MainGame.gameMode == "fullGame")
            Layout.gameTimer.setVisibility(View.VISIBLE);
        else if (MainGame.gameMode == "singleLevel")
            Layout.gameTimer.setVisibility(View.INVISIBLE);

        Time.levelTime = 0;
        currentLevel++;

        hideInterLevelMenu();

        Time.timer = new Timer();

        // Time update for timers and messages
        Time.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Time.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Time.updateTime();
                        Messages.update();
                    }
                });
            }
        }, 0, 10);

        // Movement time update
        Time.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Time.movementHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Mechanics.update();
                    }
                });
            }
        }, 0, 6);
    }

    public static void restartLevel() {
        currentLevel--;
        loadNextLevel();
    }

    public static void levelCompleted(View v) {
        levelCompleted();
    }

    public static void levelCompleted() {

        Time.resetTimers();
        fadeGameElementsOut();
        resetGameElements();

        BestScores.updateBestScores();
        loadInterLevelMenu();

        // AdSense
        if (currentLevel == 2 || currentLevel == 4 || currentLevel == 6 || currentLevel == 8 || currentLevel == 10)
            loadGoogleAdSense();
    }

    public static void fadeGameElementsOut() {

        int animationTime = 1200;

        Ball.ball.animate().alpha(0.0f).setDuration(animationTime);
        Destination.destination.animate().alpha(0.0f).setDuration(animationTime);

        if (BlackHole.blackHoleA != null)
            BlackHole.blackHoleA.animate().alpha(0.0f).setDuration(animationTime);

        if (BlackHole.blackHoleB != null)
            BlackHole.blackHoleB.animate().alpha(0.0f).setDuration(animationTime);

        if (Bonus.bonus != null)
            Bonus.bonus.animate().alpha(0.0f).setDuration(animationTime);

        for (Propeller p : propellers)
            p.getImage().animate().alpha(0.0f).setDuration(animationTime);

        for (Obstacle o : obstacles) {
            if (o.getImage() != null)
                o.getImage().animate().alpha(0.0f).setDuration(animationTime);
        }

        for (GameButton g : gameButtons)
            g.getImage().animate().alpha(0.0f).setDuration(animationTime);

        for (DestructionBall d : destructionBalls)
            d.getImage().animate().alpha(0.0f).setDuration(animationTime);
    }

    public static void loadGoogleAdSense() {

        if (MainMenu.mInterstitialAd.isLoaded()) {
            MainMenu.mInterstitialAd.show();
            Log.d("TAG", "The interstitial has been loaded.");
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public static void resetGameElements() {

        propellers.clear();
        obstacles.clear();
        destructionBalls.clear();
        gameButtons.clear();

        Ball.ball = null;
        Destination.destination = null;
        BlackHole.blackHoleA = null;
        BlackHole.blackHoleB = null;
        Bonus.bonus = null;
    }

    public static void setLevel1() {

        int ballPositionX = (int) (screenWidth * 0.46);
        int ballPositionY = (int) (screenHeight * 0.7);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        int destinationPositionX = (int) (screenWidth * 0.62);
        int destinationPositionY = (int) (screenHeight * 0.04);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (-screenWidth * 0.18), (int) (screenHeight * 0.32), 0);
        Obstacle.addRedBrick( (int) ( -screenWidth * 0.18 + brickUnitW), (int) (screenHeight * 0.32), 0);
        Obstacle.addRedBrick( (int) ( -screenWidth * 0.18 + brickUnitW * 2), (int) (screenHeight * 0.32), 0);
        Obstacle.addRedBrick( (int) ( -screenWidth * 0.18 + brickUnitW * 3), (int) (screenHeight * 0.32), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.20), (int) (screenHeight * 0.51), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.20 + brickUnitW), (int) (screenHeight * 0.51), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.20 + brickUnitW * 2), (int) (screenHeight * 0.51), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.20 + brickUnitW * 3), (int) (screenHeight * 0.51), 0);

        Propeller.addPropeller( (int) (screenWidth * 0.10), (int) (screenHeight * 0.13));
        Propeller.addPropeller( (int) (screenWidth * 0.70), (int) (screenHeight * 0.6));
        Propeller.addPropeller( (int) (screenWidth * 0.175), (int) (screenHeight * 0.78));
    }

    public static void setLevel2() {

        float ballPositionX = (float) (screenWidth * 0.14);
        float ballPositionY = (float) (screenHeight * 0.56);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.11);
        float destinationPositionY = (float) (screenHeight * 0.525);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (screenWidth * 0.4), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.4 + brickUnitW), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.4 + brickUnitW * 2), (int) (screenHeight * 0.3), 0);

        Obstacle.addRedBrick( (int) (-screenWidth * 0.02), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.02 + brickUnitW), (int) (screenHeight * 0.48), 0);

        Obstacle.addRedBrick( (int) (-screenWidth * 0.02 + brickUnitW * 2), (int) (screenHeight * 0.48), 1);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.02 + brickUnitW * 2), (int) (screenHeight * 0.48 + brickUnitW), 1);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.02 + brickUnitW * 2), (int) (screenHeight * 0.48 + brickUnitW * 2), 1);

        Obstacle.addRedBrick( (int) (screenWidth * 0.723 + brickUnitH), (int) (screenHeight * 0.48 + brickUnitW * 2), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.723 + brickUnitH + brickUnitW), (int) (screenHeight * 0.48 + brickUnitW * 2), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.723), (int) (screenHeight * 0.48 + brickUnitW * 2), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.63), (int) (screenHeight * 0.06));
        Propeller.addPropeller( (int) (screenWidth * 0.08), (int) (screenHeight * 0.22));
        Propeller.addPropeller( (int) (screenWidth * 0.81), (int) (screenHeight * 0.81));
    }

    public static void setLevel3() {

        BlackHole.setBlackHoles((float) (screenWidth * 0.1), (float) (screenHeight * 0.075), (float) (screenWidth * 0.11), (float) (screenHeight * 0.85));

        float ballPositionX = (float) (screenWidth * 0.74);
        float ballPositionY = (float) (screenHeight * 0.66);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.71);
        float destinationPositionY = (float) (screenHeight * 0.48);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (-screenWidth * 0.1), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW * 4), (int) (screenHeight * 0.25), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.22), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.22 + brickUnitW), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.22 + brickUnitW * 2), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.22 + brickUnitW * 3), (int) (screenHeight * 0.45), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.66), (int) (screenHeight * 0.45 + brickUnitH), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.66), (int) (screenHeight * 0.45 + brickUnitH + brickUnitW), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.66), (int) (screenHeight * 0.45 + brickUnitH + brickUnitW * 2.9), 1);

        Obstacle.addRedBrick( (int) (screenWidth * 0.19), (int) (screenHeight * 0.66), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.19), (int) (screenHeight * 0.66 + brickUnitW), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.19 + Obstacle.longerBrickLength - Obstacle.shorterBrickLenght), (int) (screenHeight * 0.66 + brickUnitH + brickUnitW), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.19 + Obstacle.longerBrickLength - Obstacle.shorterBrickLenght), (int) (screenHeight * 0.66 + brickUnitH + brickUnitW * 2), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.74), (int) (screenHeight * 0.075));
        Propeller.addPropeller( (int) (screenWidth * 0.74), (int) (screenHeight * 0.31));
        Propeller.addPropeller( (int) (screenWidth * 0.435), (int) (screenHeight * 0.51));
    }

    public static void setLevel4() {

        BlackHole.setBlackHoles((float) (screenWidth * 0.71), (float) (screenHeight * 0.07), (float) (screenWidth * 0.436), (float) (screenHeight * 0.782));

        float ballPositionX = (float) (screenWidth * 0.45);
        float ballPositionY = (float) (screenHeight * 0.15);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.05);
        float destinationPositionY = (float) (screenHeight * 0.04);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (brickUnitW), (int) (screenHeight * 0.29), 0);
        Obstacle.addRedBrick( (int) (brickUnitW * 2), (int) (screenHeight * 0.29), 0);

        Obstacle.addRedBrick( (int) (brickUnitW * 2 + Obstacle.longerBrickLength - Obstacle.shorterBrickLenght ), (int) (screenHeight * 0.29 + brickUnitH), 1);

        Obstacle.addRedBrick( (int) (-screenWidth * 0.1), (int) (screenHeight * 0.29 + brickUnitH + brickUnitW), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW), (int) (screenHeight * 0.29 + brickUnitH + brickUnitW), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW * 2), (int) (screenHeight * 0.29 + brickUnitH + brickUnitW), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.29 + brickUnitH + brickUnitW), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.1 + brickUnitW * 4), (int) (screenHeight * 0.29 + brickUnitH + brickUnitW), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.43), (int) (screenHeight * 0.74), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.43 - brickUnitH), (int) (screenHeight * 0.74), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.43 + brickUnitW), (int) (screenHeight * 0.74), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.43 + brickUnitW), (int) (screenHeight * 0.74 + brickUnitW), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.41), (int) (screenHeight * 0.34));
        Propeller.addPropeller( (int) (screenWidth * 0.755), (int) (screenHeight * 0.34));
        Propeller.addPropeller( (int) (screenWidth * 0.765), (int) (screenHeight * 0.87));

        Bonus.setBonusPosition((float) (screenWidth * 0.05), (float) (screenHeight * 0.525));
    }

    public static void setLevel5() {

        BlackHole.setBlackHoles((float) (screenWidth * 0.058), (float) (screenHeight * 0.255), (float) (screenWidth * 0.67), (float) (screenHeight * 0.475));

        float ballPositionX = (float) (screenWidth * 0.15);
        float ballPositionY = (float) (screenHeight * 0.86);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.025);
        float destinationPositionY = (float) (screenHeight * 0.80);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller( (int) (screenWidth * 0.08), (int) (screenHeight * 0.05));
        Propeller.addPropeller( (int) (screenWidth * 0.92 - Propeller.propellerLength), (int) (screenHeight * 0.05));

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.40), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.40 + brickUnitW), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.40 + brickUnitW * 3), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.40 + brickUnitW * 4), (int) (screenHeight * 0.2), 0);

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.55), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.55 + brickUnitW), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.55 + brickUnitW * 2), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.55 + brickUnitW * 3), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.55 + brickUnitW * 4), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.55 + brickUnitW * 5), (int) (screenHeight * 0.4), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.065 + brickUnitW), (int) (screenHeight * 0.64), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.065 + brickUnitW * 2), (int) (screenHeight * 0.64), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.39), (int) (screenHeight * 0.81), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.39 + brickUnitW), (int) (screenHeight * 0.81), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.39 + brickUnitW * 2), (int) (screenHeight * 0.81), 0);

        Bonus.setBonusPosition((float) (screenWidth * 0.77), (float) (screenHeight * 0.87));
    }

    public static void setLevel6() {

        float ballPositionX = (float) (screenWidth * 0.15);
        float ballPositionY = (float) (screenHeight * 0.17);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.035);
        float destinationPositionY = (float) (screenHeight * 0.08);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.42), 0, (float)0.5);
        DestructionBall.addDestructionBall( (int) (screenWidth * 0.705), 0, (float)(1));

        GameButton.addGameButton( (int) (screenWidth * 0.16), 0);
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(1));

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.39), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.39 + brickUnitW), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.39 + brickUnitW * 2), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.39 + brickUnitW * 3), (int) (screenHeight * 0.3), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.2), (int) (screenHeight * 0.49), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + brickUnitW), (int) (screenHeight * 0.49), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + brickUnitW * 2), (int) (screenHeight * 0.49), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + brickUnitW * 3), (int) (screenHeight * 0.49), 0);

        Bonus.setBonusPosition((float) (screenWidth * 0.786), (float) (screenHeight * 0.534));

        Obstacle.addRedBrick( (int) (screenWidth * 0.2), (int) (screenHeight * 0.65), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + brickUnitW), (int) (screenHeight * 0.65), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + brickUnitW * 2), (int) (screenHeight * 0.65), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + brickUnitW * 3), (int) (screenHeight * 0.65), 0);

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.5), (int) (screenHeight * 0.8), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.5 + brickUnitW), (int) (screenHeight * 0.8), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.5 + brickUnitW * 2), (int) (screenHeight * 0.8), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.5 + brickUnitW * 3), (int) (screenHeight * 0.8), 0);

        Propeller.addPropeller( (int) (screenWidth * 0.07), (int) (screenHeight * 0.86));
    }

    public static void setLevel7() {

        BlackHole.setBlackHoles((float) (screenWidth * 0.35), (float) (screenHeight * 0.06), (float) (screenWidth * 0.062), (float) (screenHeight * 0.252));

        float ballPositionX = (float) (screenWidth * 0.8);
        float ballPositionY = (float) (screenHeight * 0.07);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.69);
        float destinationPositionY = (float) (screenHeight * 0.018);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.6), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.6 + brickUnitW), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.6 + brickUnitW * 2), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.6 + brickUnitW * 3), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.6 + brickUnitW * 4), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.6 + brickUnitW * 5), (int) (screenHeight * 0.2), 0);

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW * 2), (int) (screenHeight * 0.4), 0);

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.4), 1);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.4), 1);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.4 + brickUnitW * 2), 1);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.4 + brickUnitW * 4), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.1), (int) (screenHeight * 0.5));
        Propeller.addPropeller( (int) (screenWidth * 0.1), (int) (screenHeight * 0.8));

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.2), (int) (screenHeight * 0.63), (float)(1));
        Level.destructionBalls.get(0).rotate180();
        DestructionBall.addDestructionBall( (int) (screenWidth * 0.2), (int) (screenHeight * 0.83), (float)(1));
        Level.destructionBalls.get(1).rotate180();
        DestructionBall.addDestructionBall( (int) (screenWidth * 0.2), (int) (screenHeight * 1.03), (float)(1));
        Level.destructionBalls.get(2).rotate180();

        DestructionBall.setSpeed(5);

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1), (int) (screenHeight * 0.7), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.1 + brickUnitW * 2), (int) (screenHeight * 0.7), 0);

        GameButton.addGameButton( (int) (screenWidth * 0.1), 0);
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(1));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(2));
    }

    public static void setLevel8() {

        BlackHole.setBlackHoles((float) (screenWidth * 0.78), (float) (screenHeight * 0), (float) (screenWidth * 0.3), (float) (screenHeight * 0.37));

        float ballPositionX = (float) (screenWidth * 0.43);
        float ballPositionY = (float) (screenHeight * 0.88);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.575);
        float destinationPositionY = (float) (screenHeight * 0.87);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Bonus.setBonusPosition((float) (screenWidth * 0.025), (float) (screenHeight * 0.885));

        Propeller.addPropeller( (int) (screenWidth * 0.04), (int) (screenHeight * 0.21));
        Propeller.addPropeller( (int) (screenWidth * 0.035), (int) (screenHeight * 0.71));
        Propeller.addPropeller( (int) (screenWidth * 0.48), (int) (screenHeight * 0.71));

        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitW * 4), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitW * 3), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitW * 2), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitW), (int) (screenHeight * 0.15), 0);

        Obstacle.addRedBrick((int) (screenWidth * 0.23 + brickUnitH), (int) (screenHeight * 0.5), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitW - brickUnitH), (int) (screenHeight * 0.66), 0);

        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitH), (int) (screenHeight * 0.15 + brickUnitH), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitH), (int) (screenHeight * 0.15 + brickUnitH + brickUnitW), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitH), (int) (screenHeight * 0.15 + brickUnitH + brickUnitW * 2), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitH), (int) (screenHeight * 0.15 + brickUnitH + brickUnitW * 3), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75 - brickUnitH), (int) (screenHeight * 0.15 + brickUnitH + brickUnitW * 4), 1);

        Obstacle.addRedBrick((int) (screenWidth * 0.23), (int) (screenHeight * 0.422), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.23), (int) (screenHeight * 0.422 + brickUnitW), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.23), (int) (screenHeight * 0.422 + brickUnitW * 2), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.23), (int) (screenHeight * 0.422 + brickUnitH + brickUnitW * 3), 1);

        Obstacle.addGreyBrick( (int) (-brickUnitW * 0.01), (int) (screenHeight * 0.84), 0);

        GameButton.addGameButton( (int) (screenWidth * 0.1), 0);
        // Game Button is also a part of 'obstacles' array, so I need to connect this button to the second last element in that set.
        gameButtons.get(0).addButtonMovingObstacleConnection(Level.obstacles.get(Level.obstacles.size() - 2));

        Obstacle.addRedBrick( (int) (-brickUnitW * 0.01 + brickUnitW), (int) (screenHeight * 0.84), 0);
        Obstacle.addRedBrick( (int) (-brickUnitW * 0.01 + brickUnitW * 2), (int) (screenHeight * 0.84), 0);
    }

    public static void setLevel9() {

        float ballPositionX = (float) (screenWidth * 0.525);
        float ballPositionY = (float) (screenHeight * 0.3);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.43);
        float destinationPositionY = (float) (screenHeight * 0.5);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller( (int) (screenWidth * 0.2), (int) (screenHeight * 0.05));
        Propeller.addPropeller( (int) (screenWidth * 0.796), (int) (screenHeight * 0.46));
        Propeller.addPropeller( (int) (screenWidth * 0.29), (int) (screenHeight * 0.88));

        Obstacle.addRedBrick( (int) (-screenWidth * 0.145), (int) (screenHeight * 0.47), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.2 + brickUnitW * 2), (int) (screenHeight * 0.47), 0);
        Obstacle.addRedBrick( (int) (-screenWidth * 0.2 + brickUnitW * 3), (int) (screenHeight * 0.47), 0);

        Obstacle.addRedBrick((int) (-screenWidth * 0.2 + brickUnitW * 4), (int) (screenHeight * 0.17), 1);
        Obstacle.addRedBrick((int) (-screenWidth * 0.2 + brickUnitW * 4), (int) (screenHeight * 0.17 + brickUnitW), 1);
        Obstacle.addRedBrick((int) (-screenWidth * 0.2 + brickUnitW * 4), (int) (screenHeight * 0.17 + brickUnitW * 2), 1);
        Obstacle.addRedBrick((int) (-screenWidth * 0.2 + brickUnitW * 4), (int) (screenHeight * 0.17 + brickUnitW * 3), 1);
        Obstacle.addRedBrick((int) (-screenWidth * 0.2 + brickUnitW * 4), (int) (screenHeight * 0.17 + brickUnitW * 4), 1);

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.18), (int) (0), (float)(1));

        GameButton.addGameButton( (int) (screenWidth * 0.34), (int) (screenHeight * 0.47 + brickShort ));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
    }

    public static void setLevel10() {

        float ballPositionX = (float) (screenWidth * 0.5);
        float ballPositionY = (float) (screenHeight * 0.5);
        Ball.setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.04);
        float destinationPositionY = (float) (screenHeight * 0.832);
        Destination.setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller( (int) (screenWidth * 0.051), (int) (screenHeight * 0.28));
        Propeller.addPropeller( (int) (screenWidth * 0.75), (int) (screenHeight * 0.41));

        Obstacle.addRedBrick((int) (-screenWidth * 0.1), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) (-screenWidth * 0.1 + brickUnitW), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) (-screenWidth * 0.1 + brickUnitW * 2), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) (-screenWidth * 0.1 + brickUnitW * 3), (int) (screenHeight * 0.15), 0);

        Obstacle.addRedBrick((int) (screenWidth * 0.27), (int) (screenHeight * 0.30), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.27 + brickUnitW), (int) (screenHeight * 0.30), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.27 + brickUnitW * 2), (int) (screenHeight * 0.30), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.27 + brickUnitW * 3), (int) (screenHeight * 0.30), 0);

        Obstacle.addRedBrick((int) (screenWidth * 0.22), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.22 + brickUnitW), (int) (screenHeight * 0.45), 0);

        Obstacle.addGreyBrick((int) (screenWidth * 0.01 ), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.01 + brickUnitW), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.01 + brickUnitW * 2), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.01 + brickUnitW * 3), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) (screenWidth * 0.01 + brickUnitW * 4), (int) (screenHeight * 0.6), 0);

        Obstacle.addRedBrick((int) (-screenWidth * 0.12), (int) (screenHeight * 0.75), 0);
        Obstacle.addRedBrick((int) (-screenWidth * 0.12 + brickUnitW), (int) (screenHeight * 0.75), 0);
        Obstacle.addRedBrick((int) (-screenWidth * 0.12 + brickUnitW * 2), (int) (screenHeight * 0.75), 0);
        Obstacle.addRedBrick((int) (-screenWidth * 0.12 + brickUnitW * 3), (int) (screenHeight * 0.75), 0);

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.7), (int) (screenHeight * 0.97), (float)(1));
        Level.destructionBalls.get(0).rotate180();
        DestructionBall.setSpeed(2);

        Bonus.setBonusPosition((float) (screenWidth * 0.721), (float) (screenHeight * 0.885));

        GameButton.addGameButton( (int) (screenWidth * 0.15), (int) (0));
        GameButton.addGameButton( (int) (screenWidth * 0.125), (int) (screenHeight * 0.75 + brickShort));

        gameButtons.get(0).addButtonMovingObstacleConnection(Level.obstacles.get(Level.obstacles.size() - 11));
        gameButtons.get(1).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
    }
}
