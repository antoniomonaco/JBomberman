package model.obj;

import controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;

public abstract class GameObject extends Observable {

    protected boolean collision;
    protected BufferedImage sprite;
    protected int x;
    protected int y;
    protected int frameDelay;
    protected boolean animated;

    public void destroy(){
        delete();
    }
    protected BufferedImage[] idleAnimation;
    public abstract BufferedImage[] initializeAnimationSprites();

    /**
     * Ho effettuato l'overloading perché in questo modo, tutte le sottoclassi possono implementare il
     * metodo senza parametri, nel quale verrà invocato il metodo seguente che presenta due parametri.
     * Facendo ciò, tutte le classi che chiamano il metodo per ricevere l'array con gli sprite,
     * non hanno bisogno di sapere la grandezza dell'array e il path
     */
    public BufferedImage[] initializeAnimationSprites(int size, String path){
        idleAnimation = new BufferedImage[size];
        try {
            for(int i = 0; i<size; i++){
                idleAnimation[i] = ImageIO.read(getClass().getResourceAsStream("/Sprites/"+path+(i+1)+".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return idleAnimation;
    }
    public void delete(){
        GameController.getInstance().removeObject(this);
    }
    public abstract JLabel getView();

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getFrameDelay() {
        return frameDelay;
    }
    public boolean isAnimated() {
        return animated;
    }
    public void setIsAnimated(boolean animated) {
        this.animated = animated;
    }

    public BufferedImage getSprite() {
        return sprite;
    }
    public boolean getCollision(){
        return collision;
    }
}
