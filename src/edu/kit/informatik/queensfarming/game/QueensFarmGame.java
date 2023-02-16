package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.Player;
import edu.kit.informatik.queensfarming.entity.market.MCMarket;
import edu.kit.informatik.queensfarming.entity.market.PriceRatio;
import edu.kit.informatik.queensfarming.entity.market.TSMarket;
import edu.kit.informatik.queensfarming.entity.tiles.*;
import edu.kit.informatik.queensfarming.entity.vegetables.*;
import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.userinterface.Messages;
import edu.kit.informatik.queensfarming.userinterface.Shell;
import edu.kit.informatik.queensfarming.utility.Coordinates;
import edu.kit.informatik.queensfarming.utility.VegetablesOccurence;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Comparator;


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
    private static final int BARN_INDEX = 0;
    private static final int VEGETABLE_SPOIL = 6;
    private static final int INDEX_OF_NEXT_PLAYER = 1;
    private static final String CREATE_FLUSH_RIGHT1 = "%-";
    private static final String CREATE_FLUSH_RIGHT2 = "s%";
    private static final String CREATE_FLUSH_RIGHT3 = "d";
    private static final String MUSHROOM = "mushroom";
    private static final String CARROT = "carrot";
    private static final String SALAD = "salad";
    private static final String TOMATO = "tomato";


    private TSMarket tsMarket = new TSMarket();
    private MCMarket mcMarket = new MCMarket();
    private final StringBuilder stringBuilder = new StringBuilder();

    private List<Player> playerList = new ArrayList<>();

    private Player currentPlayer;
    private final int goldToWin;
    private final int goldToStart;
    private final int numberOfPlayers;
    private final long seedToShuffle;
    private int soldCarrots = 0;
    private int soldMushrooms = 0;
    private int soldSalad = 0;
    private int soldTomato = 0;
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

    public String buyVegetables(String input) {
        String[] splittedString = input.split(Shell.COMMAND_SEPERATOR);
        int price = 0;
        String veggie = Shell.COMMAND_SEPERATOR;
        switch(splittedString[2]) {
            case (MUSHROOM) -> {
                price = mcMarket.getCurrentMushroomPrice();
                currentPlayer.setGold(currentPlayer.getGold() - mcMarket.getCurrentMushroomPrice());
                veggie = MUSHROOM;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Mushroom());
            }
            case (CARROT) -> {
                price = mcMarket.getCurrentCarrotPrice();
                currentPlayer.setGold(currentPlayer.getGold() - mcMarket.getCurrentCarrotPrice());
                veggie = CARROT;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Carrot());
            }
            case (SALAD) -> {
                price = tsMarket.getCurrentSalatPrice();
                currentPlayer.setGold(currentPlayer.getGold() - tsMarket.getCurrentSalatPrice());
                veggie = SALAD;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Salad());
            }
            case (TOMATO) -> {
                price = tsMarket.getCurrentTomatoPrice();
                currentPlayer.setGold(currentPlayer.getGold() - tsMarket.getCurrentTomatoPrice());
                veggie = TOMATO;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Tomato());
            }
        }
        return Messages.BOUGHT_OBJECT.format(veggie, price);
    }
    public String buyLand(String input) {
        String[] splittedString = input.split(Shell.COMMAND_SEPERATOR);
        int xCoordinate = Integer.parseInt(splittedString[2]);
        int yCoordinate = Integer.parseInt(splittedString[3]);
        if (unassignedTiles.size() == 0) {
            throw new GameException("Error: all Tiles are already used on the player's game board"); //TODO try und catch nicht vergesen
        }
        Tile boughtTile = unassignedTiles.get(0);
        boughtTile.setCoordinates(new Coordinates(xCoordinate, yCoordinate));
        unassignedTiles.remove(0);
        currentPlayer.getBoardGame().add(boughtTile);
        return Messages.BOUGHT_OBJECT.format(boughtTile.getName(), 0); //TODO Prize berechnen mit Formel
    }

    public String sellVegetables(String input) {
        int goldBeforeMove = currentPlayer.getGold();
        int soldVeggies = 0;
        String[] splittedString = input.split(Shell.COMMAND_SEPERATOR);
        if (splittedString.length == 1) {
            return Messages.SELL_VEGETABLES.format(0, VegetablesOccurence.VEGETABLES.format(), 0);
        } else if (splittedString[1].equals("all")) {
            for (int i = currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() - 1; i >= 0; i--) {
                    sellOneVegetable(i);
                    soldVeggies++;
            }

        } else if (!splittedString[1].equals("all")) {
            for (int i = 1; i < splittedString.length; i++) {
                for (int j = currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() - 1; j >= 0; j--) {
                    if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().get(j).getName().equals(splittedString[i])) {
                        sellOneVegetable(j);
                        soldVeggies++;
                    }
                }
            }
        }
        int newGold = currentPlayer.getGold() - goldBeforeMove;
        if (soldVeggies == 1) {
            return Messages.SELL_VEGETABLES.format(soldVeggies, VegetablesOccurence.VEGETABLE.format(), newGold);
        }
        return Messages.SELL_VEGETABLES.format(soldVeggies, VegetablesOccurence.VEGETABLES.format(), newGold);
    }

    public String startTurn() {
        stringBuilder.append(Messages.TURN_OF_PLAYER.format(currentPlayer.getName())).append(Shell.LINE_SEPARATOR);
        //TODO if vegetables has grown since your last turn
        if (VEGETABLE_SPOIL - currentPlayer.getBoardGame().get(BARN_INDEX).getCountdown() == 0) {
            stringBuilder.append(Messages.SPOILED_VEGETABLES.format());
        }
        String startTurnPrint = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return startTurnPrint;

    }

    public void nextTurn() {

        if (playerList.indexOf(currentPlayer) == playerList.size() - INDEX_OF_NEXT_PLAYER) {
            currentPlayer = playerList.get(START_PLAYER);
            nextRound();
        } else {
            currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + INDEX_OF_NEXT_PLAYER);
        }
        mcMarket.changePrices(soldMushrooms, soldCarrots); //TODO nochmal checken müsste aber so passen
        tsMarket.changePrices(soldTomato, soldSalad); //TODO hier auch wird nach jedem Zug ja zu default 0
        setSoldVeggiesToDefault();
    }

    public String endGame() {
        int maxGold = 0;
        List<String> winnerList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = playerList.get(i).getName();
            int finalGold = playerList.get(i).getGold();
            stringBuilder.append(Messages.GOLD_AFTER_GAME.format(i+1, playerName, finalGold))
                    .append(Shell.LINE_SEPARATOR);
            if (finalGold > maxGold) {
                winnerList.clear();
                winnerList.add(playerName);
                maxGold = finalGold;
            } else if (finalGold == maxGold) {
                winnerList.add(playerName);
            }
        }
        if (winnerList.size() == 1) {
            stringBuilder.append(Messages.PLAYER_WON.format(winnerList.get(0)));
        } else if (winnerList.size() == 2) {
            stringBuilder.append(Messages.TWO_PLAYER_WON.format(winnerList.get(0), winnerList.get(1)));
        } else if (winnerList.size() > 2) {
            String winners = Shell.EMPTY_STRING;
            for (int i = 0; i < winnerList.size()-1; i++) {
                winners += winnerList.get(i).concat(", ");
            }
            winners = winners.substring(0, winners.length()-2);
            stringBuilder.append(winners);
            stringBuilder.append(Messages.MANY_PLAYER_WON.format(winnerList.get(winnerList.size()-1)));
        }

        String winnerPrint = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return winnerPrint;
    }

    private void nextRound() {
        for (Player player : playerList) {
            if (!player.getBoardGame().get(BARN_INDEX).getVegetablesList().isEmpty()) {
                player.getBoardGame().get(BARN_INDEX).
                        setCountdown(player.getBoardGame().get(BARN_INDEX).getCountdown() + 1);
            }
            if (player.getGold() >= goldToWin) {
                endGame();
                return;
            }
        }
        // TODO state pattern einbauen weil Spiel dann zu Ende
        //TODO Wachsen des Gemüses

    }

    private String barnToString(List<PriceRatio> vegetablesInBarn) {
        Tile barn = currentPlayer.getBoardGame().get(BARN_INDEX);
        if (barn.getVegetablesList().size() == 0) {
            return (barn.getName().concat(Shell.LINE_SEPARATOR)
                    .concat(Messages.GOLD.format()).concat(String.valueOf(currentPlayer.getGold())));
        } else {
            if (VEGETABLE_SPOIL - barn.getCountdown() == 1) {
                stringBuilder.append(Messages.BARN_SPOILS_TOMORROW.format()).append(Shell.LINE_SEPARATOR);
            } else if (VEGETABLE_SPOIL - barn.getCountdown() > 1) {
                stringBuilder.append((Messages.BARN_SPOILS.format(VEGETABLE_SPOIL - barn.getCountdown())))
                        .append(Shell.LINE_SEPARATOR);
            }
        }

        int sum = barn.getVegetablesList().size();
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
        for (Vegetables vegetable : currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList()) {
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

    private void setSoldVeggiesToDefault() {
        soldMushrooms = 0;
        soldSalad = 0;
        soldCarrots = 0;
        soldTomato = 0;
    }

    private void sellOneVegetable(int indexOfVegetableList) {
        switch(currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().get(indexOfVegetableList).getName()) {
            case("mushroom") -> {
                soldMushrooms++;
                currentPlayer.setGold(currentPlayer.getGold() + mcMarket.getCurrentMushroomPrice());
            }
            case("carrot") -> {
                soldCarrots++;
                currentPlayer.setGold(currentPlayer.getGold() + mcMarket.getCurrentCarrotPrice());
            }
            case("salad") -> {
                soldSalad++;
                currentPlayer.setGold(currentPlayer.getGold() + tsMarket.getCurrentSalatPrice());
            }
            case("tomato") -> {
                soldTomato++;
                currentPlayer.setGold(currentPlayer.getGold() + tsMarket.getCurrentTomatoPrice());
            }
        }
        currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().remove(indexOfVegetableList);
    }
}
