package ozog.development.feistyball.windows;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ozog.development.feistyball.functionality.Level;
import ozog.development.feistyball.R;
import ozog.development.feistyball.functionality.Time;

// This class is responsible both for the BestScores Activity (especially onCreate method),
// and for the Best Scores static interface, used e.g. for best scores checking and updating.

public class BestScores extends AppCompatActivity {

    public static RelativeLayout rl;
    public static SharedPreferences scores;

    private ListView recordList;
    private List<String> recordsListString;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores);

        recordsListString = new ArrayList<>();
        recordList = this.findViewById(R.id.levelList);
        arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, recordsListString);
        recordList.setAdapter(arrayAdapter);

        displayListUpdate();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public static void addRecord(int levelNumber, int score) {

        scores = MainMenu.game.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        editor.putInt("level_" + levelNumber, score);
        editor.apply();
    }

    public static int getRecord(int levelNumber) {

        scores = MainMenu.game.getSharedPreferences("scores", Context.MODE_PRIVATE);
        int score = scores.getInt("level_" + levelNumber, -1); //-1 is the default value
        return score;
    }

    public static void updateBestScores() {

        int currentLevel = Level.currentLevel;
        int levelTime = Time.levelTime;
        int gameTime = Time.gameTime;

        scores = MainMenu.game.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();

        // Level record check
        if ((levelTime < getRecord(currentLevel)) || (getRecord(currentLevel) == -1)){
            Toast.makeText(MainMenu.game, "New level record!", Toast.LENGTH_SHORT).show();

            if (scores.contains("level_" + currentLevel))
                editor.remove("level_" + currentLevel).commit();

            addRecord(currentLevel, levelTime);
        }

        if (MainGame.gameMode == "fullGame"){

            // Game record check
            if ((currentLevel == Level.lastLevelNumber) && ((gameTime < getRecord(0)) || (getRecord(0) == -1))){
                Toast.makeText(MainMenu.game, "Game record broken!", Toast.LENGTH_LONG).show();

                if (scores.contains("level_" + 0))
                    editor.remove("level_" + 0).commit();

                addRecord(0, gameTime);
            }

        } else if (MainGame.gameMode == "singleLevel") {}
    }

    public void displayListUpdate() {

        recordsListString.clear();

        int gameRecord = getRecord(0);
        if (gameRecord == -1)
            recordsListString.add("Game record:                  " + "00:00:00");
        else
            recordsListString.add("Game record:                  " + Time.displayTime(gameRecord));

        for (int i = Level.lastLevelNumber; i > 0; i--) {
            int record = getRecord(i);
            if (record == -1) {
                if ( i < 10)
                    recordsListString.add("Level " + i + ":                             " + "00:00:00");
                else
                    recordsListString.add("Level " + i + ":                           " + "00:00:00");
            }
            else {
                if ( i < 10)
                    recordsListString.add("Level " + i + ":                             " + Time.displayTime(record));
                else
                    recordsListString.add("Level " + i + ":                           " + Time.displayTime(record));
            }
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public void resetBestScores(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to reset all scores?");
        builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                resetAll();
                displayListUpdate();
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void resetAll() {
        scores = MainMenu.game.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        editor.clear().commit();
    }

    public void closeBestScores(View v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
