package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * represents a specific vegetable: carrot that is a vegetable (heredity).
 * Carrots are part of the game that you can harvest, sell or store in the barn.
 *
 * @author uyxib
 * @version 1.0
 */
public class Carrot extends Vegetables {
    private static final int GROW_TIME = 1;
    private static final String C_NAME = "carrot";
    private static final String C_ABBREVIATION = "C";
    private static final int C_ID = 1;

    /**
     * instantiates the vegetable carrot with its name, an abbreviation, an ID and the time it takes till the
     * vegetable has grown.
     */

    public Carrot() {
        super(GROW_TIME, C_ABBREVIATION, C_NAME, C_ID);
    }


}
