package es.upm.pproject.sokoban.model.gamelevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import es.upm.pproject.sokoban.model.levelExceptions.*;

/**
 * This class is in charge of reading a level file and loading into memory
 */
public class LevelLoader {

    // create a logger
    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(LevelLoader.class);
    /**
     * @param type
     * @return TileType
     */
    private static TileType charToTileType(char type) {
        switch (type) {
            case '+':
                return TileType.WALL;
            case ' ':
                return TileType.GROUND;
            case '*':
                return TileType.GOAL;
            case '#':
                return TileType.BOX;
            case 'W':
                return TileType.PLAYER;
            default:
                return null;
        }
    }

    /**
     * @param path
     * @return Board
     * @throws Exception
     */
    public static Board loadBoard(String path) throws FileNotFoundException, invalidLevelCharacterException,
            multiplePlayersException, inequalNumberOfBoxesGoals,
            noBoxesException, noGoalsException, noPlayersException {
        int nPlayers = 0; // number of players
        int nBoxes = 0; // number of boxes
        int nGoals = 0; // number of goals
        File level = new File(path);
        try (Scanner sc = new Scanner(level)) {
            int rows = sc.nextInt();
            int cols = sc.nextInt();
            // int moves = sc.nextInt(); 
            // TODO load moves when file has moves
            sc.nextLine(); // this is for skipping the first line
            Board board = new Board(rows, cols);
            for (int i = 0; sc.hasNextLine() && (i < rows); i++) {
                // StringBuilder debugLine = new StringBuilder();
                String line = sc.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j); // getting de character
                    // debugLine.append(c);
                    TileType type = charToTileType(c);
                    // Throws exception if it's an invalid character
                    if (type == null) {
                        throw new invalidLevelCharacterException("Invalid character: " + line.charAt(j));
                    } else if (type == TileType.GOAL) {
                        board.setGoals(board.getGoals() + 1);
                        nGoals++;
                    } else if (type == TileType.PLAYER) {
                        board.setPlayerPosition(i, j);
                        nPlayers++;
                        if (nPlayers > 1) { // Check if there is more than one player
                            throw new multiplePlayersException("There is more than one player in the level");
                        }
                    } else if (type == TileType.BOX) {
                        nBoxes++;
                    }
                    board.setTile(i, j, type);
                }
                // Filling in the empty tiles on the right
                for (int k = line.length(); k < cols; k++) {
                    board.setTile(i, k, TileType.GROUND);
                }
                // System.out.println(debugLine);
            }
            // Check if there is not a player
            if (nPlayers == 0) {
                throw new noPlayersException("There is no player in the level");
            }
            // Check if there are no boxes
            if (nBoxes == 0) {
                throw new noBoxesException("There are no boxes in the level");
            }
            // Check if there are no goals
            if (nGoals == 0) {
                throw new noGoalsException("There are no goals in the level");
            }
            // Check if number of boxes = goals
            if (nBoxes != nGoals) {
                throw new inequalNumberOfBoxesGoals("There are " + nBoxes + " boxes and " + nGoals + " goals");
            }
            board.viewBoard();
            return board;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
    }
}
