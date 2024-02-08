package model.screen;

import view.screen.ChooseNameScreenView;

public class ChooseNameScreen extends StateScreen{
    private static ChooseNameScreen instance;
    public static ChooseNameScreen getInstance(){
        if(instance == null) instance = new ChooseNameScreen();
        return instance;
    }
    private ChooseNameScreen(){
        setButtonsNum(1);
        setFilename("choose_name_");
        loadSequence();
        setStateScreenView(ChooseNameScreenView.getInstance());
    }
}
