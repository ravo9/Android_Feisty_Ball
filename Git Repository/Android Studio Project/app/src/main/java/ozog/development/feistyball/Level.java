package ozog.development.feistyball;

import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Level{

    private static final int screenWidth;
    private static final int screenHeight;
    // Height of a horizontally lying brick.
    private static final int brickShort;
    // Width of a horizontally lying brick.
    private static final int brickLong;
    private static final int interBrickSpace;
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
        interBrickSpace = (int) (brickLong * 0.05);
        currentLevel = 0;
        lastLevelNumber = 10;

        obstacles = new ArrayList<>();
        propellers = new ArrayList<>();
        destructionBalls = new ArrayList<>();
        gameButtons = new ArrayList<>();
    }

    public static void loadInterLevelMenu() {

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

        // Hide the black holes and bonuses by default
        Layout.blackHoleA.setX(-500);
        Layout.blackHoleB.setX(-500);
        Layout.bonus01.setX(-550);

        // Choose proper level to load
        switch (currentLevel) {
            case 0:
                Time.gameTime = 0;
                //setLevel10();
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
        }, 0, 5);
    }

    public static void restartLevel() {
        currentLevel--;
        loadNextLevel();
    }

    public static void levelCompleted(View v) {
        levelCompleted();
    }

    public static void levelCompleted() {

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

        // It should wait few seconds here (elements need to be faded)
        resetGameElements();
        BestScores.updateBestScores();
        loadInterLevelMenu();

        // AdSense
        if (currentLevel == 2 || currentLevel == 4 || currentLevel == 6 || currentLevel == 8 || currentLevel == 10)
            loadGoogleAdSense();
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

        Time.timer.cancel();
        propellers.clear();
        obstacles.clear();
        destructionBalls.clear();
        gameButtons.clear();
        Layout.ball.animate().cancel();
        Layout.destination.animate().cancel();
        Layout.blackHoleA.animate().cancel();
        Layout.blackHoleB.animate().cancel();
        Layout.bonus01.animate().cancel();
        Layout.blackHoleA.setX(-500);
        Layout.blackHoleB.setX(-500);
        Layout.bonus01.setX(-550);
    }

    public static void setLevel1() {

        float ballPositionX = (screenWidth / 2) - 100;
        float ballPositionY = (float) (screenHeight * 0.7);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (screenWidth / 2 + 50);
        float destinationPositionY = (float) (screenHeight * 0.055);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( 10, (int) (screenHeight * 0.26), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2 + 20), (int) (screenHeight * 0.26), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.4 + 40), (int) (screenHeight * 0.26), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.8 - 10), (int) (screenHeight * 0.26), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.2 - 70), (int) (screenHeight * 0.40), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.4 - 50), (int) (screenHeight * 0.40), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.6 - 30), (int) (screenHeight * 0.40), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.8 - 10), (int) (screenHeight * 0.40), 0);

        Propeller.addPropeller( (int) (screenWidth * 0.10), (int) (screenHeight * 0.1));
        Propeller.addPropeller( (int) (screenWidth * 0.70), (int) (screenHeight * 0.50));
        Propeller.addPropeller( (int) (screenWidth * 0.20), (int) (screenHeight * 0.90));
    }

    public static void setLevel2() {

        float ballPositionX = (float) (screenWidth * 0.14);
        float ballPositionY = (float) (screenHeight * 0.56);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.1);
        float destinationPositionY = (float) (screenHeight * 0.52);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (screenWidth * 0.5), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.7 + 20), (int) (screenHeight * 0.3), 0);

        Obstacle.addRedBrick( (int) (-interBrickSpace), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (brickLong), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (brickLong * 2 + 20), (int) (screenHeight * 0.48), 1);
        Obstacle.addRedBrick( (int) (brickLong * 2 + 20), (int) (screenHeight * 0.48 + 20 + brickLong), 1);
        Obstacle.addRedBrick( (int) (brickLong * 2 + 20), (int) (screenHeight * 0.48 + 40 + brickLong * 2), 1);

        Obstacle.addRedBrick( (int) (screenWidth * 0.8), (int) (screenHeight * 0.77), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.74), (int) (screenHeight * 0.77), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.60), (int) (screenHeight * 0.02));
        Propeller.addPropeller( (int) (screenWidth * 0.08), (int) (screenHeight * 0.25));
        Propeller.addPropeller( (int) (screenWidth * 0.85), (int) (screenHeight * 0.85));
    }

    public static void setLevel3() {

        float ballPositionX = (float) (screenWidth * 0.65);
        // 200 is the ball width/ height
        float ballPositionY = (float) (screenHeight - 200);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.75);
        float destinationPositionY = (float) (screenHeight * 0.5);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace + brickLong), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (brickLong * 2), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace + brickLong * 3), (int) (screenHeight * 0.25), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2 + brickLong * 4), (int) (screenHeight * 0.25), 0);

        Obstacle.addRedBrick( (int) (-interBrickSpace + brickLong + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (brickLong * 2 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace + brickLong * 3 + 75), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2 + brickLong * 4 + 75), (int) (screenHeight * 0.45), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.70), (int) (screenHeight * 0.45 + brickShort + interBrickSpace), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.70), (int) (screenHeight * 0.45 + brickShort + brickLong + interBrickSpace * 2), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.70), (int) (screenHeight * 0.45 + brickShort + + brickLong * 2 + interBrickSpace * 3), 1);

        Obstacle.addRedBrick( (int) (0.86* brickLong ), (int) (screenHeight * 0.7), 1);
        Obstacle.addRedBrick( (int) (0.86 * brickLong ), (int) (screenHeight * 0.7 + brickLong + interBrickSpace), 0);
        Obstacle.addRedBrick( (int) (brickLong * 1.86 - brickShort - 1), (int) (screenHeight * 0.7 + brickLong + brickShort + interBrickSpace * 2), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.75), (int) (screenHeight * 0.1));
        Propeller.addPropeller( (int) (screenWidth * 0.75), (int) (screenHeight * 0.34));

        Layout.blackHoleA.setX((float) (screenWidth * 0.1));
        Layout.blackHoleA.setY((float) (screenHeight * 0.1));

        Layout.blackHoleB.setX((float) (screenWidth * 0.13));
        Layout.blackHoleB.setY((float) (screenHeight * 0.87));
    }

    public static void setLevel4() {

        float ballPositionX = (float) (screenWidth * 0.45);
        float ballPositionY = (float) (screenHeight * 0.15);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.05);
        float destinationPositionY = (float) (screenHeight * 0.04);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Obstacle.addRedBrick( (int) ( -interBrickSpace * 1.5 + brickLong), (int) (screenHeight * 0.315), 0);
        Obstacle.addRedBrick( (int) ( -interBrickSpace * 0.5 + brickLong * 2 + interBrickSpace), (int) (screenHeight * 0.315), 0);
        Obstacle.addRedBrick( (int) ( interBrickSpace * 0.5 + brickLong * 3 + interBrickSpace * 2), (int) (screenHeight * 0.315), 0);

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 1.5 + brickLong), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 0.5 + brickLong * 2), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 0.5 + brickLong * 3), (int) (screenHeight * 0.48), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 1.5 + brickLong * 4), (int) (screenHeight * 0.48), 0);

        Obstacle.addRedBrick( (int) (screenWidth * 0.25), (int) (screenHeight * 0.48 + brickShort + interBrickSpace), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.7), (int) (screenHeight * 0.48 - brickLong - interBrickSpace), 1);

        Obstacle.addRedBrick( (int) (screenWidth * 0.67 - brickLong - 2 * interBrickSpace - brickShort), (int) (screenHeight * 0.88 - interBrickSpace - brickLong), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.67 - brickLong - interBrickSpace), (int) (screenHeight * 0.86 - brickLong), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.67), (int) (screenHeight * 0.88), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.67), (int) (screenHeight * 0.88 - interBrickSpace - brickLong), 1);

        Propeller.addPropeller( (int) (screenWidth * 0.81), (int) (screenHeight * 0.37));
        Propeller.addPropeller( (int) (screenWidth * 0.55), (int) (screenHeight * 0.37));
        Propeller.addPropeller( (int) (screenWidth * 0.8), (int) (screenHeight * 0.9));

        Layout.blackHoleA.setX((float) (screenWidth * 0.78));
        Layout.blackHoleA.setY((float) (screenHeight * 0.04));

        Layout.blackHoleB.setX((float) (screenWidth * 0.48));
        Layout.blackHoleB.setY((float) (screenHeight * 0.78));

        Layout.bonus01.setX((float) (screenWidth * 0.05));
        Layout.bonus01.setY((float) (screenHeight * 0.54));
    }

    public static void setLevel5() {

        float ballPositionX = (float) (screenWidth * 0.15);
        float ballPositionY = (float) (screenHeight - 200);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.035);
        float destinationPositionY = (float) (screenHeight * 0.83);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller( (int) (screenWidth * 0.1), (int) (screenHeight * 0.06));
        Propeller.addPropeller( (int) (screenWidth * 0.78), (int) (screenHeight * 0.06));

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 1.5 + brickLong), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 0.5 + brickLong * 3), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 1.5 + brickLong * 4), (int) (screenHeight * 0.2), 0);

        Layout.blackHoleA.setX((float) (screenWidth * 0.07));
        Layout.blackHoleA.setY((float) (screenHeight * 0.26));

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 1.5 + brickLong), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (brickLong * 2), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 1.5 + brickLong * 3), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2.5 + brickLong * 4), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 3.5 + brickLong * 5), (int) (screenHeight * 0.4), 0);

        Layout.blackHoleB.setX((float) (screenWidth * 0.93 - 240));
        Layout.blackHoleB.setY((float) (screenHeight * 0.46));

        Obstacle.addRedBrick( (int) (-interBrickSpace * 0.3 + brickLong), (int) (screenHeight * 0.64), 0);
        Obstacle.addRedBrick( (int) (brickLong * 2), (int) (screenHeight * 0.64), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 0.6 + brickLong * 3), (int) (screenHeight * 0.64), 0);

        Obstacle.addRedBrick( (int) (brickLong * 3 - screenWidth * 0.11), (int) (screenHeight * 0.81), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace + brickLong * 4 - screenWidth * 0.11), (int) (screenHeight * 0.81), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2 + brickLong * 5 - screenWidth * 0.11), (int) (screenHeight * 0.81), 0);

        Layout.bonus01.setX((float) (screenWidth * 0.82));
        Layout.bonus01.setY((float) (screenHeight * 0.87));
    }

    public static void setLevel6() {

        float ballPositionX = (float) (screenWidth * 0.15);
        float ballPositionY = (float) (screenHeight * 0.17);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.035);
        float destinationPositionY = (float) (screenHeight * 0.09);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.4), 0, (float)0.5);
        DestructionBall.addDestructionBall( (int) (screenWidth * 0.6), 0, (float)(1));

        GameButton.addGameButton( (int) (screenWidth * 0.16), 0);
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(1));

        Obstacle.addRedBrick( (int) (-interBrickSpace * 4), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 3 + brickLong), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 2 + brickLong * 2), (int) (screenHeight * 0.3), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace + brickLong * 3), (int) (screenHeight * 0.3), 0);

        Obstacle.addRedBrick( (int) (screenWidth - brickLong * 4 - interBrickSpace * 4), (int) (screenHeight * 0.5), 0);
        Obstacle.addRedBrick( (int) (screenWidth - brickLong * 3 - interBrickSpace * 3), (int) (screenHeight * 0.5), 0);
        Obstacle.addRedBrick( (int) (screenWidth - brickLong * 2 - interBrickSpace * 2), (int) (screenHeight * 0.5), 0);
        Obstacle.addRedBrick( (int) (screenWidth - brickLong - interBrickSpace), (int) (screenHeight * 0.5), 0);

        Layout.bonus01.setX((float) (screenWidth * 0.83));
        Layout.bonus01.setY((float) (screenHeight * 0.55));

        Obstacle.addRedBrick( (int) (screenWidth - brickLong * 4 - interBrickSpace * 4), (int) (screenHeight * 0.65), 0);
        Obstacle.addRedBrick( (int) (screenWidth - brickLong * 3 - interBrickSpace * 3), (int) (screenHeight * 0.65), 0);
        Obstacle.addRedBrick( (int) (screenWidth - brickLong * 2 - interBrickSpace * 2), (int) (screenHeight * 0.65), 0);
        Obstacle.addRedBrick( (int) (screenWidth - brickLong - interBrickSpace), (int) (screenHeight * 0.65), 0);

        Obstacle.addRedBrick( (int) (-interBrickSpace * 4), (int) (screenHeight * 0.8), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 3 + brickLong), (int) (screenHeight * 0.8), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 2 + brickLong * 2), (int) (screenHeight * 0.8), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace + brickLong * 3), (int) (screenHeight * 0.8), 0);

        Propeller.addPropeller( (int) (screenWidth * 0.07), (int) (screenHeight * 0.88));
    }

    public static void setLevel7() {

        float ballPositionX = (float) (screenWidth * 0.8);
        float ballPositionY = (float) (screenHeight * 0.07);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.7);
        float destinationPositionY = (float) (screenHeight * 0.04);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Layout.blackHoleA.setX((float) (screenWidth * 0.35));
        Layout.blackHoleA.setY((float) (screenHeight * 0.06));

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace * 1.5 + brickLong), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (brickLong * 2), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 1.5 + brickLong * 3), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2.5 + brickLong * 4), (int) (screenHeight * 0.2), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 3.5 + brickLong * 5), (int) (screenHeight * 0.2), 0);

        Layout.blackHoleB.setX((float) (screenWidth * 0.07));
        Layout.blackHoleB.setY((float) (screenHeight * 0.26));

        Obstacle.addRedBrick( (int) (interBrickSpace), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2 + brickLong), (int) (screenHeight * 0.4), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 3 + brickLong * 2), (int) (screenHeight * 0.4), 0);

        Propeller.addPropeller( (int) (screenWidth * 0.1), (int) (screenHeight * 0.5));
        Propeller.addPropeller( (int) (screenWidth * 0.1), (int) (screenHeight * 0.8));

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.2), (int) (screenHeight * 0.61), (float)(1));
        Level.destructionBalls.get(0).rotate180();
        DestructionBall.addDestructionBall( (int) (screenWidth * 0.2), (int) (screenHeight * 0.81), (float)(1));
        Level.destructionBalls.get(1).rotate180();
        DestructionBall.addDestructionBall( (int) (screenWidth * 0.2), (int) (screenHeight * 1.01), (float)(1));
        Level.destructionBalls.get(2).rotate180();

        DestructionBall.setSpeed(8);

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2.5), (int) (screenHeight * 0.7), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace + brickLong * 2), (int) (screenHeight * 0.7), 0);
        Obstacle.addRedBrick( (int) (interBrickSpace * 2.5 + brickLong * 3), (int) (screenHeight * 0.7), 0);

        GameButton.addGameButton( (int) (screenWidth * 0.1), 0);
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(1));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(2));
    }

    public static void setLevel8() {

        float ballPositionX = (float) (screenWidth * 0.43);
        float ballPositionY = (float) (screenHeight * 0.88);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.62);
        float destinationPositionY = (float) (screenHeight * 0.86);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Layout.blackHoleA.setX((float) (screenWidth * 0.35));
        Layout.blackHoleA.setY((float) (screenHeight * 0.04));

        Layout.blackHoleB.setX((float) (screenWidth * 0.55));
        Layout.blackHoleB.setY((float) (screenHeight * 0.21));

        Layout.bonus01.setX((float) (screenWidth * 0.025));
        Layout.bonus01.setY((float) (screenHeight * 0.89));

        Propeller.addPropeller( (int) (screenWidth * 0.565), (int) (screenHeight * 0.71));
        Propeller.addPropeller( (int) (screenWidth * 0.04), (int) (screenHeight * 0.21));
        Propeller.addPropeller( (int) (screenWidth * 0.04), (int) (screenHeight * 0.71));

        Obstacle.addRedBrick((int) (-interBrickSpace * 3), (int) (screenHeight * 0.16), 0);
        Obstacle.addRedBrick((int) (-interBrickSpace * 2 + brickLong), (int) (screenHeight * 0.16), 0);
        Obstacle.addRedBrick((int) (-interBrickSpace + brickLong * 2), (int) (screenHeight * 0.16), 0);
        Obstacle.addRedBrick((int) (brickLong * 3), (int) (screenHeight * 0.16), 0);

        Obstacle.addRedBrick((int) (interBrickSpace * 0.75 + brickLong + brickShort), (int) (screenHeight * 0.5), 0);
        Obstacle.addRedBrick((int) (-interBrickSpace + brickLong * 3 - brickShort), (int) (screenHeight * 0.65), 0);

        Obstacle.addRedBrick((int) (screenWidth * 0.75), (int) (screenHeight * 0.2), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75), (int) (screenHeight * 0.2 + brickLong + interBrickSpace), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75), (int) (screenHeight * 0.2 + 2 * (brickLong + interBrickSpace)), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75), (int) (screenHeight * 0.2 + 3 * (brickLong + interBrickSpace)), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.75), (int) (screenHeight * 0.2 + 4 * (brickLong + interBrickSpace)), 1);

        Obstacle.addRedBrick((int) (screenWidth * 0.2), (int) (screenHeight * 0.45), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2), (int) (screenHeight * 0.45 + brickLong + interBrickSpace), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2), (int) (screenHeight * 0.45 + 2 * (brickLong + interBrickSpace)), 1);
        Obstacle.addRedBrick( (int) (screenWidth * 0.2), (int) (screenHeight * 0.45 + 3 * (brickLong + interBrickSpace) + 2 * interBrickSpace + brickShort), 1);

        Obstacle.addGreyBrick( (int) (-interBrickSpace * 3), (int) (screenHeight * 0.83), 0);

        GameButton.addGameButton( (int) (screenWidth * 0.1), 0);
        // Game Button is also a part of 'obstacles' array, so I need to connect this button to the second last element in that set.
        gameButtons.get(0).addButtonMovingObstacleConnection(Level.obstacles.get(Level.obstacles.size() - 2));

        Obstacle.addRedBrick( (int) (-interBrickSpace * 2 + brickLong), (int) (screenHeight * 0.83), 0);
        Obstacle.addRedBrick( (int) (-interBrickSpace + brickLong * 2), (int) (screenHeight * 0.83), 0);
        Obstacle.addRedBrick( (int) (brickLong * 3), (int) (screenHeight * 0.83), 0);
    }

    public static void setLevel9() {

        float ballPositionX = (float) (screenWidth * 0.6);
        float ballPositionY = (float) (screenHeight * 0.36);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.53);
        float destinationPositionY = (float) (screenHeight * 0.5);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller( (int) (screenWidth * 0.86), (int) (screenHeight * 0.42));
        Propeller.addPropeller( (int) (screenWidth * 0.22), (int) (screenHeight * 0.05));
        Propeller.addPropeller( (int) (screenWidth * 0.32), (int) (screenHeight * 0.91));

        Obstacle.addRedBrick( (int) (screenWidth * 0.37 - (brickLong + interBrickSpace)), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.37), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick( (int) (screenWidth * 0.37 + brickLong + interBrickSpace), (int) (screenHeight * 0.45), 0);

        Obstacle.addRedBrick((int) (screenWidth * 0.37 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.3 - (brickLong + interBrickSpace)), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.37 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.3), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.37 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.3 + (brickLong + interBrickSpace)), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.37 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.3 + 2 * (brickLong + interBrickSpace)), 1);
        Obstacle.addRedBrick((int) (screenWidth * 0.37 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.3 + 3 * (brickLong + interBrickSpace)), 1);

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.18), (int) (0), (float)(1));

        GameButton.addGameButton( (int) (screenWidth * 0.34), (int) (screenHeight * 0.45 + brickShort ));
        gameButtons.get(0).addButtonDestructionBallConnection(Level.destructionBalls.get(0));
    }

    public static void setLevel10() {

        float ballPositionX = (float) (screenWidth * 0.6);
        float ballPositionY = (float) (screenHeight * 0.36);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.05);
        float destinationPositionY = (float) (screenHeight * 0.84);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        Obstacle.addWalls();

        Propeller.addPropeller( (int) (screenWidth * 0.82), (int) (screenHeight * 0.43));
        Propeller.addPropeller( (int) (screenWidth * 0.08), (int) (screenHeight * 0.28));

        Obstacle.addRedBrick((int) ( -screenWidth * 0.05), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + (brickLong + interBrickSpace)), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.15), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + 3 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.15), 0);

        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + (brickLong + interBrickSpace)), (int) (screenHeight * 0.30), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.30), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + 3 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.30), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + 4 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.30), 0);

        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + (brickLong + interBrickSpace)), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.45), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + 3 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.45), 0);

        Obstacle.addGreyBrick((int) ( -screenWidth * 0.05), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + (brickLong + interBrickSpace)), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + 3 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.6), 0);
        Obstacle.addRedBrick((int) ( screenWidth * 0.05 + 4 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.6), 0);

        Obstacle.addRedBrick((int) ( -screenWidth * 0.05), (int) (screenHeight * 0.75), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + (brickLong + interBrickSpace)), (int) (screenHeight * 0.75), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + 2 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.75), 0);
        Obstacle.addRedBrick((int) ( -screenWidth * 0.05 + 3 * (brickLong + interBrickSpace)), (int) (screenHeight * 0.75), 0);

        DestructionBall.addDestructionBall( (int) (screenWidth * 0.7), (int) (screenHeight * 0.97), (float)(1));
        Level.destructionBalls.get(0).rotate180();

        Layout.bonus01.setX((float) (screenWidth * 0.73));
        Layout.bonus01.setY((float) (screenHeight * 0.9));

        GameButton.addGameButton( (int) (screenWidth * 0.2), (int) (screenHeight * 0));
        GameButton.addGameButton( (int) (screenWidth * 0.03), (int) (screenHeight * 0.75 + brickShort));
        gameButtons.get(0).addButtonMovingObstacleConnection(Level.obstacles.get(Level.obstacles.size() - 11));
        gameButtons.get(1).addButtonDestructionBallConnection(Level.destructionBalls.get(0));

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
