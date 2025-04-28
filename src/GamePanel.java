import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Util.Constant.PlayerConstants.*;
import static Util.Constant.PlayerConstants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouse;
    private KeyboardInputs keys;
    private Game game;


    public GamePanel(Game game){
        this.game = game;
        mouse = new MouseInputs(this);
        keys = new KeyboardInputs(this);
        setWinSize();

        addKeyListener(keys);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

    }

    public void setWinSize(){
        int width = Game.gameWidth;
        int height = Game.gameHeight;
        Dimension winSize = new Dimension(width, height);
        setPreferredSize(winSize);
        System.out.println("size: " + width + " Height: " + height);
    }

    public void updateGame(){

    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        game.render(graphic);

    }

    public Game getGame(){
        return game;
    }
}
