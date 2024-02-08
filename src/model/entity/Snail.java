package model.entity;

import view.screen.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Snail extends Enemy {
    private BufferedImage sprite;
    private BufferedImage defaultSprite;
    private boolean hasBeenDamaged = false;

    public Snail(int col, int row){
        super("Snail",col,row,1,2,(float) (0.54* GamePanel.SCALE));
        setPath("/Sprites/Enemies/level3/");
        initHitbox();
        setDeathSpritesNum(7);
        setWalkingSpritesNum(3);
        setCanMove(true);
    }

    @Override
    protected void initHitbox() {
        setHitboxWidth((int) (20*getScale()));
        setHitboxHeight((int) (27*getScale()));
        setHitboxOffsetX((int) (6*getScale()));
        setHitboxOffsetY((int) (3*getScale()));

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
