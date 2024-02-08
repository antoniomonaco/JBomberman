package controller;

import java.awt.*;
import java.awt.event.*;

import model.entity.Player;
import model.screen.*;
import model.utils.GameOption;
import model.utils.GameState;
import org.w3c.dom.Text;
import view.screen.CharacterLabel;
import view.screen.GamePanel;
import view.screen.LevelBarLabel;

import javax.swing.*;


public class KeyHandler implements KeyListener, MouseListener, MouseMotionListener {

	private static KeyHandler instance;
	private Player player; // = Player.getInstance();
	private GameOption gameOption = GameOption.getInstance();
	private GameController gC;
	private StateScreen pauseScreen = PauseScreen.getInstance();
	private StateScreen gameOverScreen = GameOverScreen.getInstance();
	private StateScreen menuScreen = MenuScreen.getInstance();
	private StateScreen profileScreen = ProfileScreen.getInstance();
	private LevelSelectScreen levelSelectScreen = LevelSelectScreen.getInstance();
	private VictoryScreen victoryScreen = VictoryScreen.getInstance();
	private AudioManager audioManager = AudioManager.getInstance();

	public static KeyHandler getInstance() {
		if(instance == null) instance = new KeyHandler();
		return instance;
	}

	private void KeyHandler(){ // cerca di capire perchè il costruttore crea problemi
		//player = Player.getInstance();
		//gameOption = GameOption.getInstance();

	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		gC = GameController.getInstance();
		int code = e.getKeyCode();

		if(gameOption.getGameState() == GameState.PLAY_STATE) {
			if(player == null) player = Player.getInstance();

			if (code == KeyEvent.VK_W) {
				player.setUpPressed(true);
			}
			if (code == KeyEvent.VK_S) {
				player.setDownPressed(true);
			}
			if (code == KeyEvent.VK_A) {
				player.setLeftPressed(true);
			}
			if (code == KeyEvent.VK_D) {
				player.setRightPressed(true);
			}

			if (code == KeyEvent.VK_SPACE) {
				player.releaseBomb();
			}
			if (code == KeyEvent.VK_ESCAPE) {
				gC.pauseGame();

			}
		}
		else if(gameOption.getGameState() == GameState.PAUSE_STATE){
			if (code == KeyEvent.VK_ESCAPE) {
				gC.unPauseGame();
			}
			if (code == KeyEvent.VK_W) {

				pauseScreen.decreasePointer();
			}
			if (code == KeyEvent.VK_S) {

				pauseScreen.increasePointer();
			}
			if (code == KeyEvent.VK_SPACE) {
				if(pauseScreen.getPointer() == 0) gC.unPauseGame();		//resume
				if(pauseScreen.getPointer() == 1){
					GameOption.getInstance().setGameState(GameState.MENU_STATE);
					gC.clearLevel();
					gC.swapDisplayedScreen(GamePanel.getInstance(),menuScreen.getStateScreenView());
					menuScreen.update();
				}
				if(pauseScreen.getPointer() == 2) System.exit(0); //quit
			}
		}
		else if(gameOption.getGameState() == GameState.GAME_OVER_STATE){
			if (code == KeyEvent.VK_W) {
				gameOverScreen.decreasePointer();
			}
			if (code == KeyEvent.VK_S) {
				gameOverScreen.increasePointer();
			}
			if (code == KeyEvent.VK_SPACE) {
				if(gameOverScreen.getPointer() == 0) {
					AudioManager.getInstance().stopBackgroundMusic();
					gC.restartLevel();		//yes -> restart
					GameOption.getInstance().setGameState(GameState.PLAY_STATE);
					gC.swapDisplayedScreen(gameOverScreen.getStateScreenView(),GamePanel.getInstance());
				}
				if(gameOverScreen.getPointer() == 1) System.exit(0); //no --> quit
			}
		}
		else if(gameOption.getGameState() == GameState.MENU_STATE){
			if (code == KeyEvent.VK_W) {
				menuScreen.decreasePointer();
			}
			if (code == KeyEvent.VK_S) {
				menuScreen.increasePointer();
			}
			if (code == KeyEvent.VK_SPACE) {
				audioManager.play("title_screen_select");
				if(menuScreen.getPointer() == 0) {
					gC.swapDisplayedScreen(menuScreen.getStateScreenView(),levelSelectScreen.getStateScreenView());
					GameOption.getInstance().setGameState(GameState.LEVEL_SELECT_STATE);
					levelSelectScreen.update();

				}
				else{
					gC.swapDisplayedScreen(menuScreen.getStateScreenView(),profileScreen.getStateScreenView());
					GameOption.getInstance().setGameState(GameState.PROFILE_STATE);
					profileScreen.update();

					JLabel TextLevel = new JLabel("Level = " + GameOption.getPlayerLevel());
					int labelSize = 99;
					TextLevel.setBounds((int) (1.28*labelSize*GamePanel.SCALE),(int) (0.475*labelSize*GamePanel.SCALE), (int) (labelSize* GamePanel.SCALE), (int) (labelSize*GamePanel.SCALE));
					TextLevel.setForeground(Color.WHITE);

					profileScreen.getStateScreenView().add(TextLevel);
					profileScreen.getStateScreenView().add(CharacterLabel.getInstance()); // aggiungo la label della selezionde del player
					profileScreen.getStateScreenView().add(LevelBarLabel.getInstance()); //


				}
			}
		}
		else if(gameOption.getGameState() == GameState.PROFILE_STATE){
			if (code == KeyEvent.VK_ESCAPE) {
				gC.swapDisplayedScreen(profileScreen.getStateScreenView(),menuScreen.getStateScreenView());
				GameOption.getInstance().setGameState(GameState.MENU_STATE);
				menuScreen.update();
			}
			if (code == KeyEvent.VK_D) {
				CharacterLabel.getInstance().increasePointer();
				GameOption.setCharNum(CharacterLabel.getInstance().getPointer()+1);
			}
			if (code == KeyEvent.VK_A) {
				CharacterLabel.getInstance().decreasePointer();
				GameOption.setCharNum(CharacterLabel.getInstance().getPointer()+1);
			}
		}
		else if(gameOption.getGameState() == GameState.LEVEL_SELECT_STATE) {
			
			
			if (code == KeyEvent.VK_ESCAPE) {
				gC.swapDisplayedScreen(levelSelectScreen.getStateScreenView(), menuScreen.getStateScreenView());
				GameOption.getInstance().setGameState(GameState.MENU_STATE);
				menuScreen.update();
			}
			if (code == KeyEvent.VK_W) {
				levelSelectScreen.decreasePointer();
			}
			if (code == KeyEvent.VK_S) {
				levelSelectScreen.increasePointer();
			}
			if (code == KeyEvent.VK_SPACE) {
				audioManager.play("title_screen_select");
				if(levelSelectScreen.getPointer() == 0) gC.setLevel("level1");
				else if(levelSelectScreen.getPointer() == 1) gC.setLevel("level2");
				else if(levelSelectScreen.getPointer() == 2) gC.setLevel("level3");
				gC.swapDisplayedScreen(levelSelectScreen.getStateScreenView(), GamePanel.getInstance());
				gC.startLevel();
				GameOption.getInstance().setGameState(GameState.PLAY_STATE);
			}
		}
		else if(gameOption.getGameState() == GameState.VICTORY_STATE){

			if (code == KeyEvent.VK_W) {
				victoryScreen.decreasePointer();
			}
			if (code == KeyEvent.VK_S) {
				victoryScreen.increasePointer();
			}
			if (code == KeyEvent.VK_SPACE) {
				if(victoryScreen.getPointer() == 0) {
					int actualLevel = levelSelectScreen.getPointer()+1;
					levelSelectScreen.increasePointer();
					if(actualLevel < levelSelectScreen.getSequence().length){
						actualLevel++;
						gC.setLevel("level"+actualLevel);
					}
					else gC.setLevel("level"+1);
					AudioManager.getInstance().stopBackgroundMusic();
					gC.restartLevel();		//yes -> restart
					GameOption.getInstance().setGameState(GameState.PLAY_STATE);
					gC.swapDisplayedScreen(victoryScreen.getStateScreenView(),GamePanel.getInstance());
				}
				if(victoryScreen.getPointer() == 1) System.exit(0); //no --> quit
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(gameOption.getGameState() == GameState.PLAY_STATE) {
			if (code == KeyEvent.VK_W) {
				player.setUpPressed(false);
			}
			if (code == KeyEvent.VK_S) {
				player.setDownPressed(false);
			}
			if (code == KeyEvent.VK_A) {
				player.setLeftPressed(false);
			}
			if (code == KeyEvent.VK_D) {
				player.setRightPressed(false);
			}
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(player != null){
			if (gameOption.getGameState() == GameState.PLAY_STATE) {

				if (e.getButton() == MouseEvent.BUTTON1) { // Tasto sinistro del mouse
					player.followMouse(e.getX(), e.getY());
				}
				if (e.getButton() == MouseEvent.BUTTON3) { // Tasto destro del mouse
					player.releaseBomb();
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(player != null) {
			if (gameOption.getGameState() == GameState.PLAY_STATE) {
				if (e.getButton() == MouseEvent.BUTTON1) { // Tasto sinistro del mouse
					player.setUpPressed(false);
					player.setDownPressed(false);
					player.setLeftPressed(false);
					player.setRightPressed(false);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(player != null) {
			if (gameOption.getGameState() == GameState.PLAY_STATE) {
				if (e.getButton() == MouseEvent.BUTTON1) { // Tasto sinistro del mouse
					player.followMouse(e.getX(), e.getY());
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(player != null) {
			if (gameOption.getGameState() == GameState.PLAY_STATE) player.followMouse(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
