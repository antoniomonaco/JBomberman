package model.tiles;

import controller.GameController;

import java.awt.image.BufferedImage;

public abstract class Tile {
	protected BufferedImage sprite;
	protected boolean collision;
	protected String level = GameController.level;
	public BufferedImage getSprite() {
		return sprite;
	}
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	public boolean isCollision() {
		return collision;
	}
	public void setCollision(boolean collision) {
		this.collision = collision;
	}


}
