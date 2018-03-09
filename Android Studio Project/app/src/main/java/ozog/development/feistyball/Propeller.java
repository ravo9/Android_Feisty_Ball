package ozog.development.feistyball;

import android.content.Context;
import android.widget.ImageView;

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

    public int getCenterY() { return (int)(originY + length * 0.5); }

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


    public static boolean allPropellersSwitchedOn() {

        for (Propeller p: Level.propellers) {
            if (!p.switchedOn()) {
               return false;
            }
        }
        return true;
    }


    public static void addPropeller(Context game, int x, int y) {

        ImageView propeller = new ImageView(game);
        propeller.setImageDrawable(Drawables.propellerImage);
        int propellerLength = (int)(Layout.screenWidth * 0.12);

        propeller.setMinimumHeight(propellerLength);
        propeller.setMinimumWidth(propellerLength);

        propeller.setX(x);
        propeller.setY(y);

        MainGame.rl.addView(propeller);
        Level.propellers.add(new Propeller(x, y, propellerLength, propeller));
    }
}
