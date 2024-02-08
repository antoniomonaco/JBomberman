package view.screen;

public class VictoryScreenView extends StateScreenView{
    private static VictoryScreenView instance;

    public static VictoryScreenView getInstance(){
        if(instance == null) instance = new VictoryScreenView();
        return instance;
    }
    private VictoryScreenView(){
        super();
    }
}
