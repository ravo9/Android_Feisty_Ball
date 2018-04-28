package ozog.development.feistyball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class SingleLevelMenu extends AppCompatActivity {

    private ListView levelList;
    private Button btnPlay;

    private int chosenLevel;
    private List<String> levelsListString;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_level_menu);

        chosenLevel = -1;
        btnPlay = this.findViewById(R.id.btnPlay);
        btnPlay.setEnabled(false);

        levelsListString = new ArrayList<>();
        levelList = this.findViewById(R.id.levelList);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, levelsListString);
        levelList.setAdapter(arrayAdapter);

        displayListUpdate();

        levelList.setClickable(true);
        levelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                btnPlay.setEnabled(true);
                chosenLevel = Level.lastLevelNumber - position;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void displayListUpdate() {

        levelsListString.clear();

        for (int i = Level.lastLevelNumber; i > 0; i--) {
            int record = BestScores.getRecord(i);
            if (record == -1){
                if ( i < 10)
                    levelsListString.add("Level " + i + ":                             " + "00:00:00");
                else
                    levelsListString.add("Level " + i + ":                           " + "00:00:00");
            }
            else {
                if ( i < 10)
                    levelsListString.add("Level " + i + ":                             " + Time.displayTime(record));
                else
                    levelsListString.add("Level " + i + ":                           " + Time.displayTime(record));
            }
        }

        arrayAdapter.notifyDataSetChanged();
    }

    public void startSingleLevelMode(View v) {
        Intent intent = new Intent(this, MainGame.class);
        startActivity(intent);
        MainGame.gameMode = "singleLevel";
        MainGame.singleLevelNumber = chosenLevel;
        finish();
    }

    public void closeSingleLevelMenu(View v) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
