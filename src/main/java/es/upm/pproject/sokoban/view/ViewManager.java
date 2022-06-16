package es.upm.pproject.sokoban.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewManager {
    
    private static int WIDTH = 960;
    private static int HEIGHT = 720;

    static SokobanScene CURRENTSCENE = null;
    static Stage CURRENTSTAGE = null;

    /**
     * @return Scene
     */
    public static Scene getStartingScene() {
        Image back = new Image("file:src/main/resources/maintitle.png");
        ImageView background = new ImageView();
        Group root = new Group();
        Scene scene = new Scene(root);
        background.fitWidthProperty().bind(scene.widthProperty());
        background.setImage(back);
        background.setPreserveRatio(true);
        background.setSmooth(true);
        background.setCache(true);

        Button playButton = new Button("Play Game");
        playButton.setFont(new Font("Impact", 45));
        playButton.setTranslateX(110);
        playButton.setTranslateY(400);
        playButton.setStyle("-fx-background-color: #ffff00");
        //TODO Cambiar al nivel 
        playButton.setOnAction((event) -> {
            App.loadNextLevel();
          });

        root.getChildren().addAll(background,playButton);
        return scene;
    }

    private static Image boxImage;
    private static Image goalImage;
    private static Image groundImage;
    private static Image playerRightImage;
    private static Image playerLeftImage;
    private static Image wallImage;
    private static Image boxInGoalImage;

    private static int boardSize;
    private static int tileSize;
    private static Board CURRENTBOARD;
    private static boolean GOALTILE; // this variable is true when the player is in a goaltile
    private static Level CURRENTLEVEL;

    /**
     * @param board
     */
    public static void setGUIBoardSize(Board board) {
        int col = board.getCols();
        int row = board.getRows();
        if (col >= row)
            boardSize = col;
        else
            boardSize = row;
    }

    /**
     * @return int
     */
    public static int getGUIBoardSize() {
        return boardSize;
    }

    /**
     * @throws FileNotFoundException
     */
    public static void loadImages() throws FileNotFoundException {
        tileSize = 720 / boardSize;
        boxImage = new Image(new FileInputStream("src/main/resources/Tiles/box.png"), tileSize, tileSize, true, false);
        goalImage = new Image(new FileInputStream("src/main/resources/Tiles/goal.png"), tileSize, tileSize, true,
                false);
        groundImage = new Image(new FileInputStream("src/main/resources/Tiles/ground.png"), tileSize, tileSize, true,
                false);
        playerRightImage = new Image(new FileInputStream("src/main/resources/Tiles/playerright.png"), tileSize,
                tileSize, true, false);
        playerLeftImage = new Image(new FileInputStream("src/main/resources/Tiles/playerleft.png"), tileSize, tileSize,
                true, false);
        wallImage = new Image(new FileInputStream("src/main/resources/Tiles/wall.png"), tileSize, tileSize, true,
                false);
        boxInGoalImage = new Image(new FileInputStream("src/main/resources/Tiles/boxingoal.png"), tileSize, tileSize,
                true, false);
    }

    /**
     * @param level
     * @return Scene
     * @throws FileNotFoundException
     */
    public static Scene loadLevelState(Level level) throws FileNotFoundException {

        SokobanScene scene = new SokobanScene(WIDTH, HEIGHT, boardSize, level); // This is the object to be returned,
                                                                                // must be
        CURRENTSCENE = scene;
        CURRENTBOARD = level.getBoard();
        CURRENTLEVEL = level;
        ImageView[][] imageGrid = scene.getImageGrid();
        // TODO Read level board and load the tiles to the graphical interface
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Tile tile = CURRENTBOARD.getTile(i, j);
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
                    scene.getBoardGrid().add(imageGrid[i][j], j, i);
                } else {
                    scene.getBoardGrid().add(new ImageView(groundImage), j, i);
                }
            }
        }

        return scene;
    }

    public static void showWinnerScene() {
        SokobanSounds.playWinnerSound();
        Label winnerText = new Label();
        winnerText.setText("You have won");
        winnerText.setStyle("-fx-font: 70 arial;");
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(winnerText);
        Scene newScene = new Scene(root, WIDTH, HEIGHT);
        App.setNewScene(newScene);
    }

    /**
     * @param tiletype
     * @return Image
     */
    private static Image getImage(TileType tiletype) {
        if (tiletype == TileType.PLAYER) {
            return playerRightImage;
        }
        if (tiletype == TileType.BOX) {
            return boxImage;
        }
        if (tiletype == TileType.GOAL) {
            return goalImage;
        }
        if (tiletype == TileType.BOXINGOAL) {
            return boxInGoalImage;
        }
        if (tiletype == TileType.PLAYERINGOAL) {
            return playerRightImage;
        }
        return groundImage;
    }

    public static void showIncorrectLevelDialog() {

    }

    public static void exchangeImages(int i1, int j1, int i2, int j2, TileType one, TileType two) {
        CURRENTSCENE.getImageGrid()[i2][j2].setImage(getImage(two));
        CURRENTSCENE.getImageGrid()[i1][j1].setImage(getImage(one));
    }
}
