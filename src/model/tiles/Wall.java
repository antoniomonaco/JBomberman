package model.tiles;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends Tile {
    private final String path = "/Sprites/Tileset/";
    public Wall() {
    	this.collision = true;
		try {
			this.sprite = ImageIO.read(getClass().getResourceAsStream(path+level+"/wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
