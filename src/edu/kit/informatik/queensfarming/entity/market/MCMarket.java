package edu.kit.informatik.queensfarming.entity.market;


import edu.kit.informatik.queensfarming.entity.vegetables.Vegetable;
import edu.kit.informatik.queensfarming.userinterface.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the price table of the market with mushrooms and carrots
 * the prizes of the two vegetables change when mushrooms or carrots are sold
 *
 * @author uyxib
 * @version 1.0
 */
public class MCMarket extends Market {
    private static final int MUSHROOM_INDEX = 0;
    private static final int CARROT_INDEX = 1;
    private int currentMushroomPrice;
    private int currentCarrotPrice;

    /**
     * instantiates a new markt of mushrooms and carrots with ist pricetable
     */

    public MCMarket() {
        this.priceTable[4][0] = 12;
        this.priceTable[4][1] = 3;
        this.priceTable[3][0] = 15;
        this.priceTable[3][1] = 2;
        this.priceTable[2][0] = 16;
        this.priceTable[2][1] = 2;
        this.priceTable[1][0] = 17;
        this.priceTable[1][1] = 2;
        this.priceTable[0][0] = 20;
        this.priceTable[0][1] = 1;
        currentMushroomPrice = priceTable[this.currentPrice][MUSHROOM_INDEX];
        currentCarrotPrice = priceTable[this.currentPrice][CARROT_INDEX];
    }

    /**
     * change the prices of mushrooms and carrots depending on the amount of sold vegetables
     * if no vegetables where sold, the prizes don't change
     *
     * @param soldMushrooms mushrooms that have been sold in the last turn
     * @param soldCarrots carrots that have been sold in the last turn
     */
    @Override
    public void changePrices(int soldMushrooms, int soldCarrots) {
        int difference = (soldMushrooms - soldCarrots) / Market.PRICE_CONVERSION;
        this.currentPrice = setPriceLevel(currentPrice, difference);
        this.currentCarrotPrice = this.priceTable[this.currentPrice][CARROT_INDEX];
        this.currentMushroomPrice = this.priceTable[this.currentPrice][MUSHROOM_INDEX];
    }

    /**
     * create a price table with the sort of vegetable and the corresponding current prize
     * @return list of pairs (vegetables and the corresponding prize)
     */
    @Override
    public List<PriceRatio> createPrizeTable() {
        List<PriceRatio> priceTable = new ArrayList<>(SIZE_OF_PRIZETABLE);
        priceTable.add(new PriceRatio(Vegetable.MUSHROOM.format()
                + Messages.COLON.format(), getCurrentMushroomPrice()));
        priceTable.add(new PriceRatio(Vegetable.CARROT.format()
                + Messages.COLON.format(), getCurrentCarrotPrice()));
        return priceTable;
    }

    /**
     * gets the current prize of mushrooms
     * @return current prize of mushrooms
     */
    public int getCurrentMushroomPrice() {
        return currentMushroomPrice;
    }

    /**
     * gets the current prize of carrots
     * @return current prize of carrots
     */

    public int getCurrentCarrotPrice() {
        return currentCarrotPrice;
    }
}
