import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Spring extends Entity {

    private BufferedImage springSprite;
    public Spring(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public void makeSpringSprite(){
        if(springSprite == null){

            BufferedImage image = LoadSave.getSprites(LoadSave.mainSprites);
            springSprite = image.getSubimage(195, 216, 16, 12);
        }
    }

    public void drawSpring(Graphics graphic){
        makeSpringSprite();
        graphic.drawImage(springSprite, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
    }

    public boolean isMarioHere(Mario mario){

        Rectangle marioHitbox = new Rectangle(mario.getHitbox().x, mario.getHitbox().y,
                mario.getHitbox().width, mario.getHitbox().height - 5);

        return marioHitbox.intersects(hitbox) && mario.isAirborne();

    }

    public void doSpringJump(Mario mario) {

        mario.getPhysics().setYAirSpeed(-7);
    }
}
