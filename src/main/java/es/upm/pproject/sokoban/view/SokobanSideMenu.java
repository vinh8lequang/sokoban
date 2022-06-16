package es.upm.pproject.sokoban.view;

import es.upm.pproject.sokoban.controller.MovementExecutor;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        this.getChildren().addAll(
            creeateMovesBox(),
            createUndoRedoBox(), 
            createSaveStateButton()
            );
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
            level.undo();
        });
        // add them to the box
        undoRedoBox.getChildren().addAll(undoButton, redoButton);
        return undoRedoBox;
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
