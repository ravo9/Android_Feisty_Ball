package ozog.development.feistyball;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class BestScores extends AppCompatActivity {

    public static RelativeLayout rl;
    public static Context c;

    public static ListView recordList;
    public static List<String> recordsListString;
    public static ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores);

        rl = findViewById(R.id.RelativeLayout2);
        c = getApplicationContext();

        recordsListString = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>
                (c, android.R.layout.simple_list_item_1, recordsListString);

        recordList = findViewById(R.id.recordList);

        recordList.setAdapter(arrayAdapter);

        loadRecords();
    }

    public void loadRecords() {

        for (int i = Level.lastLevelNumber; i > 0; i--)
            recordsListString.add("Level " + i + ":     00:00:00");

        arrayAdapter.notifyDataSetChanged();
    }

    public void closeBestScores(View v) {
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
        finish();
    }
}
