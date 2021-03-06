package es.upm.pproject.sokoban.controller;

import java.util.Stack;

import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import es.upm.pproject.sokoban.view.SokobanSounds;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.scene.input.KeyCode;
import javafx.util.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovementExecutor {
    private static Level CURRENTLEVEL;
    private static Board CURRENTBOARD;

    private static Logger logger = LoggerFactory.getLogger(App.class);

    private static Stack<Pair<Board, TileExchange>> undoStateStack;
    private static Stack<Pair<Board, TileExchange>> redoStateStack;

    /**
     * @param direction
     */

    public static void initStacks() {
        undoStateStack = new Stack<>();
        redoStateStack = new Stack<>();
    }

    public static void undo() {
        logger.info("Clicked Undo");
        if (!undoStateStack.isEmpty()) {
            Pair<Board, TileExchange> lastState = undoStateStack.pop();
            redoStateStack.push(lastState);
            Board lastBoard = lastState.getKey();
            CURRENTLEVEL.setBoard(lastBoard);
            TileExchange lastExchange = lastState.getValue();
            Pair<Integer, Integer> t1 = lastExchange.getTileToReplacePosition();
            Pair<Integer, Integer> t2 = lastExchange.getTileToReplaceWithPosition();
            ViewManager.exchangeImages(
                    t1.getKey(), t1.getValue(),
                    t2.getKey(), t2.getValue(),
                    lastBoard.getTile(t1.getKey(),
                            t1.getValue()).getTileType(),
                    lastBoard.getTile(t2.getKey(),
                            t2.getValue()).getTileType());
            if (lastExchange.isBoxMovement()) {
                undo();
                logger.debug("Box movement, another undo is needed");
            } else {
                CURRENTLEVEL.subtractOneMove();
            }
        } else {
            logger.debug("Undo Stack is empty, user cannot undo more");
        }
    }

    public static void redo() {
        if (!redoStateStack.isEmpty()) {
            Pair<Board, TileExchange> lastState = redoStateStack.pop();
            Board lastBoard = lastState.getKey();
            CURRENTLEVEL.setBoard(lastBoard);
            TileExchange lastExchange = lastState.getValue();
            Pair<Integer, Integer> t1 = lastExchange.getTileToReplacePosition();
            Pair<Integer, Integer> t2 = lastExchange.getTileToReplaceWithPosition();
            ViewManager.exchangeImages(
                    t1.getKey(), t1.getValue(),
                    t2.getKey(), t2.getValue(),
                    lastBoard.getTile(t1.getKey(),
                            t1.getValue()).getTileType(),
                    lastBoard.getTile(t2.getKey(),
                            t2.getValue()).getTileType());
            if (lastExchange.isBoxMovement()) {
                redo();
            } else {
                CURRENTLEVEL.addOneMove();
            }
        } else {
            logger.info("Redo Stack is empty, user cannot redo more");
        }
    }

    public static void updateSceneOnInput(KeyCode direction) {
        logger.info("Recieved input: " + direction.getName());
        CURRENTLEVEL = App.getCurrentLevel();
        CURRENTBOARD = CURRENTLEVEL.getBoard();
        int tileToReplaceI = directionToIRow(direction, CURRENTBOARD.getPlayerPositionI());
        int tileToReplaceJ = directionToJCol(direction, CURRENTBOARD.getPlayerPositionJ());
        if (executeMovementIfPossible(direction, tileToReplaceI, tileToReplaceJ)) {
            logger.info(CURRENTBOARD.toString());
        }
    }

    /**
     * @param direction
     * @param tileToReplaceIRow
     * @param tileToReplaceJCol
     */
    private static boolean executeMovementIfPossible(KeyCode direction, int tileToReplaceIRow, int tileToReplaceJCol) {
        logger.info("Executing movement");
        boolean wasMovementExecuted = false;
        boolean isBoxMovement = false;
        if (CURRENTBOARD.getTile(tileToReplaceIRow, tileToReplaceJCol).getTileType().isMoveable()) {
            // player want to move a box
            int tileToReplaceINext = directionToIRow(direction, tileToReplaceIRow);
            int tileToReplaceJNext = directionToJCol(direction, tileToReplaceJCol);
            // we get the next tile to the box in the direction
            // check if the next tile can be replaced
            if (CURRENTBOARD.getTile(tileToReplaceINext, tileToReplaceJNext).getTileType().isReplaceable()) {
                undoStateStack.add(
                        new Pair<Board, TileExchange>(
                                new Board(CURRENTBOARD),
                                new TileExchange(tileToReplaceIRow, tileToReplaceJCol,
                                        tileToReplaceINext, tileToReplaceJNext, false)));
                // let's exchange them
                exchangeTilesAndImageGrid(tileToReplaceIRow, tileToReplaceJCol, tileToReplaceINext, tileToReplaceJNext);
                isBoxMovement = true;
                wasMovementExecuted = true;
                logger.info(
                        "Box movement executed, exchanged tile: ({}, {}) with ({},{})", tileToReplaceIRow,
                        tileToReplaceJCol, tileToReplaceINext, tileToReplaceJNext);
                SokobanSounds.playBoxMovingSound();
            }
        }
        if (CURRENTBOARD.getTile(tileToReplaceIRow, tileToReplaceJCol).getTileType().isReplaceable()) {
            int currentTileI = CURRENTBOARD.getPlayerPositionI();
            int currentTileJ = CURRENTBOARD.getPlayerPositionJ();
            undoStateStack.add(
                    new Pair<Board, TileExchange>(
                            new Board(CURRENTBOARD),
                            new TileExchange(tileToReplaceIRow, tileToReplaceJCol, currentTileI, currentTileJ,
                                    isBoxMovement)));
            exchangeTilesAndImageGrid(currentTileI, currentTileJ,
                    tileToReplaceIRow,
                    tileToReplaceJCol);
            logger.info(
                    "Box movement executed, exchanged tile: ({}, {}) with ({},{})", currentTileI, currentTileJ,
                    tileToReplaceIRow,
                    tileToReplaceJCol);
            CURRENTLEVEL.addOneMove();
            SokobanSounds.playPlayerMovingSound();
            redoStateStack = new Stack<>();
            wasMovementExecuted = true;
        } else {
            logger.info("Tile to replace is not replaceable (Wall)");
            SokobanSounds.playWallSound();
        }
        return wasMovementExecuted;
    }

    /**
     * @param i1
     * @param j1
     * @param i2
     * @param j2
     */
    public static void exchangeTilesAndImageGrid(int i1, int j1, int i2, int j2) {
        logger.info("Exchanging tiles: ({}, {}) with ({},{})", i1, j1, i2, j2);
        TileType toMoveTo = CURRENTBOARD.getTile(i2, j2).getTileType();
        TileType origin = CURRENTBOARD.getTile(i1, j1).getTileType();
        boolean normalMove = true;
        if (toMoveTo.equals(TileType.GROUND) && origin.equals(TileType.BOXINGOAL)) {
            CURRENTBOARD.setTile(i2, j2, TileType.BOX);
            CURRENTBOARD.setTile(i1, j1, TileType.GOAL);
            logger.info("Setting tile ({},{}) to BOX", i2, j2);
            logger.info("Setting tile ({},{}) to GOAL", i1, j1);
            CURRENTBOARD.setGoals(CURRENTBOARD.getGoals() + 1);
            normalMove = false;
        }
        if (toMoveTo.equals(TileType.GROUND) && origin.equals(TileType.PLAYERINGOAL)) {
            CURRENTBOARD.setTile(i2, j2, TileType.PLAYER);
            CURRENTBOARD.setTile(i1, j1, TileType.GOAL);
            logger.info("Setting tile ({},{}) to PLAYER", i2, j2);
            logger.info("Setting tile ({},{}) to GOAL", i1, j1);
            normalMove = false;
        }
        if (toMoveTo.equals(TileType.GOAL) && origin.equals(TileType.BOX)) {
            // update the goaltile type so we know next move we only update the next tile to
            // player
            // We change the goaltile to the player but the old player one stays the same
            CURRENTBOARD.setTile(i2, j2, TileType.BOXINGOAL);
            CURRENTBOARD.setTile(i1, j1, TileType.GROUND);
            logger.info("Setting tile ({},{}) to BOXINGOAL", i2, j2);
            logger.info("Setting tile ({},{}) to GROUND", i1, j1);
            int goals = CURRENTBOARD.getGoals();
            CURRENTBOARD.setGoals(goals - 1);
            SokobanSounds.playCorrectSound();
            if (CURRENTBOARD.getGoals() == 0) {
                App.setGlobalScore(App.getGlobalScore() + CURRENTLEVEL.getMoves());
                ViewManager.showWinnerScene();
            }
            normalMove = false;
        }
        // 1. player wants to move to a goal tile
        if (toMoveTo.equals(TileType.GOAL) && origin.equals(TileType.PLAYER)) {
            // update the goaltile type so we know next move we only update the next tile to
            // player
            // We change the goaltile to the player but the old player one stays the same
            CURRENTBOARD.setTile(i2, j2, TileType.PLAYERINGOAL);
            CURRENTBOARD.setTile(i1, j1, TileType.GROUND);
            logger.info("Setting tile ({},{}) to PLAYERINGOAL", i2, j2);
            logger.info("Setting tile ({},{}) to GROUND", i1, j1);
            normalMove = false;
        }
        // 3. Normal case when we just exchange a player tile with a ground tile
        if (normalMove) {
            CURRENTBOARD.exchangeTiles(i1, j1, i2, j2);
            logger.info("Exchanged tiles in board: ({}, {}) with ({},{})", i1, j1, i2, j2);
        }
        TileType one = CURRENTBOARD.getTile(i1, j1).getTileType();
        TileType two = CURRENTBOARD.getTile(i2, j2).getTileType();
        // we also have to update the player position, tiles can be exchanged and
        // neither have to be a player necessarily
        if (two.equals(TileType.PLAYER) || two.equals(TileType.PLAYERINGOAL)) {
            CURRENTBOARD.setPlayerPosition(i2, j2);
        }
        // we have done the move in the board but we have to update the images
        ViewManager.exchangeImages(i1, j1, i2, j2, one, two);
    }

    /**
     * @param direction
     * @param j
     * @return int
     */
    private static int directionToJCol(KeyCode direction, int j) {
        switch (direction) {
            case LEFT:
                logger.info("Direction: LEFT");
                j--;
                break;

            case RIGHT:
                logger.info("Direction: RIGHT");

                j++;
                break;
            default:
                break;
        }
        return j;
    }

    /**
     * @param direction
     * @param i
     * @return int
     */
    private static int directionToIRow(KeyCode direction, int i) {
        switch (direction) {
            case UP:
                logger.info("Direction: UP");
                i--;
                break;
            case DOWN:
                logger.info("Direction: DOWN");
                i++;
                break;
            default:
                break;
        }
        return i;
    }

}
