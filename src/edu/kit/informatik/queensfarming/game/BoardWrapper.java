package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.Player;
import edu.kit.informatik.queensfarming.entity.tiles.Tile;
import edu.kit.informatik.queensfarming.entity.vegetables.*;
import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.userinterface.ExceptionMessages;
import edu.kit.informatik.queensfarming.userinterface.Messages;
import edu.kit.informatik.queensfarming.userinterface.Shell;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a wrapper class for the queens farm game where a player harvest and plant
 * and the result of the game will be calculated here
 *
 * @author uyxib
 * @version 1.0
 */
public class BoardWrapper {

    private static final int AREA_IS_NOT_BOUGHT = -1;
    private static final int BARN_INDEX = 0;
    private static final String DIVIDE_PLAYERS = ", ";
    private static final String MUSHROOM = "mushroom";
    private static final String CARROT = "carrot";
    private static final String SALAD = "salad";
    private static final String TOMATO = "tomato";
    private StringBuilder stringBuilder;

    /**
     * instantiates a new Board Wrapper object
     */
    public BoardWrapper() {
        this.stringBuilder = new StringBuilder();
    }

    /**
     * harvest the given tile on the game board of the current player and adds them to the barn
     * start the countdown if the tile was full but is now not empty, stopps the countdown if the tile is empty now
     * @throws GameException the player wants to harvest on the barn, or if he tries to harvest more vegetables
     * than on the field or if he tries to harvest on a field that doesn't exist on his board or if the tile is empty.
     * @param input the given input command to harvest a tile
     * @param currentPlayer the current Player that is harvesting in this moment
     * @return the string how many vegetables where successfully harvested and which vegetable exactly
     */

    public String harvest(int[] input, Player currentPlayer) {
        int xCoordinate = input[0];
        int yCoordinate = input[1];
        int numberOfVeggies = input[2];
        int indexToHarvestOn = isTileBought(currentPlayer, xCoordinate, yCoordinate);
        Tile currentTile = currentPlayer.getBoardGame().get(indexToHarvestOn);
        if (currentPlayer.getBoardGame().get(indexToHarvestOn).getVegetablesList().size() < numberOfVeggies) {
            throw new GameException(ExceptionMessages.TOO_MUCH_HARVESTING.format());
        }
        if (numberOfVeggies == 0) {
            throw new GameException(ExceptionMessages.HARVEST_ZERO_VEGETABLES.format());
        }
        String veggieName = currentTile.getVegetablesList().get(0).getName();
        checksIllegalBarnMove(xCoordinate, yCoordinate);
        for (int i = 1; i <= numberOfVeggies; i++) {
            switch(veggieName) {
                case(MUSHROOM) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Mushroom());
                case(TOMATO) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Tomato());
                case(SALAD) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Salad());
                case(CARROT) -> currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().add(new Carrot());
            }
            currentTile.getVegetablesList().remove(numberOfVeggies - i);
        }
        startsCountdown(currentPlayer, numberOfVeggies);
        if (numberOfVeggies == 1) {
            return Messages.HARVESTED_LAND.format(numberOfVeggies, veggieName).concat(Shell.LINE_SEPARATOR);
        }
        switch(veggieName) {
            case(MUSHROOM) -> veggieName = Vegetable.MUSHROOM.format();
            case(TOMATO) -> veggieName = Vegetable.TOMATO.format();
            case(SALAD) -> veggieName = Vegetable.SALAT.format();
            case(CARROT) -> veggieName = Vegetable.CARROT.format();
        }
        return Messages.HARVESTED_LAND.format(numberOfVeggies, veggieName).concat(Shell.LINE_SEPARATOR);
    }

    /**
     * gets the the amount of vegetables that will be harvested
     * @param input input command of the player
     * @return the amount of vegetables that will be harvested
     */
    public int getNumberOfHarvestVeggies(int[] input) {
        return input[2];
    }

    /**
     *
     * @param input the input command of the player
     * @param currentPlayer currentPlayer who wants to do an action on the current tile
     * @return the current Tile
     */
    public Tile getCurrentTile(int[] input, Player currentPlayer) {
        int xCoordinate = input[0];
        int yCoordinate = input[1];
        int indexToHarvestOn = isTileBought(currentPlayer, xCoordinate, yCoordinate);
        return currentPlayer.getBoardGame().get(indexToHarvestOn);
    }

    /**
     * plant 1 vegetable that the player want to plant on a specific field
     * @throws GameException if area of land was not bought before, or if the tile to plant is not empty,
     * or if the given vegetable can not be planted on the tile, or if the veggie to plant doesn't exist in the barn
     *
     * @param input the give input from the player
     * @param currentPlayer the current player that is planting
     * @return the index where the vegetable is planted on the board game
     */
    public int plant(String[] input, Player currentPlayer) {
        int xCoordinate = Integer.parseInt(input[0]);
        int yCoordinate = Integer.parseInt(input[1]);
        int indexToPlantOn = isTileBought(currentPlayer, xCoordinate, yCoordinate);
        String veggieToPlant = input[2];
        checksIllegalBarnMove(xCoordinate, yCoordinate);
        if (!currentPlayer.getBoardGame().get(indexToPlantOn).getVegetablesList().isEmpty()) {
            throw new GameException("Error: The tile you want to plant on is not empty.");
        }

        checkAllowedVeggies(currentPlayer, veggieToPlant, indexToPlantOn);
        checkVeggiesInBarn(currentPlayer, veggieToPlant);

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
        currentPlayer.getBoardGame().get(indexToPlantOn).setCountdown(Tile.COUNTDOWN_START);
        resetCountdown(currentPlayer);
        return indexToPlantOn;
    }

    /**
     * gets the index of the game board list where the give tile should be located
     * @param xCoordinate x-Coordinate where the player wants to do something on the game board
     * @param yCoordinate y-Coordinate where the player wants to do something on the game board
     * @return index of the game board list where the tile is located
     */
    private int getBoardGameIndexFromCoordinates(Player currentPlayer, int xCoordinate, int yCoordinate) {
        for (int i = 0; i < currentPlayer.getBoardGame().size(); i++) {
            if (currentPlayer.getBoardGame().get(i).getCoordinates().getxCoordinate() == xCoordinate
                    && currentPlayer.getBoardGame().get(i).getCoordinates().getyCoordinate() == yCoordinate) {
                return i;
            }
        }
        return AREA_IS_NOT_BOUGHT;
    }

    /**
     * checks if the given tile is bought if not @throws exception
     * @param xCoordinate xCoordinate of the given tile
     * @param yCoordinate yCoordinate of the given tile
     * @param currentPlayer the current Player that is playing in this moment
     * @return the index of the given tile on the game board
     */
    public int isTileBought(Player currentPlayer, int xCoordinate, int yCoordinate) {
        int indexToPlantOn = AREA_IS_NOT_BOUGHT;
        if (getBoardGameIndexFromCoordinates(currentPlayer, xCoordinate, yCoordinate) != AREA_IS_NOT_BOUGHT) {
            indexToPlantOn = getBoardGameIndexFromCoordinates(currentPlayer, xCoordinate, yCoordinate);
        } else {
            throw new GameException(ExceptionMessages.TILE_NOT_ON_BOARD.format());
        }
        return indexToPlantOn;
    }

    /**
     * calculates the winner if the game ended (quit or the gold to win is reached)
     * @param playerList the list of players in the Queens Farm game
     * @param numberOfPlayers the number of players in the game
     * @param goldToWin the amount of gold a player needs to win the game
     * @return the String to show the end of the game (different format if 1, 2 or more than 2 players won)
     */

    public String endGame(List<Player> playerList, int numberOfPlayers, int goldToWin) {
        int maxGold = 0;
        List<String> playerReachedGoldToWin = new ArrayList<>();
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
            if (finalGold >= goldToWin) {
                playerReachedGoldToWin.add(playerName);
            }
        }
        if (playerReachedGoldToWin.size() > 0) {
            stringBuilder.append(createsWinnerOutput(playerReachedGoldToWin));
        } else {
            stringBuilder.append(createsWinnerOutput(winnerList));
        }
        String winnerPrint = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return winnerPrint;
    }

    /**
     * creates the last output line at the end of a queens farm game
     * @param winnerList the list with all players who have won the game
     * @return the formatted string with the names of all players who have won the game
     */
    private String createsWinnerOutput(List<String> winnerList) {
        if (winnerList.size() == 1) {
             return Messages.PLAYER_WON.format(winnerList.get(0));
        } else if (winnerList.size() == 2) {
            return Messages.TWO_PLAYER_WON.format(winnerList.get(0), winnerList.get(1));
        }
        String winners = Shell.EMPTY_STRING;
        for (int i = 0; i < winnerList.size() - 1; i++) {
            winners += winnerList.get(i).concat(DIVIDE_PLAYERS);
        }
        winners = winners.substring(0, winners.length() - 2);
        return winners.concat(Messages.MANY_PLAYER_WON.format(winnerList.get(winnerList.size() - 1)));
    }

    /**
     * checks if a player tries to plant or harvest on the barn tile. The barn tile is only for storing the vegetables.
     * @param xCoordinate the x-Coordinate of the tile where the player wants to do an action
     * @param yCoordinate the y-Coordinate of the tile where the player wants to do an action
     */
    private void checksIllegalBarnMove(int xCoordinate, int yCoordinate) {
        if (xCoordinate == BARN_INDEX && yCoordinate == BARN_INDEX) {
            throw new GameException(ExceptionMessages.ILLEGAL_PLANT_ON_BARN.format());
        }
    }

    /**
     * checks if the given vegetable is allowed to plant on the give filed
     *
     * @param vegetable the vegetable a player wants to plant
     * @param index index that represents the tile on the gameboard
     */
    private void checkAllowedVeggies(Player currentPlayer, String vegetable, int index) {
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
    private void checkVeggiesInBarn(Player currentPlayer, String vegetable) {
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
     * resets the countdown of the current player if the barn of the player is empty
     * @param currentPlayer the player whos turn it is currently
     */
    public void resetCountdown(Player currentPlayer) {
        if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().isEmpty()) {
            currentPlayer.getBoardGame().get(BARN_INDEX).setCountdown(Tile.NO_COUNTDOWN);
        }
    }

    /**
     * starts the countdown of the current player if the barn was empty and is null not empty anymore
     * @param currentPlayer the player whos turn it is currently
     */
    public void startsCountdown(Player currentPlayer, int numberOfNewVegetables) {
        if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() == numberOfNewVegetables) {
            currentPlayer.getBoardGame().get(BARN_INDEX).setCountdown(Tile.COUNTDOWN_START);
        }
    }
}
