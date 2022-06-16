package es.upm.pproject.sokoban.controller.movements;

import es.upm.pproject.sokoban.controller.MovementExecutor;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Inputs {

    /**
     * @param scene
     */
    public static void setInputHandler(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN ||
                        event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                    MovementExecutor.updateSceneOnInput(event.getCode());
                    logger.info(MovementExecutor.class.toString());
                }
                
            }
        });
    }
}
