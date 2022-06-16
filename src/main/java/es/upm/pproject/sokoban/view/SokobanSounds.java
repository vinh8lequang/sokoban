package es.upm.pproject.sokoban.view;

import java.io.File;

import javafx.scene.media.AudioClip;

public class SokobanSounds {

    static AudioClip winnerSound;
    static AudioClip wallSound;
    static AudioClip correctSound;
    static AudioClip playerMovingSound;
    static AudioClip boxMovingSound;

    public static void playWallSound() {
        AudioClip wallSound = new AudioClip(new File("src/main/resources/audio/wall.wav").toURI().toString());
        wallSound.play();
    }

    public static void playCorrectSound() {
        correctSound = new AudioClip(new File("src/main/resources/audio/found.wav").toURI().toString());
        correctSound.play();
    }

    public static void playBoxMovingSound() {
        boxMovingSound = new AudioClip(new File("src/main/resources/audio/box.wav").toURI().toString());
        boxMovingSound.play();
    }

    public static void playPlayerMovingSound() {
        playerMovingSound = new AudioClip(new File("src/main/resources/audio/normalMove.wav").toURI().toString());
        playerMovingSound.play();
    }

    public static void playWinnerSound() {
        winnerSound = new AudioClip(new File("src/main/resources/audio/winner.mp3").toURI().toString());
        winnerSound.play();
    }

    public static void stopWinnerSound() {
        winnerSound.stop();
    }
}
