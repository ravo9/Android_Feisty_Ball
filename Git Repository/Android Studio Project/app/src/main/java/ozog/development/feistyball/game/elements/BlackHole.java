package ozog.development.feistyball.game.elements;

import android.widget.ImageView;

import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class BlackHole {

    public static ImageView blackHoleA;
    public static ImageView blackHoleB;
    public static int blackHoleWidth;
    public static int blackHoleRadius;

    static {
        blackHoleWidth = (int)(Layout.screenWidth * 0.205);
        blackHoleRadius = (int)(blackHoleWidth * 0.5);
    }

    public static void addBlackHoles() {

        blackHoleA = new ImageView(MainMenu.game);
        blackHoleA.setImageDrawable(Drawables.blackHoleImage);

        blackHoleB = new ImageView(MainMenu.game);
        blackHoleB.setImageDrawable(Drawables.blackHoleImage);

        MainGame.rl.addView(blackHoleA);
        blackHoleA.getLayoutParams().height = blackHoleWidth;
        blackHoleA.getLayoutParams().width = blackHoleWidth;

        MainGame.rl.addView(blackHoleB);
        blackHoleB.getLayoutParams().height = blackHoleWidth;
        blackHoleB.getLayoutParams().width = blackHoleWidth;
    }

    public static void setBlackHoles(float Ax, float Ay, float Bx, float By) {

        addBlackHoles();

        blackHoleA.setX(Ax);
        blackHoleA.setY(Ay);

        blackHoleB.setX(Bx);
        blackHoleB.setY(By);
    }
}
