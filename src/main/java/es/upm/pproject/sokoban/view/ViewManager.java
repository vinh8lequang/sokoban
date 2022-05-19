package es.upm.pproject.sokoban.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.PlayerTile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class ViewManager {

    static SokobanScene CURRENTSCENE = null;

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
    private static Board CURRENTBOARD;

    public static void setGUIBoardSize(Board board) {
        int col = board.getCols();
        int row = board.getRows();
        if (col >= row)
            boardSize = col;
        else
            boardSize = row;
    }

    public static int getGUIBoardSize() {
        return boardSize;
    }

    public static void loadImages() throws FileNotFoundException {
        tileSize = 720 / boardSize;
        boxImage = new Image(new FileInputStream("resources\\Tiles\\box.png"), tileSize, tileSize, true, false);
        goalImage = new Image(new FileInputStream("resources\\Tiles\\goal.png"), tileSize, tileSize, true, false);
        groundImage = new Image(new FileInputStream("resources\\Tiles\\ground.png"), tileSize, tileSize, true, false);
        playerRightImage = new Image(new FileInputStream("resources\\Tiles\\playerright.png"), tileSize, tileSize, true,
                false);
        playerLeftImage = new Image(new FileInputStream("resources\\Tiles\\playerleft.png"), tileSize, tileSize, true,
                false);
        wallImage = new Image(new FileInputStream("resources\\Tiles\\wall.png"), tileSize, tileSize, true, false);
    }

    public static Scene loadLevelState(Board board) throws FileNotFoundException {

        SokobanScene scene = new SokobanScene(1280, 720, boardSize); // This is the object to be returned, must be
        CURRENTSCENE = scene;
        CURRENTBOARD = board;
        ImageView[][] imageGrid = scene.getImageGrid();
        // TODO Read level board and load the tiles to the graphical interface
        for (int j = 0; j < boardSize; j++) {
            for (int i = 0; i < boardSize; i++) {
                Tile tile = board.getTile(j, i);
                if (tile != null) {
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
                    scene.getBoardGrid().add(imageGrid[i][j], i, j);
                } else {
                    scene.getBoardGrid().add(new ImageView(groundImage), i, j);
                }
            }
        }

        // TODO Right side of the GUI, (load level interface, undo button, move counter,
        // restart button...)

        return scene;
    }

    public static void updateSceneOnInput(KeyCode code) {
        ImageView[][] imageGrid = CURRENTSCENE.getImageGrid();
        int desiredX = CURRENTBOARD.getPlayerPositionX();
        int desiredY = CURRENTBOARD.getPlayerPositionY();
        switch (code) {
            case UP:
                desiredY += 1;
                break;
            case DOWN:
                desiredY -= 1;
                break;

            case LEFT:
                desiredX -= 1;
                break;

            case RIGHT:
                desiredX += 1;
                break;
            default:
                LOGGER.error("Unknown input with no appropiate handle");
                break;
        }
        // TODO finish movement checking and image setting for the possible distinct
        // cases
        if (checkIfMovementIsPosible(desiredX, desiredY)) {
            CURRENTBOARD.setTile(desiredX, desiredY, TileType.PLAYER);
            CURRENTSCENE.getImageGrid()[desiredX][desiredY] = new ImageView(playerRightImage);
            CURRENTSCENE.getImageGrid()[CURRENTBOARD.getPlayerPositionX()][CURRENTBOARD
                    .getPlayerPositionY()] = new ImageView(groundImage);
            CURRENTBOARD.setPlayerPosition(desiredX, desiredY);
        }
    }

    private static boolean checkIfMovementIsPosible(int desiredX, int desiredY) {

        return false;
    }
}
