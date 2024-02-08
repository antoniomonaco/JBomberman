package controller;

import java.awt.image.BufferedImage;

public class EntityAnimationController {
    private BufferedImage[] standingSprites;
    private BufferedImage[] walkingSpritesUp;
    private BufferedImage[] walkingSpritesDown;
    private BufferedImage[] walkingSpritesRight;
    private BufferedImage[] walkingSpritesLeft;
    private BufferedImage[] deathSprites;
    private int frameCount = 0;
    private final int walkingFrameDelay = 8;
    private final int dyingFrameDelay = 8;
    private  String lastDirection = "down";

    public EntityAnimationController(BufferedImage[] standingSprites, BufferedImage[] walkingSpritesUp,
                                     BufferedImage[] walkingSpritesDown, BufferedImage[] walkingSpritesRight,
                                     BufferedImage[] walkingSpritesLeft,BufferedImage[] deathSprites)  {
        this.standingSprites = standingSprites;
        this.walkingSpritesUp = walkingSpritesUp;
        this.walkingSpritesDown = walkingSpritesDown;
        this.walkingSpritesRight = walkingSpritesRight;
        this.walkingSpritesLeft = walkingSpritesLeft;
        this.deathSprites = deathSprites;
    }

    /**
     * Questo metodo è responsabile di determinare e recuperare lo sprite (immagine)
     * appropriato in base allo stato attuale di un personaggio di gioco.
     * Lo sprite del personaggio cambia a seconda di vari fattori come il movimento,
     * la morte e la direzione del movimento.
     * @param isMovingNow Indica se il personaggio del giocatore è attualmente in movimento.
     * @param upPressed Indica se il tasto di movimento 'w' è premuto
     * @param downPressed Indica se il tasto di movimento 's' è premuto
     * @param leftPressed Indica se il tasto di movimento 'a' è premuto
     * @param rightPressed Indica se il tasto di movimento 'r' è premuto
     * @param isDead Indica se il personaggio del giocatore è attualmente in uno stato di morte.
     * @return BufferedImage: L'immagine che rappresenta lo sprite attuale del personaggio del giocatore.
     */
    public BufferedImage getCurrentSprite(boolean isMovingNow, boolean upPressed, boolean downPressed,
                                          boolean leftPressed, boolean rightPressed, boolean isDead) {

        if (isDead) {
            if (frameCount < deathSprites.length * dyingFrameDelay) {
                BufferedImage sprite = deathSprites[frameCount / dyingFrameDelay];
                frameCount++;
                lastDirection = "down";
                return sprite;
            } else {
                return null; // Fine dell'animazione di morte
            }
        }
        else if (isMovingNow) {
            String direction = "down"; // Default
            if (upPressed)
                direction = "up";
            else if (downPressed)
                direction = "down";
            else if (leftPressed)
                direction = "left";
            else if (rightPressed)
                direction = "right";

            if (!direction.equals(lastDirection)) {
                // Se la direzione di movimento è cambiata, resetto il frameCount e aggiorno lastDirection
                frameCount = 0;
                lastDirection = direction;
            }

            BufferedImage sprite = null;
            int spriteIndex = frameCount / walkingFrameDelay; // Calcola l'indice del frame

            BufferedImage[] currentDirectionSprites = switch (direction) {
                case "up" -> walkingSpritesUp;
                case "down" -> walkingSpritesDown;
                case "right" -> walkingSpritesRight;
                case "left" -> walkingSpritesLeft;
                default -> null;
            };

            if (currentDirectionSprites != null) {
                sprite = currentDirectionSprites[spriteIndex % currentDirectionSprites.length];

                // Incrementa il frameCount solo se il player si sta muovendo
                frameCount = (frameCount + 1) % (currentDirectionSprites.length * walkingFrameDelay);
            }
            return sprite;
        } else {
            // Se il player non sta camminando, mostra lo sprite fermo nella direzione in cui si è mosso per ultima
            return switch (lastDirection) {
                case "up" -> standingSprites[0];
                case "down" -> standingSprites[1];
                case "right" -> standingSprites[2];
                case "left" -> standingSprites[3];
                default -> null;
            };
        }
    }
}

