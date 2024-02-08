package controller;

import java.awt.Graphics2D;
import java.io.*;

import model.tiles.*;
import view.screen.GamePanel;

public class TileManager {
	
	private Tile normalFloor;
	private Tile shadowFloor;
	private Tile wall;
	private int[][] mapNum;
	private Tile[] tiles;
	private Border border ;
	private static TileManager instance = null;
	public static TileManager getInstance() {
		if(instance == null) instance = new TileManager();
		return instance;
	}
	private TileManager() {}

	public void loadSprite(){
		normalFloor = new Floor();
		wall = new Wall();
		shadowFloor = new ShadowFloor();
		border = new Border();
		tiles = new Tile[4];
		tiles[0] = normalFloor;
		tiles[1] = wall;
		tiles[2] = shadowFloor;
		tiles[3] = border;
	}

	/**
	 * Questo metodo è responsabile del caricamento della mappa di gioco da un file di testo.
	 * La mappa è rappresentata da una matrice di numeri interi, dove ogni numero indica un tipo
	 * specifico di Tile nella mappa.
	 * Il metodo legge il file di testo corrispondente al livello di gioco corrente e
	 * riempie la matrice mapNum con i valori letti dal file.
	 */
	public void loadMap() {
		mapNum = new int[GamePanel.MAX_SCREEN_ROW][GamePanel.MAX_SCREEN_COL];
		loadSprite();
		try {
			InputStream is = getClass().getResourceAsStream("/maps/" + GameController.level + ".txt");

			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int numCols = GamePanel.MAX_SCREEN_COL;
			int numRows = GamePanel.MAX_SCREEN_ROW;

			mapNum = new int[numRows][numCols];

			int row = 0;
			String line;
			while ((line = br.readLine()) != null && row < numRows) {
				String[] numbers = line.split(" ");
				for (int col = 0; col < numCols && col < numbers.length; col++) {
					int num = Integer.parseInt(numbers[col]);
					mapNum[row][col] = num;
				}
				row++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (row < GamePanel.MAX_SCREEN_ROW && col < GamePanel.MAX_SCREEN_COL) {
			int tileNum = mapNum[row][col];

			if (tileNum != 3) g2.drawImage(tiles[tileNum].getSprite(), x, y, GamePanel.TILE_WIDTH, GamePanel.TILE_HEIGHT, null);
			row++;
			y += GamePanel.TILE_HEIGHT;

			if (row == GamePanel.MAX_SCREEN_ROW - 1) {
				row = 0;
				y = 0;
				col++;
				x += GamePanel.TILE_WIDTH;
			}
		}
		// disegno il bordo mappa
		g2.drawImage(border.getSprite(), 0, (int)(32*GamePanel.SCALE), GamePanel.SCREEN_WIDTH, (int)(GamePanel.SCREEN_HEIGHT - 32 * GamePanel.SCALE), null);
	}
	public int[][] getMapNum() {
		return mapNum;
	}
	public Tile[] getTiles() {
		return tiles;
	}
	
	
	
	
}








