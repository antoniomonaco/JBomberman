package controller;

import model.entity.Enemy;
import model.tiles.Tile;
import model.utils.EnemyFactory;
import view.screen.GamePanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemySpawner {
    private CopyOnWriteArrayList enemies;
    private Random r;
    private int col;
    private int row;
    private int enemyNumber;
    private int mapNum[][];
    private Tile[] tiles;
    private ArrayList<Point> objectCoordinates;
    private int counter;
    private GameController gameController;
    private ArrayList<Point> enemyCoordinates;
    private Enemy e;
    private EnemyFactory enemyFactory;

    public EnemySpawner(){
        enemyNumber = 3;
        enemies = new CopyOnWriteArrayList<>();
        r = new Random();
        objectCoordinates = BreakableController.getObjectCoordinates();
        enemyCoordinates = new ArrayList<>();
        enemyFactory = new EnemyFactory();
    }

    /**
     * Genero casualmente una riga e una colonna, se la posizione è libera mi posiziona un nemico finché
     * non viene raggiunto il numero massimo di nemici posizionabili
     * Aggiungo poi le coordinate di ogni nemico a una lista per permettere alle altre classi di controllare
     * la presenza di questi ultimi
     */
    public void spawn(){
        mapNum = TileManager.getInstance().getMapNum();
        tiles = TileManager.getInstance().getTiles();
        gameController = GameController.getInstance();
        while(counter < enemyNumber)
        {
            col = generateCol();
            row = generateRow();
            int num = mapNum[row][col];
            Point point = new Point(col,row);

            if(!tiles[num].isCollision() && !objectCoordinates.contains(point) && !enemyCoordinates.contains(point) ){ //&
                e = enemyFactory.createEnemy(gameController.getLevel(), col,row);

                gameController.addEnemies(e);
                enemies.add(e);
                enemyCoordinates.add(new Point(col,row));
                counter ++;
            }
        }
        counter = 0;
    }
    public int generateCol(){
        col = r.nextInt(4,GamePanel.MAX_SCREEN_COL - 1);
        return col;
    }
    public int generateRow(){
        row = r.nextInt(5,GamePanel.MAX_SCREEN_ROW - 1);
        return row;
    }
    public CopyOnWriteArrayList getEnemies() {
        return enemies;
    }

}
