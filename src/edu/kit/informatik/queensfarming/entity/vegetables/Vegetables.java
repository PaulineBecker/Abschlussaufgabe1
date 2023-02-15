package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 *
 *
 * @author uyxib
 * @verion 1.0
 */
public abstract class Vegetables {

    protected int timeToGrow;
    protected String abbreviation;
    protected String name;

    protected Vegetables(int timeToGrow, String abbreviation, String name) {
        this.timeToGrow = timeToGrow;
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public int getTimeToGrow() {
        return timeToGrow;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }
}
