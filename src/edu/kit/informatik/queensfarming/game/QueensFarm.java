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
import edu.kit.informatik.queensfarming.entity.vegetables.*;
import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.userinterface.ExceptionMessages;
import edu.kit.informatik.queensfarming.userinterface.GameState;
import edu.kit.informatik.queensfarming.userinterface.ExecutionState;
import edu.kit.informatik.queensfarming.userinterface.Shell;
import edu.kit.informatik.queensfarming.userinterface.Messages;
import edu.kit.informatik.queensfarming.userinterface.Commands;
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
public class QueensFarm implements QueensFarmGame {
    private static final int SIZE_OF_PRICETABLE = 4;
    private static final int MULTIPLIER_G_LFI_FO = 2;
    private static final int MULTIPLIER_FIELD = 3;
    private static final int START_COORDINATES = -1;
    private static final int AREA_IS_NOT_BOUGHT = -1;
    private static final int START_PLAYER = 0;
    private static final int BARN_INDEX = 0;
    private static final int INDEX_OF_NEXT_PLAYER = 1;
    private static final int DISTANCE = 1;
    private static final String MUSHROOM = "mushroom";
    private static final String CARROT = "carrot";
    private static final String SALAD = "salad";
    private static final String TOMATO = "tomato";
    private ExecutionState executionState;
    private GameState gameState;
    private int movesInTurn = 0;
    private final int goldToWin;
    private final int goldToStart;
    private final int numberOfPlayers;
    private final long seedToShuffle;
    private int soldCarrots = 0;
    private int soldMushrooms = 0;
    private int soldSalad = 0;
    private int soldTomato = 0;
    private final TSMarket tsMarket = new TSMarket();
    private final MCMarket mcMarket = new MCMarket();
    private Player currentPlayer;
    private final StringBuilder stringBuilder = new StringBuilder();

    private final List<Player> playerList = new ArrayList<>();
    private final List<Tile> tilesInCountdown = new ArrayList<>();
    private final List<String> nameList;
    private final List<Tile> unassignedTiles = new ArrayList<>();
    private final BoardWrapper boardWrapper = new BoardWrapper();

    /**
     * instantiates a new Queens Farm Game with the start gold, gold to win, number of players, player names and seed
     * @param goldToWin gold that is necessary to win the game
     * @param goldToStart the amount of gold every player has at the beginning of the game
     * @param playerNumber the number of players that are currently playing
     * @param seedToShuffle the seed to shuffle the tiles before the game starts
     * @param playerNames the names of the players in one list
     */

    public QueensFarm(int goldToWin, int goldToStart, int playerNumber,
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
    }

    /**
     * Returns whether or not this structure is active.
     * @return whether or not this structure is active
     */
    @Override public boolean isActive() {
        return executionState == ExecutionState.RUNNING;
    }

    /**
     * Quits this structure so {@link #isActive()} will return {@code false}.
     */
    @Override public void quit() {
        executionState = ExecutionState.EXITED;
    }

    /**
     * shows the market with all the different prizes of the vegetables
     * @return String to visualize the market with the prizes of vegetables
     */
    @Override public String showMarket() {
        return mcMarket.marketToString(getFinalPrizeTable());
    }

    /**
     * shows the barn of the current Player with all the vegetables in it, the sum and the amount of gold
     * @return the string to visualize the barn
     */
    @Override public String showBarn() {
        return currentPlayer.barnToString();
    }

    /**
     * Returns the represantation of the current Board of a player with all the tiles on it.
     * Every tile shows its vegetable, capacity and countdown
     * @return the current game board of a player
     */
    @Override public String showBoard() {
        return currentPlayer.boardToString();
    }

    /**
     * current Player buys 1 Vegetables with the current markt prize
     * @param input inout that matches to the buy action in the game
     * @return the String with the prize for the bought vegetable and the name of the vegetable
     */
    @Override public String buyVegetable(String input) {
        int price = 0;
        String veggie = Shell.COMMAND_SEPERATOR;
        switch(input) {
            case (MUSHROOM) -> {
                price = mcMarket.getCurrentMushroomPrice();
                isVegetableBuyAllowed(price);
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
        boardWrapper.startsCountdown(currentPlayer, 1);
        return Messages.BOUGHT_OBJECT.format(veggie, price).concat(Shell.LINE_SEPARATOR);
    }

    /**
     * the current player buys land. Gets the coordinates where the new land should be on the game board of the player
     * calculates the prize of the land
     * the players gets the first tile of the shuffled cards and has to pay the prize for gold
     * @throws GameException if player doesn't have enough gold, if the place on the game board is already used or
     * if there are no cards to buy left, if the player tries to build from top to botton
     * @param coordinates input from player that matches the buy command
     * @return the prize of the land and which tile the player has bought
     */
    @Override public String buyLand(List<Integer> coordinates) {
        int xCoordinate = coordinates.get(0);
        int yCoordinate = coordinates.get(1);
        boolean buyable = false;
        if (unassignedTiles.size() == 0) {
            throw new GameException(ExceptionMessages.ALL_TILES_USED.format());
        }
        Tile boughtTile = unassignedTiles.get(0);
        for (Tile tile : currentPlayer.getBoardGame()) {
            if (tile.getCoordinates().compareTo(new Coordinates(xCoordinate, yCoordinate)) == DISTANCE
            && tile.getCoordinates().getyCoordinate() - yCoordinate != DISTANCE) {
                buyable = true;
            }
        }
        if (!buyable) {
            throw new GameException(ExceptionMessages.NOT_ADJACENT_LAND.format());
        }
        int prize = 10 * (Math.abs(xCoordinate) + Math.abs(yCoordinate) - 1);
        if (currentPlayer.getGold() - prize < 0) {
            throw new GameException(ExceptionMessages.LAND_TO_EXPENSIVE.format());
        }
        if (getBoardGameIndexFromCoordinates(xCoordinate, yCoordinate) != AREA_IS_NOT_BOUGHT) {
            throw new GameException(ExceptionMessages.TILE_BOUGHT.format());
        }
        boughtTile.setCoordinates(new Coordinates(xCoordinate, yCoordinate));
        unassignedTiles.remove(0);
        currentPlayer.getBoardGame().add(boughtTile);
        currentPlayer.setGold(currentPlayer.getGold() - prize);
        movesInTurn++;
        return Messages.BOUGHT_OBJECT.format(boughtTile.getName(), prize).concat(Shell.LINE_SEPARATOR);
    }

    /**
     * harvest the given tile on the gameboard of the current player and adds them to the barn
     * start the countdown if the tile was full but is now not empty, stopps the countdown if the tile is empty now
     * @throws GameException the player wants to harvest on the barn, or if he tries to harvest more vegetables
     * than on the field or if he tries to harvest on a field that doesn't exist on his board or if the tile is empty.
     * @param input the given input command to harvest a tile
     * @return the string how many vegetables where successfully harvested and which vegetable exactly
     */
    @Override public String harvest(int[] input) {
        String harvestOutput = boardWrapper.harvest(input, currentPlayer);
        if (boardWrapper.getCurrentTile(input, currentPlayer).getVegetablesList().size() == 0) {
            tilesInCountdown.remove(boardWrapper.getCurrentTile(input, currentPlayer));
            boardWrapper.getCurrentTile(input, currentPlayer).setCountdown(Tile.NO_COUNTDOWN);
        } else if ((boardWrapper.getCurrentTile(input, currentPlayer).getVegetablesList().size()
                + boardWrapper.getNumberOfHarvestVeggies(input))
                == boardWrapper.getCurrentTile(input, currentPlayer).getCapacity()) {
            tilesInCountdown.add(boardWrapper.getCurrentTile(input, currentPlayer));
            boardWrapper.getCurrentTile(input, currentPlayer).setCountdown(Tile.COUNTDOWN_START);
        }
        movesInTurn++;
        return harvestOutput;
    }

    /**
     * plant 1 vegetable that the player want to plant on a specific field
     * @throws GameException if area of land was not bought before, or if the tile to plant is not empty,
     * or if the given vegetable can not be planted on the tile, or if the veggie to plant doesn't exist in the barn
     * @param input the give input from the player
     */
    @Override public void plant(String[] input) {
        tilesInCountdown.add(currentPlayer.getBoardGame().get(boardWrapper.plant(input, currentPlayer)));
        movesInTurn++;
    }

    /**
     * sells all the vegetables the player want to. He can say sell all (whole barn) or concrets vegetables from barn
     * @param input input that matches to the sell action in the game
     * @return the String with the amount of sold vegetable and the gold prize a player gets for the vegetables
     */
    @Override public String sellVegetables(String[] input) {
        int goldBeforeMove = currentPlayer.getGold();
        int soldVeggies = 0;
        if (input.length == 0) {
            movesInTurn++;
            return Messages.SELL_VEGETABLES.format(0, VegetablesOccurence.VEGETABLES.format(), 0)
                    .concat(Shell.LINE_SEPARATOR);
        } else if (input[0].equals(Commands.ALL_VEGETABLES)) {
            for (int i = currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size()- 1; i >= 0; i--) {
                sellOneVegetable(i);
                soldVeggies++;
            }
        } else {
            currentPlayer.checkVegetablesInBarn(input);
            for (String vegetable : input) {
                for (int j = currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() - 1; j >= 0; j--) {
                    if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().
                            get(j).getName().equals(vegetable)) {
                        sellOneVegetable(j);
                        soldVeggies++;
                        break;
                    }
                }
            }
        }
        int newGold = currentPlayer.getGold() - goldBeforeMove;
        boardWrapper.resetCountdown(currentPlayer);
        movesInTurn++;
        if (soldVeggies == 1) {
            return Messages.SELL_VEGETABLES.format(soldVeggies, VegetablesOccurence.VEGETABLE.format(), newGold)
                    .concat(Shell.LINE_SEPARATOR);
        }
        return Messages.SELL_VEGETABLES.format(soldVeggies, VegetablesOccurence.VEGETABLES.format(), newGold)
                .concat(Shell.LINE_SEPARATOR);
    }

    /**
     * End a turn so {@link #playerIsTurning()} will return {@code false}.
     */
    public void endTurn() {
        gameState = GameState.END_TURN;
    }

    /**
     * Returns whether or not player is turning.
     * @return whether or not player is turning.
     */
    public boolean playerIsTurning() { return gameState == GameState.START_TURN; }

    /**
     * calculates the winner if the game ended (quit or the gold to win is reached)
     * @return the String to show the end of the game (different format if 1, 2 or more than 2 players won)
     */
    public String endGame() {
        return boardWrapper.endGame(playerList, numberOfPlayers, goldToWin);
    }

    /**
     * introduce a new turn of the next player with its name and shows if vegetables have spoiled or grown
     * @return the string to show whos turn is it and what happened during the last round
     */
    public String startTurn() {
        gameState = GameState.START_TURN;
        stringBuilder.append(Shell.LINE_SEPARATOR);
        stringBuilder.append(Messages.TURN_OF_PLAYER.format(currentPlayer.getName()));
        if (currentPlayer.getGrownVegetables() == 1) {
            stringBuilder.append(Shell.LINE_SEPARATOR).append(Messages.GROWN_VEGETABLE.format());
        } else if (currentPlayer.getGrownVegetables() != Player.NO_GROWN_VEGETABLES) {
            stringBuilder.append(Shell.LINE_SEPARATOR).
                    append(Messages.GROWN_VEGETABLES.format(currentPlayer.getGrownVegetables()));
        } if (currentPlayer.isBarnSpoiled()) {
            stringBuilder.append(Shell.LINE_SEPARATOR).append(Messages.SPOILED_VEGETABLES.format());
            currentPlayer.setBarnSpoiled(false);
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
            if (tile.getVegetablesList().get(0).getTimeToGrow() - tile.getCountdown() == 0) {
                tile.setCountdown(Tile.COUNTDOWN_START);
                int currentAmountOfVeggies = tile.getVegetablesList().size();
                for (int i = 0; i < currentAmountOfVeggies; i++) {
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
                    tilesInCountdown.remove(tile);
                    tile.setCountdown(Tile.NO_COUNTDOWN);
                }
            }
        }

        for (Player player : playerList) {
            if (!player.getBoardGame().get(BARN_INDEX).getVegetablesList().isEmpty()) {
                player.getBoardGame().get(BARN_INDEX).setCountdown(player.getBoardGame()
                        .get(BARN_INDEX).getCountdown() + 1);
            } if (player.getBoardGame().get(BARN_INDEX).getCountdown() == Player.VEGETABLE_SPOIL) {
                player.getBoardGame().get(BARN_INDEX).getVegetablesList().clear();
                boardWrapper.resetCountdown(player);
                player.setBarnSpoiled(true);
            }
            if (player.getGold() >= goldToWin) {
                executionState = ExecutionState.EXITED;
            }
        }
    }

    /**
     * gets the number of moves a player has made, only 2 moves in one single turn are allowed.
     * @return the number of moves a player has made
     */
    public int getMovesInTurn() {
        return movesInTurn;
    }

    private List<PriceRatio> getFinalPrizeTable() {
        List<PriceRatio> finalPriceTable = new ArrayList<>(SIZE_OF_PRICETABLE);
        finalPriceTable.addAll(mcMarket.createPrizeTable());
        finalPriceTable.addAll(tsMarket.createPrizeTable());
        return finalPriceTable;
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
            throw new GameException((ExceptionMessages.VEGETABLE_TOO_EXPENSIVE.format()));
        }
    }

    /**
     * gets the index of the game board list where the give tile should be loacted
     * @param xCoordinate x-Coordinate where the player wants to do something on the game board
     * @param yCoordinate y-Coordinate where the player wants to do something on the game board
     * @return index of the game board list where the tile is located
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
}