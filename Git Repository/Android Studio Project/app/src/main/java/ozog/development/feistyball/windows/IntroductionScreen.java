package ozog.development.feistyball.windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ozog.development.feistyball.R;

public class IntroductionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_screen);
    }

    public void openNewGame(View v){

        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
        MainGame.gameMode = "fullGame";
        MainGame.singleLevelNumber = -1;
        finish();
    }
}
