package ozog.development.feistyball;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

public class DestructionBall {

    private boolean switchedOn;
    private int ballCenterX;
    private int ballCenterY;
    private int originY;
    // 1 means going up; -1 means going down
    private int movementWay;
    // 1 means open, 0 - hidden
    private float movementState;
    private ImageView image;

    private static int width;
    private static int height;
    private static int speed;

    static {
        width = (int)(Layout.screenHeight * 0.125);
        height = (int) (width * 2.25);
        speed = 5;
    }

    DestructionBall (Context game, int oX, int oY, float movementState) {

        ImageView destructionBall = new ImageView(game);

        destructionBall.setMinimumWidth(width);
        destructionBall.setMinimumHeight(height);
        destructionBall.setImageDrawable(Drawables.destructionBall);
        MainGame.rl.addView(destructionBall);

        originY = oY;

        destructionBall.setX(oX);
        destructionBall.setY(oY);

        switchedOn = true;

        if (movementState == 1)
            movementWay = 1;
        else
            movementWay = -1;


        this.image = destructionBall;
        this.movementState = movementState;

        // Initial position
        this.image.setY(this.image.getY() -  movementState * height);

        ballCenterX = (int) (this.image.getX() + 0.5 * width);
        ballCenterY = (int) (this.image.getY() + height - 0.5 * width);
    }

    public static void updateMovementState() {

        for (DestructionBall d: Level.destructionBalls) {
            if (d.switchedOn == true) {

                if ( d.movementWay == -1 ) {
                    if (d.image.getY() < d.originY) {
                        d.image.setY(d.image.getY() + speed);
                        d.ballCenterY += speed;
                    }
                    else
                        d.movementWay *= -1;
                }
                else if ( d.movementWay == 1) {
                    if (d.image.getY() > d.originY - height * 0.65) {
                        d.image.setY(d.image.getY() - speed);
                        d.ballCenterY -= speed;
                    }
                    else
                        d.movementWay *= -1;
                }
            }
        }
    }

    public int getBlackBallCenterX() {
        return ballCenterX;
    }

    public int getBlackBallCenterY() {
        return ballCenterY;
    }

    public static float getBlackBallRadius() {
        return (float) (width * 0.5);
    }

    public boolean isSwitchedOn() {
        if ( switchedOn )
            return true;
        else
            return false;
    }

    public void switchOff() {
        switchedOn = false;
    }

    public static void addDestructionBall (Context game, int x, int y, float movementState) {

        Level.destructionBalls.add(new DestructionBall(game, x, y, movementState));
    }
}
