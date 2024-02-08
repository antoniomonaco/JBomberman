package view.entity;

import controller.EntityAnimationController;
import model.entity.Entity;
import model.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class EntityView extends JLabel implements Observer {
    private Entity entity;
    private Image resizedImage;
    private ImageIcon resizedIcon;
    private String path;
    private String name;
    private BufferedImage[] standingSprites;
    private BufferedImage[] walkingSpritesUp;
    private BufferedImage[] walkingSpritesDown;
    private BufferedImage[] walkingSpritesRight;
    private BufferedImage[] walkingSpritesLeft;
    private BufferedImage[] deathSprites;
    private EntityAnimationController animationController;
    public EntityView(Entity entity) {
        this.entity = entity;
        this.path = entity.getPath();
        this.setBounds(entity.getX(), entity.getY(), entity.getTileWidth(), entity.getTileHeight());
        initializeAnimationSprites();
        animationController = new EntityAnimationController(standingSprites, walkingSpritesUp, walkingSpritesDown,
                walkingSpritesRight, walkingSpritesLeft,deathSprites);
        this.setVisible(true);

    }

    //  Serve per adattare l'immagine alla grandezza della JLabel
    public ImageIcon getResizedIcon(BufferedImage originalImage) {
        resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_FAST);
        resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    public void updateIcon() {
        this.setIcon(resizedIcon);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setBounds(entity.getX(), entity.getY(), entity.getTileWidth(), entity.getTileHeight());

        BufferedImage currentSprite = animationController.getCurrentSprite(
                entity.isMoving(), entity.isUpPressed(), entity.isDownPressed(), entity.isLeftPressed(),
                entity.isRightPressed(),entity.isDead());

        if (currentSprite != null) this.setIcon(getResizedIcon(currentSprite));
        updateIcon();
    }


    private void initializeAnimationSprites() {
        // Inizializza gli array di sprite per ogni direzione di movimento

        path = entity.getPath();
        name = entity.getName();
        int walkingSpritesNum = entity.getWalkingSpritesNum();

        //name = GameOption.getInstance().getPlayerName();
        standingSprites = new BufferedImage[4];
        walkingSpritesUp = new BufferedImage[walkingSpritesNum];
        walkingSpritesDown = new BufferedImage[walkingSpritesNum];
        walkingSpritesRight = new BufferedImage[walkingSpritesNum];
        walkingSpritesLeft = new BufferedImage[walkingSpritesNum];
        deathSprites = new BufferedImage[entity.getDeathSpritesNum()];

        int deathSpritesNum = entity.getDeathSpritesNum();
        try {
            // Carica gli sprite per ogni direzione di movimento
            standingSprites[0] = ImageIO.read(getClass().getResourceAsStream(path + name + "/standing_up.png"));
            standingSprites[1] = ImageIO.read(getClass().getResourceAsStream(path + name + "/standing_down.png"));
            standingSprites[2] = ImageIO.read(getClass().getResourceAsStream(path + name + "/standing_right.png"));
            standingSprites[3] = ImageIO.read(getClass().getResourceAsStream(path + name + "/standing_left.png"));

            for(int i = 0; i< walkingSpritesNum;i++){
                walkingSpritesUp[i] = ImageIO.read(getClass().getResourceAsStream(path + name + "/walking_up_"+(i+1)+".png"));
            }
            for(int i = 0; i< walkingSpritesNum;i++){
                walkingSpritesDown[i] = ImageIO.read(getClass().getResourceAsStream(path + name + "/walking_down_"+(i+1)+".png"));
            }
            for(int i = 0; i< walkingSpritesNum;i++){
                walkingSpritesRight[i] = ImageIO.read(getClass().getResourceAsStream(path + name + "/walking_right_"+(i+1)+".png"));
            }
            for(int i = 0; i< walkingSpritesNum;i++){
                walkingSpritesLeft[i] = ImageIO.read(getClass().getResourceAsStream(path + name + "/walking_left_"+(i+1)+".png"));
            }

            for (int i = 0; i < deathSpritesNum; i++) {
                deathSprites[i] = ImageIO.read(getClass().getResourceAsStream(path + name + "/death_" + i + ".png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(String path) {
        this.path = path;
    }
}
