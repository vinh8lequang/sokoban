package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidLevelException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(InvalidLevelException.class);
    public InvalidLevelException(String message) {
        super(message);
    }
}
