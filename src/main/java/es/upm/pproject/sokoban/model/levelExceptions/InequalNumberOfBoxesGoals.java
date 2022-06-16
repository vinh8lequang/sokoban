package es.upm.pproject.sokoban.model.levelExceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InequalNumberOfBoxesGoals extends Exception{
    private static Logger logger = LoggerFactory.getLogger(InequalNumberOfBoxesGoals.class);
    public InequalNumberOfBoxesGoals(String message) {
        super(message);
    }
}
