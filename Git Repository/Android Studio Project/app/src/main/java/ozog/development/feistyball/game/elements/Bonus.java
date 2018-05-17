package ozog.development.feistyball.game.elements;

import android.view.View;
import android.widget.ImageView;

import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Bonus
{
    public static ImageView bonus;
    public static int bonusWidth;
    public static int bonusRadius;

    public static void addBonus() {

        bonusWidth = (int)(Layout.screenWidth * 0.18);
        bonusRadius = (int)(bonusWidth * 0.5);

        bonus = new ImageView(MainMenu.game);
        bonus.setImageDrawable(Drawables.bonus6SecondImage);

        MainGame.rl.addView(bonus);

        bonus.getLayoutParams().height = bonusWidth;
        bonus.getLayoutParams().width = bonusWidth;
    }

    public static void removeBonus() {

        bonus.setVisibility(View.INVISIBLE);
        bonus = null;
    }

    public static void setBonusPosition(float x, float y) {

        addBonus();

        bonus.setX(x);
        bonus.setY(y);
    }
}
