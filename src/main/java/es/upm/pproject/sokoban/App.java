package es.upm.pproject.sokoban;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    static Stage currentStage;
    static Level level;
    /**
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            currentStage = stage;
            stage.getIcons().add(new Image(new FileInputStream("src/main/resources/sokovinhi.png")));
            stage.setTitle("SokoVinh");
            level = new Level("src/main/resources/Levels/level1.txt");
            Board board = level.getBoard();
            ViewManager.setGUIBoardSize(board);
            ViewManager.loadImages();
            stage.setScene(ViewManager.loadLevelState(level));
            stage.show();
            // Media sound = new Media(new File("src/main/resources/audio/gameMusic.mp3").toURI().toString());
            // MediaPlayer mediaPlayer = new MediaPlayer(sound);
            // mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(date.toString());
        launch();
    }

}
