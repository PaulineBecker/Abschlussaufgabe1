package edu.kit.informatik.queensfarming.entity.market;

import java.util.List;

/**
 * represents the marekt where players can buy vegetables.
 * Prices will adapt if a player has sold vegetables.
 *
 * @author uyxib
 * @version 1.0
 */
public abstract class Market {

    /**
     * the price conversion to change prices on the market
     */
    protected static final int SIZE_OF_PRIZETABLE = 2;

    protected static final int MAX_PRIZE = 4;
    protected static final int MIN_PRIZE = 0;
    protected final int priceConversion;
    protected int currentPrice;
    protected final int[][] priceTable;



    protected Market() {
        this.priceConversion = 2;
        this.priceTable = new int[5][2];
        this.currentPrice = 2;
    }

    public int setPriceLevel(int currentPrice, int difference) {
        if (currentPrice + difference <= MIN_PRIZE) {
            return MIN_PRIZE;
        } else if (currentPrice + difference >= MAX_PRIZE) {
            return MAX_PRIZE;
        }
        return currentPrice + difference;
    }

    public abstract void changePrices(int soldFirstVegetable, int soldSecondVegetable);

    public abstract List<PriceRatio> createPrizeTable();

}
