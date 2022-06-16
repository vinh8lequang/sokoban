package es.upm.pproject.sokoban.model.gamelevel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.controller.MovementExecutor;
import es.upm.pproject.sokoban.model.levelExceptions.*;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Level {
    private Board board;
    private Integer moves;
    public String levelPath;
    StringProperty movesString = new SimpleStringProperty();

    // TODO undo stack
    public Level(String levelPath) throws InvalidLevelException {
        this.levelPath=levelPath;
        try {
            this.board = LevelLoader.loadBoard(levelPath);
        } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
            ViewManager.showIncorrectLevelDialog(e.getMessage());
            throw new InvalidLevelException(e.getMessage());
        } finally {
            this.moves = board.getMoves();
            this.movesString.set(moves.toString());
        }

    }

    public Level(Level another) {
        this.levelPath = another.levelPath;
        this.board = new Board(another.getBoard());
        this.moves = another.getMoves();
        this.movesString = another.getStrMoves();
    }


    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * @return the moves
     */
    public Integer getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(Integer moves) {
        this.moves = moves;
    }

    public void addOneMove() {
        this.moves++;
        this.movesString.set(moves.toString());
    }

    public void subtractOneMove() {
        this.moves--;
        this.movesString.set(moves.toString());
    }

    /**
     * @return the strMoves
     */
    public StringProperty getStrMoves() {
        return this.movesString;
    }

    public void setStrMoves() {
        this.movesString.set("YOU HAVE WON");
    }

    public String saveLevel() {
        File saveDir = new File("saves");
        saveDir.mkdir();
        Date date = new Date();
        String nombre = date.toString();

        File saveFile = new File("saves/" + nombre + ".vinh");
        try {
            saveFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(board.getRows() + " " + board.getCols() + "\n");
            writer.write(board.toString());
            writer.write( " \n" + getMoves());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nombre;
    }
    public void restartLevel(){
        try {
            this.board = LevelLoader.loadBoard(levelPath);
        } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
            e.printStackTrace();
        } finally{
            this.moves = 0;
            this.movesString.set(moves.toString());
        }
        MovementExecutor.initStacks();
        try {
            App.currentStage.setScene(ViewManager.loadLevelState(this));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
