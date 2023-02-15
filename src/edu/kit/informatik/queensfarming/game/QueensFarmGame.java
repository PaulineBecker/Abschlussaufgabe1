package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.market.MCMarket;
import edu.kit.informatik.queensfarming.entity.market.PriceRatio;
import edu.kit.informatik.queensfarming.entity.market.TSMarket;
import edu.kit.informatik.queensfarming.userinterface.Shell;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uyxib
 * @verion 1.0
 */
public class QueensFarmGame {
    protected static final int SIZE_OF_PRIZETABLE = 4;

    private TSMarket tsMarket = new TSMarket();
    private MCMarket mcMarket = new MCMarket();
    private StringBuilder stringBuilder = new StringBuilder();


//in Konstruktor: market
        //shuffle
        //Players
        //längeste Länge vom String mit Leerzeichen + längste Länge vom
        //alles auf die Längste Länge streckt



    public String showMarket(){
        return marketToString(getFinalPrizeTable());
    }

    private List<PriceRatio> getFinalPrizeTable(){
        List<PriceRatio> finalPriceTable = new ArrayList<>(SIZE_OF_PRIZETABLE);
        finalPriceTable.addAll(mcMarket.createPrizeTable());
        finalPriceTable.addAll(tsMarket.createPrizeTable());
        return finalPriceTable;
    }
    private String marketToString(List<PriceRatio> finalVegetablePrizes) {
        int lengthOfStringMax = 0;
        int lengthOfIntMax = 0;

        for (PriceRatio listValue : finalVegetablePrizes) {
            if (listValue.getVegetable().length() > lengthOfStringMax) {
                lengthOfStringMax = listValue.getVegetable().length();
            }
            int lengthOfPrize = String.valueOf(listValue.getPrice()).length();
            if (lengthOfPrize > lengthOfIntMax) {
                lengthOfIntMax = lengthOfPrize;
            }
        }
        for (PriceRatio listValue : finalVegetablePrizes) {
            stringBuilder.append(String.format("%-" + lengthOfStringMax + "s%" + lengthOfIntMax + "d" +
                    Shell.LINE_SEPARATOR, listValue.getVegetable(), listValue.getPrice()));
        }
        String marketToString = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return marketToString;
    }

}
