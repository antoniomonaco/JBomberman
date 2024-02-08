package model.tiles;

import model.tiles.Floor;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ShadowFloor extends Floor {
    private final String path = "/Sprites/Tileset/";
    private String fileName = "floor02.png";


	public ShadowFloor() {
    	this.collision = false;
		try {
			this.sprite = ImageIO.read(getClass().getResourceAsStream(path+level+"/"+fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
