package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected final int width;
    protected final int height;
    protected float x, y;
    protected Rectangle2D.Float hitbox;
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void makeHitBox(float x, float y, float width, float height){
        hitbox = new Rectangle2D.Float((int) x, (int) y, width, height);
    }

    protected void updateHitBox(){
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }



    protected void drawHitbox(Graphics graphic){
        graphic.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
