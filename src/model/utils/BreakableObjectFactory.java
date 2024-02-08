package model.utils;

import controller.GameController;
import model.obj.Alarm;
import model.obj.BreakableObject;
import model.obj.Chest;
import model.obj.Foils;

public class BreakableObjectFactory {
    private BreakableObject b;
    public BreakableObject createObject(int col,int row){
        switch (GameController.level) {
            case "level1" -> b = new Chest(col, row);
            case "level2" -> b = new Foils(col, row);
            case "level3" -> b = new Alarm(col, row);
        }
        return b;
    }
}
