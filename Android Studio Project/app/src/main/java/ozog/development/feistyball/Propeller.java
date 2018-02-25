package ozog.development.feistyball;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;

public class Propeller {

    private boolean switchedOn;
    private int originX;
    private int originY;
    private int length;
    private ImageView image;

    Propeller(int oX, int oY, int l, ImageView img) {
        originX = oX;
        originY = oY;
        length = l;
        switchedOn = false;
        image = img;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }

    public int getCenterX() {
        return (int)(originX + length * 0.5);
    }

    public int getCenterY() {
        return (int)(originY + length * 0.5);
    }

    public void switchOn() {
        switchedOn = true;
    }

    public boolean switchedOn() {
       if (switchedOn)
           return true;
       else
           return false;
    }

    public ImageView getImage() {
        return image;
    }
}
