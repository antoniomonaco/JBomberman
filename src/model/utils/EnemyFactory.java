package model.utils;

import model.entity.Boom;
import model.entity.Chicken;
import model.entity.Enemy;
import model.entity.Snail;

public class EnemyFactory {
    public Enemy createEnemy(String level,int col,int row){

        switch(level){
            case "level1" : return new Boom(col,row);
            case "level2" : return new Chicken(col,row);
            case "level3" : return  new Snail(col,row);
        }

        return null;
    }
}
