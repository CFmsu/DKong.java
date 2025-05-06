import Util.Collision;
import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    public boolean deathFlag = false;
    public boolean winFlag = false;

    public Mario(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnims();
    }

    public void update(Rectangle[] platforms) {

        animUpdate();
        setAnimation();

        physics.doGravity();

        float[]newPos = Collision.checkNewPos(x, y, physics, hitbox, platforms);
        x = newPos[0];
        y = newPos[1];

        updatePos();
        updateHitBox();
        physics.isStanding(Collision.isOnGround(hitbox, platforms));

        //When jumping, Mario gets a small speed boost to help jump over barrels. After he jumps, this sets Mario's X speed back to normal
        if(!isAirborne()){
            physics.setXAirSpeed(0F);
        }

        //logic for Mario falling into pit. Placeholder for now as it isn't a top priority
        if(pitFallCheck((int) y, platforms)){
            deathFlag = true;
        }
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

    }

    private void loadAnims() {

        BufferedImage spriteSheet = LoadSave.getSprites(LoadSave.mainSprites);

        //I wanted to add all of these directly into a 2D array without initializing them all,
        // but there would be constant errors in console and sprites would never align properly.
        BufferedImage[] moveAnim = new BufferedImage[3];
        BufferedImage[] climbAnim = new BufferedImage[4];
        BufferedImage[] hammerAnim = new BufferedImage[2];
        BufferedImage[] hammerJumpAnim = new BufferedImage[5];
        BufferedImage[] dieAnim = new BufferedImage[4];

        int width = 18;
        int height = 20;

        //these animations are coded in the order that they occur in on the sprite sheet, excluding idle because it needs the running sprites.

        for (int i = 0; i < moveAnim.length; i++) {
            moveAnim[i] = spriteSheet.getSubimage(i * 19, 128, width -1, height -2);
        }

        BufferedImage[] idle = new BufferedImage[]{moveAnim[0]};

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

        animations = new BufferedImage[][]{idle, moveAnim, climbAnim, jump, hammerJumpAnim, hammerAnim, dieAnim};

    }

    public void updatePos() {

        float[] newPosition = physics.doPhysics(x, y);

        x = newPosition[0];
        y = newPosition[1];
        float moveSpeed = 1;

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
        if(!physics.isAirborne()){
            physics.jump(4.5F);

            //Gives mario a small boost when jumping to help with jumping over barrels
            if(facingRight){
                physics.setXAirSpeed(.5F);
            }
            else{
                physics.setXAirSpeed(-.5F);
            }
        }
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

    public void marioReset(){
        setPos(200, 905);
    }

    public void drawMarioDeath(Graphics graphic, int timer, int stopper){
        if(timer < stopper){
            graphic.setColor(Color.RED);
            graphic.setFont(new Font("Arial", Font.BOLD, 50));
            graphic.drawString("You died!", 600, 600);
            setPos(1000, -1000);
        }
        else{
            deathFlag = false;
            setPos(200, 905);
            timer = 0;
        }
    }

    public void drawMarioWin(Graphics graphic, int timer, int stopper){
        if(timer < stopper){
            graphic.setColor(Color.GREEN);
            graphic.setFont(new Font("Arial", Font.BOLD, 50));
            graphic.drawString("You win!", 600, 600);
            setPos((int) x, (int) y);
        }
        else{
            deathFlag = false;
            winFlag = false;
            marioReset();
            timer = 0;
        }
    }

    public void deathHandler(ArrayList<Barrel> barrels){
        if(!deathFlag){
            marioReset();
            deathFlag = true;
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
        physics.setXAirSpeed(0);
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
