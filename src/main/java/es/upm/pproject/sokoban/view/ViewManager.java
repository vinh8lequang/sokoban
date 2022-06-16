package es.upm.pproject.sokoban.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import es.upm.pproject.sokoban.model.levelExceptions.InvalidLevelException;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewManager {

    public static int WIDTH = 960;
    public static int HEIGHT = 720;

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
        playButton.setOnAction((event) -> {
            try {
                App.loadNextLevel();
            } catch (InvalidLevelException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage());
            }
            App.toggleMusic();
        });
        Button loadLevelButton = new Button("Select Level");
        loadLevelButton.setFont(new Font("Impact", 45));
        loadLevelButton.setTranslateX(110);
        loadLevelButton.setTranslateY(500);
        loadLevelButton.setStyle("-fx-background-color: #ffff00");
        loadLevelButton.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(CURRENTSTAGE);
            App.loadLevel(selectedFile.getAbsolutePath());

        });
        root.getChildren().addAll(background, playButton, loadLevelButton);
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

        SokobanScene scene = new SokobanScene(WIDTH, HEIGHT, boardSize, level);
        
        CURRENTSCENE = scene;
        CURRENTBOARD = level.getBoard();
        CURRENTLEVEL = level;
        ImageView[][] imageGrid = scene.getImageGrid();
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
        winnerText.setStyle("-fx-font: 70 impact;");
        Label globalScoreText = new Label();
        globalScoreText.setText("Your global score is: "+ App.globalScore);
        globalScoreText.setStyle("-fx-font: 25 impact;");
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        Button nextLevelButton = new Button("Next Level");
        nextLevelButton.setFont(new Font("Impact", 45));
        // nextLevelButton.setTranslateX(110);
        // nextLevelButton.setTranslateY(400);
        nextLevelButton.setStyle("-fx-background-color: #ffff00");
        nextLevelButton.setOnAction((event) -> {
            try {
                App.loadNextLevel();
            } catch (InvalidLevelException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage());
            }
            SokobanSounds.stopWinnerSound();
        });
        root.getChildren().addAll(winnerText,globalScoreText, nextLevelButton);
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

    public static void showIncorrectLevelDialog(String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(CURRENTSTAGE);
        VBox dialogVbox = new VBox(20);
        Text dialogText = new Text("Incorrect level, reason: \n" + message);
        dialogText.setStyle("-fx-background-color: #ffff00");
        dialogText.setFont(new Font("Comic Sans", 25));
        dialogVbox.getChildren().add(dialogText);
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public static void exchangeImages(int i1, int j1, int i2, int j2, TileType one, TileType two) {
        CURRENTSCENE.getImageGrid()[i2][j2].setImage(getImage(two));
        CURRENTSCENE.getImageGrid()[i1][j1].setImage(getImage(one));
    }

    public static void askForSavingLevelDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(CURRENTSTAGE);
        dialog.setResizable(false);
        dialog.setOnCloseRequest((event) -> App.toggleMusicOn());
        VBox dialogVbox = new VBox(5);
        dialogVbox.setAlignment(Pos.CENTER);
        Text dialogText = new Text("You're exiting to the main menu, do you want \nto save your level?");
        dialogText.setStyle("-fx-background-color: #ffff00");
        dialogText.setFont(new Font("Comic Sans", 18));
        HBox buttons = new HBox(5);
        Button yesButton = new Button("Yes");
        yesButton.setFont(new Font("Impact", 25));
        yesButton.setStyle("-fx-background-color: #ffff00");
        yesButton.setOnAction((event) -> {
            CURRENTLEVEL.saveLevel();
            dialog.close();
            App.setNewScene(ViewManager.getStartingScene());
        });
        Button stayButNoSaveButton = new Button("Stay on level but don't save");
        stayButNoSaveButton.setFont(new Font("Impact", 25));
        stayButNoSaveButton.setStyle("-fx-background-color: #ffff00");
        stayButNoSaveButton.setOnAction((event) -> {
            App.decreaseLevelCounter();
            dialog.close();
            App.setNewScene(ViewManager.getStartingScene());
        });

        Button noButton = new Button("No");
        noButton.setFont(new Font("Impact", 25));
        noButton.setStyle("-fx-background-color: #ffff00");
        
        noButton.setOnAction((event) -> {
            App.resetLevelCounter();
            dialog.close();
            App.setNewScene(ViewManager.getStartingScene());
        });
        buttons.getChildren().addAll(yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().addAll(dialogText,stayButNoSaveButton,buttons);
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public static void createSavedLevelDialog(String savedName) {
        VBox dialogVbox = new VBox(5);
        dialogVbox.setAlignment(Pos.CENTER);
        Text dialogText = new Text("Level was correctly saved on saves folder as \n\"" + savedName + '"');
        dialogText.setStyle("-fx-background-color: #ffff00");
        dialogText.setFont(new Font("Comic Sans", 18));
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        Stage dialog = new Stage();
        dialogVbox.getChildren().add(dialogText);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(CURRENTSTAGE);
        dialog.setResizable(false);
        dialog.setOnCloseRequest((event) -> App.toggleMusicOn());
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
