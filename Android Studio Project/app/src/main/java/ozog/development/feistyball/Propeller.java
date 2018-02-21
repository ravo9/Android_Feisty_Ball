package ozog.development.feistyball;

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

        MainGame.propellers.add(this);
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
        // Animation code taken from StackOverflow
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        image.setAnimation(rotate);

        switchedOn = true;
    }

    public boolean switchedOn() {
       if (switchedOn == true)
           return true;
       else
           return false;
    }

    public ImageView getImage() {
        return image;
    }
}
