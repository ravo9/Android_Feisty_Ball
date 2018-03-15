package ozog.development.feistyball;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

public class Obstacle {

    private int originX;
    private int originY;
    private int width;
    private int height;
    private boolean movementSwitchedOn;
    private ImageView image;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView img) { image = img; }

    public static void addRedBrick(Context game, int x, int y, int orientation) {

        ImageView brick = new ImageView(game);

        int brickWidth = 0;
        int brickHeight = 0;

        if (orientation == 0) {
            brick.setImageDrawable(Drawables.brickImageHorizontal);
            brickWidth = (int)(Layout.screenWidth * 0.2);
            brickHeight = (int)(brickWidth * 0.25);
        }
        else if (orientation == 1) {
            brick.setImageDrawable(Drawables.brickImageVertical);
            brickHeight = (int)(Layout.screenWidth * 0.2);
            brickWidth = (int)(brickHeight * 0.25);
        }

        brick.setMinimumHeight(brickHeight);
        brick.setMinimumWidth(brickWidth);

        //brick.setMaxHeight(brickHeight);
        //brick.setMaxWidth(brickWidth);

        MainGame.rl.addView(brick);

        brick.setX(x);
        brick.setY(y);

        Level.obstacles.add(new Obstacle(x, y, brickWidth, brickHeight, brick));
    }

    public static void addGreyBrick(Context game, int x, int y, int orientation) {

        ImageView brick = new ImageView(game);

        int brickWidth = 0;
        int brickHeight = 0;

        if (orientation == 0) {
            brick.setImageDrawable(Drawables.greyBrickImageHorizontal);
            brickWidth = (int)(Layout.screenWidth * 0.2);
            brickHeight = (int)(brickWidth * 0.25);
        }
        else if (orientation == 1) {
            brick.setImageDrawable(Drawables.brickImageVertical);
            brickHeight = (int)(Layout.screenWidth * 0.2);
            brickWidth = (int)(brickHeight * 0.25);
        }

        brick.setMinimumHeight(brickHeight);
        brick.setMinimumWidth(brickWidth);

        Log.d("Brick Width", Integer.toString(brickWidth));
        Log.d("Brick Height", Integer.toString(brickHeight));

        MainGame.rl.addView(brick);

        brick.setX(x);
        brick.setY(y);

        Level.obstacles.add(new Obstacle(x, y, brickWidth, brickHeight, brick));
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
