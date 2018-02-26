import android.widget.ImageView;

public class Bonus {

    private int originX;
    private int originY;
    private int length;
    private ImageView image;

    Bonus(int oX, int oY, int l, ImageView img) {
        originX = oX;
        originY = oY;
        length = l;
        image = img;
    }

    public int getOriginX() { return originX; }

    public int getOriginY() {
        return originY;
    }

    public int getLength() {
        return length;
    }

    public ImageView getImage() {
        return image;
    }
}
