package es.upm.pproject.sokoban.controller;

import java.io.FileNotFoundException;
import java.util.Stack;

import es.upm.pproject.sokoban.App;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import es.upm.pproject.sokoban.view.SokobanScene;
import es.upm.pproject.sokoban.view.SokobanSounds;
import es.upm.pproject.sokoban.view.ViewManager;
import javafx.scene.input.KeyCode;

public class MovementExecutor {
    private static Level CURRENTLEVEL;
    private static Board CURRENTBOARD;

    private static Stack<Board> undoStateStack;
    private static Stack<Board> redoStateStack;
    /**
     * @param direction
     */

    public static void initStacks(){
        undoStateStack = new Stack<Board>();
        redoStateStack = new Stack<Board>();
    }

    public static void undo(){
        if(!undoStateStack.isEmpty()){
            CURRENTBOARD = undoStateStack.pop();
            CURRENTLEVEL.setBoard(CURRENTBOARD);
            CURRENTLEVEL.subtractOneMove();
            System.out.println(CURRENTBOARD.toString());
            try {
                ViewManager.loadLevelState(CURRENTLEVEL);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            App.setNewScene(new SokobanScene(ViewManager.WIDTH, ViewManager.HEIGHT, ViewManager.getGUIBoardSize(), CURRENTLEVEL));
        }
    }

    public static void updateSceneOnInput(KeyCode direction) {
        CURRENTLEVEL = App.getCurrentLevel();
        CURRENTBOARD = CURRENTLEVEL.getBoard();
        int tileToReplaceI = directionToIRow(direction, CURRENTBOARD.getPlayerPositionI());
        int tileToReplaceJ = directionToJCol(direction, CURRENTBOARD.getPlayerPositionJ());
        // TODO finish movement checking and image setting for the possible distinct
        // cases
        executeMovementIfPossible(direction, tileToReplaceI, tileToReplaceJ);
    }

    /**
     * @param direction
     * @param tileToReplaceIRow
     * @param tileToReplaceJCol
     */
    private static void executeMovementIfPossible(KeyCode direction, int tileToReplaceIRow, int tileToReplaceJCol) {
        boolean movedBox = false;
        if (CURRENTBOARD.getTile(tileToReplaceIRow, tileToReplaceJCol).getTileType().isMoveable()) {
            // player want to move a box
            int tileToReplaceINext = directionToIRow(direction, tileToReplaceIRow);
            int tileToReplaceJNext = directionToJCol(direction, tileToReplaceJCol);
            // we get the next tile to the box in the direction
            // check if the next tile can be replaced
            if (CURRENTBOARD.getTile(tileToReplaceINext, tileToReplaceJNext).getTileType().isReplaceable()) {
                Board levelState = new Board(CURRENTBOARD);
                undoStateStack.add(levelState);
                movedBox = true;
                // let's exchange them
                exchangeTilesAndImageGrid(tileToReplaceIRow, tileToReplaceJCol, tileToReplaceINext, tileToReplaceJNext);
                SokobanSounds.playBoxMovingSound();
            }
        }
        if (CURRENTBOARD.getTile(tileToReplaceIRow, tileToReplaceJCol).getTileType().isReplaceable()) {
            if (!movedBox){
                Board levelState = new Board(CURRENTBOARD);
                undoStateStack.add(levelState);
            }
            movedBox = false;
            exchangeTilesAndImageGrid(CURRENTBOARD.getPlayerPositionI(), CURRENTBOARD.getPlayerPositionJ(),
                    tileToReplaceIRow,
                    tileToReplaceJCol);
            CURRENTLEVEL.addOneMove();
            SokobanSounds.playPlayerMovingSound();
        } else {
            SokobanSounds.playWallSound();
        }
    }


    /**
     * @param i1
     * @param j1
     * @param i2
     * @param j2
     */
    public static void exchangeTilesAndImageGrid(int i1, int j1, int i2, int j2) {
        TileType toMoveTo = CURRENTBOARD.getTile(i2, j2).getTileType();
        TileType origin = CURRENTBOARD.getTile(i1, j1).getTileType();
        boolean normalMove = true;
        if (toMoveTo.equals(TileType.GROUND) && origin.equals(TileType.BOXINGOAL)) {
            CURRENTBOARD.setTile(i2, j2, TileType.BOX);
            CURRENTBOARD.setTile(i1, j1, TileType.GOAL);
            CURRENTBOARD.setGoals(CURRENTBOARD.getGoals() + 1);

            normalMove = false;
        }
        if (toMoveTo.equals(TileType.GROUND) && origin.equals(TileType.PLAYERINGOAL)) {
            CURRENTBOARD.setTile(i2, j2, TileType.PLAYER);
            CURRENTBOARD.setTile(i1, j1, TileType.GOAL);
            normalMove = false;
        }
        if (toMoveTo.equals(TileType.GOAL) && origin.equals(TileType.BOX)) {
            // update the goaltile type so we know next move we only update the next tile to
            // player
            // We change the goaltile to the player but the old player one stays the same
            CURRENTBOARD.setTile(i2, j2, TileType.BOXINGOAL);
            CURRENTBOARD.setTile(i1, j1, TileType.GROUND);
            int goals = CURRENTBOARD.getGoals();
            CURRENTBOARD.setGoals(goals - 1);
            SokobanSounds.playCorrectSound();
            if (CURRENTBOARD.getGoals() == 0) {
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
            normalMove = false;
        }
        // 3. Normal case when we just exchange a player tile with a ground tile
        if (normalMove) {
            CURRENTBOARD.exchangeTiles(i1, j1, i2, j2);
        }
        TileType one = CURRENTBOARD.getTile(i1, j1).getTileType();
        TileType two = CURRENTBOARD.getTile(i2, j2).getTileType();
        // we also have to update the player position, tiles can be exchanged and
        // neither have to be a player necessarily
        if (two.equals(TileType.PLAYER) || two.equals(TileType.PLAYERINGOAL)) {
            CURRENTBOARD.setPlayerPosition(i2, j2);
        }
        // we have done the move in the board but we have to update the images
        ViewManager.exchangeImages(i1, j1, i2, j2, one , two);
       
    }

    /**
     * @param direction
     * @param j
     * @return int
     */
    private static int directionToJCol(KeyCode direction, int j) {
        switch (direction) {
            case LEFT:
                j--;
                break;

            case RIGHT:
                j++;
                break;
            default:
                // LOGGER.error("Unknown input with no appropiate handle");
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
                i--;
                break;
            case DOWN:
                i++;
                break;
            default:
                // LOGGER.error("Unknown input with no appropiate handle");
                break;
        }
        return i;
    }



}
