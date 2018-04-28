package ozog.development.feistyball;

import android.widget.ImageView;
import android.widget.Toast;

public class DestructionBall {

    private boolean switchedOn;
    private int ballCenterX;
    private int ballCenterY;
    private int originY;
    // 1 means going up; -1 means going down
    private int movementWay;
    private ImageView image;

    private static int width;
    private static int height;
    private static int speed;

    static {
        width = (int)(Layout.screenHeight * 0.125);
        height = (int) (width * 2.25);
        speed = 5;
    }

    DestructionBall (int oX, int oY, float movementState) {

        ImageView destructionBall = new ImageView(MainMenu.game);

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

        // Initial position
        this.image.setY(this.image.getY() -  movementState * height);

        ballCenterX = (int) (this.image.getX() + 0.5 * width);
        ballCenterY = (int) (this.image.getY() + height - 0.5 * width);
    }

    public static void updateMovementState() {

        for (DestructionBall d: Level.destructionBalls) {
            if (d.switchedOn) {

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

    public ImageView getImage() {
        return image;
    }

    public void rotate180() {
        image.setRotation(180);
        ballCenterY = (int) (this.image.getY() + 0.5 * width);
    }

    public boolean isSwitchedOn() {
        if ( switchedOn )
            return true;
        else
            return false;
    }

    public void destructionBallHit() {

        // Stop this destruction ball
        switchOff();

        Time.gameTime += 10 * 100;
        Time.levelTime += 10 * 100;
        Toast.makeText(MainMenu.game, "Penalty: +" + 10 + " seconds!", Toast.LENGTH_SHORT).show();
    }

    public void switchOff() {
        switchedOn = false;
    }

    public void toggle() {
        if ( switchedOn )
            switchedOn = false;
        else
            switchedOn = true;
    }

    public static void setSpeed(int s) { speed = s; }

    public static void addDestructionBall (int x, int y, float movementState) {

        Level.destructionBalls.add(new DestructionBall(x, y, movementState));
    }
}
