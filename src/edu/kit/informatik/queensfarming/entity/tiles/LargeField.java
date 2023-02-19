package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Salad;
import edu.kit.informatik.queensfarming.entity.vegetables.Tomato;
import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Large Field Tile on the game board with its capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class LargeField extends Tile {

    private static final int LARGE_FIELD_CAPACITY = 8;
    private static final int LARGE_FIELD_ID = 3;
    private static final String LARGE_FIELD_NAME = "Large Field";
    private static final String LARGE_FIELD_ABBREVIATION = "LFi";

    /**
     * instantiates a new large field with its new coordinates
     * @param coordinates coordinates where the large filed is located on the game board of a player
     */
    public LargeField(Coordinates coordinates) {
        super(LARGE_FIELD_ID, LARGE_FIELD_NAME, LARGE_FIELD_CAPACITY, COUNTDOWN_START,
                coordinates, LARGE_FIELD_ABBREVIATION);
        this.allowedVegetables.add(new Carrot());
        this.allowedVegetables.add(new Salad());
        this.allowedVegetables.add(new Tomato());

    }
}
