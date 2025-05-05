import Util.Collision;

import java.awt.*;

public class Game implements Runnable {

    private Window window;
    private GamePanel panel;
    private Thread gameLoop;
    private final int fps = 120;
    private final int ups = 200;
    private Mario mario;
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
        levelHandler.makeBarrel(200, 120, 70, 60);
        mario = new Mario(200, 905, 100, 100);
        levelHandler.makeAllSprings();

    }

    private void startLoop() {

        gameLoop = new Thread(this);
        gameLoop.start();

    }

    public void update() {
        Rectangle[] platforms = levelHandler.getPlatformList();
        mario.update(platforms);
        levelHandler.updateBarrels(200, 120, 70, 60);
        levelHandler.updateSprings();

    }

    public void render(Graphics graphic) {

        if(mario.deathFlag){
            msgTimer++;
            mario.marioReset(levelHandler.getBarrels());
            mario.drawMarioDeath(graphic, msgTimer, msgStop);
            //coordinates spawning the first new barrel with when the death screen stops
            levelHandler.setBarrelTimer(1200);
        }

        if(!mario.deathFlag){
            msgTimer = 0;
        }

        mario.render(graphic);

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
                System.out.println("FPS: " + fps + " UPS: " + updates);
                fps = 0;
                updates = 0;

            }
        }
    }

    public Mario getMario() {
        return mario;
    }

}
