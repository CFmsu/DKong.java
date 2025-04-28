import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class LevelHandler {
    private Game game;
    private Rail rail;
    private BufferedImage railSprite, smallRailSprite;
    private Level level;


    public LevelHandler(Game game) {
        this.game = game;
        //levelSprite = LoadSave.getSprites(LoadSave.levelSprites);
    }

    public Level getCurrentLevel() {
        return level;
    }

    public void setCurrentLevel(Level currentLevel) {
        level = currentLevel;
    }

    public static void makeRail(Graphics graphic, Rail rail, int x, int y, int width, int height) {

        rail = new Rail(x, y, width, height);

       // graphic.drawImage(railSprite, 0, 1000, null);
        //rail1 = (new Rail(0, 1000, 100, 100));
        /*
       // graphic.drawImage(railSprite, 400, 800, null);
        rail2 = (new Rail(400, 800, 100, 100));
      //  graphic.drawImage(railSprite, -500, 600, null);
        rail3 = (new Rail(-500, 600, 100, 100));
      //  graphic.drawImage(railSprite, 400, 450, null);
        rail4 = (new Rail(400, 450, 100, 100));
        //graphic.drawImage(railSprite, -200, 250, null);
        rail5 = (new Rail(-200, 250, 100, 100));
        //graphic.drawImage(smallRailSprite, 150, 100, null);
        rail6 = (new Rail(150, 100, 100, 100));

         */
    }

    public void update() {

    }

    public Rail getRail() {
        return rail;
    }
}
