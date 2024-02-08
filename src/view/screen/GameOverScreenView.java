package view.screen;

public class GameOverScreenView extends StateScreenView{
    private static GameOverScreenView instance;

    public static GameOverScreenView getInstance(){
        if(instance == null) instance = new GameOverScreenView();
        return instance;
    }
    private GameOverScreenView(){
        super();
    }
}
