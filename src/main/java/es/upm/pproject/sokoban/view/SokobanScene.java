package es.upm.pproject.sokoban.view;

import es.upm.pproject.sokoban.controller.movements.Inputs;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SokobanScene extends Scene {

    
    private GridPane boardGrid;
    private HBox mainHBox;
    private ImageView[][] imageGrid;

    public SokobanScene(double width, double height, int boardSize, Level level) {
        super(new HBox(5), width, height);
        this.mainHBox = (HBox) this.getRoot();
        imageGrid = new ImageView[boardSize][boardSize];
        Inputs.setInputHandler(this);
        VBox gridContainer = new VBox(); 
        this.boardGrid = new GridPane();
        gridContainer.getChildren().addAll(boardGrid);
        this.mainHBox.getChildren().addAll(gridContainer, new SokobanSideMenu(level));
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

    /**
     * @return the hbox
     */
    public HBox getHbox() {
        return mainHBox;
    }

    /**
     * @param hbox the hbox to set
     */
    public void setHbox(HBox hbox) {
        this.mainHBox = hbox;
    }
}
