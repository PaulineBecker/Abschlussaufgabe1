package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.entity.vegetables.Vegetables;
import edu.kit.informatik.queensfarming.utility.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a tile with an id and a name, capacity, a list of all allowed vegetables,
 * countdown how long the vegetables grow or how long it takes till they spoil, coordinates where the tile is located,
 * and an abbreviation of the Tile.
 *
 * @author uyxib
 * @version 1.0
 */
public abstract class Tile {

    /**
     * the beginning of the countdown
     */
    public static final int COUNTDOWN_START = 0;

    /**
     * id (int) of a specific tile
     */
    public int id;

    /**
     * name of a specific tile
     */
    public String name;

    /**
     * the capacity of a specific tile (how many vegetables can grow on a tile)
     */
    public int capacity;

    /**
     * List with all allowed vegetables that can grow on a tile
     */
    public List<String> allowedVegetables = new ArrayList<>();

    /**
     * countdown how long it takes till a specific vegetable has grown on the tile
     */
    public int countdown;

    /**
     * coordinates where the specific tile is located on the game board
     */
    public Coordinates coordinates;

    /**
     * abbreviation of a specific tile how the tile is shown on the game board
     */
    public String abbreviation;

    /**
     * list with all vegetables
     */

    public List<Vegetables> vegetablesList = new ArrayList<>();

    protected Tile(int id, String name, int capacity, int countdown, Coordinates coordinates, String abbreviation) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.countdown = countdown;
        this.coordinates = coordinates;
        this.abbreviation = abbreviation;
    }
}
