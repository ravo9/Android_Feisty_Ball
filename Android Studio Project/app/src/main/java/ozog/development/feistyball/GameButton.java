package ozog.development.feistyball;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameButton {

    private boolean pressed;
    private ArrayList<DestructionBall> connectedDestructionBalls;
    private ArrayList<Obstacle> connectedMovingObstacles;
    private ImageView image;

    private static int width;
    private static int heightPressed;
    private static int heightUnpressed;

    static {
        width = (int)(Layout.screenWidth * 0.1);
        heightPressed = (int) (width * 0.27);
        heightUnpressed = (int) (width * 0.3);
    }

    GameButton (Context game, int oX, int oY) {

        ImageView gameButton = new ImageView(game);

        gameButton.setMinimumWidth(width);
        gameButton.setMinimumHeight(heightUnpressed);

        gameButton.setImageDrawable(Drawables.gameButtonUnpressed);

        gameButton.setX(oX);
        gameButton.setY(oY);

        MainGame.rl.addView(gameButton);

        this.pressed = false;
        this.connectedDestructionBalls = new ArrayList<>();
        this.connectedMovingObstacles = new ArrayList<>();
        this.image = gameButton;

        Level.obstacles.add(new Obstacle(oX, oY, width, heightPressed, null));
    }

    public boolean isPressed() {
        if ( pressed )
            return true;
        else
            return false;
    }

    public void toggle() {
        if (isPressed()) {
            pressed = false;
            image.setImageDrawable(Drawables.gameButtonUnpressed);

            // Toggle connected destruction balls
            for (DestructionBall d: connectedDestructionBalls)
                d.toggle();

            // Toggle connected moving obstacles
            for (Obstacle o: connectedMovingObstacles)
                o.toggleMovement(1);
        }
        else {
            pressed = true;
            image.setImageDrawable(Drawables.gameButtonPressed);

            // Toggle connected destruction balls
            for (DestructionBall d: connectedDestructionBalls)
                d.toggle();

            // Toggle connected moving obstacles
            for (Obstacle o: connectedMovingObstacles)
                o.toggleMovement(1);
        }
    }

    public int getButtonX() {
        return (int)(image.getX());
    }

    public int getButtonY() {
        return (int)(image.getY());
    }

    public ImageView getImage() {
        return image;
    }

    public static int getButtonWidth() {
        return (int)(width);
    }

    public static int getButtonHeight() {
        return (int)(heightPressed);
    }

    public void addButtonDestructionBallConnection(DestructionBall d) {
        this.connectedDestructionBalls.add(d);
    }

    public void addButtonMovingObstacleConnection(Obstacle o) {
        this.connectedMovingObstacles.add(o);
    }

    public static void addGameButton (Context game, int x, int y) {

        Level.gameButtons.add(new GameButton(game, x, y));
    }
}
