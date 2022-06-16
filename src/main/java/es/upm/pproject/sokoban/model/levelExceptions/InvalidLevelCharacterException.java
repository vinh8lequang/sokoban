package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidLevelCharacterException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(InvalidLevelCharacterException.class);
    public InvalidLevelCharacterException(String message) {
        super(message);
    }
}
