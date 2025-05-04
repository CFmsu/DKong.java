import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String playerSprites = "sprites/mainSprites.png";
    public static final String levelSprites = "sprites/railSprites.png";
    public static final String barrelSprites = "sprites/mainSprites.png";

    public static BufferedImage getSprites(String file) {

        InputStream input = null;
        BufferedImage spriteSheet = null;
        try {

            input = LoadSave.class.getResourceAsStream("/" + file);
            spriteSheet = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return spriteSheet;
    }
}
