package view.obj;

import controller.ObjectAnimationController;
import model.obj.GameObject;
import view.screen.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ObjectView extends JLabel {
    final int baseTileWidth = 16; // Larghezza di base di ogni tile (in pixel)
    final int baseTileHeight = 16; // Altezza di base di ogni tile (in pixel)
    final float scale = GamePanel.SCALE; // Fattore di scala per ingrandire i tile sullo schermo
    final int tileWidth = (int) (baseTileWidth * scale); // Larghezza effettiva di ogni tile (in pixel)
    final int tileHeight = (int) (baseTileHeight * scale); // Altezza effettiva di ogni tile (in pixel)
    private BufferedImage sprite;
    private Image resizedImage;
    private ImageIcon resizedIcon;
    private static ObjectView instance = null;
    private BufferedImage[] idleAnimation;
    private ObjectAnimationController objectAnimationController;
    private GameObject obj;

    public ObjectView(GameObject obj){
        this.obj = obj;
        this.objectAnimationController = new ObjectAnimationController(idleAnimation);
        this.setBounds(obj.getX(), obj.getY(), tileWidth, tileHeight);
        this.idleAnimation = obj.initializeAnimationSprites();
        this.objectAnimationController = new ObjectAnimationController(idleAnimation);
        this.setVisible(true);
        sprite = obj.getSprite();
    }
    public ImageIcon getResizedIcon(BufferedImage originalImage) {
        if(originalImage != null) {
            resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
            resizedIcon = new ImageIcon(resizedImage);
        }
        return resizedIcon;
    }
    public void update() {

        if(obj.isAnimated()) {
            sprite = objectAnimationController.getCurrentSprite(obj.getFrameDelay());
            this.setIcon(getResizedIcon(sprite));
        }
        else {
            this.setIcon(getResizedIcon(sprite));
        }
    }

    public void setIdleAnimation(BufferedImage[] idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public void setGameObject(GameObject obj){

    }

}
