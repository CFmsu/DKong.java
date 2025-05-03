import entities.Entity;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rail extends Entity {

    private BufferedImage railSprite, smallRailSprite;

    public Rail(float x, float y, int width, int height) {
        super(x, y, width, height);

        hitbox = new Rectangle((int)x, (int)y, width, height);

    }

    public void drawRail(Graphics graphic) {
        if(railSprite == null) {

            BufferedImage image = LoadSave.getSprites(LoadSave.levelSprites);
            railSprite = image.getSubimage(0, 703, 1167, 40);

        }

        graphic.drawImage(railSprite, (int) x, (int) y,null);
        drawHitbox(graphic);
    }

}
