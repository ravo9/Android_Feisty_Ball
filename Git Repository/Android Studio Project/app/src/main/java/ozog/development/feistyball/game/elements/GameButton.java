package ozog.development.feistyball.game.elements;

import android.widget.ImageView;
import java.util.ArrayList;

import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.functionality.Level;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class GameButton {

    private boolean isPressed;
    private ArrayList<DestructionBall> connectedDestructionBalls;
    private ArrayList<Obstacle> connectedMovingObstacles;
    private ImageView gameButton;

    private static int width;
    private static int heightPressed;
    private static int heightUnpressed;

    static {
        width = (int)(Layout.screenWidth * 0.12);
        heightPressed = (int) (width * 0.27);
        heightUnpressed = (int) (width * 0.3);
    }

    GameButton (int oX, int oY) {

        ImageView gameButton = new ImageView(MainMenu.game);

        gameButton.setImageDrawable(Drawables.gameButtonUnpressed);

        gameButton.setX(oX);
        gameButton.setY(oY);

        MainGame.rl.addView(gameButton);

        gameButton.getLayoutParams().width = width;
        gameButton.getLayoutParams().height = heightUnpressed;

        this.isPressed = false;
        this.connectedDestructionBalls = new ArrayList<>();
        this.connectedMovingObstacles = new ArrayList<>();
        this.gameButton = gameButton;

        Level.obstacles.add(new Obstacle(oX, oY, width, heightPressed, null));
    }

    public boolean isisPressed() {
        if ( isPressed ) return true;
        else return false;
    }

    public void toggle() {

        if (isisPressed()) {
            isPressed = false;
            gameButton.setImageDrawable(Drawables.gameButtonUnpressed);
            gameButton.getLayoutParams().height = heightUnpressed;
        }
        else {
            isPressed = true;
            gameButton.setImageDrawable(Drawables.gameButtonPressed);
            gameButton.getLayoutParams().height = heightPressed;
        }

        // Toggle connected destruction balls
        for (DestructionBall d: connectedDestructionBalls)
            d.toggle();

        // Toggle connected moving obstacles
        for (Obstacle o: connectedMovingObstacles)
            o.toggleMovement(1);
    }

    public int getButtonX() {
        return (int)(gameButton.getX());
    }

    public int getButtonY() {
        return (int)(gameButton.getY());
    }

    public ImageView getImage() {
        return gameButton;
    }

    public int getButtonWidth() {
        return (int)(width);
    }

    public int getButtonHeight() {
        if (isPressed == true) return (int)(heightPressed);
        else return (int)(heightUnpressed);
    }

    public void addButtonDestructionBallConnection(DestructionBall d) {
        this.connectedDestructionBalls.add(d);
    }

    public void addButtonMovingObstacleConnection(Obstacle o) {
        this.connectedMovingObstacles.add(o);
    }

    public static void addGameButton (int x, int y) {
        Level.gameButtons.add(new GameButton(x, y));
    }
}
