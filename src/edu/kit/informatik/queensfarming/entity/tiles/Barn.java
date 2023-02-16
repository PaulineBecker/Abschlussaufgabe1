package edu.kit.informatik.queensfarming.entity.tiles;

import edu.kit.informatik.queensfarming.utility.Coordinates;

/**
 * represents the Barn that each player has during the game.
 * Barn is a tile on the Game board.
 * The Barn is filled with vegetables.
 * After 6 round the vegetables in the barn will spoil if they are still stored in the barn.
 * the capacity is endless.
 *
 * @author uyxib
 * @version 1.0
 */
public class Barn extends Tile {

    private static final int START_CAPACITY = -1;
    private static final int BARN_ID = 0;
    private static final String BARN_NAME = "Barn";
    private static final String BARN_ABBREVIATION = "B";
    private static final int BARN_COORDINATES = 0;

    /**
     * instantiates a new Barn with an ID, name of the Tile (here Barn), Capacity of the Barn,
     * Countdown (how long it takes till the vegetables are spoiled), Coordinates with the location of the barn
     * and an abbreviation of the Barn.
     */
    public Barn() {
        super(BARN_ID, BARN_NAME, START_CAPACITY, COUNTDOWN_START,
                new Coordinates(BARN_COORDINATES, BARN_COORDINATES), BARN_ABBREVIATION);
    }
}
