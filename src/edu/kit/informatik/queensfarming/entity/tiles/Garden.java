package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Mushroom;
import edu.kit.informatik.queensfarming.entity.vegetables.Salad;
import edu.kit.informatik.queensfarming.entity.vegetables.Tomato;
import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Garden Tile on the game board with its capacity, id, name and abbreviation
 *
 * @author uyxib
 * @version 1.0
 */
public class Garden extends Tile {

    private static final int GARDEN_CAPACITY = 2;
    private static final int GARDEN_ID = 1;
    private static final String GARDEN_NAME = "Garden";
    private static final String GARDEN_ABBREVIATION = "G";

    /**
     * instantiates a new garden with its new coordinates
     * @param coordinates coordinates where the garden is located on the game board of a player
     */

    public Garden(Coordinates coordinates) {
        super(GARDEN_ID, GARDEN_NAME, GARDEN_CAPACITY, COUNTDOWN_START, coordinates, GARDEN_ABBREVIATION);
        this.allowedVegetables.add(new Carrot());
        this.allowedVegetables.add(new Salad());
        this.allowedVegetables.add(new Tomato());
        this.allowedVegetables.add(new Mushroom());
    }
}
