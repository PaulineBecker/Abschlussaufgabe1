package edu.kit.informatik.queensfarming.entity.vegetables;

/**
 * represents the abstract supercalls vegetable (hereditiy).
 * There are four types of vegetables (Carrots, Mushrooms, Salad, Tomatos),
 * they are part of the game that you can harvest, sell or store in the barn.
 *
 * @author uyxib
 * @version 1.0
 */
public abstract class Vegetables {

    /**
     * the time it takes to grow a vegetable
     */
    protected int timeToGrow;
    /**
     * the abbreviation of a vegetable that is visible on the gameboard
     */
    protected String abbreviation;
    /**
     * name of the vegetable
     */
    protected String name;
    /**
     * id (int) of a vegetable
     */
    protected int id;

    /**
     * instantiates a vegetable with the given id, name, abbreviation and the time to grow
     *
     * @param timeToGrow round it takes till a vegetable has grown on the fields (int)
     * @param abbreviation shortcut of the vegetable to show it on the game board
     * @param name name of the vegetable (String)
     * @param id id of the vegetable (integer)
     */

    protected Vegetables(int timeToGrow, String abbreviation, String name, int id) {
        this.timeToGrow = timeToGrow;
        this.abbreviation = abbreviation;
        this.name = name;
        this.id = id;
    }

    /**
     * return the time to grow of a specific vegetable
     *
     * @return returns time to grow of a specific vegetable
     */
    public int getTimeToGrow() {
        return timeToGrow;
    }

    /**
     * return the abbreviation of a specific vegetable
     *
     * @return the abbreviation of a specific vegetable
     */

    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * return the name of a specific vegetable
     *
     * @return name of a specific vegetable
     */
    public String getName() {
        return name;
    }

    /**
     * return the ID of a specific vegetable
     * @return ID of a specifitc vegetable
     */
    public int getId() {
        return id;
    }
}
