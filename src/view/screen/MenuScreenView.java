package view.screen;

public class MenuScreenView extends StateScreenView{
    private static MenuScreenView instance;

    public static MenuScreenView getInstance(){
        if(instance == null) instance = new MenuScreenView();
        return instance;
    }
    private MenuScreenView(){
        super();
    }
}
