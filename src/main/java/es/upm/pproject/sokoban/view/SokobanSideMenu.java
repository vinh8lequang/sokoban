package es.upm.pproject.sokoban.view;

import es.upm.pproject.sokoban.controller.MovementExecutor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SokobanSideMenu extends VBox {

    private Label moves;
    private Label movesVal;
    private Level level;

    private String yellowStyle = "-fx-background-color: #ffff00";
    private String impactFont = "Impact";

    private static Logger logger = LoggerFactory.getLogger(SokobanSideMenu.class);
    // constructor

    public SokobanSideMenu(Level level) {
        super(5);
        logger.info("Creating Sokoban Side Menu...");
        this.level = level;
        this.setFocusTraversable(false);
        ImageView star;
        try {
            star = new ImageView(new Image(new FileInputStream("src/main/resources/star.png")));
            this.getChildren().addAll(
                    createGlobalMovesBox(),
                    createLocalMovesBox(),
                    createUndoRedoBox(),
                    createRestartButton(),
                    createSaveStateButton(),
                    createMusicControlBox(),
                    createMainMenuButton(),
                    star,
                    createCredits());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public HBox createUndoRedoBox() {
        HBox undoRedoBox = new HBox(6);
        // create undo button
        Button undoButton = new Button("Undo");
        undoButton.setFocusTraversable(false);
        undoButton.setFont(new Font(impactFont, 30));
        undoButton.setStyle(yellowStyle);
        undoButton.setMaxWidth(230);
        undoButton.setMinWidth(230);
        undoButton.setOnAction(e -> {
            MovementExecutor.undo();
        });
        // create redo button
        Button redoButton = new Button("Redo");
        redoButton.setFocusTraversable(false);
        redoButton.setFont(new Font(impactFont, 30));
        redoButton.setStyle(yellowStyle);
        redoButton.setMaxWidth(112);
        redoButton.setMinWidth(112);
        redoButton.setOnAction(e -> {
            MovementExecutor.redo();
        });
        // add them to the box
        undoRedoBox.getChildren().addAll(undoButton);
        return undoRedoBox;
    }

    public HBox createMainMenuButton() {
        HBox mainMenuBox = new HBox();
        Button menuButton = new Button("Main menu");
        menuButton.setFocusTraversable(false);
        menuButton.setFont(new Font(impactFont, 30));
        menuButton.setStyle(yellowStyle);
        menuButton.setMaxWidth(230);
        menuButton.setMinWidth(230);
        menuButton.setOnAction(e -> {
            App.toggleMusicOff();
            ViewManager.askForSavingLevelDialog();
            logger.info("Going to main menu...");
        });
        mainMenuBox.getChildren().add(menuButton);
        return mainMenuBox;
    }

    public HBox createRestartButton() {
        HBox restartBox = new HBox();
        // create a restart button
        Button restartButton = new Button("Restart");
        restartButton.setFocusTraversable(false);
        restartButton.setFont(new Font(impactFont, 30));
        restartButton.setStyle(yellowStyle);
        restartButton.setMaxWidth(230);
        restartButton.setMinWidth(230);
        restartButton.setOnAction(e -> {
            level.restartLevel();
            logger.info("Restarting level...");
        });
        // add them to the box
        restartBox.getChildren().addAll(restartButton);
        return restartBox;
    }

    public HBox createSaveStateButton() {
        HBox saveStateBox = new HBox();
        // create save button
        Button saveButton = new Button("Save level");
        saveButton.setFocusTraversable(false);
        saveButton.setFont(new Font(impactFont, 30));
        saveButton.setStyle(yellowStyle);
        saveButton.setMaxWidth(230);
        saveButton.setMinWidth(230);
        saveButton.setOnAction(e -> {
            ViewManager.createSavedLevelDialog(level.saveLevel());
            logger.info("Saving level...");
        });
        // add them to the box
        saveStateBox.getChildren().addAll(saveButton);
        return saveStateBox;
    }

    // create a box with a button that stops the music
    public VBox createMusicControlBox() {
        VBox musicControlBox = new VBox();
        // create music button
        Button musicButton = new Button("Music");
        musicButton.setFocusTraversable(false);
        musicButton.setFont(new Font(impactFont, 30));
        musicButton.setStyle(yellowStyle);
        musicButton.setMaxWidth(230);
        musicButton.setMinWidth(230);
        musicButton.setOnAction(e -> {
            if (App.toggleMusic()) {
                musicButton.setText("Stop music");
                logger.info("Music stopped...");
            } else {
                musicButton.setText("Play music");
                logger.info("Music started...");
            }
        });
        // create a javafx slider
        Slider volumeSlider = new Slider(BASELINE_OFFSET_SAME_AS_HEIGHT, USE_COMPUTED_SIZE,
                BASELINE_OFFSET_SAME_AS_HEIGHT);
        volumeSlider.setFocusTraversable(false);
        musicButton.setText("Stop music");
        volumeSlider.setMax(1.0);
        volumeSlider.setMin(0.0);
        volumeSlider.setValue(1.0);
        volumeSlider.setMaxWidth(230);
        volumeSlider.setOnMouseReleased(e -> {
            App.setVolume(volumeSlider.getValue());
            logger.info("Volume set to " + volumeSlider.getValue());
        });

        // add them to the box
        musicControlBox.getChildren().addAll(musicButton, volumeSlider);
        return musicControlBox;
    }

    public HBox createLocalMovesBox() {
        moves = new Label();
        moves.setFont(new Font(impactFont, 30));
        moves.setText("Vinh Moves:\t");
        movesVal = new Label();
        movesVal.setFont(new Font(impactFont, 30));
        movesVal.textProperty().bind(level.getStrMoves());
        HBox movesBox = new HBox();
        movesBox.getChildren().addAll(moves, movesVal);
        return movesBox;
    }

    public HBox createGlobalMovesBox() {
        moves = new Label();
        moves.setFont(new Font(impactFont, 30));
        moves.setText("Total Moves:\t");
        movesVal = new Label();
        movesVal.setFont(new Font(impactFont, 30));
        movesVal.textProperty().bind(App.getStrGlobalScore());
        HBox movesBox = new HBox();
        movesBox.getChildren().addAll(moves, movesVal);
        return movesBox;
    }

    public Text createCredits() {
        Font fuente = new Font("Helvetica", 12);
        Text text = new Text("-----------------------------------------------\nSokovinh by:" +
                "\nNicolas Cossio Miravalles\nAlvaro G. Mendez\nVinh Le Quang\nSebastian Revilla Rojas");
        text.setFont(fuente);
        text.setFill(Color.GREY);
        text.setTextAlignment(TextAlignment.RIGHT);
        return text;
    }

}
