package model.obj;

import controller.GameController;
import controller.TileManager;
import model.entity.Player;
import model.powerup.ExtraBomb;
import model.powerup.Hearth;
import model.powerup.Skate;
import model.tiles.Tile;
import model.utils.GameOption;
import view.screen.GamePanel;
import view.obj.ObjectView;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BreakableObject extends GameObject {
    protected ObjectView objectView;
    protected String name;
    private int col;
    private int row;
    private Timer timer;
    private TileManager tileM;
    private int[][] mapNum;
    private Tile[] tiles;
    private Random r;
    private GameController gC;

    public BreakableObject(int col, int row){
        this.frameDelay = 8;
        this.collision = true;
        this.col = col;
        this.row = row;
        this.x = col * GamePanel.TILE_WIDTH;
        this.y = row * GamePanel.TILE_HEIGHT;
        tileM = TileManager.getInstance();
        tiles = tileM.getTiles();
        mapNum = tileM.getMapNum();
        r = new Random();
        gC = GameController.getInstance();

    }

    @Override
    public void destroy() {
        super.destroy();
        int delay = 900;
        Player.getInstance().changeScore(5);

        double probability = r.nextDouble(); // Genera un numero casuale tra 0 e 1
        int powerupNum = r.nextInt(3);
        if (probability <= 0.2) { // Probabilità del 20% di rilasciare un powerup
            timer = new Timer(delay, e -> {
                switch (powerupNum) {
                    case 0 -> GameController.getInstance().addObject(new Skate(col, row));
                    case 1 -> GameController.getInstance().addObject(new ExtraBomb(col, row));
                    case 2 -> GameController.getInstance().addObject(new Hearth(col, row));
                }

            });
            timer.setRepeats(false);
            timer.start();
        }
        if (!GameOption.getInstance().isExitSpawned()) {
            timer = new Timer(delay, e -> {
                if (probability <= 0.4 && probability > 0.2) { // Probabilità del 20%
                    spawnExit();                               // uso anche la condizione > 0.2 per evitare che vengano
                }                                              // posizionati insieme l'uscita e il powerup
            });
            timer.setRepeats(false);
            timer.start();
        }

    }
    public void spawnExit(){
        if(!GameOption.getInstance().isExitSpawned()) {
            gC.addObject(new Exit(this.getX(), this.getY()));
            GameOption.getInstance().setExitSpawned(true);
        }
    }
    @Override
    public BufferedImage[] initializeAnimationSprites() {
        return new BufferedImage[0];
    }
    @Override
    public JLabel getView() {
        return objectView;
    }
}
