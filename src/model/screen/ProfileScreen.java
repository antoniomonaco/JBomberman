package model.screen;
import view.screen.ProfileScreenView;

import javax.swing.*;

public class ProfileScreen extends StateScreen{
    private static ProfileScreen instance;


    public static ProfileScreen getInstance(){
        if(instance == null) instance = new ProfileScreen();
        return instance;
    }
    private ProfileScreen(){
        setButtonsNum(1);
        setFilename("profile_screen_");
        loadSequence();
        setStateScreenView(ProfileScreenView.getInstance());


    }
}
