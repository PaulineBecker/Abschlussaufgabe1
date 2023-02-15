package edu.kit.informatik.queensfarming.entity.market;


import edu.kit.informatik.queensfarming.entity.vegetables.Vegetable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uyxib
 * @verion 1.0
 */
public class MCMarket extends Market {

    private int currentMushroomPrice;
    private int currentCarrotPrice;
    private static final int MUSHROOM_INDEX = 0;
    private static final int CARROT_INDEX = 1;

    public MCMarket(){
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
    @Override
    public void changePrices(int soldMushrooms, int soldCarrots) {
        int difference = (soldMushrooms - soldCarrots) / 2;
        this.currentPrice = setPriceLevel(currentPrice, difference);
        this.currentCarrotPrice = this.priceTable[this.currentPrice][CARROT_INDEX];
        this.currentMushroomPrice = this.priceTable[this.currentPrice][MUSHROOM_INDEX];
    }

    @Override
    public List<PriceRatio> createPrizeTable() {
        List<PriceRatio> priceTable = new ArrayList<>(SIZE_OF_PRIZETABLE);
        priceTable.add(new PriceRatio(Vegetable.MUSHROOM.format() + ": ", getCurrentMushroomPrice()));
        priceTable.add(new PriceRatio(Vegetable.CARROT.format() + ": ",getCurrentCarrotPrice()));
        return priceTable;
    }

    public int getCurrentMushroomPrice() {
        return currentMushroomPrice;
    }

    public int getCurrentCarrotPrice() {
        return currentCarrotPrice;
    }
}
