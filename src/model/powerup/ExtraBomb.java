package model.powerup;
import model.entity.Player;
import view.obj.ObjectView;
import javax.swing.*;

public class ExtraBomb extends Powerup{
    public ExtraBomb(int col, int row){
        super(col,row);
        this.name = "extra_bomb";
        this.player = Player.getInstance();
        this.powerupView = new ObjectView(this);
    }
    @Override
    public JLabel getView() {
        return powerupView;
    }
    @Override
    public void applyEffect(Player player) {
        super.applyEffect(player);
        player.increaseBombNumber();
    }

    @Override
    public void removeEffect() {}

}
