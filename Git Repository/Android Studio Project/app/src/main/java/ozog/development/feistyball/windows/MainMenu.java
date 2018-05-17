package ozog.development.feistyball.windows;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;

import ozog.development.feistyball.functionality.Layout;
import ozog.development.feistyball.R;

public class MainMenu extends AppCompatActivity {

    private RotateAnimation rotate;
    public static Context game;
    public static SharedPreferences displayFeedbackStatus;
    public static InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        game = getApplicationContext();

        // Upload connections to the layout elements
        Layout.loadLayoutElementsConnections(this);

        // MobApp initializations
        MobileAds.initialize(this, "ca-app-pub-9192762409910035~6472838737");
        mInterstitialAd = new InterstitialAd(this);
        // Testing ID
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        // Real ID
        mInterstitialAd.setAdUnitId("ca-app-pub-9192762409910035/7408793683");
        MainMenu.mInterstitialAd.loadAd(new AdRequest.Builder().build());

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

        Intent intent = new Intent(this, IntroductionScreen.class);
        startActivity(intent);
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
