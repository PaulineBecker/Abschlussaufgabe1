package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Mushroom;
import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Forest Tile on the game board with its capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class Forest extends Tile {

    private static final int FOREST_CAPACITY = 4;
    private static final int FOREST_ID = 4;
    private static final String FOREST_NAME = "Forest";
    private static final String FOREST_ABBREVIATION = "Fo";


    /**
     * instantiates a new forest with its new coordinates
     * @param coordinates coordinates where the forest is located on the game board of a player
     */
    public Forest(Coordinates coordinates) {
        super(FOREST_ID, FOREST_NAME, FOREST_CAPACITY, COUNTDOWN_START, coordinates, FOREST_ABBREVIATION);
        this.allowedVegetables.add(new Carrot());
        this.allowedVegetables.add(new Mushroom());
    }
}
