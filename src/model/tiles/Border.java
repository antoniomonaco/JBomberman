package model.tiles;

import java.io.IOException;

import javax.imageio.ImageIO;

import controller.GameController;
import view.screen.GamePanel;

public class Border extends Tile{

    private final String path = "/Sprites/Tileset/";
    private String level = GameController.level;

    public Border() {
        this.collision = true;
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream(path+level+"/border.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
