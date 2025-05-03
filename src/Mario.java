import Util.Physics;
import entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
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
    private boolean isFalling = false;
    private int [][] lvlData;
    private boolean facingRight = true;

    private Physics physics;
    private static final float groundLevel = 905;


    public Mario(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnims();

        physics = new Physics();
        physics.setGround(groundLevel);
    }

    public void update() {

        animUpdate();
        setAnimation();
        updatePos();
        updateHitBox();

    }

    public void render(Graphics graphic) {
        BufferedImage currentFrame = animations[action][animIndex];

        if(facingRight) {
            graphic.drawImage(currentFrame, (int)(x + width), (int) y, (int)x, (int)(y + height),
                    0, 0, currentFrame.getWidth(), currentFrame.getHeight(), null);
        }
        //flips Mario's sprites if Mario's sprites aren't going right.
        else{
            graphic.drawImage(currentFrame, (int) (x), (int) (y), width, height, null);
        }

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

        for (int i = 0; i < moveAnim.length; i++) {
            moveAnim[i] = spriteSheet.getSubimage(i * 19, 128, width -1, height -2);
        }

        BufferedImage[] idle = new BufferedImage[]{moveAnim[0]}; //The idle sprite is the same sprite as the first sprite in the moving animation

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


    public void updatePos() {

        float[] newPosition = physics.doPhysics(x, y);

        x = newPosition[0];
        y = newPosition[1];
        int moveSpeed = 1;

        if (move) {
            switch (playerDirection) {
                case LEFT:
                    x -= moveSpeed;
                    break;

                case UP:
                    y -= moveSpeed;
                    break;

                case RIGHT:
                    x += moveSpeed;
                    break;

                case DOWN:
                    y += moveSpeed;
                    break;

            }

        }
    }

    public void jump(){
        physics.jump(2.5F);
    }

    private void animUpdate() {

        animTick++;
        int animFrames = getAnimAmount(action);

        //For animations with 1 frame (jumping specifically) this prevents the animation from
        //switching to a different one too fast
        if(animFrames <= 1){
            return;
        }

        if (animTick >= animSpeed) {
            animTick = 0;
            animIndex++;

            if (animIndex >= animFrames) {
                animIndex = 0;
            }
        }
    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        move = true;

        if(direction == RIGHT){
            facingRight = true;
        }
        else{
            facingRight = false;
        }
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    private void setAnimation() {
        int actionUpdate = 1;
        if (isAirborne()){
            actionUpdate = JUMPING;
        }

        else if (move) {
            actionUpdate = RUNNING;
        }

        else {
            actionUpdate = IDLE;
        }

        if(action != actionUpdate){
            action = actionUpdate;
            animIndex = 0;
            animTick = 0;

        }
    }

    public boolean isAirborne(){
        return physics.isAirborne();
    }
}
