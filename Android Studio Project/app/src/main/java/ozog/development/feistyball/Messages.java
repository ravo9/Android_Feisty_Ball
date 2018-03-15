package ozog.development.feistyball;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

public class Messages {

    private static boolean flagStop01 = false;

    public static void update() {

        if (Level.currentLevel == 1 && Time.levelTime == 2 * 100)
            Toast.makeText(MainMenu.game, "Hit the propellers to unlock the exit point", Toast.LENGTH_LONG).show();

        if (Level.currentLevel == 1 && Propeller.allPropellersSwitchedOn() && !flagStop01){
            Toast.makeText(MainMenu.game, "Now go to the exit point!", Toast.LENGTH_SHORT).show();
            flagStop01 = true;
        }

        if (Level.currentLevel == 2 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Level 2", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 3 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Level 3", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 4 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Level 4", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 5 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Level 5", Toast.LENGTH_SHORT).show();

        if (Level.currentLevel == 6 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Use the button to stop destruction balls", Toast.LENGTH_LONG).show();

        if (Level.currentLevel == 7 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Level 7", Toast.LENGTH_SHORT).show();

        if (Level.currentLevel == 8 && Time.levelTime == 1 * 100)
            Toast.makeText(MainMenu.game, "Use the button to open the locked gate", Toast.LENGTH_LONG).show();
    }
}
