import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

//note: this is Mario's girlfriend. Mario getting to her is the win condition for the game.
public class Pauline extends Entity {

    private BufferedImage paulSprite;

    public Pauline(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public void makePaulSprite(){
        if(paulSprite == null){

            BufferedImage image = LoadSave.getSprites(LoadSave.mainSprites);
            paulSprite = image.getSubimage(91, 15, 18, 24);
        }
    }

    public void drawPaul(Graphics graphic){
        makePaulSprite();
        graphic.drawImage(paulSprite, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
        drawHitbox(graphic);
    }

    public void isMarioHere(Mario mario){
        if(mario.getHitbox().intersects(hitbox)){
            mario.winFlag = true;

        }
    }
}
