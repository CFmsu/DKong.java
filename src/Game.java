import java.awt.*;

public class Game implements Runnable {

    private Window window;
    private GamePanel panel;
    private Thread gameLoop;
    private final int fps = 120;
    private final int ups = 200;
    private Mario mario;
    private Rail[] rails;
    private Rail rail1, rail2, rail3, rail4, rail5, rail6;
    private LevelHandler levelHandler;
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
        mario = new Mario(800, 910, 100, 100);

        rail1 = new Rail(0, 1000, 100, 100);
        rail2 = (new Rail(400, 800, 100, 100));
        rail3 = (new Rail(-500, 600, 100, 100));
        rail4 = (new Rail(400, 450, 100, 100));
        rail5 = (new Rail(-200, 250, 100, 100));
        rail6 = (new Rail(150, 100, 100, 100));

        rails = new Rail[]{rail1, rail2, rail3, rail4, rail5, rail6};


    }

    //
    private void startLoop() {

        gameLoop = new Thread(this);
        gameLoop.start();

    }

    public void update() {
        mario.update();
        levelHandler.update();
    }

    public void render(Graphics graphic) {

        mario.render(graphic);

        for(int i = 0; i < rails.length; i++){

            rails[i].drawRail(rails[i], graphic);

        }

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
