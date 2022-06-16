package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoBoxesException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(NoBoxesException.class);
    public NoBoxesException(String message) {
        super(message);
    }
}
