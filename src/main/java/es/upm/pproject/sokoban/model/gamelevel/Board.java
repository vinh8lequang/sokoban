package es.upm.pproject.sokoban.model.gamelevel;

import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;

/**
 * Matrix of tiles. The board's coordinates starts from 0 to N-1.
 */
public class Board {
    
    private int rows;
    private int cols;
    private Tile [][] board;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new Tile[rows][cols];
    }

    /**
     * Gets the tile of x and y coordinates. Both coordinates must be in board range.
     * @param x
     * @param y
     * @return the tile requested
     */
    public Tile getTile(int x, int y) {
        //Checking if range is valid
        if (x < 0 || y < 0 || x >= rows || y >= cols) {
            return null;
        } else {
            return board[x][y];
        }
    }

    /**
     * Sets a tile type in the given coordinates of the board
     * @param x
     * @param y
     * @param type
     */
    public void setTile(int x, int y, TileType type) {
        //Checking if range is valid
        if (x < 0 || y < 0 || x >= rows || y >= cols) {
            int rowRange = this.rows - 1;
            int colRange = this.cols - 1;
            System.err.println("Invalid tile coordinates. Must be in range x: 0-" + rowRange + " y: 0-" + colRange);
        } else {
            board[x][y] = new Tile (type);
        }
    }

    public void viewBoard() {
        for (int i = 0; i < this.rows; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < this.cols; j++){
                char tileChar = tileToChar(this.getTile(i, j));
                if (tileChar == 'X') {
                    System.err.println("Invalid tile input for char conversion.");
                }
                line.append(tileChar);
            }
            System.out.println(line);
        }
    }

    private static char tileToChar(Tile tile) {
        switch (tile.getTileType()) {
            case WALL: return '+';
            case GROUND: return ' ';
            case GOAL: return '*';
            case BOX: return '#';
            case PLAYER: return 'W';
            default: return 'X';
        }
    }
    

}
