package ozog.development.feistyball;

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
}
