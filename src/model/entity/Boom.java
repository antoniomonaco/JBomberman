package model.entity;

import view.screen.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Boom extends Enemy{
    private BufferedImage sprite;
    private BufferedImage defaultSprite;
    private boolean hasBeenDamaged = false;

    public Boom(int col, int row){
        super("Boom",col,row,1,2, (float) (0.68 * GamePanel.SCALE));
        setPosition();
        setPath("/Sprites/Enemies/level1/");
        initHitbox();
        setDeathSpritesNum(4);
        setWalkingSpritesNum(3);
        setCanMove(true);
    }

    /**
     * Viene fatto l'override solo in questo caso a causa della grandezza dello sprite che nel caso
     * specifico di questo nemico è più piccolo di quello delle altre entità
     */
    @Override
    public void setPosition(){
        this.setX((int) (col * GamePanel.TILE_WIDTH - 4 * getScale()));
        this.setY((int) (row * GamePanel.TILE_HEIGHT - 8 * getScale()));
        //i valori sottratti servono a posizionarlo al centro del blocco
    };

    @Override
    protected void initHitbox() {
        setHitboxWidth((int) (16*getScale()));
        setHitboxHeight((int) (19*getScale()));
        setHitboxOffsetX((int) (8*getScale()));
        setHitboxOffsetY((int) (12*getScale()));
        setHitBox(new Rectangle(x, y, getHitboxWidth(), getHitboxHeight()));

    }

    @Override
    protected void setDefaultValues() {

        try {
            defaultSprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Snail/Ballom_down_1.png"));
            sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Snail/Ballom_down_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
