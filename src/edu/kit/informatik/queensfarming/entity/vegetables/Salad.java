package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * represents a specific vegetable: salad that is a vegetable (hereditiy).
 * Salad are part of the game that you can harvest, sell or store in the barn.
 *
 * @author uyxib
 * @version 1.0
 */
public class Salad extends Vegetables {

    private static final int GROW_TIME = 2;
    private static final String NAME = "salad";
    private static final String ABBREVIATION = "S";
    private static final int ID = 3;

    /**
     * instantiates the vegetable salad with its name, an abbreviation, an ID and the time it takes till the
     * vegetable has grown.
     */

    public Salad() {
        super(GROW_TIME, ABBREVIATION, NAME, ID);
    }
}
