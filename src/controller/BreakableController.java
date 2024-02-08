package controller;

import model.obj.BreakableObject;
import model.tiles.Tile;
import model.utils.BreakableObjectFactory;
import view.screen.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BreakableController {
    private TileManager tileM;
    private int[][] mapNum;
    private Tile[] tiles;
    private Random r;
    private final int maxObjectNum = 50;
    private GameController gC;
    private static ArrayList<Point> objectCoordinates;
    private BreakableObject b;
    private BreakableObjectFactory breakableObjectFactory;


    public BreakableController(GameController gC){
        tileM = TileManager.getInstance();
        this.gC = gC;
        objectCoordinates = new ArrayList<>();
        breakableObjectFactory = new BreakableObjectFactory();
    }

    /**
     * Genero casualmente una riga e una colonna, se la posizione è libera mi posiziona un oggetto finché
     * non viene raggiunto il numero massimo di oggetti posizionabili
     * Aggiungo poi le coordinate di ogni oggetto a una lista per permettere alle altre classi di controllare
     * la presenza degli oggetti
     */
    public void spawn() {
        int counter = 0;
        r = new Random();
        mapNum = tileM.getMapNum();
        tiles = tileM.getTiles();

        while( counter < maxObjectNum){
            int rCol = r.nextInt(GamePanel.MAX_SCREEN_COL - 1);
            int rRow = r.nextInt(GamePanel.MAX_SCREEN_ROW - 1);
            int num = mapNum[rRow][rCol];


            if (!tiles[num].isCollision()) {
                if (!((rCol == 2 && rRow == 3) || (rCol == 3 && rRow == 3) || (rCol == 2 && rRow == 4))) { //serve a lasciare un pò di spazio libero allo spawn
                    b = breakableObjectFactory.createObject(rCol,rRow);
                    gC.addObject(b);
                    counter++;
                    objectCoordinates.add(new Point(rCol, rRow));
                }
            }
        }
    }

    public static ArrayList<Point> getObjectCoordinates() {
        return objectCoordinates;
    }
}
