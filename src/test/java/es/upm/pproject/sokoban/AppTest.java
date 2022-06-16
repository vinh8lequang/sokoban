package es.upm.pproject.sokoban;

import org.junit.jupiter.api.Test;
import es.upm.pproject.sokoban.model.gamelevel.Board;
import es.upm.pproject.sokoban.model.gamelevel.LevelLoader;
import es.upm.pproject.sokoban.model.levelExceptions.inequalNumberOfBoxesGoals;
import es.upm.pproject.sokoban.model.levelExceptions.invalidLevelCharacterException;
import es.upm.pproject.sokoban.model.levelExceptions.invalidLevelException;
import es.upm.pproject.sokoban.model.levelExceptions.multiplePlayersException;
import es.upm.pproject.sokoban.model.levelExceptions.noBoxesException;
import es.upm.pproject.sokoban.model.levelExceptions.noGoalsException;
import es.upm.pproject.sokoban.model.levelExceptions.noPlayersException;
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
        void testCorrectBoard() throws invalidLevelException {
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
            } catch (FileNotFoundException | invalidLevelCharacterException | multiplePlayersException
                    | inequalNumberOfBoxesGoals | noBoxesException | noGoalsException | noPlayersException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally{
                assertEquals(correctBoard, board.toString()); 
            }  
        }

        @Test
        @DisplayName("Incorrect board")
        void testIncorrectBoard() {
            assertThrows(Exception,  LevelLoader.loadBoard("src/main/resources/Levels/levelIncorrecto.txt"));
            board = LevelLoader.loadBoard("src/main/resources/Levels/levelIncorrecto.txt");
        }
    }
}
