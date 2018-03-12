package ozog.development.feistyball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
        recordList = this.findViewById(R.id.recordList);
        arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, recordsListString);
        recordList.setAdapter(arrayAdapter);

        displayListUpdate();
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

    public static boolean isRecordBroken() {

        int currentLevel = Level.currentLevel;
        int levelTime = Time.levelTime;

        if ((levelTime < getRecord(currentLevel)) || (getRecord(currentLevel) == -1)){
            Toast.makeText(MainMenu.game, "New level record!", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
            return false;
    }

    public static void updateRecord() {

        int currentLevel = Level.currentLevel;
        int levelTime = Time.levelTime;

        scores = MainMenu.game.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();

        if (scores.contains("level_" + currentLevel))
            editor.remove("level_" + currentLevel).commit();

        addRecord(currentLevel, levelTime);
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
            if (record == -1)
                recordsListString.add("Level " + i + ":                             " + "00:00:00");
            else
                recordsListString.add("Level " + i + ":                             " + Time.displayTime(record));
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public void resetBestScores(View v) {

        scores = MainMenu.game.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        editor.clear().commit();

        displayListUpdate();
    }

    public void closeBestScores(View v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
