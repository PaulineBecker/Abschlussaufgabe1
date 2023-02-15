package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * @author uyxib
 * @verion 1.0
 */
public class Tomato extends Vegetables {

    private static final int GROW_TIME = 3;
    private static final String NAME = "tomato";
    private static final String ABBREVIATION = "T";

    public Tomato() {
        super(GROW_TIME, ABBREVIATION, NAME);
    }
}
