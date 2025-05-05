import Util.Collision;
import entities.Entity;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Barrel extends Entity {

    private BufferedImage barrelSprite, barrelRoll;
    private BufferedImage[] animations;


    public Barrel(float x, float y, int width, int height) {
        super(x, y, width, height);

        physics.setAirborne(true);
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
            drawHitbox(graphic);
        }
    }

    public void doBarrelPhysics(Rectangle[] platforms){

        physics.setXAirSpeed(1);

        float[] newPosition = physics.doPhysics(x, y);
        float newX = newPosition[0];
        float newY = newPosition[1];

        float[] checkPosition = Collision.checkNewPos(newX, newY, physics, hitbox, platforms);
        if(!physics.isStanding(Collision.isOnGround(hitbox, platforms)) && physics.getYAirSpeed() == 0){
            physics.setXAirSpeed(0);
            physics.setYAirSpeed(1);
        }

        setX(newX);
        setY(newY);

    }

    public BufferedImage[] getAnimations() {
        return animations;
    }


}
