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
    protected List<Integer> allowedVegetables = new ArrayList<>();

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
     * list with all vegetables
     */

    protected List<Vegetables> vegetablesList = new ArrayList<>();

    protected Tile(int id, String name, int capacity, int countdown, Coordinates coordinates, String abbreviation) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.countdown = countdown;
        this.coordinates = coordinates;
        this.abbreviation = abbreviation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Integer> getAllowedVegetables() {
        return allowedVegetables;
    }

    public int getCountdown() {
        return countdown;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public List<Vegetables> getVegetablesList() {
        return vegetablesList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAllowedVegetables(List<Integer> allowedVegetables) {
        this.allowedVegetables = allowedVegetables;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setVegetablesList(List<Vegetables> vegetablesList) {
        this.vegetablesList = vegetablesList;
    }
}
