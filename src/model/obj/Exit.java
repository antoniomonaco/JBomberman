package model.obj;

import view.obj.ObjectView;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Exit extends GameObject{
    private ObjectView exitView;
    public Exit(int x,int y){
        this.setIsAnimated(true);
        this.frameDelay = 1;
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Tileset/exit_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.collision = false;
        this.x = x;
        this.y = y;
        exitView = new ObjectView(this);
    }
    @Override
    public BufferedImage[] initializeAnimationSprites() {
        return super.initializeAnimationSprites(1,"Tileset/exit_");
    }

    @Override
    public JLabel getView() {
        return exitView;
    }
}
