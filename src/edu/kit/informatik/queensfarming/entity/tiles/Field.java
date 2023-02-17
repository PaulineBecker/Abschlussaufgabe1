package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Salad;
import edu.kit.informatik.queensfarming.entity.vegetables.Tomato;
import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Field Tile on the game board with it's capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class Field extends Tile {
    private static final int FIELD_CAPACITY = 4;
    private static final int FIELD_ID = 2;
    private static final String FIELD_NAME = "Field";
    private static final String FIELD_ABBREVIATION = "Fi";

    /**
     * instantiates a new field with its new coordinates
     * @param coordinates coordinates where the filed is located on the game board of a player
     */
    public Field(Coordinates coordinates) {
        super(FIELD_ID, FIELD_NAME, FIELD_CAPACITY, COUNTDOWN_START, coordinates, FIELD_ABBREVIATION);
        this.allowedVegetables.add(new Carrot());
        this.allowedVegetables.add(new Salad());
        this.allowedVegetables.add(new Tomato());
    }
}
