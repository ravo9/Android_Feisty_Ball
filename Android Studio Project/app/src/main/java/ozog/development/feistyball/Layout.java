package ozog.development.feistyball;

import android.app.Activity;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Layout {

    public static ImageView ball;
    public static ImageView destination;
    public static ImageView blackHoleA;
    public static ImageView blackHoleB;
    public static ImageView bonus01;
    public static ImageView destinationIcon;
    public static ImageView ballIcon;

    public static Button btnNewGame;
    public static Button btnRestartLevel;
    public static Button btnNextLevel;
    public static Button btnScores;
    public static Button btnAbout;

    public static TextView gameTimer;
    public static TextView levelTimer;
    public static TextView finalLevelTime;
    public static TextView finalTotalTime;

    public static int screenWidth;
    public static int screenHeight;

    public static void loadLayoutElementsConnections(Activity game) {

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        ball = game.findViewById(R.id.ball);
        destination = game.findViewById(R.id.destination);
        blackHoleA = game.findViewById(R.id.blackHoleA);
        blackHoleB = game.findViewById(R.id.blackHoleB);
        bonus01 = game.findViewById(R.id.bonus01);
        destinationIcon = game.findViewById(R.id.destinationIcon);
        ballIcon = game.findViewById(R.id.ballIcon);

        btnNewGame = game.findViewById(R.id.btnNewGame);
        btnRestartLevel = game.findViewById(R.id.btnRestartLevel);
        btnNextLevel = game.findViewById(R.id.btnNextLevel);
        btnScores = game.findViewById(R.id.btnScores);
        btnAbout = game.findViewById(R.id.btnAbout);

        gameTimer = game.findViewById(R.id.gameTimer);
        levelTimer = game.findViewById(R.id.levelTimer);
        finalLevelTime = game.findViewById(R.id.finalLevelTime);
        finalTotalTime = game.findViewById(R.id.finalTotalTime);
    }
}
