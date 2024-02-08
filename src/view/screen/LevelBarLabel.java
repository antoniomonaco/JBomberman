package view.screen;

import model.utils.GameOption;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelBarLabel extends JLabel {
    private String path = "/Sprites/HUD/LevelBar/level_bar_";
    private BufferedImage sprite;
    private static LevelBarLabel instance;
    private int pointer;
    private final int width = 60;
    private final int height = 60;

    public static LevelBarLabel getInstance(){
        if(instance == null) instance = new LevelBarLabel();
        return instance;
    }

    private int getPointer(){
        int xp = GameOption.getXp();
        if(xp <= 0) return 0;
        else if(xp > 0 && xp <= 20) return 1;
        else if(xp >20 && xp <= 40) return 2;
        else if(xp >40 && xp <= 60) return 3;
        else if(xp >60 && xp <= 80) return 4;
        else if(xp >80 && xp <= 100) return 5;
        return 0;
    }

    private LevelBarLabel(){
        this.setBounds((int) (1.8*width*GamePanel.SCALE),(int) (1.1*height*GamePanel.SCALE), (int) (width* GamePanel.SCALE), (int) (height*GamePanel.SCALE));
        update();
    }

    public void update(){

        pointer = getPointer();
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(path+ pointer +".png"));
            this.setIcon(getResizedIcon(sprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setText("<html><h2>header2 text</h2></html>");
    }


    public ImageIcon getResizedIcon(BufferedImage originalImage) {
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }


}
