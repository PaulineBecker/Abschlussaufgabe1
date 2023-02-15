package edu.kit.informatik.queensfarming.entity.market;


import edu.kit.informatik.queensfarming.entity.vegetables.Vegetable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uyxib
 * @verion 1.0
 */
public class TSMarket extends Market {

    private int currentTomatoPrice;
    private int currentSalatPrice;
    private static final int TOMATO_INDEX = 0;
    private static final int SALAT_INDEX = 1;

    public TSMarket(){
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

    @Override
    public void changePrices(int soldTomatos, int soldSalats) {
        int difference = (soldTomatos - soldSalats) / 2;
            this.currentPrice = setPriceLevel(currentPrice, difference);
            this.currentSalatPrice = this.priceTable[this.currentPrice][SALAT_INDEX];
            this.currentTomatoPrice = this.priceTable[this.currentPrice][TOMATO_INDEX];
    }

    @Override
    public List<PriceRatio> createPrizeTable() {
        List<PriceRatio> priceTable = new ArrayList<>(SIZE_OF_PRIZETABLE);
        priceTable.add(new PriceRatio(Vegetable.TOMATO.format() + ": ", getCurrentTomatoPrice()));
        priceTable.add(new PriceRatio(Vegetable.SALAT.format() + ": ", getCurrentSalatPrice()));
        return priceTable;
    }

    public int getCurrentTomatoPrice() {
        return currentTomatoPrice;
    }

    public int getCurrentSalatPrice() {
        return currentSalatPrice;
    }
}
