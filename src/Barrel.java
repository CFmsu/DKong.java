import entities.Entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Barrel extends Entity {

    private BufferedImage barrelSprite, barrelRoll;
    private BufferedImage[] animations;

    public Barrel(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public void makeBarrelAnims(Graphics graphic) {
        if(barrelSprite == null) {

            BufferedImage image = LoadSave.getSprites(LoadSave.playerSprites);
             barrelSprite = image.getSubimage(177, 155, 14, 10);
             barrelRoll = image.getSubimage(197, 155, 14, 10);

            animations = new BufferedImage[]{barrelSprite, barrelRoll};

        }
    }

    public void drawBarrel(Graphics graphic){
        if(animations != null && animations.length > 0){
            graphic.drawImage(animations[0], (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        }
    }

    public BufferedImage[] getAnimations() {
        return animations;
    }
}
