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

    private RotateAnimation rotate, rotate2;
    public static Context game;
    public static SharedPreferences displayFeedbackStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        game = getApplicationContext();

        // Upload connections to the layout elements
        Layout.loadLayoutElementsConnections(this);

        // Destination icon
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(10000);
        rotate.setRepeatCount(Animation.INFINITE);
        Layout.destinationIcon.setAnimation(rotate);

        // Ball icon
        rotate2 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                -2.1f, Animation.RELATIVE_TO_SELF, -0.6f);
        rotate2.setDuration(8000);
        rotate2.setRepeatCount(Animation.INFINITE);
        Layout.ballIcon.setAnimation(rotate2);
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

        String url = "https://play.google.com/store/apps/developer?id=Rafal%20Ozog&hl=en_GB";
        //String url = "market://details?id=<package_name>";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void close() {
        finish();
    }
}
