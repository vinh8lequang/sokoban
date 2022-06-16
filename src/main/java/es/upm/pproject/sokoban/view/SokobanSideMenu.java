package es.upm.pproject.sokoban.view;

import es.upm.pproject.sokoban.controller.MovementExecutor;
import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SokobanSideMenu extends VBox {

    private Label moves;
    private Label movesVal;
    private Level level;
    // constructor

    public SokobanSideMenu(Level level) {
        super(5);
        this.level = level;
        this.setFocusTraversable(false);
        this.getChildren().addAll(
                creeateMovesBox(),
                createUndoRedoBox(),
                createRestartButton(),
                createSaveStateButton(), 
                createMusicControlBox(), 
                createMainMenuButton());
    }

    public HBox createUndoRedoBox() {
        HBox undoRedoBox = new HBox(6);
        // create undo button
        Button undoButton = new Button("Undo");
        undoButton.setFocusTraversable(false);
        undoButton.setFont(new Font("Impact", 30));
        undoButton.setStyle("-fx-background-color: #ffff00");
        undoButton.setMaxWidth(112);
        undoButton.setMinWidth(112);
        undoButton.setOnAction(e -> {
            MovementExecutor.undo();
        });
        // create redo button
        Button redoButton = new Button("Redo");
        redoButton.setFocusTraversable(false);
        redoButton.setFont(new Font("Impact", 30));
        redoButton.setStyle("-fx-background-color: #ffff00");
        redoButton.setMaxWidth(112);
        redoButton.setMinWidth(112);
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


    public HBox createRestartButton(){
        HBox restartBox = new HBox();
        //create a restart button
        Button restartButton = new Button("Restart");
        restartButton.setFocusTraversable(false);
        restartButton.setFont(new Font("Impact", 30));
        restartButton.setStyle("-fx-background-color: #ffff00");
        restartButton.setMaxWidth(230);
        restartButton.setMinWidth(230);
        restartButton.setOnAction(e -> {
            level.restartLevel();
        });
        // add them to the box
        restartBox.getChildren().addAll(restartButton);
        return restartBox;
    }

    public HBox createSaveStateButton() {
        HBox saveStateBox = new HBox();
        // create save button
        Button saveButton = new Button("Save level state");
        saveButton.setFocusTraversable(false);
        saveButton.setFont(new Font("Impact", 30));
        saveButton.setStyle("-fx-background-color: #ffff00");
        saveButton.setMaxWidth(230);
        saveButton.setMinWidth(230);
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
        musicButton.setFont(new Font("Impact", 30));
        musicButton.setStyle("-fx-background-color: #ffff00");
        musicButton.setMaxWidth(230);
        musicButton.setMinWidth(230);
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
        volumeSlider.setMaxWidth(230);
        volumeSlider.setOnMouseReleased(e -> {
            App.setVolume(volumeSlider.getValue());
        });

        // add them to the box
        musicControlBox.getChildren().addAll(musicButton, volumeSlider);
        return musicControlBox;
    }

    public HBox creeateMovesBox() {
        moves = new Label();
        moves.setFont(new Font("Impact", 30));
        moves.setText("Vinh Moves:\t");
        movesVal = new Label();
        movesVal.setFont(new Font("Impact", 30));
        movesVal.textProperty().bind(level.getStrMoves());
        HBox movesBox = new HBox();
        movesBox.getChildren().addAll(moves, movesVal);
        return movesBox;
    }

}
