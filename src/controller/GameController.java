package controller;

import model.entity.Enemy;
import model.entity.Player;
import model.obj.GameObject;
import model.screen.*;
import model.utils.GameOption;
import model.utils.GameState;
import view.entity.EnemyView;
import view.entity.PlayerView;
import view.obj.ObjectView;
import view.screen.GamePanel;
import view.screen.GameWindow;
import view.screen.StateScreenView;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("deprecation")
public class GameController implements Runnable {

	private final int FPS = 60;
	private boolean running;
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Player player;
	private KeyHandler keyHandler;
	private static GameController instance = null;
	private PlayerView playerView;
	private AudioManager audioManager;
	private List<GameObject> gameObjects;
	private BreakableController breakableController;
	private GameState gameState;
	private GameOption gameOption;
	public static String level;
	private EnemyView enemyView; //temp

	// STATE SCREEN
	private StateScreen pauseScreen;
	private StateScreen gameOverScreen;
	private StateScreen menuScreen;
	private StateScreen profileScreen;
	private StateScreenView menuPanel;
	private LevelSelectScreen levelSelectScreen;
	private VictoryScreen victoryScreen;
	private ChooseNameScreen chooseNameScreen;
	private StateScreenView chooseNamePanel;
	private CopyOnWriteArrayList<Enemy> enemies;
	private EnemySpawner enemySpawner;
	private CopyOnWriteArrayList<EnemyView> enemyViewList;


	public void drawEnemyHitbox(Graphics2D g2){ //RIMUOVI QUESTO METODO
		for(Enemy e : enemies) {
			if(e!= null)e.drawHitbox(g2);
		}
	}
	public static GameController getInstance() {
		if(instance == null) instance = new GameController();
		return instance;
	}
	private GameController() {
		gameOption = GameOption.getInstance();
		gameOption.setGameController(this);
		gameState = gameOption.getGameState();
		gameObjects = new CopyOnWriteArrayList<>();

		gameWindow = GameWindow.getInstance();
		gamePanel = GamePanel.getInstance();
		pauseScreen = PauseScreen.getInstance();
		gameOverScreen = GameOverScreen.getInstance();
		menuScreen = MenuScreen.getInstance();
		profileScreen = ProfileScreen.getInstance();
		levelSelectScreen = LevelSelectScreen.getInstance();
		victoryScreen = VictoryScreen.getInstance();
		chooseNameScreen = ChooseNameScreen.getInstance();
		chooseNamePanel = chooseNameScreen.getStateScreenView();
		menuPanel = menuScreen.getStateScreenView();

    	keyHandler = KeyHandler.getInstance();
		gameWindow.add(menuPanel);
		menuPanel.addKeyListener(keyHandler);
		menuPanel.setKeyHandlerPresent(true);
		gameWindow.add(gamePanel);
		gameWindow.add(chooseNamePanel);
		gameWindow.setFocusable(true);
		gameWindow.addKeyListener(keyHandler);
		gameWindow.addMouseListener(keyHandler);
		gameWindow.addMouseMotionListener(keyHandler);
		gameWindow.pack(); // adatta la dimensione della finestra alle sue componenti
		gameWindow.setLocationRelativeTo(null); // questi due comandi sono qui perché vanno messi dopo il pack
		gameWindow.setVisible(true);			// altrimenti il keyHandler non funziona
		breakableController = new BreakableController(this);
		enemySpawner = new EnemySpawner();
		enemyViewList = new CopyOnWriteArrayList<>();
		gameWindow.setContentPane(menuPanel);
		menuScreen.update();
		gameWindow.revalidate();
		gameOption.setGameState(GameState.MENU_STATE);
		audioManager = AudioManager.getInstance();

		if(GameOption.getFirstTimePlaying()) {
			gameOption.setGameState(GameState.CHOOSE_NAME_STATE);
			swapDisplayedScreen(menuPanel,chooseNamePanel);
			chooseNameScreen.update();
		}

	}
    @Override
    public void run() {
        running = true;
        double timePerFrame = 1000000000.0/FPS;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        
        while (running) {
        	now = System.nanoTime();
        	if(now - lastFrame >= timePerFrame) {
        		update();
        		lastFrame = now;
        		frames ++;
        	}
        	if(System.currentTimeMillis() - lastCheck >= 1000) {
        		lastCheck = System.currentTimeMillis();
        		//System.out.println("FPS: "+ frames);
        		frames = 0;
        	}
        }
    }


	public void update(){

		gameState = gameOption.getGameState();

		if(gameState == GameState.PLAY_STATE) {
			player.update();
			gamePanel.repaint();

			//Aggiorna ciascun nemico nell'elenco
			enemies.stream()
		       .filter(Objects::nonNull) // Filtra gli elementi nulli
		       .forEach(Enemy::update);   // Applica l'aggiornamento a ciascun nemico rimanente

			// Aggiorna ciascun GameObject nell'elenco
			gameObjects.stream()
		           .filter(Objects::nonNull)          // Filtra gli elementi nulli
		           .map(GameObject::getView)          // Estrae le view degli oggetti di gioco
		           .filter(ObjectView.class::isInstance) // Rimuove eventuali non-ObjectView
		           .map(ObjectView.class::cast)          // Converte le viste in ObjectView
		           .forEach(ObjectView::update);         // Applica l'aggiornamento a ciascuna vista rimanente

		}
		else if(gameState == GameState.PAUSE_STATE) {
			pauseScreen.update();
		}

	}

	public void startLevel(){
		Timer timer = new Timer(1000, e1 -> {
			audioManager.playBackgroundMusic("level_theme_new");
		});
		timer.setRepeats(false);
		timer.start();

		TileManager.getInstance().loadMap();
		player = Player.getInstance();
		playerView = new PlayerView(player);
		player.addObserver(playerView);
		gamePanel.add(playerView);
		gamePanel.setPlayer(player);

		player.spawn();
		player.setDefaultValues();

		breakableController.spawn();
		enemySpawner.spawn();
		enemies = enemySpawner.getEnemies();

		gameOption.setExitSpawned(false);

		removeDisplayedScreen(menuScreen);
	}
	public void clearLevel(){
		audioManager.stopBackgroundMusic();
		removeAllObject();
		removePlayer();
		removeAllEnemies();
	}
    public void startGameLoop() {
        Thread gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }
    public void stopGameLoop() {
        running = false;
    }

	/**
	 * Permette di aggiungere nemici nel livello
	 * @param e nemico da aggiungere
	 */
	public void addEnemies(Enemy e){
		enemyView = new EnemyView(e);
		e.addObserver(enemyView);
		e.setView(enemyView);
		gamePanel.add(enemyView);
		enemyViewList.add(enemyView);
		gamePanel.setComponentZOrder(enemyView, 0);
	}

	/** Permette di aggiungere oggetti nel livello
	 * @param o oggetto da aggiungere
	 */
    public void addObject(GameObject o){
		gameObjects.add(o) ;
		gamePanel.add(o.getView());
	}
	public void removeObject(GameObject o){
		gamePanel.remove(o.getView());
		gameObjects.removeIf(gO -> gO.equals(o));
	}
	public void removePlayer(){
		gamePanel.remove(playerView);
	}
	public void removeEnemy(Enemy e){
		enemies.remove(e);
	}
	public void removeAllObject(){
		for(GameObject gO : gameObjects){
			gamePanel.remove(gO.getView());
		}
		gameObjects.clear();
	}
	public void removeAllEnemies(){
		enemies.clear();
		for(EnemyView eV: enemyViewList){
			gamePanel.remove(eV);
			enemyViewList.remove(eV);
		}
	}
	public void restartLevel(){
		clearLevel();
		startLevel();
	}
	public void pauseGame(){
		audioManager.pause();
		audioManager.pauseBackgroundMusic();
		audioManager.play("pause");
		gameOption.setGameState(GameState.PAUSE_STATE);
		gameWindow.add(pauseScreen.getStateScreenView());
		gameWindow.revalidate();
		gameWindow.repaint();
	}
	public void unPauseGame(){
		audioManager.resume();
		audioManager.resumeBackgroundMusic();
		gameOption.setGameState(GameState.PLAY_STATE);
		gameWindow.remove(pauseScreen.getStateScreenView());
	}

	public void removeDisplayedScreen(StateScreen displayedScreen){
		gameWindow.remove(displayedScreen.getStateScreenView());
		gameWindow.revalidate();
		gameWindow.repaint();
		removeListener(displayedScreen.getStateScreenView());
	}

	/**
	 *Questo metodo facilita lo scambio delle schermate visualizzate
	 * all'interno di una finestra di gioco. Rimuove la vecchia schermata,
	 * aggiunge la nuova schermata, imposta il focus e assicura che sia
	 * associato un key handler alla nuova schermata.
	 * @param oldScreen Il pannello che rappresenta la schermata attualmente visualizzata e da sostituire.
	 * @param newScreen Il pannello che rappresenta la nuova schermata da visualizzare.
	 */

	public void swapDisplayedScreen(JPanel oldScreen, JPanel newScreen){

		gameWindow.remove(oldScreen);
		gameWindow.getContentPane().removeAll();
		gameWindow.add(newScreen);

		newScreen.setFocusable(true);
		newScreen.requestFocusInWindow();

		StateScreenView stateScreenView = (StateScreenView)newScreen;
		if(!stateScreenView.isKeyHandlerPresent()) {
			newScreen.addKeyListener(keyHandler);
			stateScreenView.setKeyHandlerPresent(true);
		}

		setDisplayedScreen(newScreen);
	}
	public void setDisplayedScreen(JPanel newScreen){
		gameWindow.setContentPane(newScreen);
		gameWindow.revalidate();
		gameWindow.repaint();

	}

	public void removeListener(JPanel panel){
		panel.setFocusable(false);
		panel.removeKeyListener(keyHandler);
		panel.removeMouseListener(keyHandler);
		StateScreenView stateScreenView =(StateScreenView)panel;
		stateScreenView.setKeyHandlerPresent(false);
	}
	public List<GameObject> getGameObjects(){
		return gameObjects;
	}

	public String getLevel() {
		return level;
	}

	public CopyOnWriteArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public static void setLevel(String level) {
		GameController.level = level;
	}
}
