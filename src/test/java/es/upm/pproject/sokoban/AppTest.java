package es.upm.pproject.sokoban;

import org.junit.jupiter.api.Test;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.LevelLoader;
import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import es.upm.pproject.sokoban.model.levelExceptions.*;
import junit.extensions.ExceptionTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Nested
    @DisplayName("Board level loading tests")
    class BoardLevelLoadingTests {
        @Test
        @DisplayName("Correct board")
        void testCorrectBoard() throws InvalidLevelException {
            String correctBoard = "++++    \n" +
                    "+  +    \n" +
                    "+  +++++\n" +
                    "+      +\n" +
                    "++W*+# +\n" +
                    "+   +  +\n" +
                    "+   ++++\n" +
                    "+++++   \n";
            Board board = null;
            try {
                board = LevelLoader.loadBoard("src/main/resources/Levels/level1.txt");
            } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                    | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                assertEquals(correctBoard, board.toString()); 
            }  
        }

        @Test
        @DisplayName("No player")
        void testIncorrectBoard1() {
            assertThrows(NoPlayersException.class, () -> LevelLoader.loadBoard("src/main/resources/Levels/badlevel1.txt"), "Should throw NoPlayersException");
        }

        @Test
        @DisplayName("Mulitple players")
        void testIncorrectBoard2() {
            assertThrows(MultiplePlayersException.class, () -> LevelLoader.loadBoard("src/main/resources/Levels/badlevel2.txt"), "Should throw MultiplePlayersException");
        }

        @Test
        @DisplayName("No boxes")
        void testIncorrectBoard3() {
            assertThrows(NoBoxesException.class, () -> LevelLoader.loadBoard("src/main/resources/Levels/badlevel3.txt"), "Should throw NoBoxesException");
        }

        @Test
        @DisplayName("No goals")
        void testIncorrectBoard4() {
            assertThrows(NoGoalsException.class, () -> LevelLoader.loadBoard("src/main/resources/Levels/badlevel4.txt"), "Should throw NoGoalsException");
        }

        @Test
        @DisplayName("Inequal number of boxes and goals")
        void testIncorrectBoard5() {
            assertThrows(InequalNumberOfBoxesGoals.class, () -> LevelLoader.loadBoard("src/main/resources/Levels/badlevel5.txt"), "Should throw InequalNumberOfBoxesGoals");
        }
    }

    @Nested
    @DisplayName("Board tests")
    class BoardTests {
        
    @DisplayName("Tile tests")
    class TileTests {
        @Test
        @DisplayName("Get tile type")
        void testTile1() {
            Tile tile = new Tile(TileType.WALL);
            assertEquals(TileType.WALL, tile.getTileType());
        }

        @Test
        @DisplayName("Set tile type")
        void testTile2() {
            Tile tile = new Tile(TileType.WALL);
            tile.setTileType(TileType.BOX);
            assertEquals(TileType.BOX, tile.getTileType());
        }

    }
}
