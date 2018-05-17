package ozog.development.feistyball.game.elements;

import android.widget.ImageView;
import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Destination {

    public static ImageView destination;
    public static int destinationLength;

    public static void addDestination() {

        destinationLength = (int)(Layout.screenWidth * 0.295);

        destination = new ImageView(MainMenu.game);
        destination.setImageDrawable(Drawables.destinationImageGrey);

        MainGame.rl.addView(destination);

        destination.getLayoutParams().height = destinationLength;
        destination.getLayoutParams().width = destinationLength;
    }

    public static void setDestinationPosition(float x, float y) {

        // Reset the destination image (set grey one)
        Destination.destination.setImageDrawable(Drawables.destinationImageGrey);

        Destination.destination.setX(x);
        Destination.destination.setY(y);
    }
}
