package edu.kit.informatik.queensfarming.entity.market;

/**
 * connects the prize of a vegetable with the vegetable itself.
 *
 * @author uyxib
 * @version 1.0
 */
public class PriceRatio {
    private final String vegetable;
    private final int number;

    /**
     * instantiates a new vegetable
     * @param vegetable name of the vegetable
     * @param price current prize of the vegetable
     */
    public PriceRatio(String vegetable, int price) {
        this.vegetable = vegetable;
        this.number = price;
    }

    /**
     * gets the prize of a vegetable
     * @return prize of a vegetable
     */
    public int getNumber() {
        return number;
    }

    /**
     * gets the name of the vegetable
     * @return name of the vegetable
     */
    public String getVegetable() {
        return vegetable;
    }
}
