package ozog.development.feistyball;

import android.widget.Toast;

public class Messages {

    public static void update() {

        if (Level.currentLevel == 1 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 1", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 1 && Time.levelTime == 2)
            Toast.makeText(MainGame.c, "Hit the propellers to unlock the exit point", Toast.LENGTH_SHORT).show();

        if (Level.currentLevel == 2 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 2", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 3 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 3", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 4 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 4", Toast.LENGTH_SHORT).show();

        if (Level.currentLevel == 5 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 5", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 5 && Time.levelTime == 2)
            Toast.makeText(MainGame.c, "Destruction balls! Be careful!", Toast.LENGTH_SHORT).show();

        if (Level.currentLevel == 6 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 6", Toast.LENGTH_SHORT).show();
        if (Level.currentLevel == 7 && Time.levelTime == 1)
            Toast.makeText(MainGame.c, "Level 7", Toast.LENGTH_SHORT).show();

    }
}
