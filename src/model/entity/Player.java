package model.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.AudioManager;
import controller.GameController;
import model.utils.GameOption;
import model.screen.GameOverScreen;
import model.utils.GameState;
import model.obj.Bomb;
import view.screen.GamePanel;

public class Player extends Entity {
    private int baseTileWidth = 32; // Larghezza di base di ogni tile (in pixel)
    private int baseTileHeight = 32; // Altezza di base di ogni tile (in pixel)
    private int health;
    private int speed;
    private final int defaultSpeed = (int) (GamePanel.SCALE * 1.15); //in questo modo la velocit√† si adatta alla dimensione della schermata;
    private int lives;
    private BufferedImage sprite;
    private BufferedImage defaultSprite;
    private static Player instance;
    private static final int col = 2;
    private static final int row = 3;
    private boolean canBeDamaged = true;
    private int actualScore;
    private int[] scoreArray;

//  Hitbox Fields
    private int hitboxOffsetX; // l'hitbox partir√† alle coordinate (2,8) rispetto allo (0,0) del player
    private int hitboxOffsetY;
    private int hitboxWidth ; //=tileWidth - (int)(4*scale); // tolgo 4 pixel (2 destra e 2 sinistra)
    private int hitboxHeight ; //=tileHeight - (int)(9*scale); // tolgo 9 pixel dall'alto e 1 dal basso

// Bomb Fields
    private Timer timer;
    private Bomb bomb;
    private boolean releasable = true;
    private int bombNumber = 1;
    private CopyOnWriteArrayList<Bomb> bombArray;
    private int bombCounter;
    //private Rectangle labelBorder;
    private boolean scoreChanged;

    public static Player getInstance() {
        if(instance == null) instance = new Player();
        return instance;
    }
    private Player() {
        super(GameOption.getInstance().getPlayerName(), col,row);
        this.setPath("/Sprites/Sprite player/");

        bombArray = new CopyOnWriteArrayList<>();
        bombCounter = 0;
        setPlayerDimension();
        setTileWidth((int) (baseTileWidth * getScale())); // Larghezza effettiva di ogni tile (in pixel)
        setTileHeight((int) (baseTileHeight * getScale())); // Altezza effettiva di ogni tile (in pixel)

        scoreArray = new int[8];
        setWalkingSpritesNum(2);
        setDefaultValues();
        instance = this;
        initHitbox();
    }
    @Override
    protected void initHitbox() {
        setHitBox(new Rectangle(x,y,hitboxWidth,hitboxHeight));
        //labelBorder = new Rectangle(x, y, getTileWidth(), getTileHeight());
    }
    @Override
    public void setDefaultValues() {
        //speed = defaultSpeed;
    	speed = 3;
        health = 1;
        lives = 3;
        actualScore = 0;
        scoreChanged = true;
        setDead(false);
        canMove = true;
        try {
            defaultSprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite player/White_Bomberman/standing_down.png"));
            sprite = ImageIO.read(getClass().getResourceAsStream("/Sprites/Sprite player/White_Bomberman/standing_down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void updateHitbox() {
        setHitBoxX(x + hitboxOffsetX);
        setHitBoxY(y + hitboxOffsetY);
        //temp
        //labelBorder.x = x;
        //labelBorder.y = y;
    }

    @Override
    public void damage() {

        if(canBeDamaged) {
            canBeDamaged = false;
            this.health -= 1;
            Timer timer = new Timer(3000, e -> {
                canBeDamaged = true;
            });

            timer.setRepeats(false);
            timer.start();

            if (health <= 0) die();
        }
    }
    public void die(){
        AudioManager.getInstance().play("bomberman_dies");

        setCanMove(false);
        setDead(true);
        releasable = false;
        lives -= 1;
        timer = new Timer(1800, e -> {
            if(lives >= 0) {
                this.health += 1;
                spawn();
                setDead(false);
                this.setDirection("down");
                this.setCanMove(true);
                releasable = true;
            }
            else{
                GameOption.increaseLostMatch();
                GameOption.increaseMatchPlayed();
                GameOption.getInstance().setGameState(GameState.GAME_OVER_STATE);
                AudioManager.getInstance().stopBackgroundMusic();
                AudioManager.getInstance().playBackgroundMusic("game_over");
                GameController.getInstance().swapDisplayedScreen(GamePanel.getInstance(), GameOverScreen.getInstance().getStateScreenView());
                GameOverScreen.getInstance().update();
            }

        });
        timer.setRepeats(false);
        timer.start();
    }
    public void spawn(){
        canBeDamaged= false;
        setPlayerDimension();
        this.setX(col * GamePanel.TILE_WIDTH - baseTileWidth/2);
        this.setY(row * GamePanel.TILE_HEIGHT - (getTileHeight() / 2));
        //sottraggo la grandezza base dei tile perch√© il player √® grande il doppio, in questo
        //modo riesco a posizionarlo al centro del blocco

        timer = new Timer(3000, e -> {
            canBeDamaged = true;
        });
        timer.setRepeats(false);
        timer.start();

    }

    public void drawHitbox(Graphics g) {
        // √® solo per debug
        g.setColor(Color.yellow);
        g.drawRect(getHitBox().x, getHitBox().y, getHitBox().width, getHitBox().height);

        //TEMP
        //g.setColor(Color.red);
        //g.drawRect(labelBorder.x, labelBorder.y, labelBorder.width, labelBorder.height);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void update() {
        updateScoreArray();
        this.setDirectionAndCollision();
        collisionChecker.checkTile(this);
        collisionChecker.checkObject(this);

        if (canMove) {
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
    }

    /**
     * Il metodo si occupa di rilasciare la bomba, dopodichÈ partir‡† un timer
     * (se ho a disposizione una sola bomba),
     * alla fine di quest'ultimo sar‡† possibile rilasciarne un'altra
     */
    public void releaseBomb() {
        int delay = 2000;

        if (releasable) {
            AudioManager.getInstance().play("place_bomb");
            GameController gC = GameController.getInstance();

            if (bombNumber > 1) {
                bombNumber -= 1;
                bombArray.add(new Bomb());
                for(Bomb bomb : bombArray){
                    gC.addObject(bomb);
                }

            }
            else {
                bomb = new Bomb();
                gC.addObject(bomb);
                releasable = false;
                bombArray.clear();

                timer = new Timer(delay, e -> {
                    releasable = true;
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    public void removeBomb(Bomb bomb){
        bombArray.removeIf(gO -> gO.equals(bomb));
    }

    public void followMouse(int mouseX, int mouseY) {
        int playerCenterX = getX() + getTileWidth() / 2;
        int playerCenterY = getY() + getTileHeight() / 2;

        double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);

        // Calcola la direzione in base all'angolo tra il giocatore e il mouse
        if (angle >= -Math.PI / 4 && angle < Math.PI / 4) {
            setLeftPressed(false);
            setRightPressed(true);
            setUpPressed(false);
            setDownPressed(false);
        } else if (angle >= Math.PI / 4 && angle < 3 * Math.PI / 4) {
            setLeftPressed(false);
            setRightPressed(false);
            setUpPressed(false);
            setDownPressed(true);
        } else if ((angle >= 3 * Math.PI / 4 && angle <= Math.PI) || (angle >= -Math.PI && angle < -3 * Math.PI / 4)) {
            setLeftPressed(true);
            setRightPressed(false);
            setUpPressed(false);
            setDownPressed(false);
        } else {
            setLeftPressed(false);
            setRightPressed(false);
            setUpPressed(true);
            setDownPressed(false);
        }


    }

    /**
     * Il metodo si occupa di gestire la dimensione di ogni skin separatamente,
     * poich√© non tutte sono della stessa dimensione e non tutte hanno la stessa hitbox
     */
    public void setPlayerDimension(){
        switch (getName()) {
            case "White_Bomberman" -> {
                this.baseTileHeight = 32;
                this.baseTileWidth = 32;
                this.setScale((float) (GamePanel.SCALE * 0.8)); // c'√® un rapporto di 0.8 tra lo scale del gioco e quello del player
                this.hitboxWidth = (int) (10 * getScale());
                this.hitboxHeight = (int) (11 * getScale());
                this.hitboxOffsetX = (int) (11 * getScale());
                this.hitboxOffsetY = (int) (20 * getScale());
                setDeathSpritesNum(10);
            }
            case "Homer" -> {
                this.baseTileHeight = 32;
                this.baseTileWidth = 32;
                this.setScale((float) (GamePanel.SCALE * 0.8)); // c'√® un rapporto di 0.8 tra lo scale del gioco e quello del player
                this.hitboxWidth = (int) (15 * getScale());
                this.hitboxHeight = (int) (12 * getScale());
                this.hitboxOffsetX = (int) (8 * getScale());
                this.hitboxOffsetY = (int) (18 * getScale());
                setDeathSpritesNum(9);
            }
            case "Johnny_Bravo" -> {
                this.baseTileHeight = 32;
                this.baseTileWidth = 32;
                this.setScale((float) (GamePanel.SCALE * 0.714)); // c' √® un rapporto di 0.714 tra lo scale del gioco e quello del player
                this.hitboxWidth = (int) (15 * getScale());
                this.hitboxHeight = (int) (14 * getScale());
                this.hitboxOffsetX = (int) (9 * getScale());
                this.hitboxOffsetY = (int) (18 * getScale());
                setDeathSpritesNum(10);
            }
        }

        setTileWidth((int) (baseTileWidth * getScale()));
        setTileHeight((int) (baseTileHeight * getScale()));
        initHitbox();

    }
    private void updateScoreArray(){
        if(scoreChanged == true) { //mettendo la condizione evito di aggiornare l'array ogni secondo
            String score = String.valueOf(actualScore);
            int nZero = 8 - score.length();

            Arrays.fill(scoreArray, 0, nZero, 0);

            // Estrae le cifre dalla stringa e le inserisce nell'array
            for (int i = 0; i < score.length(); i++) {
                scoreArray[nZero + i] = Character.getNumericValue(score.charAt(i));
            }
            scoreChanged = false;
        }

    }

    public int getNearestBlockX() {
        int nearestBlockX = (getX()+8 + GamePanel.TILE_WIDTH / 2) / GamePanel.TILE_WIDTH * GamePanel.TILE_WIDTH;
        return nearestBlockX;
    }
    /** Il metodo calcola la coordinata y del blocco pi˘ vicino divisibile per la grandezza dei tile, 
     * questo serve per far sÏ che la bomba venga posizionata esattamente al centro di un blocco, 
     * e non a cavallo tra pi˘ blocchi.
     * bisogna aggiungere 8 perchÈ la dimensione del player √® il doppio di quella dei tile
     * se non lo facessi, la bomba verrebbe posizionata anche sui bordi e sugli oggetti con collisioni
     * 
     * @return
     */
    public int getNearestBlockY() {
        int blockYAtFeet = (getY()+8 + GamePanel.TILE_HEIGHT) / GamePanel.TILE_HEIGHT * GamePanel.TILE_HEIGHT;
        return blockYAtFeet;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void setIsReleasable(Boolean releasable) {
        this.releasable = releasable;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean isBombReleasable(){return releasable;}

    public void increaseBombNumber() {
        this.bombNumber += 1;
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
         this.lives = lives;
    }

    public boolean canBeDamaged() {
        return canBeDamaged;
    }

    public void setCanBeDamaged(boolean canBeDamaged) {
        this.canBeDamaged = canBeDamaged;
    }

    public int getActualScore() {
        return actualScore;
    }
    public void changeScore(int amount){
        actualScore = actualScore + amount;
        scoreChanged = true;
    }

    public void setActualScore(int actualScore) {
        this.actualScore = actualScore;
    }

    public int[] getScoreArray() {
        return scoreArray;
    }

    public int getBombNumber() {
        return bombNumber;
    }

    public CopyOnWriteArrayList<Bomb> getBombArray() {
        return bombArray;
    }

    public int getDefaultSpeed() {
        return defaultSpeed;
    }
}
