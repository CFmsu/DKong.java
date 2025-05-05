import entities.Entity.*;
import org.w3c.dom.css.Rect;

import javax.lang.model.type.ArrayType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;

public class LevelHandler {
    private Game game;
    private Rail rail;
    private BufferedImage railSprite, smallRailSprite;
    private Barrel barrel;
    private Level level;
    int barrelTimer = 0;
    int timeToMakeBarrel = 1400;

    private ArrayList<Barrel> barrels;
    private ArrayList<Rail> railList = new ArrayList<>();
    private ArrayList<Rectangle> platformList = new ArrayList<>();


    public LevelHandler(Game game) {
        this.game = game;
        barrels = new ArrayList<>();
    }

    public Level getCurrentLevel() {
        return level;
    }

    public void setCurrentLevel(Level currentLevel) {
        level = currentLevel;
    }

    public void makeAllRails() {

        railList.add(new Rail(0, 1000, 100, 100));
        railList.add(new Rail(-400, 750, 100, 100));
        railList.add(new Rail(400, 500, 100, 100));
        railList.add(new Rail(-200, 250, 100, 100));
        railList.add(new Rail(-800, 70, 100, 100));

    }

    public void drawAllRails(Graphics graphic){

        for(Rail rail : railList){
            rail.updateHitBox();
            rail.drawRail(graphic);
        }
    }

    public void drawAllBarrels(Graphics graphic){
        for(Barrel barrel : barrels){

            barrel.makeBarrelAnims(graphic);
            barrel.drawBarrel(graphic);

        }
    }

    //Invisible platforms for making sure entities are standing on them.
    public void createPlatform(Rail rail){
        platformList.add(new Rectangle((int) rail.getX() + 10, (int) (rail.getY() + 5), (int)(rail.getWidth() * 11.5), (int) (rail.getHeight() * .1)));

    }

    public void createAllPlatforms(Graphics graphic){
        for(Rail rail : railList){
            createPlatform(rail);
        }

        for(Rectangle platform : platformList){
            graphic.drawRect(platform.x, platform.y, platform.width, platform.height);
        }
    }

    public void makeBarrel(float x, float y, int width, int height){
        barrels.add(new Barrel(x, y, width, height));
    }
    //values are needed for making new barrels at a set interval each update
    public void updateBarrels(int x, int y, int width, int height) {

        Iterator<Barrel> i = barrels.iterator();
        while(i.hasNext()){

            Barrel barrel = i.next();
            barrel.doBarrelPhysics(getPlatformList());
            barrel.updateHitBox();

            if(barrel.pitFallCheck((int)barrel.getY(), getPlatformList())){
                i.remove();
                System.out.println("Barrel removed");
            }

        }
        barrelTimer++;
        if(barrelTimer >= timeToMakeBarrel){
            makeBarrel(x, y, width, height);
            barrelTimer = 0;
        }
    }

    public Rail getRail() {
        return rail;
    }

    public Rectangle[] getPlatformList() {
        return platformList.toArray(new Rectangle[0]);
    }
}
