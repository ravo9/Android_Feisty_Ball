package ozog.development.feistyball;


import android.app.Activity;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Layout {

    public static ImageView ball;
    public static ImageView destination;
    public static ImageView blackHoleA;
    public static ImageView blackHoleB;
    public static ImageView bonus01;

    public static Button btnNewGame;
    public static Button btnRestartLevel;
    public static Button btnNextLevel;
    public static Button btnScores;

    public static TextView gameTimer;
    public static TextView levelTimer;
    public static TextView finalLevelTime;
    public static TextView finalTotalTime;

    public static void loadLayoutElementsConnections(Activity game) {

        ball = game.findViewById(R.id.ball);
        destination = game.findViewById(R.id.destination);
        blackHoleA = game.findViewById(R.id.blackHoleA);
        blackHoleB = game.findViewById(R.id.blackHoleB);
        bonus01 = game.findViewById(R.id.bonus01);

        btnNewGame = game.findViewById(R.id.btnNewGame);
        btnRestartLevel = game.findViewById(R.id.btnRestartLevel);
        btnNextLevel = game.findViewById(R.id.btnNextLevel);
        btnScores = game.findViewById(R.id.btnScores);

        gameTimer = game.findViewById(R.id.gameTimer);
        levelTimer = game.findViewById(R.id.levelTimer);
        finalLevelTime = game.findViewById(R.id.finalLevelTime);
        finalTotalTime = game.findViewById(R.id.finalTotalTime);
    }
}
