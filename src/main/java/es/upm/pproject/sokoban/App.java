package es.upm.pproject.sokoban;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.LevelLoader;
import es.upm.pproject.sokoban.view.ViewManager;

/**
 * JavaFX App
 */
public class App extends Application {

    static Stage currentStage;

    
    /** 
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        try {
            currentStage = stage;
            // stage.getIcons().add(new Image(new FileInputStream("resources/sokovinhi.png")));
            stage.setTitle("SokoVinh");
            Level level = new Level("resources/Levels/level.txt");
            Board board = level.getBoard();
            ViewManager.setGUIBoardSize(board);
            ViewManager.loadImages();
            stage.setScene(ViewManager.loadLevelState(level));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        launch();
    }

}
