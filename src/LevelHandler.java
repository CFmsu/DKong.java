import entities.Entity;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

public class LevelHandler {
    private Game game;
    private Rail rail;
    private BufferedImage railSprite, smallRailSprite;
    private Level level;
    private ArrayList<Rail> railList = new ArrayList<>();
    private ArrayList<Rectangle> platformList = new ArrayList<>();


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

    public void makeAllRails() {

        railList.add(new Rail(0, 1000, 100, 100));
        railList.add(new Rail(400, 800, 100, 100));
        railList.add(new Rail(-500, 600, 100, 100));
        railList.add(new Rail(400, 450, 100, 100));
        railList.add(new Rail(-200, 250, 100, 100));
        railList.add(new Rail(150, 100, 100, 100));

    }

    public void drawAllRails(Graphics graphic){

        for(Rail rail : railList){
            rail.updateHitBox();
            rail.drawRail(graphic);
        }
    }

    //Invisible platforms for making sure entities are standing on them.
    public void createPlatform(Rail rail){
        platformList.add(new Rectangle((int) rail.getX(), (int) rail.getY(), rail.getWidth(), rail.getHeight()));

    }

    public void createAllPlatforms(Graphics graphic){
        for(Rail rail : railList){
            createPlatform(rail);
        }

        for(Rectangle platform : platformList){
            graphic.drawRect(platform.x, platform.y, platform.width, platform.height);
        }
    }

    public void update() {

    }

    public Rail getRail() {
        return rail;
    }
}
