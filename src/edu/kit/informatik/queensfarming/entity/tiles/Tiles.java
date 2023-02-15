package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.utility.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a tile with an id and a name.
 *
 * @author uyxib
 * @verion 1.0
 */
public abstract class Tiles {

    public String id;
    public String name;
    public int capacity;
    public List<String> allowedVegetables = new ArrayList<>();
    public int countdown;
    public Coordinates coordinates;
    public String abbreviation;

    protected Tiles(String id, String name, int capacity, int countdown, Coordinates coordinates, String abbreviation) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.countdown = countdown;
        this.coordinates = coordinates;
        this.abbreviation = abbreviation;
    }
}
