package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Field Tile on the game board with it's capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class Field extends Tile {
    private static final int CAPACITY = 4;
    private static final int ID = 2;
    private static final String NAME = "Field";
    private static final String ABBREVIATION = "Fi";

    public Field(Coordinates coordinates) {
        super(ID, NAME, CAPACITY, COUNTDOWN_START, coordinates, ABBREVIATION);
    }
}
