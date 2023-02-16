package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * represents a specific vegetable: tomato that is a vegetable (hereditiy).
 * Tomato are part of the game that you can harvest, sell or store in the barn.
 *
 * @author uyxib
 * @version 1.0
 */
public class Tomato extends Vegetables {

    private static final int GROW_TIME = 3;
    private static final String NAME = "tomato";
    private static final String ABBREVIATION = "T";
    private static final int ID = 2;

    /**
     * instantiates the vegetable tomato with its name, an abbreviation, an ID and the time it takes till the
     * vegetable has grown.
     */
    public Tomato() {
        super(GROW_TIME, ABBREVIATION, NAME, ID);
    }
}
