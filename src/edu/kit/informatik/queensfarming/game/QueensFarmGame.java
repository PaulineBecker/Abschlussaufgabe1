package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.exception.GameException;

import java.util.List;

/**
 * Encapsulates a Queens Farm game with different commands.
 * Copied from Thomas Weber and Moritz Gstuer and then modified from
 * @author uyxib
 * @version 1.0
 */
public interface QueensFarmGame extends Executable {

    /**
     * Plants a new vegetable on the given tile
     * @throws GameException if it's not possible to plant on the tile because of game logic reasons
     * @param input the input split string of the command from a player
     */
    void plant(String[] input);

    /**
     * Harvests vegetables on a tile of the game board
     * @throws GameException if it's not possible to harvest on the tile because there's no vegetable to harvest,
     * the player is trying to harvest more vegetables on the field or the tile where he/she tries to harvest is not
     * part of his game board
     * @param input the input of the command from a player split in an int array
     * @return string representation of a successful harvesting move
     */
    String harvest(int[] input);

    /**
     * Sell vegetables to the market. The vegetables will be removed from the players assets
     * @param input the input split string of the command from a player
     * @return string representation of a successful sell vegetables move with the prize of the vegetable and its name
     */
    String sellVegetables(String[] input);

    /**
     * Harvests vegetables on a tile of the game board
     * @throws GameException if it's not possible to buy a vegetable because the player has not enough gold
     * @param input the input string of the command from a player
     * @return string representation of a successful sell vegetables move with the amount of sold vegetables and
     * the earned money
     */
    String buyVegetable(String input);

    /**
     * Buys one vegetable from the market of the game
     * @throws GameException if it's not possible to buy a land tile because the player has not enough gold,
     * the tile is already bought, the tile is not adjacent to the other already bought tiles or the tile is
     * already bought
     * @param coordinates the coordinates from the command of the player where to buy land
     * @return string representation of a successful buy land move with the prize of the tile and the type of tile
     */
    String buyLand(List<Integer> coordinates);

    /**
     * Return the String representation of the barn
     * @return string representation of the barn of a player
     */
    String showBarn();

    /**
     * Return the String representation of the board of a player
     * @return string representation of the barn of a player
     */
    String showBoard();

    /**
     * Return the String representation of the board of a player
     * @return string representation of the market with its prizes and the names of the offered vegetables
     */
    String showMarket();




}
