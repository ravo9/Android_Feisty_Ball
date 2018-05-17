package ozog.development.feistyball.game.elements;

import android.widget.ImageView;

import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.functionality.Level;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Obstacle {

    private int originX;
    private int originY;
    private int width;
    private int height;
    private boolean movementSwitchedOn;
    private ImageView image;

    public static final int longerBrickLength;
    public static final int shorterBrickLenght;

    static {
        longerBrickLength = (int)(Layout.screenWidth * 0.22);
        shorterBrickLenght = (int)(longerBrickLength * 0.25);
    }

    Obstacle(int oX, int oY, int w, int h, ImageView img) {
        originX = oX;
        originY = oY;
        width = w;
        height = h;
        movementSwitchedOn = false;
        image = img;
    }

    public int getOriginX() { return originX; }

    public int getOriginY() {
        return originY;
    }

    public int getWidth() { return width; }

    public int getHeight() {
        return height;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView img) { image = img; }

    public static void addRedBrick(int x, int y, int orientation) {

        ImageView brick = new ImageView(MainMenu.game);

        if (orientation == 0) {
            // Horizontal brick.
            brick.setImageDrawable(Drawables.brickImageHorizontal);
            MainGame.rl.addView(brick);
            brick.getLayoutParams().width = longerBrickLength;
            brick.getLayoutParams().height = shorterBrickLenght;
            Level.obstacles.add(new Obstacle(x, y, longerBrickLength, shorterBrickLenght, brick));
        }
        else if (orientation == 1) {
            // Vertical brick.
            brick.setImageDrawable(Drawables.brickImageVertical);
            MainGame.rl.addView(brick);
            brick.getLayoutParams().width = shorterBrickLenght;
            brick.getLayoutParams().height = longerBrickLength;
            Level.obstacles.add(new Obstacle(x, y, shorterBrickLenght, longerBrickLength, brick));
        }

        brick.setX(x);
        brick.setY(y);
    }

    public static void addGreyBrick(int x, int y, int orientation) {

        ImageView brick = new ImageView(MainMenu.game);

        if (orientation == 0) {
            // Horizontal brick.
            brick.setImageDrawable(Drawables.greyBrickImageHorizontal);
            MainGame.rl.addView(brick);
            brick.getLayoutParams().width = longerBrickLength;
            brick.getLayoutParams().height = shorterBrickLenght;
            Level.obstacles.add(new Obstacle(x, y, longerBrickLength, shorterBrickLenght, brick));
        }
        else if (orientation == 1) {
            // Vertical brick.
            brick.setImageDrawable(Drawables.brickImageVertical);
            MainGame.rl.addView(brick);
            brick.getLayoutParams().width = shorterBrickLenght;
            brick.getLayoutParams().height = longerBrickLength;
            Level.obstacles.add(new Obstacle(x, y, shorterBrickLenght, longerBrickLength, brick));
        }

        brick.setX(x);
        brick.setY(y);
    }

    public static void addWalls() {
        Level.obstacles.add(new Obstacle(0, -1, Layout.screenWidth, 1, null));
        Level.obstacles.add(new Obstacle(Layout.screenWidth - 1, 0, 1, Layout.screenHeight, null));
        Level.obstacles.add(new Obstacle(0, Layout.screenHeight, Layout.screenWidth, 1, null));
        Level.obstacles.add(new Obstacle(-1, 0, 1, Layout.screenHeight, null));
    }

    public void toggleMovement(int directionX) {
        if ( movementSwitchedOn ) {
            movementSwitchedOn = false;
            float distance = (float)(Layout.screenWidth * 0.2);
            this.image.animate().translationX(this.image.getX() + distance).setDuration(2000);
        }
        else {
            movementSwitchedOn = true;
            float distance = (float)(Layout.screenWidth * 0.2);
            this.image.animate().translationX(this.image.getX() - distance).setDuration(2000);
            // A magic trick - the brick doesn't move in real. Its width and height is set to 0 and only the image moves.
            // In this moment the proper width is not returnet when the button is pressed again.
            this.width = 0;
            this.height = 0;
        }
    }
}
