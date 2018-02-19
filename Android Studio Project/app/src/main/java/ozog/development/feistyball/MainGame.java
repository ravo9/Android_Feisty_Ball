package ozog.development.feistyball;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame extends AppCompatActivity {

    static int centiseconds;
    static int seconds;
    static int minutes;

    static ImageView ball;

    boolean gameFinished;
    Timer timer;
    Handler handler;

    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        handler = new Handler();

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        // Animation code taken from StackOverflow
        /*RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        ball.setAnimation(rotate);*/

        //---

        ball = findViewById(R.id.ball);

        ball.setX( (float)screenWidth/ 2.0f - 50 );
        ball.setY( (float)screenHeight/ 2.0f - 50 );


    }

    public void newGame(View v) {

        centiseconds = 0;
        seconds = 0;
        minutes = 1;

        timer = new Timer();

        gameFinished = false;

        // Movement
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run () {
                        update();
                    }
                });
            }
        }, 0, 10);

    }

    public void update() {
        ;
    }
}
