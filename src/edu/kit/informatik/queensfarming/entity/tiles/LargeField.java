package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Large Field Tile on the game board with it's capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class LargeField extends Tile {

    private static final int CAPACITY = 8;
    private static final int ID = 3;
    private static final String NAME = "Large Field";
    private static final String ABBREVIATION = "LFi";

    public LargeField(Coordinates coordinates) {
        super(ID, NAME, CAPACITY, COUNTDOWN_START, coordinates, ABBREVIATION);
    }
}
