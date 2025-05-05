package entities;

import Util.Collision;
import Util.Physics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.concurrent.RecursiveTask;

public abstract class Entity {

    protected final int width;
    protected final int height;
    protected float x, y;
    protected Rectangle hitbox;
    protected Physics physics;
    protected static  final float pit = 1000;


    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        hitbox = new Rectangle((int) x, (int) y, width, height);
    }

    public void drawHitbox(Graphics graphic){
        graphic.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void updateHitBox(){
        hitbox.setBounds((int)x, (int)y, width, height);
    }

    public static boolean pitFallCheck(int y, Rectangle[] platforms){

        if(y > pit) {
            return true;
        }
        return false;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
