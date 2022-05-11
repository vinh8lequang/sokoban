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
import es.upm.pproject.sokoban.view.ViewManager;
/**
 * JavaFX App
 */
public class App extends Application {
    private final static String IMAGE_LOC = "file:src/main/java/es/upm/pproject/sokoban/resources/Blocks/block_07.png";

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Sokoban");
            stage.setScene(ViewManager.getStartingScene());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch();
    }

}
