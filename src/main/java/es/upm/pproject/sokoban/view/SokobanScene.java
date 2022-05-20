package es.upm.pproject.sokoban.view;

import es.upm.pproject.sokoban.controller.movements.Inputs;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SokobanScene extends Scene {

    private ImageView[][] imageGrid;
    private GridPane boardGrid;
    private HBox hbox;

    public SokobanScene(double width, double height, int boardSize) {
        super(new HBox(5), width, height);
        Inputs.setInputHandler(this);
        // Crate scene components infrastructure
        VBox gridContainer = new VBox(); // Contains the graphical representation of the board status
        this.boardGrid = new GridPane(); // Graphical representation of the board status // code bellow
        this.imageGrid = new ImageView[boardSize][boardSize];
        gridContainer.getChildren().add(boardGrid);
        this.hbox = (HBox) this.getRoot();
        this.hbox.getChildren().addAll(gridContainer);
    }

    /**
     * @return the imageGrid
     */
    public ImageView[][] getImageGrid() {
        return imageGrid;
    }

    /**
     * @param imageGrid the imageGrid to set
     */
    public void setImageGrid(ImageView[][] imageGrid) {
        this.imageGrid = imageGrid;
    }

    /**
     * @return the boardGrid
     */
    public GridPane getBoardGrid() {
        return boardGrid;
    }

    /**
     * @param boardGrid the boardGrid to set
     */
    public void setBoardGrid(GridPane boardGrid) {
        this.boardGrid = boardGrid;
    }

}