package model.screen;

import view.screen.GameOverScreenView;

public class GameOverScreen extends StateScreen{
    private static GameOverScreen instance;

    public static GameOverScreen getInstance(){
        if(instance == null) instance = new GameOverScreen();
        return instance;
    }
    private GameOverScreen(){
        setButtonsNum(2);
        setFilename("game_over_");
        loadSequence();
        setStateScreenView(GameOverScreenView.getInstance());
    }
}
