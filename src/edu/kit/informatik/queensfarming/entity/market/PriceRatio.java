package edu.kit.informatik.queensfarming.entity.market;

/**
 * connects the prize of a vegetable with the vegetable itself.
 *
 * @author uyxib
 * @verion 1.0
 */
public class PriceRatio {
    private final String vegetable;
    private final int price;

    public PriceRatio(String vegetable, int price) {
        this.vegetable = vegetable;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getVegetable() {
        return vegetable;
    }
}
