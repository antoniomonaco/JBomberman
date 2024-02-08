package view.screen;

import controller.GameController;
import model.screen.MenuScreen;
import model.utils.GameOption;
import model.utils.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class ChooseNameScreenView extends StateScreenView implements ActionListener {
    private static ChooseNameScreenView instance;
    private JTextField textField;
    private Font font;
    private JButton submit;
    private final int fieldWidth = 50;
    private final int fieldHeight = 10;
    private final int buttonWidth = 30;
    private final int buttonHeight = 10;

    public static ChooseNameScreenView getInstance(){
        if(instance == null) instance = new ChooseNameScreenView();
        return instance;
    }
    private ChooseNameScreenView(){
        super();
        textField = new JTextField("Nickname");
        submit = new JButton("Submit");
        textField.setBounds((int)(2* fieldWidth *GamePanel.SCALE),(int)(12* fieldHeight *GamePanel.SCALE), (int) (fieldWidth * GamePanel.SCALE), (int) (fieldHeight * GamePanel.SCALE));
        submit.setBounds((int)(5* buttonWidth *GamePanel.SCALE),(int)(12* buttonHeight *GamePanel.SCALE),(int) (buttonWidth * GamePanel.SCALE),(int) (buttonHeight * GamePanel.SCALE));
        submit.addActionListener(this);
        submit.setBackground(Color.DARK_GRAY);
        submit.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.WHITE);

        InputStream is = getClass().getResourceAsStream("/Font/monogram.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        textField.setFont(font.deriveFont(Font.PLAIN,30F));
        textField.setVisible(true);
        submit.setVisible(true);

        this.add(submit);
        this.add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){
            GameOption.getInstance().setNickName(textField.getText());
            GameOption.setFirstTimePlaying(false);
            GameController.getInstance().swapDisplayedScreen(this , MenuScreen.getInstance().getStateScreenView());
            GameOption.getInstance().setGameState(GameState.MENU_STATE);
        }
    }
}
