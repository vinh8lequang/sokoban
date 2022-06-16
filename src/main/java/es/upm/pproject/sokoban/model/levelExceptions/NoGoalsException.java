package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoGoalsException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(NoGoalsException.class);
    public NoGoalsException(String message) {
        super(message);
    }
}
