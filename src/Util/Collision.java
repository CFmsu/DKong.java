package Util;


import entities.Entity;

import java.awt.*;

public class Collision {

    public static Rectangle getPlatform(Rectangle hitbox, Rectangle[] platforms){
        for(Rectangle platform : platforms){
            if(hitbox.intersects(platform)){
                return platform;
            }
        }
        return null;
    }

    //If getPlatform returns not null, the entity's hitbox is on a platform.
    public static boolean isOnGround(Rectangle hitbox, Rectangle[] platforms){
        return getPlatform(hitbox, platforms) != null;
    }
    //During physics calculations, this method checks whether an entity's hitbox is
    //colliding with a platform and makes sure to update the x and y values accordingly
    public static float[] checkNewPos(float x, float y, Physics physics, Rectangle hitbox, Rectangle[] platforms){
    //temp variables determine new positions after calculating collision without manipulating positions too soon.
        Rectangle tempHitbox = new Rectangle(hitbox);
        float tempX = x;
        float tempY = y;

        tempHitbox.x = (int) (x + physics.getXAirSpeed());
        tempX = tempHitbox.x;

        if(physics.getYAirSpeed() != 0){
            tempHitbox.y = (int) (y + physics.getYAirSpeed());
            Rectangle tempPlatform = getPlatform(tempHitbox, platforms);
            //If a platform is found when entity is airborne, keep entity on the platform and stop all physics.
            if(tempPlatform != null){
                if(physics.getYAirSpeed() > 0){
                    tempY = tempPlatform.y - hitbox.height + 1;
                    physics.stopPhysicsY();
                    physics.setAirborne(false);
                }
                else if(physics.getYAirSpeed() < 0){
                    tempY = tempPlatform.y + tempPlatform.height;
                    physics.setYAirSpeed(0);
                    physics.setAirborne(true);
                }
            }
            else{
                physics.setAirborne(true);
            }
        }
        return new float[]{tempX, tempY};
    }

    public static boolean checkEntCollision(Entity mario, Entity entity){

        if(mario.getHitbox().intersects(entity.getHitbox())){
            return true;
        }
        return false;
    }
}
