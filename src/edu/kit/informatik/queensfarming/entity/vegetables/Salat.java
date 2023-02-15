package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * @author uyxib
 * @verion 1.0
 */
public class Salat extends Vegetables{

    private static final int GROW_TIME = 2;
    private static final String NAME = "salat";
    private static final String ABBREVIATION = "S";

    public Salat() {
        super(GROW_TIME, ABBREVIATION, NAME);
    }
}
