package model.powerup;

import model.entity.Player;
import view.obj.ObjectView;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Skate extends Powerup{
    public Skate(int col,int row){
        super(col,row);
        this.name = "skate";
        this.player = Player.getInstance();
        this.powerupView = new ObjectView(this);
    }
    @Override
    public void applyEffect(Player player) {
        super.applyEffect(player);
        player.setSpeed(player.getSpeed() + 2);
        removeEffect();
    }

    @Override
    public void removeEffect(){
        Timer timer = new Timer(5000, e -> {
            //player.setSpeed(player.getDefaultSpeed());
            player.setSpeed(3);
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public JLabel getView() {
        return powerupView;
    }
    public BufferedImage getSprite(){
        return sprite;
    }


}
