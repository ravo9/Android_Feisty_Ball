package ozog.development.feistyball;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class Drawables {

    public static Drawable brickImageHorizontal;
    public static Drawable brickImageVertical;
    public static Drawable propellerImage;
    public static Drawable destinationImage;
    public static Drawable destinationImageGrey;
    public static Drawable blackHoleImage;
    public static Drawable bonus6SecondImage;

    public static void uploadImages(Context game) {

        try {
            InputStream stream = game.getAssets().open("brick_h.png");
            brickImageHorizontal = Drawable.createFromStream(stream, null);
            stream = game.getAssets().open("brick_v.png");
            brickImageVertical = Drawable.createFromStream(stream, null);
            stream = game.getAssets().open("propeller.png");
            propellerImage = Drawable.createFromStream(stream, null);
            stream = game.getAssets().open("destination02.png");
            destinationImage = Drawable.createFromStream(stream, null);
            stream = game.getAssets().open("destination02grey.png");
            destinationImageGrey = Drawable.createFromStream(stream, null);
            stream = game.getAssets().open("bonus_6_sec.png");
            bonus6SecondImage = Drawable.createFromStream(stream, null);
            stream = game.getAssets().open("black_hole.png");
            blackHoleImage = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



