package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Mushroom;
import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Large Forest Tile on the game board with its capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class LargeForest extends Tile {

    private static final int LARGE_FOREST_CAPACITY = 8;
    private static final int LARGER_FOREST_ID = 5;
    private static final String LARGE_FOREST_NAME = "Large Forest";
    private static final String LARGE_FOREST_ABBREVIATION = "LFo";

    /**
     * instantiates a new large forest with its new coordinates
     * @param coordinates coordinates where the larger forest is located on the game board of a player
     */
    public LargeForest(Coordinates coordinates) {
        super(LARGER_FOREST_ID, LARGE_FOREST_NAME, LARGE_FOREST_CAPACITY, NO_COUNTDOWN, coordinates,
                LARGE_FOREST_ABBREVIATION);
        this.allowedVegetables.add(new Carrot());
        this.allowedVegetables.add(new Mushroom());
    }
}
