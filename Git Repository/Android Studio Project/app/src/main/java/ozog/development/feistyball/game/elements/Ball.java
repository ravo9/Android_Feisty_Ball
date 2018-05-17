package ozog.development.feistyball.game.elements;

import android.view.View;
import android.widget.ImageView;
import ozog.development.feistyball.functionality.Drawables;
import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.windows.MainGame;
import ozog.development.feistyball.windows.MainMenu;

public class Ball {

    public static ImageView ball;
    public static int ballWidth;
    public static int ballRadius;

    public static void addBall() {

        ballWidth = (int)(Layout.screenWidth * 0.165);
        ballRadius = (int)(ballWidth * 0.5);

        ball = new ImageView(MainMenu.game);
        ball.setImageDrawable(Drawables.ball);

        MainGame.rl.addView(ball);

        ball.getLayoutParams().height = ballWidth;
        ball.getLayoutParams().width = ballWidth;
    }

    public static void setBallPosition(float x, float y) {

        //addBall();

        ball.setX(x);
        ball.setY(y);
    }
}
