package es.upm.pproject.sokoban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import es.upm.pproject.sokoban.controller.MovementExecutor;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.levelExceptions.InvalidLevelException;
import es.upm.pproject.sokoban.view.SokobanScene;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage currentStage;
    static Level level;
    static int levelnum = 1;
    static MediaPlayer mediaPlayer;
    static boolean musicState;
    public static int  globalScore=0;

    /**
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        try {
            currentStage = stage;
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/sokovinhi.png")));
            stage.setTitle("SokoVinh");
            stage.setScene(ViewManager.getStartingScene());
            stage.show();
            mediaPlayer = new MediaPlayer(
                    new Media(new File("src/main/resources/audio/gameMusic.mp3").toURI().toString()));
            musicState = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetLevelCounter() {
        levelnum = 1;
    }
    
    public static void decreaseLevelCounter() {
        levelnum--;
    }

    public static void loadNextLevel() throws InvalidLevelException {
        try {
            level = new Level("src/main/resources/Levels/level" + levelnum++ + ".txt",false);
            Board board = level.getBoard();
            ViewManager.setGUIBoardSize(board);
            ViewManager.loadImages();
            MovementExecutor.initStacks();
            Scene scene = ViewManager.loadLevelState(level);
            currentStage.setScene(scene);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadLevel(String file) {
        try {
            level = new Level(file,false);
            Board board = level.getBoard();
            ViewManager.setGUIBoardSize(board);
            ViewManager.loadImages();
            MovementExecutor.initStacks();
            currentStage.setScene(ViewManager.loadLevelState(level));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidLevelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Scene getScene() {
        return currentStage.getScene();
    }

    // getter for the board
    public static Level getCurrentLevel() {
        return level;
    }

    /**
     * @param newScene
     */
    public static void setNewScene(Scene newScene) {
        currentStage.setScene(newScene);
        currentStage.show();
    }
    public static void toggleMusicOff() {
        mediaPlayer.stop();
    }
    public static void toggleMusicOn() {
        if (musicState)
        mediaPlayer.play();
    }

    public static boolean toggleMusic() {
        if (!musicState) {
            mediaPlayer.play();
            musicState = true;
        } else {
            mediaPlayer.stop();
            musicState = false;
        }
        return musicState;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    public static void setVolume(double value) {
        mediaPlayer.setVolume(value);
        Platform.runLater(((SokobanScene) currentStage.getScene()).getBoardGrid()::requestFocus);
    }
}
