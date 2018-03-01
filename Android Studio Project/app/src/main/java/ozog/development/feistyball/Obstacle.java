package ozog.development.feistyball;

import android.content.Context;
import android.widget.ImageView;

public class Obstacle {

    private int originX;
    private int originY;
    private int width;
    private int height;
    private ImageView image;

    Obstacle(int oX, int oY, int w, int h, ImageView img) {
        originX = oX;
        originY = oY;
        width = w;
        height = h;
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


    public static void addBrick(Context game, int x, int y, int orientation) {

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

}
