package model.tiles;

import controller.GameController;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Floor extends Tile {
    private final String path = "/Sprites/Tileset/";
    private String fileName = "/floor01.png";

    public Floor() {
        this.collision = false;
        try {
            this.sprite = ImageIO.read(getClass().getResourceAsStream(path+ GameController.level+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
