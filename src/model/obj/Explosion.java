package model.obj;

//import view.ExplosionView;
import controller.GameController;
import view.screen.GamePanel;
import view.obj.ObjectView;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Explosion extends GameObject {
    private ObjectView explosionView;
    private String type;
    public Explosion(Bomb bomb, String type){
        this.frameDelay = 5;
        this.setIsAnimated(true);
        switch (type) {
            case "center" -> {
                this.x = bomb.x;
                this.y = bomb.y;
            }
            case "end_right" -> {
                this.x = bomb.x + GamePanel.TILE_WIDTH;
                this.y = bomb.y;
            }
            case "end_up" -> {
                this.x = bomb.x;
                this.y = bomb.y - GamePanel.TILE_HEIGHT;
            }
            case "end_down" -> {
                this.x = bomb.x;
                this.y = bomb.y + GamePanel.TILE_HEIGHT;
            }
            case "end_left" -> {
                this.x = bomb.x - GamePanel.TILE_WIDTH;
                this.y = bomb.y;
            }
        }
        this.type = type;
        explosionView = new ObjectView(this);
        GameController gc = GameController.getInstance();
        for(GameObject gO : gc.getGameObjects()){
            if(gO.getView().getBounds().intersects(explosionView.getBounds())) {
                if(!(gO instanceof Exit))gO.destroy();
            }
        }
    }

    @Override
    public BufferedImage[] initializeAnimationSprites() {
        return super.initializeAnimationSprites(4,"Bombs and explosions/"+type+"_");

    }
    @Override
    public JLabel getView() {
        return explosionView;
    }
}
