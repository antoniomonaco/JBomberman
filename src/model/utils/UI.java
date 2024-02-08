package model.utils;

import controller.GameController;
import model.entity.Player;
import view.screen.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    private BufferedImage[] numbers;
    private static UI instance = null;
    private Player player;
    private GamePanel gamePanel;
    public GameController gameController;
    private HUD hud;
    private int numberWidth;
    private int numberHeight;
    private GameOption gameOption;
    Font font;

    public static UI getInstance(){
        if(instance == null) instance = new UI();
        return instance;
    }
    private UI(){
        gameOption = GameOption.getInstance();
        numberWidth = 8;
        numberHeight = 14;
        hud = new HUD();
        loadNumbers();

    }

    private void loadNumbers(){
        String path = "/Font/Numbers";
        numbers = new BufferedImage[10];
        for(int i = 0; i <=9; i++) {
            try {
                numbers[i] = ImageIO.read(getClass().getResourceAsStream(path + "/"+i+".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void draw(Graphics2D g2){
        if (player == null) player = Player.getInstance();
        GameState gameState = gameOption.getGameState();
        if (gameState == GameState.PLAY_STATE || gameState == GameState.PAUSE_STATE ) {
            //HUD
            g2.drawImage(hud.getSprite(), 0, 0, (int) (GamePanel.SCREEN_WIDTH), (int) (32 * GamePanel.SCALE), null);
            //player lives
            if (player.getLives() >= 0)
                g2.drawImage(numbers[player.getLives()], (int) (((3.2)*numberWidth) *( GamePanel.SCALE)), (int) ((0.65* numberHeight ) *(GamePanel.SCALE)), (int) (numberWidth * GamePanel.SCALE), (int) (numberHeight * GamePanel.SCALE), null);

            int arrayNum = 0;
            for(int i = 7; i <14; i ++){

                g2.drawImage(numbers[player.getScoreArray()[arrayNum]], (int) (((i-0.5)*numberWidth) *( GamePanel.SCALE)), (int) ((0.65* numberHeight ) *(GamePanel.SCALE)), (int) (numberWidth * GamePanel.SCALE), (int) (numberHeight * GamePanel.SCALE), null);
                arrayNum ++;
            }

        }

        else if (gameState == GameState.PROFILE_STATE){

            InputStream is = getClass().getResourceAsStream("/Font/monogram.ttf");
            try {
                font = Font.createFont(Font.TRUETYPE_FONT,is);
            } catch (FontFormatException | IOException e) {
                throw new RuntimeException(e);
            }


            g2.setColor(Color.WHITE);
            g2.setFont(font);
            //g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,GamePanel.SCALE*10));
            g2.drawString("Highest score: "+GameOption.getHighestScore(),10,90);
            g2.drawString("Won match: "+GameOption.getWonMatch(),10,120);
            g2.drawString("Lost match: "+GameOption.getLostMatch(),10,150);
            g2.drawString("xp: "+GameOption.getXp(),10,180);


            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (float) (GamePanel.SCALE*8.5)));
            g2.drawString("Press to select ",(int) (112*GamePanel.SCALE),(int) (215*GamePanel.SCALE));

            if(GameOption.getInstance().getNickName() != null) {
                g2.drawString(GameOption.getInstance().getNickName(),(int) (125*GamePanel.SCALE),(int) (90*GamePanel.SCALE));
            }


        }
    }


}
