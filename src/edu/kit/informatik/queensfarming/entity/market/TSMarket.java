package edu.kit.informatik.queensfarming.entity.market;


import edu.kit.informatik.queensfarming.entity.vegetables.Vegetable;
import edu.kit.informatik.queensfarming.userinterface.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the price table of the market with tomatoes and salad
 * the prizes of the two vegetables change when tomatoes or salad is sold
 *
 * @author uyxib
 * @version 1.0
 */
public class TSMarket extends Market {
    private static final int TOMATO_INDEX = 0;
    private static final int SALAT_INDEX = 1;
    private int currentTomatoPrice;
    private int currentSalatPrice;

    /**
     * instantiates a new markt of salad and tomatoes with ist price table
     */

    public TSMarket() {
        this.priceTable[4][0] = 3;
        this.priceTable[4][1] = 6;
        this.priceTable[3][0] = 5;
        this.priceTable[3][1] = 5;
        this.priceTable[2][0] = 6;
        this.priceTable[2][1] = 4;
        this.priceTable[1][0] = 7;
        this.priceTable[1][1] = 3;
        this.priceTable[0][0] = 9;
        this.priceTable[0][1] = 2;
        currentTomatoPrice = priceTable[this.currentPrice][TOMATO_INDEX];
        currentSalatPrice = priceTable[this.currentPrice][SALAT_INDEX];
    }

    /**
     * change the prices of tomatoes and salad depending on the amount of sold vegetables
     * if no vegetables where sold, the prizes don't change
     *
     * @param soldTomatoes tomatoes that have been sold in the last turn
     * @param soldSalats salad that have been sold in the last turn
     */
    @Override
    public void changePrices(int soldTomatoes, int soldSalats) {
        int difference = (soldTomatoes - soldSalats) / Market.PRICE_CONVERSION;
        this.currentPrice = setPriceLevel(currentPrice, difference);
        this.currentSalatPrice = this.priceTable[this.currentPrice][SALAT_INDEX];
        this.currentTomatoPrice = this.priceTable[this.currentPrice][TOMATO_INDEX];
    }

    /**
     * create a price table with the sort of vegetable and the corresponding current prize
     * @return list of pairs (vegetables and the corresponding prize)
     */
    @Override
    public List<PriceRatio> createPrizeTable() {
        List<PriceRatio> priceTable = new ArrayList<>(SIZE_OF_PRICETABLE);
        priceTable.add(new PriceRatio(Vegetable.TOMATO.format()
                + Messages.COLON.format(), getCurrentTomatoPrice()));
        priceTable.add(new PriceRatio(Vegetable.SALAT.format()
                + Messages.COLON.format(), getCurrentSalatPrice()));
        return priceTable;
    }

    /**
     * gets the current prize of a tomato
     * @return current prize of a tomato
     */
    public int getCurrentTomatoPrice() {
        return currentTomatoPrice;
    }

    /**
     * get the current prize of a salad
     * @return current prize of a salad
     */
    public int getCurrentSalatPrice() {
        return currentSalatPrice;
    }
}
