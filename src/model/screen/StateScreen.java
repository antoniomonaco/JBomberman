package model.screen;

import controller.AudioManager;
import view.screen.GamePanel;
import view.screen.StateScreenView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;

public abstract class StateScreen extends Observable {
    private String path = "/Sprites/Screen/";
    private String filename;
    private Image[] sequence;
    private StateScreenView stateScreenView;
    private int pointer;
    private int buttonsNum; //numero di pulsanti nella schermata


    protected void loadSequence(){
        sequence = new Image[buttonsNum];
        for( int i = 0; i< sequence.length; i++) {
            try {
                String fullPath = path + filename+i+".png";
                sequence[i] = (ImageIO.read(getClass().getResourceAsStream(fullPath)).getScaledInstance(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, Image.SCALE_FAST));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void increasePointer(){
        AudioManager.getInstance().play(("title_screen_cursor"));
        if(pointer < sequence.length-1) pointer++;
        else pointer = 0;
        update();
    }
    public void decreasePointer(){
        AudioManager.getInstance().play(("title_screen_cursor"));
        if(pointer > 0) pointer--;
        else pointer = sequence.length-1;
        update();
    }
    public void update(){
        setChanged();
        notifyObservers();
    }
    public int getPointer() {
        return pointer;
    }

    public Image[] getSequence() {
        return sequence;
    }

    public StateScreenView getStateScreenView() {
        return stateScreenView;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setSequence(Image[] sequence) {
        this.sequence = sequence;
    }

    public void setStateScreenView(StateScreenView stateScreenView) {
        this.stateScreenView = stateScreenView;
        this.addObserver(stateScreenView);
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public void setButtonsNum(int buttonsNum) {
        this.buttonsNum = buttonsNum;
    }


}
