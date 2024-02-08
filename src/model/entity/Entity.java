package model.entity;

import java.awt.Rectangle;
import java.util.Observable;
import model.utils.CollisionChecker;
import view.screen.GamePanel;

@SuppressWarnings("deprecation")
public abstract class Entity extends Observable {

    private boolean isDead;
    protected int x;
    protected int y;
    protected int col;
    protected int row;
    private String name;

    protected String direction = "down";
    protected Boolean collisionUp;
    protected Boolean collisionDown;
    protected Boolean collisionLeft;
    protected Boolean collisionRight;
    protected Boolean collisionOn;
    protected Boolean canMove = true;
    protected CollisionChecker collisionChecker;
    private int baseTileWidth = 32; // Larghezza di base di ogni tile (in pixel)
    private int baseTileHeight = 32; // Altezza di base di ogni tile (in pixel)
    private int tileHeight;
    private int tileWidth;
    private boolean upPressed, downPressed, leftPressed, rightPressed;
    private int deathSpritesNum;
    private String path;
    private float scale;
    private int walkingSpritesNum;

    //  Hitbox Fields
    private Rectangle hitBox;
    private int hitboxOffsetX; // l'hitbox partirà alle coordinate (2,8) rispetto allo (0,0) del player
    private int hitboxOffsetY;
    private int hitboxWidth ; //=tileWidth - (int)(4*scale); // tolgo 4 pixel (2 destra e 2 sinistra)
    private int hitboxHeight ; //=tileHeight - (int)(9*scale); // tolgo 9 pixel dall'alto e 1 dal basso

    public Entity(String name, int col, int row) {
        this.col = col;
        this.row = row;
        setPosition();
        this.setName(name);
        collisionChecker = new CollisionChecker();
    }

    /**
     * Il metodo serve per posizionare correttamente le entità sulla mappa
     * Sottraggo la metà della grandezza base dei tile perché le entità sono grandi il doppio rispetto ai tile
     * (le entità sono 32x32 mentre i tile 16x16),
     * in questo modo riesco a farla generare al centro del blocco
     */
    public void setPosition(){
        this.setX(col * GamePanel.TILE_WIDTH - baseTileWidth/2);
        this.setY(row * GamePanel.TILE_HEIGHT + baseTileHeight/2);
    };

    /**
     * Genera l'hitbox, viene fatto l'Override per ogni entità poiché hanno tutte una forma diversa
     * e l'hitbox va adattata di conseguenza
     * In particolare bisogna usare l'offset della x per indicare da dove deve iniziare l'hitbox partendo da sinistra
     * e l'offset della y per capire da dove parte l'hitbox partendo dall'alto
     */
    protected abstract void initHitbox();
    protected void setDirectionAndCollision(){
        if (isUpPressed()) this.setDirection("up");
        else if (isDownPressed()) this.setDirection("down");
        else if (isLeftPressed()) this.setDirection("left");
        else if (isRightPressed()) this.setDirection("right");

//		CHECK COLLISION
        collisionUp = false;
        collisionDown = false;
        collisionLeft = false;
        collisionRight = false;
    }
    protected abstract void setDefaultValues();
    protected abstract void updateHitbox();
    public abstract void damage();
    public abstract void update();
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Rectangle getHitBox() {
        return hitBox;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public Boolean getCollisionUp() {
        return collisionUp;
    }
    public void setCollisionUp(Boolean collisionUp) {
        this.collisionUp = collisionUp;
    }

    public void setCollisionDown(Boolean collisionDown) {
        this.collisionDown = collisionDown;
    }
    public void setCollisionLeft(Boolean collisionLeft) {
        this.collisionLeft = collisionLeft;
    }
    public void setCollisionRight(Boolean collisionRight) {
        this.collisionRight = collisionRight;
    }

    public abstract int getSpeed();
    public int getRow() {
        return y / tileHeight ; }// Divisione intera per ottenere il numero della riga
    public int getCol() { return x / tileWidth ; } // Divisione intera per ottenere il numero della colonna

    public Boolean getCanMove() {
        return canMove;
    }
    public void setCanMove(Boolean canMove) {
        this.canMove = canMove;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileWidth() {
        return tileWidth;
    }
    public boolean isMoving() {
        return upPressed || downPressed || leftPressed || rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public int getDeathSpritesNum() {
        return deathSpritesNum;
    }

    public void setDeathSpritesNum(int deathSpritesNum) {
        this.deathSpritesNum = deathSpritesNum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }



    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    public int getHitboxOffsetX() {
        return hitboxOffsetX;
    }

    public void setHitboxOffsetX(int hitboxOffsetX) {
        this.hitboxOffsetX = hitboxOffsetX;
    }

    public int getHitboxOffsetY() {
        return hitboxOffsetY;
    }

    public void setHitboxOffsetY(int hitboxOffsetY) {
        this.hitboxOffsetY = hitboxOffsetY;
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public void setHitboxWidth(int hitboxWidth) {
        this.hitboxWidth = hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public void setHitboxHeight(int hitboxHeight) {
        this.hitboxHeight = hitboxHeight;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
    public void setHitBoxX(int x){
        hitBox.x = x;
    }
    public void setHitBoxY(int y){
        hitBox.y = y;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getWalkingSpritesNum() {
        return walkingSpritesNum;
    }

    public void setWalkingSpritesNum(int walkingSpritesNum) {
        this.walkingSpritesNum = walkingSpritesNum;
    }

}
