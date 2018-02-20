package ozog.development.feistyball;

import android.graphics.Point;
import android.widget.ImageView;




public class Level {

    private static ImageView ball;

    private int levelNumber;
    private Point startingPosition;
    private Point destinationPosition;

    public Level (int levelNumber, Point startingPosition, Point destinationPosition) {
        this.levelNumber = levelNumber;
        this.startingPosition = startingPosition;
        this.destinationPosition = destinationPosition;
    }

    public void setLevel(int levelNumber) {

    }


}
