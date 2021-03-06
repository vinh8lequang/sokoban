package es.upm.pproject.sokoban.controller;

import javafx.util.Pair;

public class TileExchange {

    private Pair<Integer, Integer> tileToReplacePosition;
    private Pair<Integer, Integer> tileToReplaceWithPosition;
    private boolean isBoxMovement;

    public TileExchange(int i1, int j1, int i2, int j2, boolean isBoxMovement) {
        tileToReplacePosition = new Pair<Integer, Integer>(i1, j1);
        tileToReplaceWithPosition = new Pair<Integer, Integer>(i2, j2);
        this.isBoxMovement = isBoxMovement;
    }
    
    public boolean isBoxMovement() {
        return isBoxMovement;
    }
    public Pair<Integer, Integer> getTileToReplacePosition() {
        return tileToReplacePosition;
    }
    
    public Pair<Integer, Integer> getTileToReplaceWithPosition() {
        return tileToReplaceWithPosition;
    }
}
