package model.utils;

import controller.AudioManager;
import controller.GameController;
import controller.TileManager;
import model.entity.Enemy;
import model.entity.Entity;
import model.entity.Player;
import model.obj.Exit;
import model.obj.Explosion;
import model.obj.GameObject;
import model.powerup.Powerup;
import model.screen.VictoryScreen;
import view.screen.GamePanel;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionChecker {
    private TileManager tileM;
    private GameController gameController;
    CopyOnWriteArrayList<GameObject> gameObjects;
    CopyOnWriteArrayList<Enemy> enemies;

    public CollisionChecker() {
        tileM = TileManager.getInstance();

    }

    public void checkTile(Entity entity) {

        int leftX = entity.getHitBox().x;
        int rightX = leftX + entity.getHitBox().width - (int) (1 * GamePanel.SCALE);
        int topY = entity.getHitBox().y;
        int bottomY = topY + entity.getHitBox().height - (int) (1 * GamePanel.SCALE);

        int leftCol = leftX / GamePanel.TILE_WIDTH;
        int rightCol = rightX / GamePanel.TILE_WIDTH;
        int topRow = topY / GamePanel.TILE_HEIGHT;
        int bottomRow = bottomY / GamePanel.TILE_HEIGHT;


        int tileNum1, tileNum2;
        int[][] mapNum = tileM.getMapNum();

        switch (entity.getDirection()) {
            case "up" -> {
                topRow = (topY - entity.getSpeed()) / GamePanel.TILE_HEIGHT;
                tileNum1 = mapNum[topRow][leftCol];
                tileNum2 = mapNum[topRow][rightCol];

                entity.setCollisionUp(tileM.getTiles()[tileNum1].isCollision() || tileM.getTiles()[tileNum2].isCollision());

            }
            case "down" -> {
                bottomRow = (bottomY + entity.getSpeed()) / GamePanel.TILE_HEIGHT;
                tileNum1 = mapNum[bottomRow][leftCol];
                tileNum2 = mapNum[bottomRow][rightCol];

                entity.setCollisionDown(tileM.getTiles()[tileNum1].isCollision() || tileM.getTiles()[tileNum2].isCollision());
            }
            case "right" -> {
                rightCol = (rightX + entity.getSpeed()) / GamePanel.TILE_WIDTH;
                tileNum1 = mapNum[topRow][rightCol];
                tileNum2 = mapNum[bottomRow][rightCol];

                entity.setCollisionRight(tileM.getTiles()[tileNum1].isCollision() || tileM.getTiles()[tileNum2].isCollision());
            }
            case "left" -> {
                leftCol = (leftX - entity.getSpeed()) / GamePanel.TILE_WIDTH;
                tileNum1 = mapNum[topRow][leftCol];
                tileNum2 = mapNum[bottomRow][leftCol];

                entity.setCollisionLeft(tileM.getTiles()[tileNum1].isCollision() || tileM.getTiles()[tileNum2].isCollision());
            }
        }
    }

    public void checkObject(Entity entity) {
        gameController = GameController.getInstance();
        gameObjects = (CopyOnWriteArrayList<GameObject>) gameController.getGameObjects();
        for (GameObject gameObject : gameObjects) {
            Rectangle entityBounds = entity.getHitBox().getBounds();
            Rectangle nextEntityBounds = new Rectangle(entityBounds);
            if (gameObject != null) {
                Rectangle objectBounds = gameObject.getView().getBounds();

                if (entityBounds.intersects(objectBounds)) {
                    if (gameObject instanceof Powerup && entity instanceof Player ) {
                        Player player = (Player) entity;
                        AudioManager.getInstance().play("item_get");
                        Powerup powerup = (Powerup) gameObject;
                        powerup.applyEffect(player);
                        powerup.delete();


                    } else if (gameObject instanceof Explosion) {
                        entity.damage();
                    } else if (gameObject instanceof Exit) {
                        if (gameController.getEnemies().isEmpty()) {

                            entity.setCanMove(false);
                            entity.setDownPressed(false);
                            entity.setUpPressed(false);
                            entity.setRightPressed(false);
                            entity.setLeftPressed(false);
                            //serve a evitare che il tasto rimanga premuto all'infinito dopo il respawn

                            GameOption.getInstance().setGameState(GameState.VICTORY_STATE); //sostituisci con la vittoria
                            AudioManager.getInstance().stopBackgroundMusic();
                            AudioManager.getInstance().playBackgroundMusic("stage_clear");
                            GameController.getInstance().swapDisplayedScreen(GamePanel.getInstance(), VictoryScreen.getInstance().getStateScreenView());
                            GameOption.increaseWonMatch();
                            GameOption.increaseMatchPlayed();
                            VictoryScreen.getInstance().update();
                        }

                    }
                } else if (gameObject.getCollision()) {

                    switch (entity.getDirection()) {
                        case "up" -> {
                            nextEntityBounds.y -= entity.getSpeed();
                            if (nextEntityBounds.intersects(objectBounds)) entity.setCollisionUp(true);
                        }
                        case "down" -> {
                            nextEntityBounds.y += entity.getSpeed();
                            if (nextEntityBounds.intersects(objectBounds)) entity.setCollisionDown(true);
                        }
                        case "right" -> {
                            nextEntityBounds.x += entity.getSpeed();
                            if (nextEntityBounds.intersects(objectBounds)) entity.setCollisionRight(true);
                        }
                        case "left" -> {
                            nextEntityBounds.x -= entity.getSpeed();
                            if (nextEntityBounds.intersects(objectBounds)) entity.setCollisionLeft(true);
                        }
                    }

                }

            }
        }
    }

    public void checkEntity(Enemy entity) {
        gameController = GameController.getInstance();
        enemies = gameController.getEnemies();
        Player player = Player.getInstance();
        for (Enemy e : enemies) {
            Rectangle entityBounds = entity.getHitBox().getBounds();
            Rectangle nextEntityBounds = new Rectangle(entityBounds);

            Rectangle playerBounds = player.getHitBox();

            if (e != null) {

                Rectangle otherEntityBounds = e.getHitBox().getBounds();
                switch (entity.getDirection()) {
                    case "up" -> {
                        nextEntityBounds.y -= entity.getSpeed();
                        if (nextEntityBounds.intersects(otherEntityBounds) && !e.equals(entity))
                            entity.setCollisionUp(true);
                        if (nextEntityBounds.intersects(playerBounds)) {

                            if(!player.isDead()) {
                                entity.setCollisionUp(true);
                                if (entity.CanDamage()) player.damage();
                            }
                        }
                    }
                    case "down" -> {
                        nextEntityBounds.y += entity.getSpeed();
                        if (nextEntityBounds.intersects(otherEntityBounds) && !e.equals(entity))
                            entity.setCollisionDown(true);
                        if (nextEntityBounds.intersects(playerBounds)) {
                            if(!player.isDead()) {
                                entity.setCollisionDown(true);
                                if (entity.CanDamage()) player.damage();
                            }
                        }
                    }
                    case "right" -> {
                        nextEntityBounds.x += entity.getSpeed();
                        if (nextEntityBounds.intersects(otherEntityBounds) && !e.equals(entity))
                            entity.setCollisionRight(true);
                        if (nextEntityBounds.intersects(playerBounds)) {

                            if(!player.isDead()) {
                                entity.setCollisionRight(true);
                                if (entity.CanDamage()) player.damage();
                            }
                        }
                    }
                    case "left" -> {
                        nextEntityBounds.x -= entity.getSpeed();
                        if (nextEntityBounds.intersects(otherEntityBounds) && !e.equals(entity))
                            entity.setCollisionLeft(true);
                        if (nextEntityBounds.intersects(playerBounds)) {

                            if(!player.isDead()) {
                                entity.setCollisionLeft(true);
                                if (entity.CanDamage()) player.damage();
                            }
                        }
                    }
                }
            }
        }
    }
}
