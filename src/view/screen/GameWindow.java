package view.screen;

import javax.swing.*;

public class GameWindow extends JFrame{

    private static GameWindow instance;

    public static GameWindow getInstance() {
        if(instance == null) instance = new GameWindow();
        return instance;
    }

    private GameWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}
