package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Garden Tile on the game board with it's capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class Garden extends Tile {

    private static final int CAPACITY = 2;
    private static final int ID = 1;
    private static final String NAME = "Garden";
    private static final String ABBREVIATION = "G";

    public Garden(Coordinates coordinates) {
        super(ID, NAME, CAPACITY, COUNTDOWN_START, coordinates, ABBREVIATION);
    }
}
