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

    /**
     * maximal index of a price table
     */
    protected static final int MAX_PRIZE = 4;
    /**
     * minimal index of a price table
     */
    protected static final int MIN_PRIZE = 0;

    /**
     * conversion factor to calculate the index change of a price table
     */
    protected static final int PRICE_CONVERSION = 2;
    /**
     * current index of the price table
     */
    protected int currentPrice = 2;
    /**
     * priceTable with the relation between the two vegetables on a market
     */
    protected final int[][] priceTable;

    /**
     * instantiates the market with its priceTables and the currentPrizes
     */
    protected Market() {
        this.priceTable = new int[5][2];
    }

    /**
     * sets the new price level after vegetables where sold on the different markets
     *
     * @param currentPrice the current price
     * @param difference the difference to go up or down in the price tables
     * @return the new index of the price table
     */
    public int setPriceLevel(int currentPrice, int difference) {
        if (currentPrice + difference <= MIN_PRIZE) {
            return MIN_PRIZE;
        } else if (currentPrice + difference >= MAX_PRIZE) {
            return MAX_PRIZE;
        }
        return currentPrice + difference;
    }

    /**
     * abstract method to changePrices on different vegetable markets, each market contains two veggies
     * @param soldFirstVegetable first vegetable that can be sold on the market
     * @param soldSecondVegetable second vegetable that can be sold on the market
     */
    public abstract void changePrices(int soldFirstVegetable, int soldSecondVegetable);

    /**
     * abstract method to create a price table with pairs of vegetables and the corresponding prizes
     * @return list with pairs of vegetables and the current corresponding prize
     */

    public abstract List<PriceRatio> createPrizeTable();

}
