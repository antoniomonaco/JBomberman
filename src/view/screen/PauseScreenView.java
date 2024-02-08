package view.screen;

public class PauseScreenView extends StateScreenView  {
    private static PauseScreenView instance;

    public static PauseScreenView getInstance(){
        if(instance == null) instance = new PauseScreenView();
        return instance;
    }
    private PauseScreenView(){
        super();
    }

}
