package ozog.development.feistyball.functionality;

import android.widget.Toast;

import ozog.development.feistyball.game.elements.Bonus;
import ozog.development.feistyball.game.elements.DestructionBall;
import ozog.development.feistyball.game.elements.GameButton;
import ozog.development.feistyball.game.elements.Obstacle;
import ozog.development.feistyball.game.elements.Propeller;
import ozog.development.feistyball.game.elements.Ball;
import ozog.development.feistyball.game.elements.Destination;
import ozog.development.feistyball.game.elements.BlackHole;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Mechanics {

    private static float currentX , currentY;
    private static float newX, newY;
    private static int destinationCenterPointX, destinationCenterPointY;
    private static float ballCenterPointX, ballCenterPointY;
    private static int blackHoleFreezer;
    private static int buttonFreezer;
    private static float ballRadius;
    private static float blackHoleRadius;

    static {
        blackHoleFreezer = 0;
        buttonFreezer = 0;
        ballRadius = Ball.ballRadius;
        blackHoleRadius = BlackHole.blackHoleRadius;
    }

    public static void update() {

        currentX = Ball.ball.getX();
        currentY = Ball.ball.getY();

        destinationCenterPointX = (int) (Destination.destination.getX() + Destination.destinationLength * 0.5);
        destinationCenterPointY = (int) (Destination.destination.getY() + Destination.destinationLength * 0.5);

        newX = Ball.ball.getX() + Sensors.orientationValues[2] * 7;
        newY = Ball.ball.getY() - Sensors.orientationValues[1] * 7;

        ballCenterPointX = currentX + ballRadius;
        ballCenterPointY = currentY + ballRadius;

        updatePropellersRotation();
        updateDestructionBallsMovement();
        isObstacleThere();
        isPropellerThere();
        isBonusThere();
        isBlackHoleThere();
        isDestructionBallThere();
        isGameButtonThere();
        destinationUnlockCheck();
    }

    // Check if there is any obstacle (e.g. brick)
    public static void isObstacleThere() {

        if (!isObstacleAreaThere(newX, newY)) {
            Ball.ball.setX( newX );
            Ball.ball.setY( newY );
        } else if (!isObstacleAreaThere(newX, currentY)) {
            Ball.ball.setX( newX );
        } else if (!isObstacleAreaThere(currentX, newY)) {
            Ball.ball.setY(newY);
        }
    }

    public static boolean isObstacleAreaThere(float x, float y) {

        // x and y are left top corner of the ball image
        for (Obstacle o: Level.obstacles) {
            if (collides(x + ballRadius, y + ballRadius, o.getOriginX(), o.getOriginY(), o.getWidth(), o.getHeight()))
                return true;
        }

        return false;
    }

    // 'The coding daddy' function
    private static boolean collides(float ballX, float ballY, float obstacleX, float obstacleY, float obstacleWidth, float obstacleHeight) {

        float closestX = clamp(ballX, obstacleX, obstacleX + obstacleWidth);
        float closestY = clamp(ballY, obstacleY, obstacleY + obstacleHeight);

        float distanceX = ballX - closestX;
        float distanceY = ballY - closestY;

        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(Ball.ballRadius, 2);
    }

    // 'The coding daddy' function
    public static float clamp(float value, float min, float max) {

        float x = value;
        if (x < min)
            x = min;
        else if (x > max)
            x = max;
        return x;
    }

    // Check if there is a propeller
    public static void isPropellerThere() {

        int reactionRange = (int)(Propeller.propellerLength * 0.5);

        for (Propeller p: Level.propellers) {
            if (ballCenterPointX > p.getCenterX() - reactionRange && ballCenterPointX < p.getCenterX() + reactionRange) {
                if (ballCenterPointY > p.getCenterY() - reactionRange && ballCenterPointY < p.getCenterY() + reactionRange) {
                    if (!p.switchedOn())
                        p.switchOn();
                }
            }
        }
    }

    // Check if there is a bonus
    public static void isBonusThere() {

        if (Bonus.bonus != null) {
            if (ballCenterPointX > Bonus.bonus.getX() + Bonus.bonusRadius * 0.5 && ballCenterPointX < Bonus.bonus.getX() + Bonus.bonusRadius * 1.5) {
                if (ballCenterPointY > Bonus.bonus.getY() + Bonus.bonusRadius * 0.5 && ballCenterPointY < Bonus.bonus.getY() + Bonus.bonusRadius * 1.5)
                    bonusAchieved(6);
            }
        }
    }

    // Check if there is a black hole
    public static void isBlackHoleThere() {

        if (blackHoleFreezer <= 0 && BlackHole.blackHoleA != null) {
            if (ballCenterPointX > BlackHole.blackHoleA.getX() + blackHoleRadius * 0.3 && ballCenterPointX < BlackHole.blackHoleA.getX() + blackHoleRadius * 1.7) {
                if (ballCenterPointY > BlackHole.blackHoleA.getY() + blackHoleRadius * 0.3 && ballCenterPointY < BlackHole.blackHoleA.getY() + blackHoleRadius * 1.7) {

                    // Why doesn't it work, when the 0.5 is changed for 0.4?

                    Ball.ball.setX((float)(BlackHole.blackHoleB.getX() + blackHoleRadius * 0.5));
                    Ball.ball.setY((float)(BlackHole.blackHoleB.getY() + blackHoleRadius * 0.5));
                    // Freeze black hole per 3 seconds
                    // 2 x as this timer updates every 5 centiseconds instead of 10
                    blackHoleFreezer = 2 * 180;
                }
            }

            if (ballCenterPointX > BlackHole.blackHoleB.getX() + blackHoleRadius * 0.3 && ballCenterPointX < BlackHole.blackHoleB.getX() + blackHoleRadius * 1.7) {
                if (ballCenterPointY > BlackHole.blackHoleB.getY() + blackHoleRadius * 0.3 && ballCenterPointY < BlackHole.blackHoleB.getY() + blackHoleRadius * 1.7) {
                    Ball.ball.setX((float)(BlackHole.blackHoleA.getX() + blackHoleRadius * 0.5));
                    Ball.ball.setY((float)(BlackHole.blackHoleA.getY() + blackHoleRadius * 0.5));
                    // Freeze black hole per 3 seconds
                    // 2 x as this timer updates every 5 centiseconds instead of 10
                    blackHoleFreezer = 2 * 180;
                }
            }
        }
        else
            blackHoleFreezer--;
    }

    // Check if there is a destruction ball
    public static void isDestructionBallThere() {

        float reactionRange = (float)(DestructionBall.getBlackBallRadius() * 1.25);

        for (DestructionBall d: Level.destructionBalls) {
            if (ballCenterPointX > d.getBlackBallCenterX() - reactionRange && ballCenterPointX < d.getBlackBallCenterX() + reactionRange) {
                if (ballCenterPointY > d.getBlackBallCenterY() - reactionRange && ballCenterPointY < d.getBlackBallCenterY() + reactionRange) {
                    if (d.isSwitchedOn())
                        d.destructionBallHit();
                }
            }
        }
    }

    // Check if there is a game button
    public static void isGameButtonThere() {

        if (buttonFreezer <= 0) {

            float ballCenterX = Ball.ball.getX() + ballRadius;
            float ballCenterY = Ball.ball.getY() + ballRadius;

            for (GameButton g: Level.gameButtons) {
                if (collides(ballCenterX, ballCenterY, g.getButtonX(), g.getButtonY(),
                        g.getButtonWidth(), (float)(g.getButtonHeight() * 1.02))) {
                    g.toggle();
                    // 2 x as this timer updates every 5 centiseconds instead of 10
                    buttonFreezer = 2 * 80;
                }
            }
        }
        else
            buttonFreezer--;
    }

    // Update propellers rotation
    public static void updatePropellersRotation() {

        for (Propeller p: Level.propellers) {
            if (p.switchedOn()) {
                float currentPosition = p.getImage().getRotation();
                p.getImage().setRotation(currentPosition + 1);
            }
        }

        // Update also bonus elements rotation here
        if (Bonus.bonus != null)
            Bonus.bonus.setRotation(Bonus.bonus.getRotation() + (float)0.7);
    }

    // Check if all propellers are switched on and the destination can be unlocked
    public static void destinationUnlockCheck() {

        if (Propeller.allPropellersSwitchedOn()) {
            Destination.destination.setImageDrawable(Drawables.destinationImage);
            Destination.destination.setRotation(Destination.destination.getRotation() + (float)0.5);
            // Check if the destination has been achieved.
            if (ballCenterPointX > destinationCenterPointX - Destination.destinationLength * 0.2 && ballCenterPointX < destinationCenterPointX + Destination.destinationLength * 0.2) {
                if (ballCenterPointY > destinationCenterPointY - Destination.destinationLength * 0.2 && ballCenterPointY < destinationCenterPointY + Destination.destinationLength * 0.2)
                    Level.levelCompleted(MainGame.rl);
            }
        }
    }

    public static void bonusAchieved (int value) {

        Time.gameTime -= value * 100;
        Time.levelTime -= value * 100;
        Toast.makeText(MainMenu.game, "-" + value + " seconds!", Toast.LENGTH_SHORT).show();

        Bonus.removeBonus();
    }

    public static void updateDestructionBallsMovement() { DestructionBall.updateMovementState(); }
}
