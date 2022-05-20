package es.upm.pproject.sokoban.model.gamelevel.tiles;

public enum TileType {

    GOAL(true,false),
    GROUND(true,false),
    BOX(false,true),
    WALL(false, false),
    BOXINGOAL(true, false),
    PLAYER(false,true);


    final boolean replaceable;
    final boolean moveable;

    TileType(boolean replaceable, boolean moveable) {
        this.replaceable = replaceable;
        this.moveable = moveable;
    }

    /**
     * @return the replaceable
     */
    public boolean isReplaceable() {
        return replaceable;
    }

    /**
     * @return the moveable
     */
    public boolean isMoveable() {
        return moveable;
    }
}
