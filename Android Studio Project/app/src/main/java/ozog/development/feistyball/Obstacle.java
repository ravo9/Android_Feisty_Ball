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
            brickWidth = (int)(MainGame.screenWidth * 0.2);
            brickHeight = (int)(brickWidth * 0.25);
        }
        else if (orientation == 1) {
            brick.setImageDrawable(Drawables.brickImageVertical);
            brickHeight = (int)(MainGame.screenWidth * 0.2);
            brickWidth = (int)(brickHeight * 0.25);
        }

        brick.setMinimumHeight(brickHeight);
        brick.setMinimumWidth(brickWidth);

        MainGame.rl.addView(brick);

        brick.setX(x);
        brick.setY(y);

        MainGame.obstacles.add(new Obstacle(x, y, brickWidth, brickHeight, brick));
    }

    public static void addWalls() {
        MainGame.obstacles.add(new Obstacle(0, -1, MainGame.screenWidth, 1, null));
        MainGame.obstacles.add(new Obstacle(MainGame.screenWidth - 1, 0, 1, MainGame.screenHeight, null));
        MainGame.obstacles.add(new Obstacle(0, MainGame.screenHeight, MainGame.screenWidth, 1, null));
        MainGame.obstacles.add(new Obstacle(-1, 0, 1, MainGame.screenHeight, null));
    }

}
