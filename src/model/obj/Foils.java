package model.obj;

import view.obj.ObjectView;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Foils extends BreakableObject {
    public Foils(int col, int row){
        super(col,row);
        this.setIsAnimated(true);
        this.name = "foils";
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Breakable/foils_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectView = new ObjectView(this);
    }
    @Override
    public BufferedImage[] initializeAnimationSprites(){
        return super.initializeAnimationSprites(1,"Breakable/foils_");
    }
}
