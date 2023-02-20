package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.Player;
import edu.kit.informatik.queensfarming.entity.tiles.Tile;
import edu.kit.informatik.queensfarming.entity.vegetables.*;
import edu.kit.informatik.queensfarming.exception.GameException;
import edu.kit.informatik.queensfarming.userinterface.ExceptionMessages;
import edu.kit.informatik.queensfarming.userinterface.Messages;
import edu.kit.informatik.queensfarming.userinterface.Shell;

/**
 * this is the place where a player harvest and plant
 *
 * @author uyxib
 * @version 1.0
 */
public class BoardWrapper {
    /**
     * the start of a countdown
     */
    public static final int COUNTDOWN_START = 0;
    private static final int AREA_IS_NOT_BOUGHT = -1;
    private static final int BARN_INDEX = 0;
    private static final String MUSHROOM = "mushroom";
    private static final String CARROT = "carrot";
    private static final String SALAD = "salad";
    private static final String TOMATO = "tomato";

    public BoardWrapper() {}

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
        startsCountdown(currentPlayer);
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
        currentPlayer.getBoardGame().get(indexToPlantOn).setCountdown(COUNTDOWN_START);
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
     */
    public void resetCountdown(Player currentPlayer) {
        if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().isEmpty()) {
            currentPlayer.getBoardGame().get(BARN_INDEX).setCountdown(Tile.NO_COUNTDOWN);
        }
    }

    /**
     * starts the countdown of the current player if the barn was empty and is null not empty anymore
     */
    public void startsCountdown(Player currentPlayer) {
        if (currentPlayer.getBoardGame().get(BARN_INDEX).getVegetablesList().size() == 1) {
            currentPlayer.getBoardGame().get(BARN_INDEX).setCountdown(BoardWrapper.COUNTDOWN_START);
        }
    }
}
