package view.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import model.entity.Player;


public class PlayerView extends EntityView{
    private Image resizedImage;
    private ImageIcon resizedIcon;
    public PlayerView(Player player) {
        super(player);
    }
    //  Serve per adattare l'immagine alla grandezza della JLabel
    public ImageIcon getResizedIcon(BufferedImage originalImage) {
        resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_FAST);
        resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }
    public void updateIcon() {
        this.setIcon(resizedIcon);
    }
}
