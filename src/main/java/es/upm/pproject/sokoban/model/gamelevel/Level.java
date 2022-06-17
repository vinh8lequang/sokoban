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
        logger.info("Creating a level for file " + levelPath);
        this.levelPath=levelPath;
        try {
            this.board = LevelLoader.loadBoard(levelPath);
            logger.info("Level correctly loaded");
        } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
                    logger.error("Error loading level: " + e.getMessage());
            if (!debug) {
                ViewManager.showIncorrectLevelDialog(e.getMessage());
                logger.info("Showing incorrect level dialog");
            }
            throw new InvalidLevelException(e.getMessage());
        } finally {
            if (board != null) {
                this.moves = board.getMoves();
                this.movesString.set(moves.toString());
            }
            else{
                logger.error("Board has not been initialized but no exception was thrown");
            }
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
        App.setStrGlobalMoves();
    }

    public void subtractOneMove() {
        this.moves--;
        this.movesString.set(moves.toString());
        App.setStrGlobalMoves();
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
        nombre = nombre.replace(":", "");

        File saveFile = new File("saves/" + nombre + ".vinh");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            if(!saveFile.createNewFile()){
                writer.write(board.getRows() + " " + board.getCols() + "\n");
                writer.write(board.toString());
                writer.write( " \n" + getMoves());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return nombre;
    }

    public void restartLevel(){
        try {
            logger.info("Restarting level...");
            this.board = LevelLoader.loadBoard(levelPath);
        } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
            logger.error("Error restarting level, reason {}", e.getMessage());
        } finally {
            this.moves = 0;
            this.movesString.set(moves.toString());
        }
        MovementExecutor.initStacks();
        try {
            App.getStage().setScene(ViewManager.loadLevelState(this));
            logger.info("Restarting level finished, correctly loaded");
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

}
