import entities.Entity;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rail extends Entity {

    private ArrayList<Entity> railList;
    private float x, y;
    private int width, height;
    private BufferedImage railSprite, smallRailSprite;
    private Rectangle2D.Float hitbox;


    public Rail(float x, float y, int width, int height) {
        super(x, y, width, height);

        setHitbox(hitbox);

    }

    public void drawRail(Rail rail, Graphics graphic) {

        BufferedImage image = LoadSave.getSprites(LoadSave.levelSprites);

        railSprite = LoadSave.getSprites(LoadSave.levelSprites);
        railSprite = image.getSubimage(0, 703, 1167, 40);

        graphic.drawImage(railSprite, (int) rail.getX(), (int) rail.getY(), null);

        hitbox = new Rectangle2D.Float(x, y,width,height);
    }

}
