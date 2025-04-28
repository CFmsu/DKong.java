import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static Util.Constant.PlayerConstants.Directions.*;

public class KeyboardInputs implements KeyListener {

    private GamePanel panel;

    public KeyboardInputs(GamePanel panel){
        this.panel = panel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
                panel.getGame().getMario().setDirection(UP);
                break;

            case KeyEvent.VK_A:
                panel.getGame().getMario().setDirection(LEFT);
                break;

            case KeyEvent.VK_S:

                break;

            case KeyEvent.VK_D:
                panel.getGame().getMario().setDirection(RIGHT);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()){
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                panel.getGame().getMario().setMove(false);

        }

    }
}
