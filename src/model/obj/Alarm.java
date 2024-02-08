package model.obj;

import view.obj.ObjectView;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Alarm extends BreakableObject {
    public Alarm(int col, int row){
        super(col,row);
        this.setIsAnimated(true);
        this.name = "alarm";
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Breakable/alarm_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectView = new ObjectView(this);
    }
    @Override
    public BufferedImage[] initializeAnimationSprites(){
        return super.initializeAnimationSprites(4,"Breakable/alarm_");
    }
}
