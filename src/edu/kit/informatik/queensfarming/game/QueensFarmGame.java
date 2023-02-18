package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.Player;
import edu.kit.informatik.queensfarming.entity.market.MCMarket;
import edu.kit.informatik.queensfarming.entity.market.PriceRatio;
import edu.kit.informatik.queensfarming.entity.market.TSMarket;
import edu.kit.informatik.queensfarming.entity.tiles.Garden;
import edu.kit.informatik.queensfarming.entity.tiles.Field;
import edu.kit.informatik.queensfarming.entity.tiles.LargeField;
import edu.kit.informatik.queensfarming.entity.tiles.LargeForest;
import edu.kit.informatik.queensfarming.entity.tiles.Forest;
import edu.kit.informatik.queensfarming.entity.tiles.Tile;
import edu.kit.informatik.queensfarming.entity.vegetables.Mushroom;
import edu.kit.informatik.queensfarming.entity.vegetables.Tomato;
import edu.kit.informatik.queensfarming.entity.vegetables.Carrot;
import edu.kit.informatik.queensfarming.entity.vegetables.Salad;
import edu.kit.informatik.queensfarming.entity.vegetables.Vegetable;
import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.userinterface.*;
import edu.kit.informatik.queensfarming.utility.Coordinates;
import edu.kit.informatik.queensfarming.utility.VegetablesOccurence;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * represents the game where children can play QueensFarm with all the functionalities children have during the game
 * and what happens when a next turn or a next round starts
 * @author uyxib
 * @version 1.0
 */
public class QueensFarmGame {
    private static final int SIZE_OF_PRICETABLE = 4;
    private static final int MULTIPLIER_G_LFI_FO = 2;
    private static final int MULTIPLIER_FIELD = 3;
    private static final int START_COORDINATES = -1;
    private static final int AREA_IS_NOT_BOUGHT = -1;
    private static final int START_PLAYER = 0;
    private static final int BARN_INDEX = 0;
    public static final int VEGETABLE_SPOIL = 6;
    private static final int INDEX_OF_NEXT_PLAYER = 1;
    private static final String CREATE_FLUSH_RIGHT1 = "%-";
    private static final String CREATE_FLUSH_RIGHT2 = "s%";
    private static final String CREATE_FLUSH_RIGHT3 = "d";
    private static final String MUSHROOM = "mushroom";
    private static final String CARROT = "carrot";
    private static final String SALAD = "salad";
    private static final String TOMATO = "tomato";

    /**
     * The execution state of this database.
     */
    private ExecutionState executionState;

    /**
     * THe game state of this game
     */
    private GameState gameState;
    private int movesInTurn = 0;

    private final TSMarket tsMarket = new TSMarket();
    private final MCMarket mcMarket = new MCMarket();
    private final StringBuilder stringBuilder = new StringBuilder();

    private final List<Player> playerList = new ArrayList<>();
    private final List<Tile> tilesInCountdown = new ArrayList<>();

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

    /**
     * instantiates a new Queens Farm Game with the start gold, gold to win, number of players, player names and seed
     * @param goldToWin gold that is necessary to win the game
     * @param goldToStart the amount of gold every player has at the beginning of the game
     * @param playerNumber the number of players that are currently playing
     * @param seedToShuffle the seed to shuffle the tiles before the game starts
     * @param playerNames the names of the players in one list
     */

    public QueensFarmGame(int goldToWin, int goldToStart, int playerNumber,
                          long seedToShuffle, List<String> playerNames) {
        this.executionState = ExecutionState.RUNNING;
        this.gameState = GameState.START_TURN;
        this.goldToWin = goldToWin;
        this.goldToStart = goldToStart;
        this.numberOfPlayers = playerNumber;
        this.seedToShuffle = seedToShuffle;
        this.nameList = playerNames;
        createPlayers();
        this.currentPlayer = playerList.get(START_PLAYER);
        shuffleCards();
        if (this.goldToStart - this.goldToWin >= 0) {
            this.executionState = ExecutionState.EXITED;
        }
    }

    public boolean isActive() {
        return executionState == ExecutionState.RUNNING;
    }

    public void quit() {
        executionState = ExecutionState.EXITED;
    }

    public void endTurn() {
        gameState = GameState.END_TURN;
    }
    public boolean playerIsTurning() { return gameState == GameState.START_TURN; }

    /**
     * shows the market with all the different prizes of the vegetables
     * @return String to visualize the market with the prizes of vegetables
     */
    public String showMarket() {
        return marketToString(getFinalPrizeTable());
    }

    /**
     * shows the barn of the current Player with all the vegetables in it, the sum and the amount of gold
     * @return the string to visualize the barn
     */

    public String showBarn() {
        return currentPlayer.barnToString();
    }

    /**
     * current Player buys 1 Vegetables with the current markt prize
     * @param input inout that matches to the buy action in the game
     * @return the String with the prize for the bought vegetable and the name of the vegetable
     */

    public String buyVegetable(String input) {
        int price = 0;
        String veggie = Shell.COMMAND_SEPERATOR;
        switch(input) {
            case (MUSHROOM) -> {
                price = mcMarket.getCurrentMushroomPrice();
                isVegetableBuyAllowed(price); //TODO try catch for each case
                currentPlayer.setGold(currentPlayer.getGold() - mcMarket.getCurrentMushroomPrice());
                veggie = MUSHROOM;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Mushroom());
            }
            case (CARROT) -> {
                price = mcMarket.getCurrentCarrotPrice();
                isVegetableBuyAllowed(price);
                currentPlayer.setGold(currentPlayer.getGold() - mcMarket.getCurrentCarrotPrice());
                veggie = CARROT;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Carrot());
            }
            case (SALAD) -> {
                price = tsMarket.getCurrentSalatPrice();
                isVegetableBuyAllowed(price);
                currentPlayer.setGold(currentPlayer.getGold() - tsMarket.getCurrentSalatPrice());
                veggie = SALAD;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Salad());
            }
            case (TOMATO) -> {
                price = tsMarket.getCurrentTomatoPrice();
                isVegetableBuyAllowed(price);
                currentPlayer.setGold(currentPlayer.getGold() - tsMarket.getCurrentTomatoPrice());
                veggie = TOMATO;
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Tomato());
            }
        }
        movesInTurn++;
        return Messages.BOUGHT_OBJECT.format(veggie, price);
    }

    /**
     * the current player buys land. Gets the coordinates where the new land should be on the game board of the player
     * calculates the prize of the land
     * the players gets the first tile of the shuffled cards and has to pay the prize for gold
     * @throws GameException if player doesn't have enough gold, if the place on the game board is already used or
     * if there are no cards to buy left
     * @param coordinates input from player that matches the buy command
     * @return the prize of the land and which tile the player has bought
     */
    public String buyLand(List<Integer> coordinates) {
        int xCoordinate = coordinates.get(0);
        int yCoordinate = coordinates.get(1);
        boolean buyable = false;
        if (unassignedTiles.size() == 0) {
            throw new GameException("Error: all Tiles are already used on the player's game board"); //TODO try und catch nicht vergessen
        }
        Tile boughtTile = unassignedTiles.get(0);
        for (Tile tile : currentPlayer.getBoardGame()) {
            if (tile.getCoordinates().compareTo(new Coordinates(xCoordinate, yCoordinate)) == 1) {
                buyable = true;
            }
        }
        if (!buyable) {
            throw new GameException("Error: you are trying to buy a piece of land that is not adjacent to your property");
        }
        int prize = 10 * (Math.abs(xCoordinate) + Math.abs(yCoordinate) - 1);
        if (currentPlayer.getGold() - prize < 0) {
            throw new GameException("Error: you don't have enough gold to buy the tile of land.");
        }
        if (getBoardGameIndexFromCoordinates(xCoordinate, yCoordinate) != AREA_IS_NOT_BOUGHT) {
            throw new GameException("Error: the field where you want to buy land is already bought");
        }
        boughtTile.setCoordinates(new Coordinates(xCoordinate, yCoordinate));
        unassignedTiles.remove(0);
        currentPlayer.getBoardGame().add(boughtTile);
        currentPlayer.setGold(currentPlayer.getGold() - prize);
        movesInTurn++;
        return Messages.BOUGHT_OBJECT.format(boughtTile.getName(), prize);
    }

    /**
     * harvest the given tile on the gameboard of the current player and adds them to the barn
     * start the countdown if the tile was full but is now not empty, stopps the countdown if the tile is empty now
     * @throws GameException the player wants to harvest on the barn, or if he tries to harvest more vegetables
     * than on the field or if he tries to harvest on a field that doesn't exist on his board or if the tile is empty.
     * @param input the given input command to harvest a tile
     * @return the string how many vegetables where successfully harvested and which vegetable excactly
     */
    public String harvest(int[] input) {
        int xCoordinate = input[0];
        int yCoordinate = input[1];
        int numberOfVeggies = input[2];
        int indexToHarvestOn = isTileBought(xCoordinate, yCoordinate); //TODO try catch
        Tile currentTile = currentPlayer.getBoardGame().get(indexToHarvestOn);
        if (currentPlayer.getBoardGame().get(indexToHarvestOn).getVegetablesList().size() < numberOfVeggies) {
            throw new GameException(ExceptionMessages.TOO_MUCH_HARVESTING.format()); //TODO try catch
        }
        String veggieName = currentTile.getVegetablesList().get(0).getName();
        checksIllegalBarnMove(xCoordinate, yCoordinate); //TODO try catch
        for (int i = 1; i <= numberOfVeggies; i++) {
            switch(veggieName) {
                case(MUSHROOM) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Mushroom());
                case(TOMATO) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Tomato());
                case(SALAD) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Salad());
                case(CARROT) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Carrot());
            }
            currentTile.getVegetablesList().remove(numberOfVeggies - i);
        }
        if (currentTile.getVegetablesList().size() == 0) { //TODO vorher return statement problem???
            tilesInCountdown.remove(currentTile);
        } else if ((currentTile.getVegetablesList().size() + numberOfVeggies) == currentTile.getCapacity()) {
            tilesInCountdown.add(currentTile);
        }
        movesInTurn++;
        if (numberOfVeggies == 1) {
            return Messages.HARVESTED_LAND.format(numberOfVeggies, veggieName);
        }
        switch(veggieName) {
            case(MUSHROOM) -> veggieName = Vegetable.MUSHROOM.format();
            case(TOMATO) -> veggieName = Vegetable.TOMATO.format();
            case(SALAD) -> veggieName = Vegetable.SALAT.format();
            case(CARROT) -> veggieName = Vegetable.CARROT.format();
        }
        return Messages.HARVESTED_LAND.format(numberOfVeggies, veggieName);
    }

    /**
     * plant 1 vegetable that the player want to plant on a specific field
     * @throws GameException if area of land was not bought before, or if the tile to plant is not empty,
     * or if the given vegetable can not be planted on the tile, or if the veggie to plant doesn't exist in the barn
     *
     * @param input the give input from the player
     */
    public void plant(String[] input) {
        int xCoordinate = Integer.parseInt(input[0]);
        int yCoordinate = Integer.parseInt(input[1]);
        int indexToPlantOn = isTileBought(xCoordinate, yCoordinate); //TODO Try and catch
        String veggieToPlant = input[2];
        checksIllegalBarnMove(xCoordinate, yCoordinate); //TODO try catch
        if (!currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().isEmpty()) {
            throw new GameException("Error: The tile you want to plant on is not empty.");
        }

        checkAllowedVeggies(veggieToPlant, indexToPlantOn); //TODO try catch
        checkVeggiesInBarn(veggieToPlant); //TODO try catch

        switch(veggieToPlant) {
            case (MUSHROOM) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Mushroom());
            case (CARROT) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Carrot());
            case (SALAD) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Salad());
            case (TOMATO) -> currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().add(new Tomato());
        }

        for (int i = 0; i < currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size(); i++) {
            if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().
                    get(i).getName().equals(veggieToPlant)) {
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().remove(i);
                break;
            }
        }
        resetCountdown();
        movesInTurn++;
        tilesInCountdown.add(currentPlayer.getBoardGame().get(indexToPlantOn));
    }

    /**
     * sells all the vegetables the player want to. He can say sell all (whole barn) or concrets vegetables from barn
     * @param input input that matches to the sell action in the game
     * @return the String with the amount of sold vegetable and the gold prize a player gets for the vegetables
     */

    public String sellVegetables(String[] input) { //TODO was tun wenn nur ein Teil der veggies vorhanden ist in Barn
        int goldBeforeMove = currentPlayer.getGold();
        int soldVeggies = 0;
        if (input.length == 0) {
            return Messages.SELL_VEGETABLES.format(0, VegetablesOccurence.VEGETABLES.format(), 0);
        } else if (input[0].equals("all")) {
            for (int i = currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() - 1; i >= 0; i--) {
                sellOneVegetable(i);
                soldVeggies++;
            }
        } else {
            for (int i = 0; i < input.length; i++) {
                for (int j = currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() - 1; j >= 0; j--) {
                    if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().
                            get(j).getName().equals(input[i])) {
                        sellOneVegetable(j);
                        soldVeggies++;
                    }
                }
            }
        }
        int newGold = currentPlayer.getGold() - goldBeforeMove;
        resetCountdown();
        movesInTurn++;
        if (soldVeggies == 1) {
            return Messages.SELL_VEGETABLES.format(soldVeggies, VegetablesOccurence.VEGETABLE.format(), newGold);
        }
        return Messages.SELL_VEGETABLES.format(soldVeggies, VegetablesOccurence.VEGETABLES.format(), newGold);
    }

    /**
     * calculates the winner if the game ended (quit or the gold to win is reached)
     * @return the String to show the end of the game
     */

    public String endGame() {
        int maxGold = 0;
        List<String> winnerList = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = playerList.get(i).getName();
            int finalGold = playerList.get(i).getGold();
            stringBuilder.append(Messages.GOLD_AFTER_GAME.format(i + 1, playerName, finalGold))
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
            for (int i = 0; i < winnerList.size() - 1; i++) {
                winners += winnerList.get(i).concat(", ");
            }
            winners = winners.substring(0, winners.length() - 2);
            stringBuilder.append(winners);
            stringBuilder.append(Messages.MANY_PLAYER_WON.format(winnerList.get(winnerList.size() - 1)));
        }

        String winnerPrint = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return winnerPrint;
    }

    /**
     * introduce a new turn of the next player with its name and shows if vegetables have spoiled or grown
     * @return the string to show who's turn is it and what happened during the last round
     */

    public String startTurn() {
        gameState = GameState.START_TURN;
        stringBuilder.append(Shell.LINE_SEPARATOR);
        stringBuilder.append(Messages.TURN_OF_PLAYER.format(currentPlayer.getName())).append(Shell.LINE_SEPARATOR);
        if (currentPlayer.getGrownVegetables() == 1) {
            stringBuilder.append(Messages.GROWN_VEGETABLE.format());
        } else if (currentPlayer.getGrownVegetables() != Player.NO_GROWN_VEGETABLES)
            stringBuilder.append(Messages.GROWN_VEGETABLES.format(currentPlayer.getGrownVegetables()));
        if (VEGETABLE_SPOIL - currentPlayer.getBoardGame().get(BARN_INDEX).getCountdown() == 0) {
            stringBuilder.append(Messages.SPOILED_VEGETABLES.format());
        }
        String startTurnPrint = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return startTurnPrint;

    }

    /**
     * starts the new turn of the next player so the next player will be the "current player"
     * if it was the turn of the last player, it introduces a new Round
     * changes the prizes of the markt and set the sold veggies for the next player to default
     */
    public void nextTurn() {
        mcMarket.changePrices(soldMushrooms, soldCarrots); //TODO nochmal checken müsste aber so passen
        tsMarket.changePrices(soldTomato, soldSalad); //TODO hier auch wird nach jedem Zug ja zu default 0
        setSoldVeggiesToDefault();
        currentPlayer.setGrownVegetables(Player.NO_GROWN_VEGETABLES);
        if (playerList.indexOf(currentPlayer) == playerList.size() - INDEX_OF_NEXT_PLAYER) {
            currentPlayer = playerList.get(START_PLAYER);
            nextRound();
        } else {
            currentPlayer = playerList.get(playerList.indexOf(currentPlayer) + INDEX_OF_NEXT_PLAYER);
        }
        movesInTurn = 0;
        gameState = gameState.START_TURN;


    }

    /**
     * introduces a new Round in the game and has consequences for all players
     * if the countdown of a barn is full, all vegetables in the barn will be deleted
     * if the countdown on a tile if full, the vegetables will be duplicated and if then the capacity of the tile
     * is full, the countdown of the tile will be deleted.
     */

    private void nextRound() {
        int sizeOfTilesInCountdown = tilesInCountdown.size();
        for (int j = sizeOfTilesInCountdown - 1; j >= 0; j--) {
            Tile tile = tilesInCountdown.get(j);
            tile.setCountdown(tile.getCountdown() + 1);
            if (tile.getVegetablesList().get(0).getTimeToGrow() - tile.getCountdown() == 0) { //Zeit zum Wachsen
                int currentAmountOfVeggies = tile.getVegetablesList().size();
                for (int i = 0; i < currentAmountOfVeggies; i++) { //Gemüse verdoppelt sich
                    if (tile.getVegetablesList().size() < tile.getCapacity()) {
                        switch (tile.getVegetablesList().get(0).getName()) {
                            case (MUSHROOM) -> tile.getVegetablesList().add(new Mushroom());
                            case (TOMATO) -> tile.getVegetablesList().add(new Tomato());
                            case (SALAD) -> tile.getVegetablesList().add(new Salad());
                            case (CARROT) -> tile.getVegetablesList().add(new Carrot());
                        }
                        for (Player player : playerList) {
                            if (player.getBoardGame().contains(tile)) {
                                player.setGrownVegetables(player.getGrownVegetables() + 1);
                            }
                        }
                    }
                }
                if (tile.getVegetablesList().size() == tile.getCapacity()) {
                    tilesInCountdown.remove(tile); //remove tile from countdown list if tile is full
                }
            }
        }

        for (Player player : playerList) {
            if (!player.getBoardGame().get(BARN_INDEX).getVegetablesList().isEmpty()) {
                player.getBoardGame().get(BARN_INDEX).setCountdown(player.getBoardGame()
                        .get(BARN_INDEX).getCountdown() + 1);
            } if (player.getBoardGame().get(BARN_INDEX).getCountdown() == VEGETABLE_SPOIL) {
                currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().clear();
                resetCountdown();
            }
            if (player.getGold() >= goldToWin) {
                executionState = ExecutionState.EXITED;
            }
        }
        // TODO state pattern einbauen weil Spiel dann zu Ende
    }

    /**
     * resets the countdown of the current player if the barn of the player is empty
     */
    public void resetCountdown() {
        if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().isEmpty()) {
            currentPlayer.getBoardGame().get(BARN_INDEX).setCountdown(Tile.COUNTDOWN_START);
        }
    }

    public int getMovesInTurn() {
        return movesInTurn;
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
            case(MUSHROOM) -> {
                soldMushrooms++;
                currentPlayer.setGold(currentPlayer.getGold() + mcMarket.getCurrentMushroomPrice());
            }
            case(CARROT) -> {
                soldCarrots++;
                currentPlayer.setGold(currentPlayer.getGold() + mcMarket.getCurrentCarrotPrice());
            }
            case(SALAD) -> {
                soldSalad++;
                currentPlayer.setGold(currentPlayer.getGold() + tsMarket.getCurrentSalatPrice());
            }
            case(TOMATO) -> {
                soldTomato++;
                currentPlayer.setGold(currentPlayer.getGold() + tsMarket.getCurrentTomatoPrice());
            }
        }
        currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().remove(indexOfVegetableList);
    }

    private void isVegetableBuyAllowed(int prize) {
        if (currentPlayer.getGold() - prize < 0) {
            throw new GameException(("Error: you don't have enough gold to buy the vegetable."));
        }
    }

    /**
     * gets the index of the gameboard list where the give tile should be loacted
     * @param xCoordinate x-Coordinate where the player wants to do something on the game board
     * @param yCoordinate y-Coordinate where the player wants to do something on the game board
     * @return index of the gameboard list where the tile is located
     */
    private int getBoardGameIndexFromCoordinates(int xCoordinate, int yCoordinate) {
        for (int i = 0; i < currentPlayer.getBoardGame().size(); i++) {
            if (currentPlayer.getBoardGame().get(i).getCoordinates().getxCoordinate() == xCoordinate
                    && currentPlayer.getBoardGame().get(i).getCoordinates().getyCoordinate() == yCoordinate) {
                return i;
            }
        }
        return AREA_IS_NOT_BOUGHT;
    }

    /**
     * checks if the given vegetable is allowed to plant on the give filed
     *
     * @param vegetable the vegetable a player wants to plant
     * @param index index that represents the tile on the gameboard
     */
    private void checkAllowedVeggies(String vegetable, int index) {
        boolean vegetableIsAllowed = false;
        for (int i = 0; i < currentPlayer.getBoardGame().get(index).getAllowedVegetables().size(); i++) {
            if (currentPlayer.getBoardGame().get(index).getAllowedVegetables().get(i).getName().equals(vegetable)) {
                vegetableIsAllowed = true;
                break;
            }
        }
        if (!vegetableIsAllowed) {
            throw new GameException(ExceptionMessages.UNALLOWED_VEGETABLE.format(vegetable));
        }
    }

    /**
     * checks if the given vegetable is in barn
     * @param vegetable the given vegetable to check if its in barn
     */
    private void checkVeggiesInBarn(String vegetable) {
        boolean vegetableExistsInBarn = false;
        for (int i = 0; i < currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size(); i++) {
            if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().get(i).getName().equals(vegetable)) {
                vegetableExistsInBarn = true;
                break;
            }
        }
        if (!vegetableExistsInBarn) {
            throw new GameException(ExceptionMessages.VEGETABLE_NOT_IN_BARN.format(vegetable));
        }
    }

    /**
     * checks if the given tile is bought if not @throws exception
     * @param xCoordinate xCoordinate of the given tile
     * @param yCoordinate yCoordinate of the given tile
     * @return the index of the given tile on the game board
     */
    private int isTileBought(int xCoordinate, int yCoordinate) {
        int indexToPlantOn = AREA_IS_NOT_BOUGHT;
        if (getBoardGameIndexFromCoordinates(xCoordinate, yCoordinate) != AREA_IS_NOT_BOUGHT) {
            indexToPlantOn = getBoardGameIndexFromCoordinates(xCoordinate, yCoordinate);
        } else {
            throw new GameException(ExceptionMessages.TILE_NOT_ON_BOARD.format());
        }
        return indexToPlantOn;
    }

    private void checksIllegalBarnMove(int xCoordinate, int yCoordinate) {
        if (xCoordinate == BARN_INDEX && yCoordinate == BARN_INDEX) {
            throw new GameException(ExceptionMessages.ILLEGAL_PLANT_ON_BARN.format());
        }
    }
}
