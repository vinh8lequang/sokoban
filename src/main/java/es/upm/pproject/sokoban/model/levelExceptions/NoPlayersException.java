package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoPlayersException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(NoPlayersException.class);
    public NoPlayersException(String message) {
        super(message);
    }
}
