package es.upm.pproject.sokoban.model.gamelevel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.model.levelExceptions.inequalNumberOfBoxesGoals;
import es.upm.pproject.sokoban.model.levelExceptions.invalidLevelCharacterException;
import es.upm.pproject.sokoban.model.levelExceptions.invalidLevelException;
import es.upm.pproject.sokoban.model.levelExceptions.multiplePlayersException;
import es.upm.pproject.sokoban.model.levelExceptions.noBoxesException;
import es.upm.pproject.sokoban.model.levelExceptions.noGoalsException;
import es.upm.pproject.sokoban.model.levelExceptions.noPlayersException;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Level {
    private Board board;
    private Integer moves;
    StringProperty movesString = new SimpleStringProperty();

    // TODO undo stack
    public Level(String levelPath) throws invalidLevelException {
        try {
            this.board = LevelLoader.loadBoard(levelPath);
        } catch (FileNotFoundException | invalidLevelCharacterException | multiplePlayersException
                | inequalNumberOfBoxesGoals | noBoxesException | noGoalsException | noPlayersException e) {
            ViewManager.showIncorrectLevelDialog();
        } finally {
            this.moves = 0;
            this.movesString.set(moves.toString());
        }

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

    public void saveLevel(){
        Date date = new Date();
        String nombre = date.toString();

        File saveFile = new File(nombre+".vinh");
        try {
            saveFile.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(board.getRows() + " " + board.getCols() + " " + getMoves());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO
    public void loadSavedLevel(){}

    public void saveState() {
    }

    public void undo() {
    }
    
}
