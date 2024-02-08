package view.screen;

import model.utils.GameOption;
import model.utils.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class ProfileScreenView extends StateScreenView{
    private static ProfileScreenView instance;
    private UI ui = UI.getInstance();


    public static ProfileScreenView getInstance(){
        if(instance == null) instance = new ProfileScreenView();
        return instance;
    }
    private ProfileScreenView(){
        super();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ui.draw(g2);
    }


}
