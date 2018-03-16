package ozog.development.feistyball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class MainMenu extends AppCompatActivity {

    private RotateAnimation rotate;
    public static Context game;
    public static SharedPreferences displayFeedbackStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        game = getApplicationContext();

        // Upload connections to the layout elements
        Layout.loadLayoutElementsConnections(this);

        setDestinationIconAnimation();
        setBallIconAnimation();
    }

    public void setDestinationIconAnimation() {
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(10000);
        rotate.setRepeatCount(Animation.INFINITE);
        Layout.destinationIcon.setAnimation(rotate);
    }

    public void setBallIconAnimation() {
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                -2.1f, Animation.RELATIVE_TO_SELF, -0.6f);
        rotate.setDuration(8000);
        rotate.setRepeatCount(Animation.INFINITE);
        Layout.ballIcon.setAnimation(rotate);
    }

    public void openNewGame(View v){
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
        MainGame.gameMode = "fullGame";
        MainGame.singleLevelNumber = -1;
        finish();
    }

    public void openBestScores(View v){
        Intent intent = new Intent(this, BestScores.class);
        startActivity(intent);
        finish();
    }

    public void openSingleLevelMenu(View v){
        Intent intent = new Intent(this, SingleLevelMenu.class);
        startActivity(intent);
        finish();
    }

    public void openApplicationWebsite(View v) {

        String url = "market://details?id=ozog.development.feistyball";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
