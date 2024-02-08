package model.entity;

import controller.GameController;
import model.utils.GameOption;
import view.entity.EntityView;
import view.screen.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Enemy extends Entity {

    private int health;
    private int speed;
    private int baseTileWidth = 32;
    private int baseTileHeight = 32;
    private float scale;
    private int tileWidth;
    private int tileHeight;
    private boolean hasBeenDamaged = false;
    private int counter;
    private Random r;
    private  int directionNum;
    private boolean firstMove;
    private boolean canDamage;
    private int removeDelay = 2000;

    private EntityView view;

    public Enemy(String name, int col, int row,int health, int speed, float scale) {
        super(name,col, row);
        this.health = health;
        this.speed = speed;
        this.scale = scale;
        this.setX((col * GamePanel.TILE_WIDTH) );
        this.setY((row * GamePanel.TILE_HEIGHT) );
        tileWidth = (int) (baseTileWidth * scale); // Larghezza effettiva di ogni tile
        tileHeight = (int) (baseTileHeight * scale); // Altezza effettiva di ogni tile
        setTileWidth(tileWidth);
        setTileHeight(tileHeight);
        r = new Random();
        firstMove = true;
        canDamage = true;
    }

    @Override
    public void update(){
        this.setDirectionAndCollision();

        collisionChecker.checkTile(this);
        collisionChecker.checkObject(this);
        collisionChecker.checkEntity(this);
        collisionOn = collisionDown || collisionLeft || collisionUp || collisionRight;

        if (canMove){
            switch (direction) {
                case "up" -> {
                    if (isUpPressed() && !collisionUp) {
                        this.setY(this.getY() - speed);
                    }
                }
                case "down" -> {
                    if (isDownPressed() && !collisionDown) {
                        this.setY(this.getY() + speed);
                    }
                }
                case "left" -> {
                    if (isLeftPressed() && !collisionLeft) {
                        this.setX(this.getX() - speed);
                    }
                }
                case "right" -> {
                    if (isRightPressed() && !collisionRight) {
                        this.setX(this.getX() + speed);
                    }
                }
                default -> {
                }
            }
        }
        updateHitbox();
        setChanged();
        notifyObservers();
        move();

    }
    public void move() {
        counter++;
        if (collisionOn || firstMove) {
            firstMove = false;
            directionNum = r.nextInt(101);
            if (directionNum <= 25) {
                setUpPressed(true);
                setDownPressed(false);
                setRightPressed(false);
                setLeftPressed(false);
            } else if (directionNum > 25 && directionNum <= 50) {
                setDownPressed(true);
                setUpPressed(false);
                setRightPressed(false);
                setLeftPressed(false);
            } else if (directionNum > 50 && directionNum <= 75) {
                setRightPressed(true);
                setDownPressed(false);
                setUpPressed(false);
                setLeftPressed(false);
            } else if (directionNum > 75) {
                setLeftPressed(true);
                setRightPressed(false);
                setDownPressed(false);
                setUpPressed(false);
            }
            counter = 0;
        }

    }

    /**
     * Funzione di debug per mostrare l' hitbox
     */
    public void drawHitbox(Graphics g) {

        g.setColor(Color.yellow);
        g.drawRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);

    }
    public void damage() {

        if(!hasBeenDamaged) {
            hasBeenDamaged = true;
            health -= 1;
            Timer timer = new Timer(1000, e -> {
                hasBeenDamaged = false;
            });
            timer.setRepeats(false);
            timer.start();
            if (health == 0) die();
        }
    }

    public void die(){
        this.setCanMove(false);
        canDamage = false;
        setDead(true);
        Timer timer = new Timer(removeDelay, e -> {
            this.getView().setIcon(null);
            GameController.getInstance().removeEnemy(this);
        });
        timer.setRepeats(false);
        timer.start();

        Player.getInstance().changeScore(100);
        GameOption.getInstance().increaseXp(10);
    }

    @Override
    protected void updateHitbox() {
        setHitBoxX(x + getHitboxOffsetX());
        setHitBoxY(y + getHitboxOffsetY());

    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public void setScale(float scale) {
        this.scale = scale;
    }

    public EntityView getView() {
        return view;
    }

    public void setView(EntityView view) {
        this.view = view;
    }

    public boolean CanDamage() {
        return canDamage;
    }
}
