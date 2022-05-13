package es.upm.pproject.sokoban.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ViewManager {
    public static Scene getStartingScene() {
        Image back = new Image("file:src/main/java/es/upm/pproject/sokoban/model/   resources/titleImage.png");
        ImageView background = new ImageView();
        Group root = new Group();
        Scene scene = new Scene(root);
        background.fitWidthProperty().bind(scene.widthProperty());
        background.setImage(back);
        background.setPreserveRatio(true);
        background.setSmooth(true);
        background.setCache(true);
        root.getChildren().add(background);
        return scene; 
    }
}
