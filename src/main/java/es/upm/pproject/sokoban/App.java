package es.upm.pproject.sokoban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.sokoban.controller.MovementExecutor;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.levelExceptions.InvalidLevelException;
import es.upm.pproject.sokoban.view.SokobanScene;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    public static int globalScore = 0;

    private static Logger logger = LoggerFactory.getLogger(App.class);

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
            logger.info(stage.getTitle() + " started");
            stage.setScene(ViewManager.getStartingScene());
            logger.info(stage.getScene().toString());
            stage.show();
            mediaPlayer = new MediaPlayer(
                    new Media(new File("src/main/resources/audio/gameMusic.mp3").toURI().toString()));
            logger.info(mediaPlayer.toString());
            musicState = false;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public static void resetLevelCounter() {
        levelnum = 1;
        logger.info(Integer.toString(levelnum));
    }

    public static void decreaseLevelCounter() {
        levelnum--;
        logger.info(Integer.toString(levelnum));
    }

    public static void loadNextLevel() throws InvalidLevelException {
        try {
            level = new Level("src/main/resources/Levels/level" + levelnum++ + ".txt", false);
            logger.info(level.toString());
            Board board = level.getBoard();
            logger.info(board.toString());
            ViewManager.setGUIBoardSize(board);
            ViewManager.loadImages();
            MovementExecutor.initStacks();
            Scene scene = ViewManager.loadLevelState(level);
            logger.info(scene.toString());
            currentStage.setScene(scene);
            logger.info(currentStage.toString());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public static void loadLevel(String file) {
        try {
            level = new Level(file, false);
            logger.info(level.toString());
            Board board = level.getBoard();
            logger.info(board.toString());
            ViewManager.setGUIBoardSize(board);
            ViewManager.loadImages();
            MovementExecutor.initStacks();
            currentStage.setScene(ViewManager.loadLevelState(level));
            logger.info(currentStage.toString());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (InvalidLevelException e) {
            logger.error(e.getMessage());
        }
    }

    public static Scene getScene() {
        logger.info(currentStage.toString());
        return currentStage.getScene();
    }

    // getter for the board
    public static Level getCurrentLevel() {
        logger.info(level.toString());
        return level;
    }

    /**
     * @param newScene
     */
    public static void setNewScene(Scene newScene) {
        currentStage.setScene(newScene);
        currentStage.show();
        logger.info(currentStage.toString());
    }

    public static void toggleMusicOff() {
        mediaPlayer.stop();
        logger.info(mediaPlayer.toString());
    }

    public static void toggleMusicOn() {
        if (musicState)
            mediaPlayer.play();
        logger.info(mediaPlayer.toString());
    }

    public static boolean toggleMusic() {
        if (!musicState) {
            mediaPlayer.play();
            logger.info(mediaPlayer.toString());
            musicState = true;
            logger.info(Boolean.toString(musicState));
        } else {
            mediaPlayer.stop();
            logger.info(mediaPlayer.toString());
            musicState = false;
            logger.info(Boolean.toString(musicState));
        }
        return musicState;
    }

    public static void setVolume(double value) {
        mediaPlayer.setVolume(value);
        logger.info(Double.toString(mediaPlayer.getVolume()));
        Platform.runLater(((SokobanScene) currentStage.getScene()).getBoardGrid()::requestFocus);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info(BasicConfigurator.class.toString());
        launch();
    }
}
