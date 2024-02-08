package view.screen;

public class LevelSelectScreenView extends StateScreenView{
    private static LevelSelectScreenView instance;

    public static LevelSelectScreenView getInstance(){
        if(instance == null) instance = new LevelSelectScreenView();
        return instance;
    }
    private LevelSelectScreenView(){
        super();
    }
}
