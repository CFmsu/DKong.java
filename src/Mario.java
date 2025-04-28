import entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Util.Constant.PlayerConstants.*;
import static Util.Constant.PlayerConstants.Directions.*;
import static Util.Constant.PlayerConstants.Directions.DOWN;

public class Mario extends Entity {

    private BufferedImage[][] animations;
    private int animTick, animIndex, animSpeed = 30;
    private int action = IDLE;
    private int playerDirection = -1;
    private boolean move = false;
    private int [][] lvlData;
    private float xOffset = 21 * Game.tileScale;
    private float yOffset = 4 * Game.tileScale;

    public Mario(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnims();
        makeHitBox(x, y, width, height);
    }

    public void update() {

        animUpdate();
        setAnimation();
        updatePos();
        updateHitBox();

    }

    public void render(Graphics graphic) {

        graphic.drawImage(animations[RUNNING][animIndex], (int) (x), (int) (y), width, height, null);
        drawHitbox(graphic);

    }

    private void loadAnims() {

        BufferedImage spriteSheet = LoadSave.getSprites(LoadSave.playerSprites);

        //I wanted to add all of these directly into a 2D array without initializing them all,
        // but there would be constant errors in console and sprites would never align properly.
        BufferedImage[] moveAnim = new BufferedImage[3];
        BufferedImage[] climbAnim = new BufferedImage[4];
        BufferedImage[] hammerAnim = new BufferedImage[2];
        BufferedImage[] hammerJumpAnim = new BufferedImage[5];
        BufferedImage[] dieAnim = new BufferedImage[4];

        int width = 18;
        int height = 20;

        //these animations are coded in the order that they occur in on the sprite sheet

        BufferedImage[] idle = new BufferedImage[]{moveAnim[0]}; //The idle sprite is the same sprite as the first sprite in the moving animation

        for (int i = 0; i < moveAnim.length; i++) {
            moveAnim[i] = spriteSheet.getSubimage(i * 19, 128, width -1, height -2);
        }

        for (int i = 0; i < climbAnim.length; i++) {
            climbAnim[i] = spriteSheet.getSubimage(i * 20, 150, width, height);
        }

        BufferedImage[] jump = new BufferedImage[]{spriteSheet.getSubimage(0, 170, width, height)};

        for (int i = 0; i < hammerJumpAnim.length; i++) {
            hammerJumpAnim[i] = spriteSheet.getSubimage(i * 20, 190, width, height);
        }

        for (int i = 0; i < hammerAnim.length; i++) {
            hammerAnim[i] = spriteSheet.getSubimage(i * 20, 215, width, height);
        }

        for (int i = 0; i < dieAnim.length; i++) {
            dieAnim[i] = spriteSheet.getSubimage(i * 21, 236, width, height);
        }

        //directly putting all animations into 2D array

        animations = new BufferedImage[][]{idle, moveAnim, climbAnim, jump, hammerJumpAnim, hammerAnim, dieAnim};

    }

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;

    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        move = true;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    private void setAnimation() {

        if (move) {
            action = RUNNING;
        } else {
            action = IDLE;
        }
    }

    public void updatePos() {
        if (move) {
            switch (playerDirection) {
                case LEFT:
                    x -= 1;
                    break;

                case UP:
                    y -= 1;
                    break;

                case RIGHT:
                    x += 1;
                    break;

                case DOWN:
                    y += 1;
                    break;

            }
        }
    }

    private void animUpdate() {

        animTick++;

        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;

            if (animIndex >= getAnimAmount(action)) {
                animIndex = 0;
            }
        }
    }
}
