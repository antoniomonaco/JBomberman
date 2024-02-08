package view.screen;

import model.screen.StateScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public abstract class StateScreenView extends JPanel implements Observer {
    private Image image;
    private StateScreenView instance;
    private boolean keyHandlerPresent;

    protected StateScreenView() {
        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        this.setDoubleBuffered(true); // migliora le performance(?)
        this.setLayout(null);
        this.setBackground(Color.black);

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image background) {
        this.image = background;
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void update(Observable o, Object arg) {
        StateScreen stateScreen = (StateScreen)o;
        this.setImage(stateScreen.getSequence()[stateScreen.getPointer()]);
        this.repaint();
    }

    public boolean isKeyHandlerPresent() {
        return keyHandlerPresent;
    }

    public void setKeyHandlerPresent(boolean keyHandlerPresent) {
        this.keyHandlerPresent = keyHandlerPresent;
    }
}
