package model.powerup;


import model.entity.Player;
import view.obj.ObjectView;
import javax.swing.*;

public class Hearth extends Powerup{
    public Hearth(int col, int row) {
        super(col, row);
        this.name = "hearth";
        this.player = Player.getInstance();
        this.powerupView = new ObjectView(this);
    }

    @Override
    public void applyEffect(Player player){
        super.applyEffect(player);
        if(player.getLives() <9) player.setLives(player.getLives()+1);
    }

    @Override
    public void removeEffect() {}

    @Override
    public JLabel getView() {
        return powerupView;
    }
}
