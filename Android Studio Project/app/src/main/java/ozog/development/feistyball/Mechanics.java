package ozog.development.feistyball;

import android.view.View;
import android.widget.Toast;

public class Mechanics {

    private static float currentX , currentY;
    private static float newX, newY;
    private static float ballCenterPointX, ballCenterPointY;
    private static int blackHoleFreezer;
    private static boolean isBonus01Achieved;

    static {
        blackHoleFreezer = 0;
        isBonus01Achieved = false;
    }

    public static void update() {

        currentX = Layout.ball.getX();
        currentY = Layout.ball.getY();

        newX = Layout.ball.getX() + Sensors.orientationValues[2] * 10;
        newY = Layout.ball.getY() - Sensors.orientationValues[1] * 10;

        ballCenterPointX = currentX + 90;
        ballCenterPointY = currentY + 90;

        isObstacleThere();
        isPropellerThere();
        isBonusThere();
        isBlackHoleThere();
        updatePropellersRotation();
        destinationUnlockCheck();

    }

    // Check if there is any obstacle (e.g. brick)
    public static void isObstacleThere() {

        if (isObstacleAreaThere(newX, newY) == false) {
            Layout.ball.setX( newX );
            Layout.ball.setY( newY );
        } else if (isObstacleAreaThere(newX, currentY) == false) {
            Layout.ball.setX( newX );
        } else if (isObstacleAreaThere(currentX, newY) == false) {
            Layout.ball.setY(newY);
        }
    }

    // Check if there is a propeller
    public static void isPropellerThere() {
        for (Propeller p: Level.propellers) {
            if (ballCenterPointX > p.getCenterX() - 50 && ballCenterPointX < p.getCenterX() + 50) {
                if (ballCenterPointY > p.getCenterY() - 50 && ballCenterPointY < p.getCenterY() + 50) {
                    if (!p.switchedOn())
                        p.switchOn();
                }
            }
        }
    }

    // Check if there is a bonus
    public static void isBonusThere() {

        if (ballCenterPointX > Layout.bonus01.getX() + 100 - 50 && ballCenterPointX < Layout.bonus01.getX() + 100 + 50) {
            if (ballCenterPointY > Layout.bonus01.getY() + 100 - 50 && ballCenterPointY < Layout.bonus01.getY() + 100 + 50) {
                if (!isBonus01Achieved) {
                    bonusAchieved(6);
                    isBonus01Achieved = true;
                }
            }
        }
    }

    // Check if there is a black hole
    public static void isBlackHoleThere() {

        if (blackHoleFreezer == 0) {
            if (ballCenterPointX > Layout.blackHoleA.getX() + 120 - 50 && ballCenterPointX < Layout.blackHoleA.getX() + 120 + 50) {
                if (ballCenterPointY > Layout.blackHoleA.getY() + 120 - 50 && ballCenterPointY < Layout.blackHoleA.getY() + 120 + 50) {
                    Layout.ball.setX(Layout.blackHoleB.getX() + 30);
                    Layout.ball.setY(Layout.blackHoleB.getY() + 30);
                    // Freeze black hole per 3 seconds
                    blackHoleFreezer = 300;
                }
            }

            if (ballCenterPointX > Layout.blackHoleB.getX() + 120 - 50 && ballCenterPointX < Layout.blackHoleB.getX() + 120 + 50) {
                if (ballCenterPointY > Layout.blackHoleB.getY() + 120 - 50 && ballCenterPointY < Layout.blackHoleB.getY() + 120 + 50) {
                    Layout.ball.setX(Layout.blackHoleA.getX() + 30);
                    Layout.ball.setY(Layout.blackHoleA.getY() + 30);
                    // Freeze black hole per 3 seconds
                    blackHoleFreezer = 300;
                }
            }
        }
        else
            blackHoleFreezer--;
    }

    public static boolean isObstacleAreaThere(float x, float y) {

        // x and y are left top corner of the ball image
        for (Obstacle o: Level.obstacles) {
            if (collides(x + 100, y + 100, o))
                return true;
        }

        return false;
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
        Layout.bonus01.setRotation(Layout.bonus01.getRotation() + (float)0.7);
    }

    // Check if all propellers are switched on and the destination can be unlocked
    public static void destinationUnlockCheck() {

        boolean allSwitched = true;
        for (Propeller p: Level.propellers) {
            if (!p.switchedOn()) {
                allSwitched = false;
            }
        }
        if (allSwitched == true) {
            Layout.destination.setImageDrawable(Drawables.destinationImage);
            Layout.destination.setRotation(Layout.destination.getRotation() + (float)0.5);
            // Check if the destination has been achieved.
            if (ballCenterPointX > Layout.destination.getX() +  175 - 50 && ballCenterPointX < Layout.destination.getX() + 175 + 50) {
                if (ballCenterPointY > Layout.destination.getY() + 175 - 50 && ballCenterPointY < Layout.destination.getY() + 175 +50) {
                    Level.levelComplited(MainGame.rl);
                }
            }
        }
    }

    // 'The coding daddy' function
    private static boolean collides(float ballX, float ballY, Obstacle o) {

        float closestX = clamp(ballX, o.getOriginX(), o.getOriginX() + o.getWidth());
        float closestY = clamp(ballY, o.getOriginY(), o.getHeight() + o.getOriginY());

        float distanceX = ballX - closestX;
        float distanceY = ballY - closestY;

        // 100 is a ball radius
        return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(100, 2);
    }

    // 'The coding daddy' function
    public static float clamp(float value, float min, float max) {
        float x = value;
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }
        return x;
    }

    public static void bonusAchieved (int value) {
        Time.gameTime -= 600;
        Time.levelTime -= 600;
        Toast.makeText(MainGame.c, "-" + value + " seconds!", Toast.LENGTH_SHORT).show();
        Layout.bonus01.setVisibility(View.INVISIBLE);
    }

    public static void resetBonus01 () {
       isBonus01Achieved = false;
    }
}
