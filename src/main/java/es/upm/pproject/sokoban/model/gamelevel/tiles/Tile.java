package es.upm.pproject.sokoban.model.gamelevel.tiles;

/**
 * The class that creates a tile. A tile can be of different TileTypes (enum).
 */
public class Tile {

    private TileType type;

    public Tile(TileType type) {
        this.type = type;
    }

    public TileType getTileType() {
        return type;
    }

    public void setTileType(TileType type) {
        this.type = type;
    }
}
