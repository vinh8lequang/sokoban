package es.upm.pproject.sokoban.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
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

    private static Image boxImage;
    private static Image goalImage;
    private static Image groundImage;
    private static Image playerRightImage;
    private static Image playerLeftImage;
    private static Image wallImage;

    private static int boardSize;
    private static int tileSize;

    public static void setGUIBoardSize(Board board){
        int col = board.getCols();
        int row = board.getRows();
        if (col >= row) boardSize = col;
        else boardSize = row;
    }
    public static int getGUIBoardSize(){
        return boardSize;
    }


    public static void loadImages() throws FileNotFoundException{
        tileSize = 720/boardSize;
        boxImage = new Image(new FileInputStream("resources\\Tiles\\box.png"),tileSize,tileSize,true,false);
        goalImage = new Image(new FileInputStream("resources\\Tiles\\goal.png"),tileSize,tileSize,true,false);
        groundImage = new Image(new FileInputStream("resources\\Tiles\\ground.png"),tileSize,tileSize,true,false);
        playerRightImage = new Image(new FileInputStream("resources\\Tiles\\playerright.png"),tileSize,tileSize,true,false);
        playerLeftImage = new Image(new FileInputStream("resources\\Tiles\\playerleft.png"),tileSize,tileSize,true,false);
        wallImage = new Image(new FileInputStream("resources\\Tiles\\wall.png"),tileSize,tileSize,true,false);
    }

    public static Scene loadLevelState(Board board) throws FileNotFoundException {

        //Crate scene components infrastructure
        HBox root = new HBox(5); //Contains all the elements
        VBox gridContainer = new VBox(); //Contains the graphical representation of the board status
        GridPane boardGrid = new GridPane(); //Graphical representation of the board status
        Scene scene = new Scene(root); //This is the object to be returned, must be modified by the code bellow

        int rowMin,rowMax,colMin,colMax;
        if(!board.isSymmetric()){
            if(board.getRows() < board.getCols()){
                colMin = 0;
                colMax = boardSize;
                rowMin = boardSize/2 - board.getRows()/2;
                rowMax = boardSize/2 + board.getRows()/2;
            }
            else{
                rowMin = 0;
                rowMax = boardSize;
                colMin = boardSize/2 - board.getCols()/2;
                colMax = boardSize/2 + board.getCols()/2;
            }
        }
        else{
            rowMin=0;
            colMin=0;
            rowMax=boardSize;
            colMax=boardSize;
        }   


        //TODO Read level board and load the tiles to the graphical interface
        ImageView imageGrid[][] = new ImageView[boardSize][boardSize];
        for(int j =0;j < boardSize;j++){
            for(int i=0;i<boardSize;i++){
                if((j >= rowMin && j<=rowMax) && (i >= colMin && i<=colMax)){
                    Tile tile = board.getTile(j, i);
                    switch (tile.getTileType()) {
                        case BOX:
                            imageGrid[i][j] = new ImageView(boxImage);
                            break;
                        case GOAL:
                            imageGrid[i][j] = new ImageView(goalImage);
                            break;
                        case PLAYER:
                            imageGrid[i][j] = new ImageView(playerRightImage);
                            break;
                        case WALL:
                            imageGrid[i][j] = new ImageView(wallImage);
                            break;
                        default:
                            imageGrid[i][j] = new ImageView(groundImage);
                            break;
                    }
                    boardGrid.add(imageGrid[i][j],i,j);
                }
                
            }
        }

        //TODO Right side of the GUI, (load level interface, undo button, move counter, restart button...) 
        Label label = new Label("Nico gay");
        label.maxWidth(100);

        //TODO Attach all the interfaces nodes to the scene object and return it
        gridContainer.getChildren().add(boardGrid);
        root.getChildren().addAll(gridContainer,label);
        

        return scene;
    }
}
