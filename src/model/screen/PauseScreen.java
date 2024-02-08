package model.screen;

import view.screen.PauseScreenView;

public class PauseScreen extends StateScreen {

    private static PauseScreen instance;

    public static PauseScreen getInstance(){
        if(instance == null) instance = new PauseScreen();
        return instance;
    }
    private PauseScreen(){
        setButtonsNum(3);
        setFilename("pause_screen_");
        loadSequence();
        setStateScreenView(PauseScreenView.getInstance());
    }

}
