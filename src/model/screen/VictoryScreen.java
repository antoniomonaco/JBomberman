package model.screen;

import view.screen.ProfileScreenView;
import view.screen.VictoryScreenView;

public class VictoryScreen extends StateScreen{
    private static VictoryScreen instance;

    public static VictoryScreen getInstance(){
        if(instance == null) instance = new VictoryScreen();
        return instance;
    }
    private VictoryScreen(){
        setButtonsNum(2);
        setFilename("victory_");
        loadSequence();
        setStateScreenView(VictoryScreenView.getInstance());
    }

}
