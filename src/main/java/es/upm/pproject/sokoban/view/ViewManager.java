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
    private static boolean GOALTILE; // this variable is true when the player is in a goaltile

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
        boxImage = new Image(new FileInputStream("resources/Tiles/box.png"), tileSize, tileSize, true, false);
        goalImage = new Image(new FileInputStream("resources/Tiles/goal.png"), tileSize, tileSize, true, false);
        groundImage = new Image(new FileInputStream("resources/Tiles/ground.png"), tileSize, tileSize, true, false);
        playerRightImage = new Image(new FileInputStream("resources/Tiles/playerright.png"), tileSize, tileSize, true, false);
        playerLeftImage = new Image(new FileInputStream("resources/Tiles/playerleft.png"), tileSize, tileSize, true, false);
        wallImage = new Image(new FileInputStream("resources/Tiles/wall.png"), tileSize, tileSize, true, false);
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

        return (Scene) scene;
    }

    public static void updateSceneOnInput(KeyCode direction) {
        ImageView[][] imageGrid = CURRENTSCENE.getImageGrid();
        int tileToReplaceI = directionToIRow(direction, CURRENTBOARD.getPlayerPositionI());
        int tileToReplaceJ = directionToJCol(direction, CURRENTBOARD.getPlayerPositionJ());
        // TODO finish movement checking and image setting for the possible distinct
        // cases
        executeMovementIfPossible(direction, tileToReplaceI, tileToReplaceJ);
    }

    private static int directionToJCol(KeyCode direction, int j) {
        switch (direction) {
        case LEFT:
            j--;
            break;

        case RIGHT:
            j++;
            break;
        default:
            // LOGGER.error("Unknown input with no appropiate handle");
            break;
        }
        return j;
    }

    private static int directionToIRow(KeyCode direction, int i) {
        switch (direction) {
        case UP:
            i++;
            break;
        case DOWN:
            i--;
            break;
        default:
            // LOGGER.error("Unknown input with no appropiate handle");
            break;
        }
        return i;
    }

    public static void exchangeTilesAndImageGrid(int i1, int j1, int i2, int j2) {
        TileType toMoveTo = CURRENTBOARD.getTile(i2, j2).getTileType();
        boolean normalMove = true;
        // 1. player wants to move to a goal tile
        if (toMoveTo.equals(TileType.GOAL)) {
            // update the goaltile type so we know next move we only update the next tile to player
            GOALTILE = true;
            // We change the goaltile to the player but the old player one stays the same
            CURRENTBOARD.setTile(i2, j2, TileType.PLAYER);
            normalMove = false;
        }
        // 2. player wants to move out of what used to be a goaltile
        if (GOALTILE && normalMove) {
            // we change the destination tile to the player one
            CURRENTBOARD.setTile(i2, j2, TileType.PLAYER);
            // we set the old player tile to the goaltile
            CURRENTBOARD.setTile(i1, j1, TileType.GOAL);

            // update the goaltile type so we know next move we update both tiles
            GOALTILE = false;
            normalMove = false;
        }
        // 3. Normal case when we just exchange a player tile with a ground tile
        if (normalMove) {
            CURRENTBOARD.exchangeTiles(i1, j1, i2, j2);
        }
        TileType one = CURRENTBOARD.getTile(i1, j1).getTileType();
        TileType two = CURRENTBOARD.getTile(i2, j2).getTileType();
        // we also have to update the player position, tiles can be exchanged and neither have to be a player necessarily
        if (two.equals(TileType.PLAYER)) {
            CURRENTBOARD.setPlayerPosition(i2, j2);
        }
        // we have done the move in the board but we have to update the images
        CURRENTSCENE.getImageGrid()[i2][j2].setImage(getImage(one));
        CURRENTSCENE.getImageGrid()[i1][j1].setImage(getImage(two));
    }

    private static Image getImage(TileType tiletype) {
        if (GOALTILE) {
            return goalImage;
        }
        if (tiletype == TileType.PLAYER) {
            return playerRightImage;
        }
        if (tiletype == TileType.BOX) {
            return boxImage;
        }
        if (tiletype == TileType.GOAL) {
            GOALTILE = true;
        }
        return groundImage;
    }

    private static void executeMovementIfPossible(KeyCode direction, int tileToReplaceIRow, int tileToReplaceJCol) {
        if (CURRENTBOARD.getTile(tileToReplaceIRow, tileToReplaceJCol).getTileType().isMoveable()) {
            // player want to move a box
            int tileToReplaceXNext = directionToIRow(direction, tileToReplaceIRow);
            int tileToReplaceYNext = directionToJCol(direction, tileToReplaceJCol);
            // we get the next tile to the box in the direction
            // check if the next tile can be replaced
            if (CURRENTBOARD.getTile(tileToReplaceXNext, tileToReplaceYNext).getTileType().isReplaceable()) {
                // let's exchange them
                exchangeTilesAndImageGrid(tileToReplaceXNext, tileToReplaceYNext, tileToReplaceIRow, tileToReplaceJCol);
            }
        }
        if (CURRENTBOARD.getTile(tileToReplaceIRow, tileToReplaceJCol).getTileType().isReplaceable()) {
            exchangeTilesAndImageGrid(CURRENTBOARD.getPlayerPositionI(), CURRENTBOARD.getPlayerPositionJ(), tileToReplaceIRow,
                    tileToReplaceJCol);
        }
    }
}
