package edu.kit.informatik.queensfarming.entity.market;

import edu.kit.informatik.queensfarming.userinterface.Shell;

import java.util.List;

/**
 * represents the market where players can buy vegetables.
 * Prices will adapt if a player has sold vegetables.
 *
 * @author uyxib
 * @version 1.0
 */
public abstract class Market {
    /**
     * the price conversion to change prices on the market
     */
    protected static final int SIZE_OF_PRICETABLE = 2;

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
    private static final String CREATE_FLUSH_RIGHT1 = "%-";
    private static final String CREATE_FLUSH_RIGHT2 = "s%";
    private static final String CREATE_FLUSH_RIGHT3 = "d";

    /**
     * current index of the price table
     */
    protected int currentPrice = 2;
    /**
     * priceTable with the relation between the two vegetables on a market
     */
    protected final int[][] priceTable;


    private final StringBuilder stringBuilder = new StringBuilder();


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

    /**
     * creates the representation of the market in the game with all the prizes and the vegetables
     * @param finalVegetablePrizes a table with all the current prizes connect to the suitable vegetable
     * @return the string representation of the markt with its current prizes
     */
    public String marketToString(List<PriceRatio> finalVegetablePrizes) {
        int lengthOfStringMax = 0;
        int lengthOfIntMax = 0;

        for (PriceRatio listValue : finalVegetablePrizes) {
            if (listValue.getVegetable().length() > lengthOfStringMax) {
                lengthOfStringMax = listValue.getVegetable().length();
            }
            int lengthOfPrize = String.valueOf(listValue.getNumber()).length();
            if (lengthOfPrize > lengthOfIntMax) {
                lengthOfIntMax = lengthOfPrize;
            }
        }
        for (PriceRatio listValue : finalVegetablePrizes) {
            stringBuilder.append(String.format(CREATE_FLUSH_RIGHT1 + lengthOfStringMax + CREATE_FLUSH_RIGHT2
                    + lengthOfIntMax + CREATE_FLUSH_RIGHT3
                    + Shell.LINE_SEPARATOR, listValue.getVegetable(), listValue.getNumber()));
        }
        String marketToString = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return marketToString;
    }

}
