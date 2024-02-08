package view.entity;

import model.entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyView extends EntityView{
    private Image resizedImage;
    private ImageIcon resizedIcon;
    public EnemyView(Entity entity) {
        super(entity);
    }
    public ImageIcon getResizedIcon(BufferedImage originalImage) {
        resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST);
        resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    public void updateIcon() {
        this.setIcon(resizedIcon);
    }
}
