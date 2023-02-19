package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * represents a specific vegetable: salad that is a vegetable (heredity).
 * Salad are part of the game that you can harvest, sell or store in the barn.
 *
 * @author uyxib
 * @version 1.0
 */
public class Salad extends Vegetables {

    private static final int GROW_TIME = 2;
    private static final String S_NAME = "salad";
    private static final String S_ABBREVIATION = "S";
    private static final int S_ID = 3;

    /**
     * instantiates the vegetable salad with its name, an abbreviation, an ID and the time it takes till the
     * vegetable has grown.
     */

    public Salad() {
        super(GROW_TIME, S_ABBREVIATION, S_NAME, S_ID);
    }
}
