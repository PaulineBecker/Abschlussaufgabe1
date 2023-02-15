package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * @author uyxib
 * @verion 1.0
 */
public class Mushroom extends Vegetables{

    private static final int GROW_TIME = 4;
    private static final String NAME = "mushroom";
    private static final String ABBREVIATION = "M";

    public Mushroom() {
        super(GROW_TIME, ABBREVIATION, NAME);
    }
}
