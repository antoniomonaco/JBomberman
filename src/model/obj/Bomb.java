package model.obj;

import controller.AudioManager;
import controller.GameController;
import controller.TileManager;
//import view.BombView;
import model.entity.Player;
import view.screen.GamePanel;
import view.obj.ObjectView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends GameObject {
    private ObjectView bombView;
    private Player player;
    private Timer timer;
    private TileManager tileM;
    private Explosion up;
    private Explosion down;
    private Explosion right;
    private Explosion left;
    private Explosion center;

    public Bomb(){
        this.frameDelay = 12;
        this.collision = true;
        this.setIsAnimated(true);
        player = Player.getInstance();
        x = (player.getNearestBlockX());
        y = (player.getNearestBlockY());
        this.bombView = new ObjectView(this);
        tileM = TileManager.getInstance();
        Timer timer = new Timer(2000, e -> {
            this.explode();
            player.removeBomb(this);
            this.delete();
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public BufferedImage[] initializeAnimationSprites() {
        return super.initializeAnimationSprites(3,"Bombs and explosions/Bomb_");
    }

    @Override
    public JLabel getView() {
        return bombView;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void explode(){
        AudioManager.getInstance().play("bomb_explodes");
        int col = x / GamePanel.TILE_WIDTH;
        int row = y / GamePanel.TILE_HEIGHT;

        int rightTile = tileM.getMapNum()[row][col + 1];
        int leftTile = tileM.getMapNum()[row][col - 1];
        int upTile = tileM.getMapNum()[row - 1][col];
        int downTile = tileM.getMapNum()[row + 1][col];

        center = new Explosion(this, "center");
        GameController.getInstance().addObject(center);

        if( !tileM.getTiles()[rightTile].isCollision()) {
            right = new Explosion(this, "end_right");
            GameController.getInstance().addObject(right);
        }
        if( !tileM.getTiles()[leftTile].isCollision()) {
            left = new Explosion(this, "end_left");
            GameController.getInstance().addObject(left);
        }
        if( !tileM.getTiles()[downTile].isCollision()){
            down = new Explosion(this, "end_down");
            GameController.getInstance().addObject(down);
        }
        if( !tileM.getTiles()[upTile].isCollision()){
            up = new Explosion(this, "end_up");
            GameController.getInstance().addObject(up);
        }


        int delay = 350;
        timer = new Timer(delay, e -> {
            if(center != null)center.delete();
            if(right != null)right.delete();
            if(up != null)up.delete();
            if(down != null)down.delete();
            if(left != null)left.delete();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
