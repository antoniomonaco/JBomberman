package model.screen;

import view.screen.PauseScreenView;

public class MenuScreen extends StateScreen{
    private static MenuScreen instance;
    public static MenuScreen getInstance(){
        if(instance == null) instance = new MenuScreen();
        return instance;
    }
    private MenuScreen(){
        setButtonsNum(2);
        setFilename("menu_screen_");
        loadSequence();
        setStateScreenView(PauseScreenView.getInstance());
    }
}
