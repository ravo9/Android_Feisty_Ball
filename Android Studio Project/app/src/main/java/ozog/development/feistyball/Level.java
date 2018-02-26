package ozog.development.feistyball;


public class Level {

    private static int screenWidth;
    private static int screenHeight;


    static {

        screenWidth = MainGame.screenWidth;
        screenHeight = MainGame.screenHeight;
    }

    public static void setLevel1(MainGame game) {

        float ballPositionX = (screenWidth / 2) - 100;
        float ballPositionY = (float) (screenHeight * 0.7);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (screenWidth / 2 + 50);
        float destinationPositionY = (float) (screenHeight * 0.055);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

        game.addBrick(10, (int) (screenHeight * 0.26));
        game.addBrick((int)(screenWidth * 0.2 + 20), (int) (screenHeight * 0.26));
        game.addBrick((int)(screenWidth * 0.4 + 40), (int) (screenHeight * 0.26));
        game.addBrick((int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.26));

        game.addBrick((int)(screenWidth * 0.2 - 70), (int) (screenHeight * 0.40));
        game.addBrick((int)(screenWidth * 0.4 - 50), (int) (screenHeight * 0.40));
        game.addBrick((int)(screenWidth * 0.6 - 30), (int) (screenHeight * 0.40));
        game.addBrick((int)(screenWidth * 0.8 - 10), (int) (screenHeight * 0.40));

        game.addPropeller((int)(screenWidth * 0.10), (int) (screenHeight * 0.1));
        game.addPropeller((int)(screenWidth * 0.70), (int) (screenHeight * 0.50));
        game.addPropeller((int)(screenWidth * 0.20), (int) (screenHeight * 0.90));

    }

    public static void setLevel2(MainGame game) {

        float ballPositionX = (float) (screenWidth * 0.8);
        float ballPositionY = (float) (screenHeight * 0.2);
        setBallPosition(ballPositionX, ballPositionY);

        float destinationPositionX = (float) (screenWidth * 0.15);
        float destinationPositionY = (float) (screenHeight * 0.86);
        setDestinationPosition(destinationPositionX, destinationPositionY);

        game.addWalls();

        game.addBrick((int)(screenWidth * 0.45), (int) (screenHeight * 0.3));
        game.addBrick((int)(screenWidth * 0.65 + 40), (int) (screenHeight * 0.3));

        game.addBrick((int)(screenWidth * 0.75), (int) (screenHeight * 0.8));

        game.addPropeller((int)(screenWidth * 0.60), (int) (screenHeight * 0.02));
        game.addPropeller((int)(screenWidth * 0.02), (int) (screenHeight * 0.25));
        game.addPropeller((int)(screenWidth * 0.85), (int) (screenHeight * 0.85));

    }



    public static void setBallPosition(float x, float y) {
        MainGame.ball.setX(x);
        MainGame.ball.setY(y);
    }

    public static void setDestinationPosition(float x, float y) {
        MainGame.destination.setImageDrawable(MainGame.destinationImageGrey);
        MainGame.destination.setX(x);
        MainGame.destination.setY(y);
    }
}
