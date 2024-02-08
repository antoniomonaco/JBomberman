package model.entity;

import view.screen.GamePanel;

import java.awt.*;

public class Chicken extends Enemy{
    public Chicken(int col, int row) {
        super("Chicken",col,row,1,2, (float) (0.54* GamePanel.SCALE));
        setPath("/Sprites/Enemies/level2/");
        initHitbox();
        setDeathSpritesNum(13);
        setWalkingSpritesNum(2);
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

    }

}
