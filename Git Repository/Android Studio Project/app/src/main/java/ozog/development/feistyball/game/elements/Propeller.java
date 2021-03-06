package ozog.development.feistyball.game.elements;

import android.widget.ImageView;

import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.functionality.Level;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Propeller {

    private boolean switchedOn;
    private int originX;
    private int originY;
    private ImageView image;
    public static int propellerLength;

    static {
        propellerLength = (int)(Layout.screenWidth * 0.18);
    }

    Propeller(int oX, int oY, ImageView img) {
        originX = oX;
        originY = oY;
        switchedOn = false;
        image = img;
    }

    public int getCenterX() {
        return (int)(originX + propellerLength * 0.5);
    }

    public int getCenterY() { return (int)(originY + propellerLength * 0.5); }

    public void switchOn() {
        switchedOn = true;
    }

    public boolean switchedOn() { return switchedOn;}

    public ImageView getImage() {
        return image;
    }


    public static boolean allPropellersSwitchedOn() {

        for (Propeller p: Level.propellers) {
            if (!p.switchedOn()) return false;
        }
        return true;
    }


    public static void addPropeller(int x, int y) {

        ImageView propeller = new ImageView(MainMenu.game);

        propeller.setImageDrawable(Drawables.propellerImage);

        MainGame.rl.addView(propeller);

        propeller.getLayoutParams().width = propellerLength;
        propeller.getLayoutParams().height = propellerLength;

        propeller.setX(x);
        propeller.setY(y);

        Level.propellers.add(new Propeller(x, y, propeller));
    }
}
