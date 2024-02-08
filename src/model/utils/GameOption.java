package model.utils;

import controller.GameController;
import model.entity.Player;
import view.screen.LevelBarLabel;

import java.io.*;

public class GameOption {
    private GameState gameState = GameState.MENU_STATE;
    private GameController gameController;
    private static String playerName;
    private static GameOption instance;
    private boolean exitSpawned; // serve a vedere se l'uscita è stata spawnata
    private static int highestScore;
    private static int wonMatch;
    private static int lostMatch;
    private static int characterNum;
    private static int matchPlayed;
    private static String nickName;
    private static int xp;
    private static int playerLevel;
    private static Boolean firstTimePlaying;
    public static GameOption getInstance() {
        if(instance == null) instance = new GameOption();
        return instance;
    }
    private GameOption(){
        loadSave();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGameController(GameController gC) {
        this.gameController = gC;
    }

    public static void save(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("res/Saves/save.txt"));

            //score
            bw.write(String.valueOf(highestScore));

            bw.newLine();
            //matchPlayed
            bw.write(String.valueOf(matchPlayed));

            bw.newLine();
            //wonMatch
            bw.write(String.valueOf(wonMatch));

            bw.newLine();
            //lostMatch
            bw.write(String.valueOf(lostMatch));

            bw.newLine();
            //playerName
            bw.write(playerName);

            bw.newLine();
            //Character Number
            bw.write(String.valueOf(characterNum));

            bw.newLine();
            //Player Xp
            bw.write(String.valueOf(xp));

            bw.newLine();
            //Player Level
            bw.write(String.valueOf(playerLevel));

            bw.newLine();
            //First Time Playing
            if(firstTimePlaying) bw.write(String.valueOf(1));
            else bw.write(String.valueOf(0));

            bw.newLine();
            //NickName
            bw.write(nickName);

            bw.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void loadSave(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("res/Saves/save.txt"));
            String s = br.readLine();

            //score
            highestScore = Integer.parseInt(s);

            s = br.readLine();
            //matchPlayed
            matchPlayed = Integer.parseInt(s);

            s = br.readLine();
            //wonMatch
            wonMatch = Integer.parseInt(s);

            s = br.readLine();
            //lostMatch
            lostMatch = Integer.parseInt(s);

            s = br.readLine();
            //playerName
            playerName = s;

            s = br.readLine();
            //Character Number
            characterNum = Integer.parseInt(s);

            s = br.readLine();
            //player xp
            xp = Integer.parseInt(s);

            s = br.readLine();
            //player level
            playerLevel = Integer.parseInt(s);

            s = br.readLine();
            //player level
            if(Integer.parseInt(s) == 0) firstTimePlaying = false;
            else firstTimePlaying = true;

            s = br.readLine();
            //NickName
            nickName = s;

            br.close();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isExitSpawned() {
        return exitSpawned;
    }

    public void setExitSpawned(boolean exitSpawned) {
        this.exitSpawned = exitSpawned;
    }

    public static int getHighestScore() {
        return highestScore;
    }

    public static void setHighestScore(int score){
        if(score > highestScore) highestScore = score;
        save();
    }

    public static int getWonMatch() {
        return wonMatch;
    }

    public static void increaseWonMatch() {
        wonMatch = wonMatch + 1;
        setHighestScore(Player.getInstance().getActualScore());
        save();
    }

    public static int getLostMatch() {
        return lostMatch;
    }

    public static void increaseLostMatch() {
        lostMatch = lostMatch+1;
        save();
    }
    public static void setCharNum(int charNum) {
        characterNum = charNum;
        save();
    }

    public static int getCharacterNum() {
        return characterNum;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        Player.getInstance().setName(playerName);
        save();
    }
    public static void increaseMatchPlayed() {
        matchPlayed = matchPlayed+1;
        save();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public static int getXp() {
        return xp;
    }

    public void increaseXp(int xp) {
        this.xp += xp;
        if (this.xp >= 100){
            playerLevel += 1;
            this.xp = 0;

        }
        LevelBarLabel.getInstance().update();
        save();
    }

    public static int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public static Boolean getFirstTimePlaying() {
        return firstTimePlaying;
    }

    public static void setFirstTimePlaying(Boolean firstTimePlaying) {
        GameOption.firstTimePlaying = firstTimePlaying;
        save();
    }
}
