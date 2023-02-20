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
    public static final int NO_COUNTDOWN = -1;

    /**
     * id (int) of a specific tile
     */
    protected int id;

    /**
     * name of a specific tile
     */
    protected String name;

    /**
     * the capacity of a specific tile (how many vegetables can grow on a tile)
     */
    protected int capacity;

    /**
     * List with all allowed vegetables that can grow on a tile
     */
    protected List<Vegetables> allowedVegetables = new ArrayList<>();

    /**
     * countdown how long it takes till a specific vegetable has grown on the tile
     */
    protected int countdown;

    /**
     * coordinates where the specific tile is located on the game board
     */
    protected Coordinates coordinates;

    /**
     * abbreviation of a specific tile how the tile is shown on the game board
     */
    protected String abbreviation;

    /**
     * list with all vegetables that are already planted on the tile (at the beginning list is empty)
     */

    protected List<Vegetables> vegetablesList = new ArrayList<>();

    /**
     * Instantiates a new Tile with its id, name , capacity, countdown, coordinates and the abbreviation of the tile
     * @param id int value of the tile
     * @param name name representation of the tile
     * @param capacity capacity of the tile how much vegetable can plant there
     * @param countdown how long it takes till vegetables are grown on the tile
     * @param coordinates coordinates where the tile is located on the board game of a player
     * @param abbreviation abbreviation / short representation of the Tile
     */

    protected Tile(int id, String name, int capacity, int countdown, Coordinates coordinates, String abbreviation) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.countdown = countdown;
        this.coordinates = coordinates;
        this.abbreviation = abbreviation;
    }

    /**
     * gets the ID of a tile
     * @return ID of a tile
     */
    public int getId() {
        return id;
    }

    /**
     * gets the name of a tile
     * @return name of a tile
     */

    public String getName() {
        return name;
    }

    /**
     * gets the capacity of a tile
     * @return capacity of a tile
     */

    public int getCapacity() {
        return capacity;
    }

    /**
     * gets the list of vegetables that are allowed to grow on the tile
     * @return list of vegetables that can grown on the tile
     */

    public List<Vegetables> getAllowedVegetables() {
        return allowedVegetables;
    }

    /**
     * gets the countdown of a tile
     * @return countdown of a tile
     */
    public int getCountdown() {
        return countdown;
    }

    /**
     * gets the coordinates of a tile where the tile is located on the board game of a player
     * @return coordinates of a tile
     */

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * gets the abbreviation of a tile, the way the tile is represented on the board game
     * @return abbreviation of a tile
     */

    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * gets the list of vegetables that are in the given moment planted on a tile
     * @return the list of vegetables growing on the tile
     */

    public List<Vegetables> getVegetablesList() {
        return vegetablesList;
    }

    /**
     * sets the countdown of a tile how long it takes till vegetables are grown or spoiled (if tile is the barn)
     * @param countdown countdown of a tile
     */
    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    /**
     * sets the coordinates of a tile
     * @param coordinates new coordinates of the tile
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
