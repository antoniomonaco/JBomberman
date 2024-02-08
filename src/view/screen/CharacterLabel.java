package view.screen;

import controller.AudioManager;
import model.utils.GameOption;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CharacterLabel extends JLabel {
    private String[] names;
    private String path = "/Sprites/Sprite player/";
    private String name;
    private BufferedImage sprite;
    private static CharacterLabel instance;
    private int playerNum;
    private int pointer;

    public static CharacterLabel getInstance(){
        if(instance == null) instance = new CharacterLabel();
        return instance;
    }

    private CharacterLabel(){
        int size = 50;
        this.setBounds((int) (2.25*size*GamePanel.SCALE),(int) (2.35*size*GamePanel.SCALE), (int) (size* GamePanel.SCALE), (int) (size*GamePanel.SCALE));
        playerNum = 3;
        loadNames();
        name = GameOption.getInstance().getPlayerName();
        pointer = GameOption.getCharacterNum() - 1;
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(path + name + "/standing_down.png"));
            this.setIcon(getResizedIcon(sprite));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNames() {
        names = new String[playerNum];
        names[0] = "White_Bomberman";
        names[1] = "Johnny_Bravo";
        names[2] = "Homer";
    }

    public ImageIcon getResizedIcon(BufferedImage originalImage) {
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }
    public void increasePointer(){
        AudioManager.getInstance().play(("title_screen_cursor"));
        if(pointer < names.length-1) pointer++;
        else pointer = 0;
        GameOption.getInstance().setPlayerName(names[pointer]);
        setSprite(names[pointer]);
    }
    public void decreasePointer(){
        AudioManager.getInstance().play(("title_screen_cursor"));
        if(pointer > 0) pointer--;
        else pointer = names.length-1;
        GameOption.getInstance().setPlayerName(names[pointer]);
        setSprite(names[pointer]);
    }
    public void setSprite(String name){
        try {
            sprite = ImageIO.read(getClass().getResourceAsStream(path + name + "/standing_down.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setIcon(getResizedIcon(sprite));
    }

    public int getPointer() {
        return pointer;
    }
}
