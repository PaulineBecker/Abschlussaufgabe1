package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.Player;
import edu.kit.informatik.queensfarming.entity.market.MCMarket;
import edu.kit.informatik.queensfarming.entity.market.PriceRatio;
import edu.kit.informatik.queensfarming.entity.market.TSMarket;
import edu.kit.informatik.queensfarming.entity.tiles.*;
import edu.kit.informatik.queensfarming.entity.vegetables.Vegetable;
import edu.kit.informatik.queensfarming.entity.vegetables.Vegetables;
import edu.kit.informatik.queensfarming.userinterface.Messages;
import edu.kit.informatik.queensfarming.userinterface.Shell;
import edu.kit.informatik.queensfarming.utility.Coordinates;

import java.util.*;

/**
 * @author uyxib
 * @version 1.0
 */
public class QueensFarmGame {
    private static final int SIZE_OF_PRICETABLE = 4;
    private static final int MULTIPLIER_G_LFI_FO = 2;
    private static final int MULTIPLIER_FIELD = 3;
    private static final int START_COORDINATES = -1;
    private static final int START_PLAYER = 0;
    private static final String CREATE_FLUSH_RIGHT1 = "%-";
    private static final String CREATE_FLUSH_RIGHT2 = "s%";
    private static final String CREATE_FLUSH_RIGHT3 = "d";


    private TSMarket tsMarket = new TSMarket();
    private MCMarket mcMarket = new MCMarket();
    private final StringBuilder stringBuilder = new StringBuilder();

    private List<Player> playerList = new ArrayList<>();

    private Player currentPlayer;
    private final int goldToWin;
    private final int goldToStart;
    private final int numberOfPlayers;
    private final long seedToShuffle;
    private final List<String> nameList;
    private final List<Tile> unassignedTiles = new ArrayList<>();

    public QueensFarmGame(int goldToWin, int goldToStart, int playerNumber,
                          long seedToShuffle, List<String> playerNames) {
        this.goldToWin = goldToWin;
        this.goldToStart = goldToStart;
        this.numberOfPlayers = playerNumber;
        this.seedToShuffle = seedToShuffle;
        this.nameList = playerNames;
        createPlayers();
        this.currentPlayer = playerList.get(START_PLAYER);
        shuffleCards();
    }

    public String showMarket() {
        return marketToString(getFinalPrizeTable());
    }

    public String showBarn() {
        return barnToString(createVegetableList());
    }

    private String barnToString(List<PriceRatio> vegetablesInBarn) {
        Tile barn = currentPlayer.getBoardGame().get(Player.INDEX_OF_BARN);
        if (barn.vegetablesList.size() == 0) {
            return (barn.name.concat(Shell.LINE_SEPARATOR)
                    .concat(Messages.GOLD.format()).concat(String.valueOf(currentPlayer.getGold())));
        }

        int sum = barn.vegetablesList.size();
        int sumLength = String.valueOf(sum).length();
        int goldLength = String.valueOf(currentPlayer.getGold()).length();
        int lengthOfStringMax = 0;
        int lengthOfIntMax = 0;

        for (PriceRatio listValue : vegetablesInBarn) {
            if (listValue.getVegetable().length() > lengthOfStringMax) {
                lengthOfStringMax = listValue.getVegetable().length();
            }
            int lengthOfPrize = String.valueOf(listValue.getNumber()).length();
            if (lengthOfPrize > lengthOfIntMax) {
                lengthOfIntMax = lengthOfPrize;
            }
        }

        if (sumLength > lengthOfIntMax) { lengthOfIntMax = sumLength; }
        if (goldLength > lengthOfIntMax) { lengthOfIntMax = goldLength; }

        String formatBarn = CREATE_FLUSH_RIGHT1 + lengthOfStringMax + CREATE_FLUSH_RIGHT2 + lengthOfIntMax
                + CREATE_FLUSH_RIGHT3 + Shell.LINE_SEPARATOR;
        for (PriceRatio listValue : vegetablesInBarn) {
            stringBuilder.append(String.format(formatBarn, listValue.getVegetable(), listValue.getNumber()));
        }

        stringBuilder.append("-".repeat(lengthOfIntMax + lengthOfStringMax)).append(Shell.LINE_SEPARATOR);
        stringBuilder.append(String.format(formatBarn, Messages.SUM.format(), sum)).append(Shell.LINE_SEPARATOR);
        stringBuilder.append(String.format(formatBarn, Messages.GOLD.format(), currentPlayer.getGold()));


        String barnToString = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return barnToString;
    }

    private List<PriceRatio> getFinalPrizeTable() {
        List<PriceRatio> finalPriceTable = new ArrayList<>(SIZE_OF_PRICETABLE);
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

    private int countVegetables(int id) {
        int count = 0;
        for (Vegetables vegetable : currentPlayer.getBoardGame().get(Player.INDEX_OF_BARN).vegetablesList) {
            if (id == vegetable.getId()) {
                count++;
            }
        }
        return count;
    }

    private List<PriceRatio> createVegetableList() {
        List<PriceRatio> vegetablesInBarn = new ArrayList<>();
        vegetablesInBarn.add(new PriceRatio(Vegetable.CARROT.format()
                + Messages.COLON.format(), countVegetables(1)));
        vegetablesInBarn.add(new PriceRatio(Vegetable.MUSHROOM.format()
                + Messages.COLON.format(), countVegetables(0)));
        vegetablesInBarn.add(new PriceRatio(Vegetable.SALAT.format()
                + Messages.COLON.format(), countVegetables(3)));
        vegetablesInBarn.add(new PriceRatio(Vegetable.TOMATO.format()
                + Messages.COLON.format(), countVegetables(2)));
        vegetablesInBarn.removeIf(vegetablesCount -> vegetablesCount.getNumber() == 0);
        vegetablesInBarn.sort(new Comparator<PriceRatio>() {
            @Override
            public int compare(PriceRatio o1, PriceRatio o2) {
                return Integer.compare(o1.getNumber(), o2.getNumber());
            }
        });
        return vegetablesInBarn;
    }

    private void createPlayers() {
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new Player(goldToStart, nameList.get(i)));
        }
    }

    private void shuffleCards() {
        Coordinates startCoordinates = new Coordinates(START_COORDINATES, START_COORDINATES);

        Random randomSeed = new Random(seedToShuffle);


        for (int i = 0; i < numberOfPlayers * MULTIPLIER_G_LFI_FO; i++) {
            unassignedTiles.add(new Garden(startCoordinates));
        }
        for (int i = 0; i < numberOfPlayers * MULTIPLIER_FIELD; i++) {
            unassignedTiles.add(new Field(startCoordinates));
        }
        for (int i = 0; i < numberOfPlayers * MULTIPLIER_G_LFI_FO; i++) {
            unassignedTiles.add(new LargeField(startCoordinates));
        }
        for (int i = 0; i < numberOfPlayers * MULTIPLIER_G_LFI_FO; i++) {
            unassignedTiles.add(new Forest(startCoordinates));
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            unassignedTiles.add(new LargeForest(startCoordinates));
        }

        Collections.shuffle(unassignedTiles, randomSeed);
    }

}
