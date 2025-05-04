package Util;

import java.awt.*;

public class Physics {

    private float gravity = 0.2F;
    private float maxYspeed = 4F;
    private float moveSpeed = 3F;
    private float ground = 900F;
    private float xAirSpeed = 0F, yAirSpeed = 0F;
    private boolean airborne = false;
    private boolean isOnGround = true;


    public void doGravity() {

        //if an entity is airborne, it will jump slowly but fall faster
        if (airborne) {
            float floatGravity = gravity;

            if(yAirSpeed < 0){
                floatGravity = gravity * .2F;
            }

            yAirSpeed += floatGravity;

            if (yAirSpeed > maxYspeed) {
                yAirSpeed = maxYspeed;
            }
        }
    }

    public float[] doPhysics(float x, float y) {

        doGravity();
        x += xAirSpeed;
        y += yAirSpeed;
        return new float[]{x, y};
    }

    public void stopPhysicsY(){
        yAirSpeed = 0F;
        airborne = false;
    }

    public void jump(float jumpHeight){
        //
        if(!airborne){
            //negative jump height will have entity jump upward.
            yAirSpeed = -jumpHeight;
            airborne = true;
        }
    }

    public void isStanding(boolean standing){
        if(standing) {

            airborne = false;

            if(yAirSpeed > 0){
                yAirSpeed = 0F;
            }
        }
        else{
            airborne = true;
        }
    }

    public float getXAirSpeed() {
        return xAirSpeed;
    }

    public float getYAirSpeed() {
        return yAirSpeed;
    }

    public boolean isAirborne() {
        return airborne;
    }

    public void setXAirSpeed(float xAirSpeed) {
        this.xAirSpeed = xAirSpeed;
    }

    public void setYAirSpeed(float yAirSpeed) {
        this.yAirSpeed = yAirSpeed;
    }

    public void setAirborne(boolean airborne) {
        this.airborne = airborne;
    }

    public void setGround(float ground){
        this.ground = ground;
    }

}
