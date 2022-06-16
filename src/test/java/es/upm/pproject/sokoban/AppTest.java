package es.upm.pproject.sokoban;

import org.junit.jupiter.api.Test;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.LevelLoader;
import es.upm.pproject.sokoban.model.gamelevel.Level;
import es.upm.pproject.sokoban.model.gamelevel.tiles.Tile;
import es.upm.pproject.sokoban.model.gamelevel.tiles.TileType;
import es.upm.pproject.sokoban.model.levelExceptions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private String correctBoard = "++++    \n" +
    "+  +    \n" +
    "+  +++++\n" +
    "+      +\n" +
    "++W*+# +\n" +
    "+   +  +\n" +
    "+   ++++\n" +
    "+++++   \n";

    @Nested
    @DisplayName("Board level loading tests")
    class BoardLevelLoadingTests {
        @Test
        @DisplayName("Correct board")
        void testCorrectBoard() throws InvalidLevelException {
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

        @Test
        @DisplayName("File not found")
        void testIncorrectBoard6() {
            assertThrows(FileNotFoundException.class, () -> LevelLoader.loadBoard("src/main/resources/Levels/aaa.txt"), "Should throw FileNotFoundException");
        }
    }
        
    @Nested
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

        @Test
        @DisplayName("Compare tiles")
        void testTile3() {
            Tile tile1 = new Tile(TileType.GROUND);
            Tile tile2 = new Tile(TileType.PLAYER);
            assertNotEquals(tile1.equals(tile2), "Should not be equal");
        }
    }

    @Nested
    @DisplayName("Level tests")
    class LevelTests {
        @Test
        @DisplayName("Incorrect level")
        void testLevel1() {
            Level level = null;
            try {
                level = new Level("src/main/resources/Levels/level1.txt",false);
            } catch (InvalidLevelException e) {
                e.printStackTrace();
            } finally{
                assertEquals(correctBoard, level.getBoard().toString()); 
            }  
        }

        @Test
        @DisplayName("Copy level")
        void testLevel2() {
            Level level1 = null;
            Level level2 = null;
            try {
                level1 = new Level("src/main/resources/Levels/level1.txt",false);
                level2 = new Level(level1);
            } catch (InvalidLevelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{
                assertEquals(correctBoard, level2.getBoard().toString()); 
            }  
        }

        @Test
        @DisplayName("Set board")
        void testLevel3() {
            Level level1 = null;
            Level level2 = null;
            try {
                level1 = new Level("src/main/resources/Levels/level1.txt",false);
                level2 = new Level("src/main/resources/Levels/level2.txt",false);
                level2.setBoard(level1.getBoard());
            } catch (InvalidLevelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{
                assertEquals(correctBoard, level2.getBoard().toString()); 
            }  
        }

        @Test
        @DisplayName("Set moves")
        void testLevel4() {
            Level level = null;
            try {
                level = new Level("src/main/resources/Levels/level1.txt",false);
                level.setMoves(5);
            } catch (InvalidLevelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{
                assertSame(5, level.getMoves()); 
            }  
        }

        @Test
        @DisplayName("Add/Subtract one move")
        void testLevel5() {
            Level level = null;
            try {
                level = new Level("src/main/resources/Levels/level1.txt",false);
                level.addOneMove();
                level.addOneMove();
                level.subtractOneMove();
            } catch (InvalidLevelException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{
                assertSame(1, level.getMoves()); 
            }  
        }

        @Test
        @DisplayName("Bad level")
        void testLevel6() {
            boolean exception = false;
            try {
                Level level = new Level("src/main/resources/Levels/badlevel1.txt",true);
            } catch (InvalidLevelException e) {
                exception = true;
                assertTrue(exception);
            }
            // assertThrows(InvalidLevelException.class, () -> new Level("src/main/resources/Levels/badlevel1.txt"), "Should throw InvalidLevelException");
        }
    }

    @Nested
    @DisplayName("Board tests")
    class BoardTests {
        @Test
        @DisplayName("Get tile")
        void testBoard1() {
            Board board = null;
            Tile tile = null;
            try {
                board = LevelLoader.loadBoard("src/main/resources/Levels/level1.txt");
                tile = board.getTile(1, 1);
            } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                    | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                assertEquals(TileType.GROUND, tile.getTileType());
            }
        }

        @Test
        @DisplayName("Symmetric board")
        void testBoard2() {
            Board board = null;
            boolean isSymmetric = false;
            try {
                board = LevelLoader.loadBoard("src/main/resources/Levels/level1.txt");
                isSymmetric = board.isSymmetric();
            } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                    | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                assertTrue(isSymmetric);
            }
        }

        
        @Test
        @DisplayName("Exchange tiles")
        void testBoard3() {
            Board board = null;
            try {
                board = LevelLoader.loadBoard("src/main/resources/Levels/level1.txt");
                board.exchangeTiles(4, 2, 3, 2); //exchange player with ground
            } catch (FileNotFoundException | InvalidLevelCharacterException | MultiplePlayersException
                    | InequalNumberOfBoxesGoals | NoBoxesException | NoGoalsException | NoPlayersException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                assertEquals(TileType.GROUND, board.getTile(4, 2).getTileType());
                assertEquals(TileType.PLAYER, board.getTile(3, 2).getTileType());
            }
        }
    }
}
