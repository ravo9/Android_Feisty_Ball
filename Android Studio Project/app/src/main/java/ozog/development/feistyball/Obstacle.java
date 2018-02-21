package ozog.development.feistyball;

public class Obstacle {

    private int originX;
    private int originY;
    private int width;
    private int height;

    Obstacle(int oX, int oY, int w, int h) {
        originX = oX;
        originY = oY;
        width = w;
        height = h;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
