import Util.Collision;
import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        physics.doGravity();

        boolean standing = physics.isStanding(Collision.isOnGround(hitbox, platforms));

        if(facingRight){
            physics.setXAirSpeed(2);
        }
        else {
            physics.setXAirSpeed(-2);
        }

        if(standing){
            for(Rectangle platform : platforms){
                if(hitbox.intersects(platform)){
                    setY(platform.y - hitbox.height);
                    updateHitBox();
                    break;
                }
            }
            physics.stopPhysicsY();
            physics.setAirborne(false);
        }
        else{
            physics.setAirborne(true);
        }

        if(swapDirection(platforms)){
            setFacingRight(!facingRight);
        }

        float[] newPosition = physics.doPhysics(x, y);
        float newX = newPosition[0];
        float newY = newPosition[1];

        setX(newX);
        setY(newY);
        updateHitBox();
    }

    public boolean swapDirection(Rectangle[] platforms){
        for(Rectangle platform : platforms){
            if(!hitbox.intersects(platform) && physics.getYAirSpeed() > 0){
                return true;
            }
        }
        return false;
    }

    public BufferedImage[] getAnimations() {
        return animations;
    }
}
