package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * @author uyxib
 * @verion 1.0
 */
public class Carrot extends Vegetables {
    private static final int GROW_TIME = 1;
    private static final String NAME = "carrot";
    private static final String ABBREVIATION = "C";

    public Carrot() {
        super(GROW_TIME, ABBREVIATION, NAME);
    }
}
