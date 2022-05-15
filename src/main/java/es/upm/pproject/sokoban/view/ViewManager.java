package es.upm.pproject.sokoban.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.model.gamelevel.Level;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class ViewManager {
    public static Scene getStartingScene() {
        Image back = new Image("file:resources/titleImage.png");
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

    public static Scene loadLevelState(Level level) throws FileNotFoundException {

        //Crate scene components infrastructure
        HBox root = new HBox(5); //Contains all the elements
        VBox gridContainer = new VBox(); //Contains the graphical representation of the board status
        GridPane boardGrid = new GridPane(); //Graphical representation of the board status
        Scene scene = new Scene(root); //This is the object to be returned, must be modified by the code bellow
       
        //Calculate the tile dimension s depending on the bvoard size
        int boardSize =20;
        int tileSize = 720/boardSize;

        //TODO Load images for the box, wall, player, score pos, here we should select the final textures
        //This may be realized on a external method and declare the Images global in order to save procesing power

        //Test stuff, not relevant for future changes
        ImageView i00 = new ImageView(new Image(new FileInputStream("resources\\Blocks\\block_06.png"),tileSize,tileSize,true,false));
        ImageView i01 = new ImageView(new Image(new FileInputStream("resources\\Blocks\\block_07.png"),tileSize,tileSize,true,false));
        ImageView i10 = new ImageView(new Image(new FileInputStream("resources\\Blocks\\block_08.png"),tileSize,tileSize,true,false));
        ImageView i11 = new ImageView(new Image(new FileInputStream("resources\\Blocks\\block_06.png"),tileSize,tileSize,true,false));
        boardGrid.add(i00,0,0);
        boardGrid.add(i01,1,1);
        boardGrid.add(i10,2,2);
        boardGrid.add(i11,3,3);

        //TODO Read level board and load the tiles to the graphical interface
        

        //TODO Right side of the GUI, (load level interface, undo button, move counter, restart button...) 
        Label label = new Label("Nico gay");
        label.maxWidth(100);

        //TODO Attach all the interfaces nodes to the scene object and return it
        gridContainer.getChildren().add(boardGrid);
        root.getChildren().addAll(gridContainer,label);
        

        return scene;
    }
}
