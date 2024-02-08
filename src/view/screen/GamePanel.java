package view.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import controller.GameController;
import controller.TileManager;
//import controller.TileController;
import model.entity.Player;
import model.utils.GameOption;
import model.utils.GameState;
import model.utils.UI;

public class GamePanel extends StateScreenView {

    public final static int baseTileWidth = 16; // Larghezza di base di ogni tile (in pixel)
    public final static int baseTileHeight = 16; // Altezza di base di ogni tile (in pixel)
    public final static float SCALE = 3.5f; //3.5f; // Fattore di scala per ingrandire i tile sullo schermo
    public final static int TILE_WIDTH = (int) (baseTileWidth * SCALE); // Larghezza effettiva di ogni tile (in pixel)
    public final static int TILE_HEIGHT = (int) (baseTileHeight * SCALE); // Altezza effettiva di ogni tile (in pixel)
    public final static int MAX_SCREEN_COL = 17; // Numero massimo di colonne di tile visualizzabili sullo schermo
    public final static int MAX_SCREEN_ROW = 15; // Numero massimo di righe di tile visualizzabili sullo schermo
    public final static int SCREEN_WIDTH = TILE_WIDTH * MAX_SCREEN_COL; // Larghezza totale dello schermo (in pixel)
    public final static int SCREEN_HEIGHT = TILE_HEIGHT * MAX_SCREEN_ROW; // Altezza totale dello schermo (in pixel)
    private static GamePanel instance;
    private TileManager tileM;
    private Player player;
    private UI ui;
    private GameOption gameOption = GameOption.getInstance();

    public static GamePanel getInstance() {
        if(instance == null) instance = new GamePanel();
        return instance;
    }

    private GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setDoubleBuffered(true); // migliora le performance(?)
        this.setLayout(null);
        this.setBackground(Color.black);
        tileM  = TileManager.getInstance();
        ui = UI.getInstance();
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        GameState gameState = gameOption.getGameState();

        if(gameState == GameState.PLAY_STATE || gameState == GameState.PAUSE_STATE) {
            tileM.draw(g2);
            ui.draw(g2);
            //player.drawHitbox(g2);
            //GameController.getInstance().drawEnemyHitbox(g2);
        }
    }

    public float getScale() {
        return SCALE;
    }
}

