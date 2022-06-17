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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Level {
    private Board board;
    private Integer moves;
    public String levelPath;
    StringProperty movesString = new SimpleStringProperty();

    private static Logger logger = LoggerFactory.getLogger(Level.class);

    public Level(String levelPath, boolean debug) throws InvalidLevelException {
        this.levelPath=levelPath;
        logger.info(levelPath);
        try {
            this.board = LevelLoader.loadBoard(levelPath);
            logger.info(board.toString());
        } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
            if (!debug) {
                ViewManager.showIncorrectLevelDialog(e.getMessage());
            }
            throw new InvalidLevelException(e.getMessage());
        } finally {
            if (board != null) {
                this.moves = board.getMoves();
                logger.info(Integer.toString(moves));
                this.movesString.set(moves.toString());
                logger.info(movesString.toString());
            }
        }
    }

    public Level(Level another) {
        this.levelPath = another.levelPath;
        logger.info(levelPath);
        this.board = new Board(another.getBoard());
        logger.info(board.toString());
        this.moves = another.getMoves();
        logger.info(Integer.toString(moves));
        this.movesString = another.getStrMoves();
        logger.info(movesString.toString());
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
        logger.info(board.toString());
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
        logger.info(Integer.toString(moves));
    }

    public void addOneMove() {
        this.moves++;
        logger.info(Integer.toString(moves));
        this.movesString.set(moves.toString());
        logger.info(movesString.toString());
    }

    public void subtractOneMove() {
        this.moves--;
        logger.info(Integer.toString(moves));
        this.movesString.set(moves.toString());
        logger.info(movesString.toString());
    }   

    /**
     * @return the strMoves
     */
    public StringProperty getStrMoves() {
        return this.movesString;
    }

    public void setStrMoves() {
        this.movesString.set("YOU HAVE WON");
        logger.info(movesString.toString());
    }

    public String saveLevel() {
        File saveDir = new File("saves");
        saveDir.mkdir();
        logger.info(saveDir.toString());
        Date date = new Date();
        String nombre = date.toString();
        logger.info(nombre);

        File saveFile = new File("saves/" + nombre + ".vinh");
        logger.info(saveFile.toString());
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            boolean created = saveFile.createNewFile();
            if(created){
                writer.write(board.getRows() + " " + board.getCols() + "\n");
                writer.write(board.toString());
                writer.write( " \n" + getMoves());
                logger.info(writer.toString());
                writer.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return nombre;
    }
    public void restartLevel(){
        try {
            this.board = LevelLoader.loadBoard(levelPath);
            logger.info(board.toString());
        } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
            logger.error(e.getMessage());
        } finally{
            this.moves = 0;
            logger.info(Integer.toString(moves));
            this.movesString.set(moves.toString());
            logger.info(movesString.toString());
        }
        MovementExecutor.initStacks();
        try {
            App.currentStage.setScene(ViewManager.loadLevelState(this));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

}
