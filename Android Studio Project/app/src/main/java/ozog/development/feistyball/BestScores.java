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

import java.util.ArrayList;
import java.util.List;

public class BestScores extends AppCompatActivity {

    public static RelativeLayout rl;
    public static Context c;

    public static SharedPreferences scores;

    public static ListView recordList;
    public static List<String> recordsListString;
    public static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores);

        rl = findViewById(R.id.RelativeLayout2);
        //c = getApplicationContext();

        recordsListString = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, recordsListString);
        recordList = findViewById(R.id.recordList);
        recordList.setAdapter(arrayAdapter);

        loadRecords();
    }


    public void addRecord(int levelNumber, int score) {
        scores = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        editor.putInt("level_" + levelNumber, score);
        editor.apply();
    }

    public void getRecord(int levelNumber) {
        scores = this.getSharedPreferences("scores", Context.MODE_PRIVATE);
        int score = scores.getInt("level_" + levelNumber, -1); //0 is the default value
    }

    public void loadRecords() {

        Database.bestScores = new Database(this);
        Database.bestScores.createTable();

        recordsListString.clear();

        // Tu cos nie gra.
        //int gameRecord = Database.bestScores.getRecord(0);
        //recordsListString.add("The whole game:      " + gameRecord);
/*
        for (int i = Level.lastLevelNumber; i > 0; i--) {
            int record = Database.bestScores.getRecord(i);
            recordsListString.add("Level " + i + ":                             " + record);
        }

        arrayAdapter.notifyDataSetChanged();*/
    }

    public void closeBestScores(View v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
