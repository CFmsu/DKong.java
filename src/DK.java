import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DK extends Entity {

    private BufferedImage kongSprite, kongDefeat, kongWin;
    private static final int neutral = 0;
    private static final int win = 1;
    private static final int lose = 2;
    private int emotion = neutral;


    public DK(float x, float y, int width, int height) {
        super(x, y, width, height);
        makeKongSprite();
    }

    public void makeKongSprite() {
        if (kongSprite == null || kongDefeat == null || kongWin == null) {
            BufferedImage image = LoadSave.getSprites(LoadSave.mainSprites);

            kongSprite = image.getSubimage(2, 42, 43, 34);
            kongWin = image.getSubimage(44, 42, 43, 34);
            kongDefeat = image.getSubimage(228, 79, 41, 36);

        }

    }

    public void drawKongSprite(Graphics graphic){

        if(emotion == win){
            drawWinKong(graphic);
        }
        else if(emotion == lose){
            drawLoseKong(graphic);
        }
        else{
            drawNormKong(graphic);
        }
    }

    public void drawNormKong(Graphics graphic) {
        graphic.drawImage(kongSprite, (int) getX(), (int) getY(), getWidth(), getHeight(), null);
    }

    public void drawLoseKong(Graphics graphic) {
        graphic.drawImage(kongDefeat, (int) getX() - 5, (int) getY() - 5, getWidth() - 5, getHeight() + 5, null);

    }

    public void drawWinKong(Graphics graphic) {
        graphic.drawImage(kongWin, (int) getX() - 11, (int) getY(), getWidth(), getHeight(), null);

    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }
}
