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

    public PriceRatio(String vegetable, int price) {
        this.vegetable = vegetable;
        this.number = price;
    }

    public int getNumber() {
        return number;
    }

    public String getVegetable() {
        return vegetable;
    }
}
