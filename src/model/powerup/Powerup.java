package model.powerup;

import model.entity.Player;
import model.obj.GameObject;
import model.utils.GameOption;
import view.screen.GamePanel;
import view.obj.ObjectView;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Powerup extends GameObject {
    protected Player player;
    protected String name;
    private int col;
    private int row;
    protected ObjectView powerupView;

    public Powerup(int col,int row){
        this.col = col;
        this.row = row;
        this.x = col * GamePanel.TILE_WIDTH;
        this.y = row * GamePanel.TILE_HEIGHT;
        this.frameDelay = 2;
        this.setIsAnimated(true);

    }
    public void applyEffect(Player player){
        player.changeScore(50);
        GameOption.getInstance().increaseXp(10);
    }
    public BufferedImage[] initializeAnimationSprites(){
        return super.initializeAnimationSprites(6,"Sprite items/Powerup/"+name+"_");

        /*
        idleAnimation = new BufferedImage[6];
        try {
            idleAnimation[0] = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite items/Powerup/"+name+"_1.png"));
            idleAnimation[1] = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite items/Powerup/"+name+"_2.png"));
            idleAnimation[2] = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite items/Powerup/"+name+"_3.png"));
            idleAnimation[3] = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite items/Powerup/"+name+"_4.png"));
            idleAnimation[4] = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite items/Powerup/"+name+"_5.png"));
            idleAnimation[5] = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite items/Powerup/"+name+"_6.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //powerupView.setIdleAnimation(idleAnimation);
        return idleAnimation;

         */
    }
    private Timer timer;
    public abstract void removeEffect();

}
