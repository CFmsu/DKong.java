import Util.Collision;

import java.awt.*;

public class Game implements Runnable {

    private Window window;
    private GamePanel panel;
    private Thread gameLoop;
    private final int fps = 120;
    private final int ups = 200;
    private Mario mario;
    private DK kong;
    private LevelHandler levelHandler;
    private int msgTimer = 0;
    private int msgStop = 450;
    //Below attributes calculate the size of a window as a grid to make placing entities easier
    public final static int tileStartSize = 32;
    public final static float tileScale = 1.5F;
    public final static int tileWidth = 30;
    public final static int tileHeight = 40;
    public final static int tileSize = (int) (tileStartSize * tileScale);
    public final static int gameWidth = tileSize * tileWidth;
    public final static int gameHeight = tileSize * tileHeight;

    public Game() {
        initiateClasses();

        panel = new GamePanel(this);
        window = new Window(panel);
        panel.setFocusable(true);
        panel.requestFocus();

        startLoop();

    }

    private void initiateClasses() {
        levelHandler = new LevelHandler(this);
        levelHandler.makeAllRails();
        levelHandler.makePaul(100, -10, 80, 100);
        levelHandler.makeAllSprings();
        levelHandler.makeBarrel(200, 120, 70, 60);

        mario = new Mario(200, 905, 100, 100);
        kong = new DK(110, 120, 160, 140);



    }

    private void startLoop() {

        gameLoop = new Thread(this);
        gameLoop.start();

    }

    public void update() {
        Rectangle[] platforms = levelHandler.getPlatformList();
        mario.update(platforms);
        levelHandler.updatePauline();
        levelHandler.updateBarrels(200, 120, 70, 60);
        levelHandler.updateSprings();

    }

    public void render(Graphics graphic) {

        if(mario.winFlag){
            msgTimer++;
            mario.drawMarioWin(graphic, msgTimer, msgStop);
            levelHandler.eraseBarrels();
            kong.setEmotion(2);
            //coordinates spawning the first new barrel with when the message screen stops
            levelHandler.setBarrelTimer(1200);
            if(msgTimer >= msgStop){
                mario.marioReset();
            }
        }

        if(mario.deathFlag){
            msgTimer++;
            mario.marioReset();
            kong.setEmotion(1);
            levelHandler.eraseBarrels();
            mario.drawMarioDeath(graphic, msgTimer, msgStop);

            levelHandler.setBarrelTimer(1200);
        }

        //if msgTimer = 0 is removed from here, death messages won't appear for dying to barrels after the first death message
        if(!mario.deathFlag && !mario.winFlag){
            msgTimer = 0;
            kong.setEmotion(0);
        }

        mario.render(graphic);
        kong.drawKongSprite(graphic);
        levelHandler.drawPauline(graphic);

        levelHandler.drawAllRails(graphic);
        levelHandler.createAllPlatforms(graphic);
        levelHandler.drawAllBarrels(graphic);
        levelHandler.drawAllSprings(graphic);

    }

    @Override
    //Method for looping the game. Timed on nanoseconds.
    public void run() {

        double FPStime = (double) 1000000000 / fps;
        double UPSTime = (double) 1000000000 / ups;
        int fps = 0;
        int updates = 0;
        long lastFpsCheck = System.currentTimeMillis();
        long prevTime = System.nanoTime();
        double updateTime = 0;
        double updateFrame = 0;


        while (true) {

            long thisTime = System.nanoTime();

            updateTime += (thisTime - prevTime) / UPSTime;
            updateFrame += (thisTime - prevTime) / FPStime;
            prevTime = thisTime;

            if (updateTime >= 1) {
                update();
                updates++;
                updateTime--;
            }

            if (updateFrame >= 1) {
                panel.repaint();
                updateFrame--;
                fps++;

            }

            if (System.currentTimeMillis() - lastFpsCheck >= 1000) {
                lastFpsCheck = System.currentTimeMillis();
                fps = 0;
                updates = 0;

            }
        }
    }

    public Mario getMario() {
        return mario;
    }

}
