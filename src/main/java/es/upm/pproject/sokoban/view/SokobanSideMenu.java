package es.upm.pproject.sokoban.view;

import es.upm.pproject.sokoban.controller.MovementExecutor;
import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SokobanSideMenu extends VBox {

    private Label moves;
    private Label movesVal;
    private Level level;
    // constructor

    public SokobanSideMenu(Level level) {
        super();
        this.level = level;
        this.setFocusTraversable(false);
        this.getChildren().addAll(
                creeateMovesBox(),
                createUndoRedoBox(),
                createSaveStateButton(), 
                createMusicControlBox(), 
                createMainMenuButton());
    }

    public HBox createUndoRedoBox() {
        HBox undoRedoBox = new HBox();
        // create undo button
        Button undoButton = new Button("Undo");
        undoButton.setFocusTraversable(false);
        undoButton.setOnAction(e -> {
            MovementExecutor.undo();
        });
        // create redo button
        Button redoButton = new Button("Redo");
        redoButton.setFocusTraversable(false);
        redoButton.setOnAction(e -> {
            MovementExecutor.redo();
        });
        // add them to the box
        undoRedoBox.getChildren().addAll(undoButton, redoButton);
        return undoRedoBox;
    }

    public HBox createMainMenuButton() {
        HBox mainMenuBox = new HBox();
        Button menuButton = new Button("Go to main menu");
        menuButton.setFocusTraversable(false);
        menuButton.setOnAction(e -> {
            App.toggleMusic();
            ViewManager.askForSavingLevelDialog();
        });
        mainMenuBox.getChildren().add(menuButton);
        return mainMenuBox;
    }


    public HBox createSaveStateButton() {
        HBox saveStateBox = new HBox();
        // create save button
        Button saveButton = new Button("Save level state");
        saveButton.setFocusTraversable(false);
        saveButton.setOnAction(e -> {
            level.saveLevel();
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
        musicButton.setOnAction(e -> {
            if (App.toggleMusic()) {
                musicButton.setText("Stop music");
            } else {
                musicButton.setText("Play music");

            }
        });
        // create a javafx slider
        Slider volumeSlider = new Slider(BASELINE_OFFSET_SAME_AS_HEIGHT, USE_COMPUTED_SIZE,
                BASELINE_OFFSET_SAME_AS_HEIGHT);
        volumeSlider.setFocusTraversable(false);
        musicButton.setText("Turn off music");
        volumeSlider.setMax(1.0);
        volumeSlider.setMin(0.0);
        volumeSlider.setValue(1.0);
        volumeSlider.setOnMouseReleased(e -> {
            App.setVolume(volumeSlider.getValue());
        });

        // add them to the box
        musicControlBox.getChildren().addAll(musicButton, volumeSlider);
        return musicControlBox;
    }

    public HBox creeateMovesBox() {
        moves = new Label();
        moves.setStyle("-fx-font: 24 arial;");
        moves.setText("Moves:\t");
        movesVal = new Label();
        movesVal.setStyle("-fx-font: 24 arial;");
        movesVal.textProperty().bind(level.getStrMoves());
        HBox movesBox = new HBox();
        movesBox.getChildren().addAll(moves, movesVal);
        return movesBox;
    }

}
