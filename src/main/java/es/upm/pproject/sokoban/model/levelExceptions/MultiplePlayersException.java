package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiplePlayersException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(MultiplePlayersException.class);
    public MultiplePlayersException(String message) {
        super(message);
    }
}
