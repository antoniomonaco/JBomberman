package model.screen;

import view.screen.LevelSelectScreenView;

public class LevelSelectScreen extends StateScreen {
    private static LevelSelectScreen instance;

    public static LevelSelectScreen getInstance(){
        if(instance == null) instance = new LevelSelectScreen();
        return instance;
    }
    private LevelSelectScreen(){
        setButtonsNum(3);
        setFilename("level_select_");
        loadSequence();
        setStateScreenView(LevelSelectScreenView.getInstance());
    }
}
