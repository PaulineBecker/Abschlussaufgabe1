package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * represents a specific vegetable: mushroom that is a vegetable (heredity).
 * Mushrooms are part of the game that you can harvest, sell or store in the barn.
 * @author uyxib
 * @version 1.0
 */
public class Mushroom extends Vegetables {

    private static final int GROW_TIME = 4;
    private static final String M_NAME = "mushroom";
    private static final String M_ABBREVIATION = "M";
    private static final int M_ID = 0;

    /**
     * instantiates the vegetable mushroom with its name, an abbreviation, an ID and the time it takes till the
     * vegetable has grown.
     */

    public Mushroom() {
        super(GROW_TIME, M_ABBREVIATION, M_NAME, M_ID);
    }
}
