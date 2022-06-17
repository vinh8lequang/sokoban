package es.upm.pproject.sokoban.model.gamelevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;

/**
 * Matrix of tiles. The board's coordinates starts from 0 to N-1.
 */
public class Board {

    private static Logger logger = LoggerFactory.getLogger(Board.class);

    /**
     * @return the goals
     */
    public int getGoals() {
        return goals;
    }

    /**
     * @param goals the goals to set
     */
    public void setGoals(int goals) {
        this.goals = goals;
    }

    private int rows;
    private int cols;
    private Tile[][] tileMatrix;
    private int playerPositionI;
    private int playerPositionJ;
    private int goals;
    private Integer moves;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        tileMatrix = new Tile[rows][cols];
        playerPositionI = -1;
        playerPositionJ = -1;
        this.goals = 0;
        this.moves = 0;
    }

    public Board(Board another) {
        this.cols = another.getCols();
        this.rows = another.getRows();
        this.tileMatrix = new Tile[rows][cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.tileMatrix[i][j] = new Tile(another.getTile(i, j));
            }
        }
        this.goals = another.getGoals();
        this.playerPositionI = another.getPlayerPositionI();
        this.playerPositionJ = another.getPlayerPositionJ();
    }

    /**
     * Gets the tile of i and j coordinates. Both coordinates must be in board
     * range.
     * 
     * @param i
     * @param j
     * @return the tile requested
     */
    public Tile getTile(int i, int j) {
        // Checking if range is valid
        if (i < 0 || j < 0 || i >= rows || j >= cols) {
            return null;
        } else {
            return tileMatrix[i][j];
        }
    }

    /**
     * Sets a tile type in the given coordinates of the board
     * 
     * @param i
     * @param j
     * @param type
     */
    public void setTile(int i, int j, TileType type) {
        // Checking if range is valid
        if (!(i < 0 || j < 0 || i >= rows || j >= cols)) {
            tileMatrix[i][j] = new Tile(type);
        }
    }

    /**
     * @param tile
     * @return char
     */
    private char tileToChar(Tile tile) {
        switch (tile.getTileType()) {
            case WALL:
                return '+';
            case GROUND:
                return ' ';
            case GOAL:

                return '*';
            case BOX:
                return '#';
            case PLAYER:
                return 'W';
            default:
                return 'i';
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder boardRep = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                char tileChar = tileToChar(this.getTile(i, j));
                boardRep.append(tileChar);
            }
            boardRep.append('\n');
        }
        return boardRep.toString();
    }

    /**
     * @return int
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return int
     */
    public int getCols() {
        return cols;
    }

    /**
     * @return boolean
     */
    public boolean isSymmetric() {
        if (rows == cols) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param i
     * @param j
     */
    public void setPlayerPosition(int i, int j) {
        logger.info("Setting new player position: ({}, {})", i, j);
        setPlayerPositionI(i);
        setPlayerPositionJ(j);
    }

    /**
     * @return the playerPositionI
     */
    public int getPlayerPositionI() {
        return playerPositionI;
    }

    /**
     * @param playerPositionI the playerPositionI to set
     */
    public void setPlayerPositionI(int playerPositionI) {
        this.playerPositionI = playerPositionI;
    }

    /**
     * @return the playerPositionJ
     */
    public int getPlayerPositionJ() {
        return playerPositionJ;
    }

    /**
     * @param playerPositionJ the playerPositionJ to set
     */
    public void setPlayerPositionJ(int playerPositionJ) {
        this.playerPositionJ = playerPositionJ;
    }

    /**
     * @param i1
     * @param j1
     * @param i2
     * @param j2
     */
    public void exchangeTiles(int i1, int j1, int i2, int j2) {
        Tile tmp = this.tileMatrix[i1][j1];
        this.tileMatrix[i1][j1] = this.tileMatrix[i2][j2];
        this.tileMatrix[i2][j2] = tmp;
        logger.info("Exchaning tile: ({} {}) of type with ({} {}) of type", i1, j1, tmp.getTileType(), i2, j2,
                this.tileMatrix[i2][j2].getTileType());
    }

    public void setMoves(Integer score) {
        this.moves = score;
    }

    public Integer getMoves() {
        return this.moves;
    }

}
