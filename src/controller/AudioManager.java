package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {

    private static AudioManager instance;
    private String path;
    private Clip clip;
    private Clip backgroundClip;


    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Vado a ottenere il percorso assoluto del progetto perché a differenza di quanto accade con le immagini,
     * non posso accedere direttamente alla cartella res del progetto con il metodo getResourceAsStream().
     * In questo modo la cartella con i suoni viene trovata a prescindere dall'utente che esegue il progetto
     */
    private AudioManager() {
        String projectPath = System.getProperty("user.dir");
        path = projectPath + "/res/Sound/";
    }

    public void playBackgroundMusic(String filename){
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path+filename+".wav"));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);
            backgroundClip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        }
    }
    public void stopBackgroundMusic(){
        if(backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }
    public void pauseBackgroundMusic(){
        if(backgroundClip != null) backgroundClip.stop();
    }
    public void resumeBackgroundMusic() {
        if(backgroundClip != null) backgroundClip.start();
    }

    public void play(String filename) {

        try {
            InputStream in = new BufferedInputStream(new FileInputStream(path+filename+".wav"));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        }

    }
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

}
