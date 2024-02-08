package controller;

import java.awt.image.BufferedImage;

public class ObjectAnimationController {
    private BufferedImage[] idleAnimation;
    private int frameCount = 0;
    private BufferedImage sprite;
    public ObjectAnimationController(BufferedImage[] idleAnimation){
        this.idleAnimation = idleAnimation;
    }
    public BufferedImage getCurrentSprite(int frameDelay) {
        sprite = idleAnimation[frameCount / frameDelay];
        frameCount = (frameCount + 1) % (idleAnimation.length * frameDelay);
        return sprite;
    }
}
