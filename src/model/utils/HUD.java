package model.utils;

import model.tiles.Tile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class HUD extends Tile {

    public HUD() {
        try {
            this.sprite = ImageIO.read(new File("res/Sprites/HUD/score_bar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
